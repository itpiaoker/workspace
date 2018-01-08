package cn.com.carenet.scheduler.quartz.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.com.carenet.common.web.entity.TaskInfo;
import cn.com.carenet.logging.mongo.MLogger;
import cn.com.carenet.logging.mongo.MLoggerFactory;
import cn.com.carenet.scheduler.bean.TransposeInfos;
import cn.com.carenet.scheduler.constant.DBConstant;
import cn.com.carenet.scheduler.dao.JobRunInfoDAO;
import cn.com.carenet.scheduler.dao.WorkFlowDAO;
import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.mapper.TaskInfoMapper;
import cn.com.carenet.scheduler.utils.ExecJobUtils;
import cn.com.carenet.scheduler.utils.PropertiesReader;

/**
 * 
 * @author Xueyun Lian, Sherard Lee
 * @since JULY/2017
 */
@Service
public class TransposeJob implements Job {
	final private static MLogger LOG = MLoggerFactory.getLogger(TransposeJob.class);
	protected JobParameter jobParam;
	private TransposeInfos transposeInfos;
	private String workFlowType = "工作流(Carenet® Tumbling Cloud™)";
	@Autowired
	private JobRunInfoDAO jobRunInfoDAO;
	@Autowired
	private WorkFlowDAO workFlowDAO;
	@Autowired
	private TaskInfoMapper taskInfoMapper;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobParam = (JobParameter) context.getJobDetail().getJobDataMap().get(JobParameter.JOB_PARAM);
		String transWorkFlowID = jobParam.getWorkFlowID();
		LOG.setWorkFlowID(transWorkFlowID);
		LOG.setWorkFlowType(workFlowType);
		TaskDetails taskDetails = jobRunInfoDAO.selectTasksDetails(transWorkFlowID);
		String transposeInfoStr = taskDetails.getBigBean();
		transposeInfos = JSON.parseObject(transposeInfoStr, TransposeInfos.class);
		ExecJobUtils execJobUtils = new ExecJobUtils();
		Map<Integer, List<String>> transposeMapTree = transposeInfos.getTransposeMapTree();
		workFlowDAO.updateExecState(transWorkFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXECING,
				DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
		try {
			for (int i = transposeMapTree.size() - 1; i >= 0; i--) {
				List<String> list = transposeInfos.getTransposeMapTree().get(i + "");
				List<String> waitExecWorkFlowIds = new ArrayList<>();
				for (String string : list) {
					String workFlowId = transposeInfos.getWorkFlowIdsDepend().get(string);
					waitExecWorkFlowIds.add(workFlowId);
					TaskInfo selectInfos = taskInfoMapper.selectByPrimaryKey(workFlowId);
					new Thread(new Runnable() {
						@Override
						public void run() {
							LOG.debug("Start execute job, ID:{}, task name:{}", workFlowId, selectInfos.getTaskName());
							execJobUtils.execSingleJob(workFlowId);
						}
					}, "Transpose-" + transWorkFlowID + "-" + workFlowId).start();
				}

				int retryTimes = PropertiesReader.getRetryTimes();
				while (true) {
					List<Boolean> isSuccessExec = new ArrayList<>();
					String workIDNow = "";
					for (String workFlowId : waitExecWorkFlowIds) {
						TaskInfo selectInfos = taskInfoMapper.selectByPrimaryKey(workFlowId);
						int transposeState = selectInfos.getExecState();
						if (transposeState == 5) {
							LOG.info("Work flow Success! ID:{}, task name:{}", workFlowId, selectInfos.getTaskName());
							isSuccessExec.add(true);
						} else {
							isSuccessExec.add(false);
						}
						if (transposeState == 3) {
							LOG.error("Work flow error! ID:{}, task name:{}", workFlowId, selectInfos.getTaskName());
							retryTimes--;
							new Thread(new Runnable() {
								@Override
								public void run() {
									execJobUtils.execSingleJob(workFlowId);
								}
							}, "Transpose-" + transWorkFlowID + "-" + workFlowId + "-"
									+ (PropertiesReader.getRetryTimes() - retryTimes)).start();
							workIDNow = workFlowId;
						}
					}
					if (!isSuccessExec.contains(false)) {
						break;
					}
					if (isSuccessExec.contains(false) && retryTimes < 0) {
						TaskInfo selectInfos = taskInfoMapper.selectByPrimaryKey(workIDNow);
						LOG.error("Work flow ID:{}, task name:{}, running for several times and failed.", workIDNow,
								selectInfos.getTaskName());
						workFlowDAO.updateExecState(transWorkFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXEC_FAILED,
								DBConstant.TASK_INFO_HANG_STATUS_WAIT);
						throw new RuntimeException(String.format(
								"Job failed to execute in %d times, please check your work flows!", retryTimes));
					}
					Thread.sleep(3000);
				}
			}
		} catch (Exception e) {
			workFlowDAO.updateExecState(transWorkFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXEC_FAILED,
					DBConstant.TASK_INFO_HANG_STATUS_WAIT);
			LOG.error(e.getMessage());
		}
		workFlowDAO.updateExecState(transWorkFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXEC_SUCCESS,
				DBConstant.TASK_INFO_HANG_STATUS_WAIT);
	}
}
