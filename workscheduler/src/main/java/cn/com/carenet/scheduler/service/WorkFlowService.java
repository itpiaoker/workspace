package cn.com.carenet.scheduler.service;

import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.com.carenet.common.web.entity.TaskInfo;
import cn.com.carenet.scheduler.constant.DBConstant;
import cn.com.carenet.scheduler.constant.JobStatusConstant;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.dao.WorkFlowDAO;
import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.entity.TaskMetric;
import cn.com.carenet.scheduler.mapper.TaskDetailsMapper;
import cn.com.carenet.scheduler.mapper.TaskInfoMapper;
import cn.com.carenet.scheduler.mapper.TaskMetricMapper;
import cn.com.carenet.scheduler.quartz.QuartzManagerImpl;
import cn.com.carenet.scheduler.quartz.jobs.JobParameter;
import cn.com.carenet.scheduler.taskExec.StromJobManager;
import cn.com.carenet.scheduler.utils.ExecJobUtils;
import cn.com.carenet.scheduler.web.TaskDetailsWriter;
import cn.com.carenet.scheduler.web.WorkFlowConfManager;

@Service
public class WorkFlowService {
	final private static Logger LOG = LoggerFactory.getLogger(WorkFlowService.class);
	@Autowired
	private QuartzManagerImpl quartzJob;
	@Autowired
	private ExecJobUtils execJobUtils;
	@Autowired
	private TaskDetailsWriter taskDetailsWriter;
	@Autowired
	private StromJobManager stromJobManager;
	@Autowired
	private WorkFlowDAO workFlowDAO;
	@Autowired
	private TaskMetricMapper taskMetricMapper;
	@Autowired
	private TaskInfoMapper taskInfoMapper;
	@Autowired
	private TaskDetailsMapper taskDetailsMapper;
	@Autowired
	private WorkFlowConfManager workFlowConfManager;

	private boolean error = false;
	private String errorMsg;

	private void flushMsg() {
		/*
		 * flush error message.
		 */
		error = false;
		errorMsg = null;
	}

	private void flushMsgError(String errorMsg) {
		/*
		 * flush error message.
		 */
		error = true;
		this.errorMsg = errorMsg;
	}

