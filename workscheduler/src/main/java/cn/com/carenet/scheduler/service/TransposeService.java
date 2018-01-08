package cn.com.carenet.scheduler.service;

import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.carenet.common.web.entity.TaskInfo;
import cn.com.carenet.scheduler.bean.TransposeInfos;
import cn.com.carenet.scheduler.constant.DBConstant;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.dao.TransposeDAO;
import cn.com.carenet.scheduler.dao.WorkFlowDAO;
import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.json.TransposeJSONSpliter;
import cn.com.carenet.scheduler.mapper.TaskInfoMapper;
import cn.com.carenet.scheduler.quartz.QuartzManagerImpl;
import cn.com.carenet.scheduler.quartz.jobs.JobParameter;

/**
 * Title: Description:
 *
 * @author lianxy
 * @date 2017/7/3
 */

@Service
public class TransposeService {
	final private static Logger LOG = LoggerFactory.getLogger(TransposeService.class);
	@Autowired
	private QuartzManagerImpl quartzJob;
	@Autowired
	private TransposeDAO transposeDAO;
	@Autowired
	private WorkFlowDAO workFlowDAO;
	@Autowired
	private TaskInfoMapper taskInfoMapper;

	public String update(String workFlowID) {
		workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_SAVING,
				DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
		try {
			TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(workFlowID);
			Integer taskType = taskInfo.getTaskType();
			if (taskType != 1) {
				String msg = String.format("Workflow:%s is not a transpose workflow, please check for it!",
						workFlowID);
				LOG.debug(msg);
				return msg;
			}
			String json = taskInfo.getTaskInfo();
			TransposeJSONSpliter transposeJSONSpliter = new TransposeJSONSpliter(json, workFlowID);
			transposeJSONSpliter.buildTransposeMapTree();

			String cronExpression = transposeJSONSpliter.getCronExpression();
			long timestampExpression = transposeJSONSpliter.getTimestampExpression();
			boolean repeat = transposeJSONSpliter.isRepeat();
			Map<Integer, List<String>> transposeMapTree = transposeJSONSpliter.getTransposeMapTree();
			Map<String, String> workFlowIdsDepend = transposeJSONSpliter.getWorkFlowIdsDepend();

			TransposeInfos transposeInfos = new TransposeInfos();
			transposeInfos.setCronExpression(cronExpression);
			transposeInfos.setRepeat(repeat);
			transposeInfos.setTimestampExpression(timestampExpression);
			transposeInfos.setTransposeMapTree(transposeMapTree);
			transposeInfos.setWorkFlowIdsDepend(workFlowIdsDepend);
			transposeInfos.setWorkFlowID(workFlowID);

			transposeDAO.updateInfos(transposeInfos);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_SAVE_FAILED,
					DBConstant.TASK_INFO_HANG_STATUS_WAIT);
			String message = String.format("Task ID:{%s}. Failed to save the transpose task!", workFlowID);
			return message;
		}
		// update the saving status
		workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_SAVE_SUCCESS,
				DBConstant.TASK_INFO_HANG_STATUS_WAIT);
		return null;
	}

	public String delete(String workFlowID) {
		workFlowDAO.delete(workFlowID, DBConstant.TASK_INFO_DELETE_FLAG_DELETED);
		return null;
	}

	public String start(String workFlowID) {
		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(workFlowID);
		if (taskInfo != null && taskInfo.getTaskInfo() != null) {
			Integer taskType = taskInfo.getTaskType();
			if (taskType != 1) {
				String msg = String.format("Workflow:%s is not a normal workflow, please check for it!",
						workFlowID);
				LOG.debug(msg);
				return msg;
			}
		}
		TaskDetails taskDetails = workFlowDAO.selectTasksDetails(workFlowID);
		int repeatStat = taskDetails.getTaskRepeat();
		String cronExpression = taskDetails.getQuartzCron();
		long timestampExpression = taskDetails.getQuartzTime();
		JobParameter jobParam = this.flushTransposeQuatzJob(workFlowID, cronExpression, timestampExpression,
				WebModuleNameConstant.transpose);
		try {
			if (repeatStat == 1) {
				quartzJob.addCronJob(jobParam);
			} else {
				quartzJob.addNonReapeatJob(jobParam);
			}
			workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXECING,
					DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
		} catch (SchedulerException e) {
			LOG.error(e.getMessage());
			workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXEC_FAILED,
					DBConstant.TASK_INFO_HANG_STATUS_WAIT);
			String message = String.format("Work flow ID:[%s], fail to start , please check for it.", workFlowID);
			return message;
		}
		workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_SAVE_SUCCESS,
				DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
		return null;
	}

	public String stop(String workFlowID) {
		TaskDetails taskDetails = workFlowDAO.selectTasksDetails(workFlowID);
		String cronExpression = taskDetails.getQuartzCron();
		long timestampExpression = taskDetails.getQuartzTime();
		JobParameter jobParam = flushTransposeQuatzJob(workFlowID, cronExpression, timestampExpression,
				WebModuleNameConstant.transpose);
		try {
			this.closeJob(jobParam);
			workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_SAVE_SUCCESS,
					DBConstant.TASK_INFO_HANG_STATUS_WAIT);
		} catch (SchedulerException e) {
			LOG.error(e.getMessage());
			String message = String.format("Work flow ID:[%s], fail to stop , please check for it.", workFlowID);
			return message;
		}
		return null;
	}

	private JobParameter closeJob(JobParameter jobParam) throws SchedulerException {
		quartzJob.pauseJob(jobParam);
		quartzJob.removeJob(jobParam);
		return jobParam;
	}

	private JobParameter flushTransposeQuatzJob(String workFlowID, String croExpression, long timestampExpression,
			String typeName) {
		JobParameter jobParam = new JobParameter();
		jobParam.setJobClassName("cn.com.carenet.scheduler.quartz.jobs.TransposeJob");
		jobParam.setJobGroup(workFlowID);
		jobParam.setCronExpression(croExpression);
		jobParam.setTimestampExpression(timestampExpression);
		jobParam.setWorkFlowID(workFlowID);
		jobParam.setTypeName(typeName);
		jobParam.setJobName(workFlowID + typeName);
		return jobParam;
	}

}