	public void update(String workFlowID, String modifiedType) {
		this.flushMsg();
		LOG.debug("Get the web message:WorkflowID:{}, ModifiedType:{}", workFlowID, modifiedType);
		try {
			TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(workFlowID);
			String json = null;
			if (taskInfo != null && taskInfo.getTaskInfo() != null) {
				Integer taskType = taskInfo.getTaskType();
				if (taskType != 0) {
					String msg = String.format("Workflow:%s is not a normal workflow, please check for it!",
							workFlowID);
					LOG.debug(msg);
					error = true;
					this.flushMsgError(msg);
					return;
				}
				/*
				 * lock the saving status.
				 */
				this.execSaving(workFlowID);
				/*
				 * get the job informations.
				 */
				json = taskInfo.getTaskInfo();
			} else {
				this.flushMsgError(String.format("This work flow does not exist!, ID:%s.", workFlowID));
				return;
			}

			if (taskDetailsMapper.selectByPrimaryKey(workFlowID) == null) {
				modifiedType = JobStatusConstant.typeSave;
			} else {
				modifiedType = JobStatusConstant.typeUpdate;
			}
			workFlowConfManager.generatePorps(json, workFlowID);
			// TYPE_NAME
			String workFlowType = workFlowConfManager.getWorkFlowType();
			Map<String, JSONObject> optionMaps = workFlowConfManager.getOptionMap();
			Set<String> kafkaTopics = workFlowConfManager.getKafkaTopics();
			if (taskMetricMapper.selectManyByPrimaryKey(workFlowID) != null) {
				taskMetricMapper.deleteByPrimaryKey(workFlowID);
			}

			if (kafkaTopics.size() > 0) {
				for (String topicName : kafkaTopics) {
					TaskMetric taskMetric = new TaskMetric();
					taskMetric.setKafkaTopic(topicName);
					taskMetric.setTaskId(Integer.valueOf(workFlowID));
					taskMetric.setTypeName(workFlowType);
					if (optionMaps.get(topicName) != null)
						taskMetric.setOptionMap(optionMaps.get(topicName).toJSONString());
					taskMetricMapper.insert(taskMetric);
				}
			}
			// QUARTZ_TIME
			long timestamp = workFlowConfManager.getTimestampExpression();
			// QUARTZ_CRON
			String cronExp = workFlowConfManager.getCronExpression();
			JobParameter jobParam = this.flushQuatzJob(workFlowID, cronExp, timestamp, workFlowType);
			if (quartzJob.isJobExist(jobParam)) {
				quartzJob.removeJob(jobParam);
			}
			String workFlowErrorMessage = null;
			if (JobStatusConstant.typeSave.equals(modifiedType)) {
				workFlowErrorMessage = taskDetailsWriter.insertTaskDetails(workFlowConfManager);
			} else if (JobStatusConstant.typeUpdate.equals(modifiedType)) {
				workFlowErrorMessage = taskDetailsWriter.updateTaskDetails(workFlowConfManager);
				workFlowDAO.dropOldTimeInfo(workFlowID);
			}

			if (workFlowErrorMessage != null) {
				this.execFail(workFlowID);
				this.flushMsgError(workFlowErrorMessage);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.execFail(workFlowID);
			LOG.error(e.getMessage());
			this.flushMsgError(e.getMessage());
			return;
		}
		this.execSuccess(workFlowID);
	}

	public void start(String workFlowID, String modifiedType) {
		this.flushMsg();
		LOG.debug("Get the web message:WorkflowID:{},ModifiedType:{}", workFlowID, modifiedType);
		try {
			/*TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(workFlowID);
			if (taskInfo != null && taskInfo.getTaskInfo() != null) {
				Integer taskType = taskInfo.getTaskType();
				if (taskType != 0) {
					String msg = String.format("Workflow:%s is not a normal workflow, please check for it!",
							workFlowID);
					LOG.debug(msg);
					error = true;
					this.flushMsgError(msg);
					return;
				}
			}*/

			TaskDetails taskDetails = taskDetailsMapper.selectByPrimaryKey(workFlowID);
			if (taskDetails == null) {
				this.execFail(workFlowID);
				String message = String.format("Work flow ID:{%s}. The job does not exist, please check for it!",
						workFlowID);
				this.flushMsgError(message);
				return;
			} else {
				String typeName = taskDetails.getTypeName();
				LOG.debug(typeName);
				switch (typeName) {
				case WebModuleNameConstant.sparkStreaming:
					execJobUtils.execSparkStreamingJob(workFlowID, typeName);
					break;
				case WebModuleNameConstant.storm:
					stromJobManager.setTopologyName(workFlowID);
					stromJobManager.submitToNimbus();
					break;
				default:
					// default is normal batch jobs
					int repeatStat = taskDetails.getTaskRepeat();
					String cronExpression = taskDetails.getQuartzCron();
					long timestampExpression = taskDetails.getQuartzTime();
					JobParameter jobParam = this.flushQuatzJob(workFlowID, cronExpression, timestampExpression,
							typeName);
					if (quartzJob.isJobExist(jobParam)) {
						quartzJob.removeJob(jobParam);
						LOG.debug("remove exist job");
					}
					if (repeatStat == 1) {
						LOG.debug("add repeat job");
						quartzJob.addCronJob(jobParam);
					} else {
						LOG.debug("add non repeat job");
						quartzJob.addNonReapeatJob(jobParam);
					}
				}
			}
		} catch (Exception e) {
			this.execFail(workFlowID);
			LOG.error(e.getMessage());
			this.flushMsgError(e.getMessage());
			return;
		}
	}

	public void stop(String workFlowID, String modifiedType) {
		this.flushMsg();
		LOG.debug("Get the web message:WorkflowID:{},ModifiedType:{}", workFlowID, modifiedType);
		try {
			TaskDetails taskDetails = taskDetailsMapper.selectByPrimaryKey(workFlowID);
			if (taskMetricMapper.selectByPrimaryKey(workFlowID) != null) {
				TaskMetric taskMetric = new TaskMetric();
				taskMetric.setTaskId(Integer.parseInt(workFlowID));
				taskMetric.setExecStat(0);
				taskMetricMapper.updateByPrimaryKey(taskMetric);
			}
			if (taskDetails == null) {
				String message = String.format("Work flow ID:{%s}. The job does not exist, please check for it!",
						workFlowID);
				flushMsgError(message);
			} else {
				String typeName = taskDetails.getTypeName();
				switch (typeName) {
				case WebModuleNameConstant.sparkStreaming:
					/* spark streaming cannot be close */
					this.execusing(workFlowID);
					break;
				case WebModuleNameConstant.storm:
					stromJobManager.setTopologyName(workFlowID);
					stromJobManager.deactivateTopology();
					stromJobManager.killTopology();
					break;
				default:
					// default is normal batch jobs
					String cronExpression = taskDetails.getQuartzCron();
					long timestampExpression = taskDetails.getQuartzTime();
					JobParameter jobParam = this.flushQuatzJob(workFlowID, cronExpression, timestampExpression,
							typeName);
					quartzJob.removeJob(jobParam);
					this.execSuccess(workFlowID);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			this.flushMsgError(e.getMessage());
		}
	}

	public String delete(String workFlowID, String modifiedType) {
		this.flushMsg();
		LOG.debug("Get the web message:WorkflowID:{},ModifiedType:{}", workFlowID, modifiedType);
		try {
			if (taskMetricMapper.selectByPrimaryKey(workFlowID) != null) {
				taskMetricMapper.deleteByPrimaryKey(workFlowID);
			}
			TaskDetails taskDetails = taskDetailsMapper.selectByPrimaryKey(workFlowID);
			if (taskDetails == null) {
				String message = String.format("Work flow ID:{%s}. The job does not exist, please check for it!",
						workFlowID);
				return message;
			} else {
				String typeName = taskDetails.getTypeName();
				switch (typeName) {
				case WebModuleNameConstant.sparkStreaming:
					/* spark streaming cannot be close */
					this.execusing(workFlowID);
					break;
				case WebModuleNameConstant.storm:
					stromJobManager.setTopologyName(workFlowID);
					if (!stromJobManager.getTopologyStat().equals("None")) {
						stromJobManager.deactivateTopology();
						stromJobManager.killTopology();
					}
					break;
				default:
					// default is normal batch jobs
					String cronExpression = taskDetails.getQuartzCron();
					long timestampExpression = taskDetails.getQuartzTime();
					JobParameter jobParam = this.flushQuatzJob(workFlowID, cronExpression, timestampExpression,
							typeName);
					quartzJob.removeJob(jobParam);
				}
			}
		} catch (Exception e) {
			this.updateExecState(workFlowID, DBConstant.TASK_INFO_DELETE_FLAG_NOT_DELETED,
					DBConstant.TASK_INFO_HANG_STATUS_WAIT);
			LOG.error(e.getMessage());
			return e.getMessage();
		}
		this.updateExecState(workFlowID, DBConstant.TASK_INFO_DELETE_FLAG_DELETED,
				DBConstant.TASK_INFO_HANG_STATUS_WAIT);
		return null;
	}

	private JobParameter flushQuatzJob(String workFlowID, String croExpression, long timestampExpression,
			String typeName) {
		JobParameter jobParam = new JobParameter();
		jobParam.setJobClassName("cn.com.carenet.scheduler.quartz.jobs.BatchJob");
		jobParam.setJobGroup(workFlowID);
		jobParam.setCronExpression(croExpression);
		jobParam.setTimestampExpression(timestampExpression);
		jobParam.setWorkFlowID(workFlowID);
		jobParam.setTypeName(typeName);
		jobParam.setJobName(workFlowID + typeName);
		return jobParam;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public boolean isError() {
		return error;
	}

	private void execusing(String workFlowID) {
		TaskMetric tasksMetric = workFlowDAO.selectTasksMetric(workFlowID);
		if (tasksMetric != null) {
			String sparkAppId = tasksMetric.getSparkAppId();
			ExecJobUtils.stopStreamingGraceFull(sparkAppId);
		}

		this.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXECING,
				DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
	}

	private void execSaving(String workFlowID) {
		this.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_SAVING,
				DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
	}

	private void execSuccess(String workFlowID) {
		this.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_SAVE_SUCCESS,
				DBConstant.TASK_INFO_HANG_STATUS_WAIT);
	}

	private void execFail(String workFlowID) {
		this.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_SAVE_FAILED,
				DBConstant.TASK_INFO_HANG_STATUS_WAIT);
	}

	private void updateExecState(String workFlowID, Integer state, Integer lockStat) {
		TaskInfo execTaskInfo = new TaskInfo();
		execTaskInfo.setId(Integer.parseInt(workFlowID));
		execTaskInfo.setExecState(state);
		execTaskInfo.setHangStatus(lockStat);
		taskInfoMapper.updateByPrimaryKey(execTaskInfo);
	}

}
