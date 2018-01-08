package cn.com.carenet.scheduler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.esotericsoftware.minlog.Log;
import com.mongodb.client.MongoDatabase;

import cn.com.carenet.common.web.entity.TaskInfo;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.constant.DBConstant;
import cn.com.carenet.scheduler.constant.JobStatusConstant;
import cn.com.carenet.scheduler.constant.SparkJobPropertiesBean;
import cn.com.carenet.scheduler.dao.AlgorithmModelDAO;
import cn.com.carenet.scheduler.dao.AlgorithmModelParamDAO;
import cn.com.carenet.scheduler.dao.WorkFlowDAO;
import cn.com.carenet.scheduler.entity.TaskMetric;
import cn.com.carenet.scheduler.mapper.TaskDetailsMapper;
import cn.com.carenet.scheduler.mapper.TaskInfoMapper;
import cn.com.carenet.scheduler.mapper.TaskMetricMapper;
import cn.com.carenet.scheduler.quartz.QuartzManagerImpl;
import cn.com.carenet.scheduler.quartz.jobs.JobParameter;
import cn.com.carenet.scheduler.utils.ExecJobUtils;
import cn.com.carenet.scheduler.utils.MongoDBUtils;
import cn.com.carenet.scheduler.web.TaskDetailsWriter;
import cn.com.carenet.scheduler.web.WorkFlowConfManager;

@Service("algorithmModelService")
public class AlgorithmModelService {
	private boolean error = false;
	final private static Logger LOG = LoggerFactory.getLogger(WorkFlowService.class);
	private String errorMsg;
	@Autowired
	private QuartzManagerImpl quartzJob;
	@Autowired
	private TaskDetailsWriter taskDetailsWriter;
	@Autowired
	private TaskMetricMapper taskMetricMapper;
	@Autowired
	private TaskInfoMapper taskInfoMapper;
	@Autowired
	private TaskDetailsMapper taskDetailsMapper;
	@Autowired
	AlgorithmModelDAO algorithmModelDAO;
	@Autowired
	AlgorithmModelParamDAO algorithmModelParamDAO;
	@Autowired
	SparkJobPropertiesBean sparkJobPropertiesBean;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	private WorkFlowConfManager workFlowConfManager;
	@Autowired
	private WorkFlowDAO workFlowDAO;

	private void flushMsg() {
		/*
		 * flush error message.
		 */
		error = false;
		errorMsg = null;
	}

	public void update(String modelId, String modifiedType) {
		this.flushMsg();
		LOG.debug("Get the web message:WorkflowID:{}, ModifiedType:{}", modelId, modifiedType);
		try {
			// WebJSONSpliter webJsonSpliter = new WebJSONSpliter(modelId);
			TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(modelId);
			String json = null;
			if (taskInfo != null && taskInfo.getTaskInfo() != null) {
				Integer taskType = taskInfo.getTaskType();
				if (taskType != 5) {
					String msg = String.format("Workflow:%s is not a algorithm workflow, please check for it!",
							modelId);
					LOG.debug(msg);
					this.flushMsgError(msg);
					return;
				}
				/*
				 * lock the saving status.
				 */
				this.execSaving(modelId);
				/*
				 * get the job informations.
				 */
				json = taskInfo.getTaskInfo();
			} else {
				this.flushMsgError(String.format("This work flow does not exist!, ID:%s.", modelId));
				return;
			}
			if (taskDetailsMapper.selectByPrimaryKey(modelId) == null) {
				modifiedType = JobStatusConstant.typeSave;
			} else {
				modifiedType = JobStatusConstant.typeUpdate;
			}
			workFlowConfManager.generatePorps(json, modelId);

			// TYPE_NAME
			String workFlowType = workFlowConfManager.getWorkFlowType();
			Map<String, JSONObject> optionMaps = workFlowConfManager.getOptionMap();
			Set<String> kafkaTopics = workFlowConfManager.getKafkaTopics();
			Map<String, DataSourceProp> dataSourceMap = workFlowConfManager.getDataSourceMap();
			for (DataSourceProp dataSourceProp : dataSourceMap.values()) {
				System.err.println(dataSourceProp.getWorkFlowID() + "  " + dataSourceProp.getDependents());
			}
			if (taskMetricMapper.selectManyByPrimaryKey(modelId) != null) {
				taskMetricMapper.deleteByPrimaryKey(modelId);
			}

			if (kafkaTopics.size() > 0) {
				for (String topicName : kafkaTopics) {
					TaskMetric taskMetric = new TaskMetric();
					taskMetric.setKafkaTopic(topicName);
					taskMetric.setTaskId(Integer.valueOf(modelId));
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
			JobParameter jobParam = this.flushQuatzJob(modelId, cronExp, timestamp, workFlowType);
			if (quartzJob.isJobExist(jobParam)) {
				quartzJob.removeJob(jobParam);
			}
			String workFlowErrorMessage = null;
			if (JobStatusConstant.typeSave.equals(modifiedType)) {
				workFlowErrorMessage = taskDetailsWriter.insertTaskDetails(workFlowConfManager);
			} else if (JobStatusConstant.typeUpdate.equals(modifiedType)) {
				workFlowErrorMessage = taskDetailsWriter.updateTaskDetails(workFlowConfManager);
				workFlowDAO.dropOldTimeInfo(modelId);
			}

			if (workFlowErrorMessage != null) {
				this.execFail(modelId);
				this.flushMsgError(workFlowErrorMessage);
				return;
			}
		} catch (Exception e) {
			this.execFail(modelId);
			LOG.error(e.getLocalizedMessage());
			this.flushMsgError(e.getLocalizedMessage());
			return;
		}
		this.execSuccess(modelId);
	}

	private void execSuccess(String modelId) {
		this.updateExecState(modelId, DBConstant.TASK_INFO_EXEC_STATE_SAVE_SUCCESS,
				DBConstant.TASK_INFO_HANG_STATUS_WAIT);
	}

	private void execFail(String modelId) {
		this.updateExecState(modelId, DBConstant.TASK_INFO_EXEC_STATE_SAVE_FAILED,
				DBConstant.TASK_INFO_HANG_STATUS_WAIT);
	}

	public String delete(String modelId) {
		return null;
	}

	public String start(String modelId) {
		this.flushMsg();
		ExecJobUtils execJobUtils = new ExecJobUtils();
		try {
			execJobUtils.execSparkMLlibJob(modelId);
		} catch (Exception e) {
			Log.error(e.getLocalizedMessage());
		}
		return null;
	}

	public String stop(MongoDatabase database, String modelId) {
		TaskMetric tasksMetric = workFlowDAO.selectTasksMetric(modelId);
		if (tasksMetric != null) {
			String sparkAppId = tasksMetric.getSparkAppId();
			ExecJobUtils.stopStreamingGraceFull(sparkAppId);
		}

		TaskInfo execTaskInfo = new TaskInfo();
		if (execTaskInfo != null) {
			Integer taskType = execTaskInfo.getTaskType();
			if (taskType != 5) {
				String msg = String.format("Workflow:%s is not a algorithm workflow, please check for it!", modelId);
				LOG.debug(msg);
				this.flushMsgError(msg);
				return msg;
			}
		}
		execTaskInfo.setId(Integer.parseInt(modelId));
		execTaskInfo.setExecState(DBConstant.TASK_INFO_EXEC_STATE_EXECING);
		execTaskInfo.setHangStatus(DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
		taskInfoMapper.updateByPrimaryKey(execTaskInfo);

		return null;
	}

	public Document catSummary(MongoDatabase database, String modelId) {
		List<Document> documents = MongoDBUtils.findByWorkFlowId(database, "modelValidation", modelId);
		if (documents == null || documents.isEmpty()) {
			return null;
		} else {
			return documents.get(0);
		}

	}

	public HashMap<String, Object> getSummarys(Document document) {
		HashMap<String, Object> resultMap = new HashMap<>();
		Set<String> set = new HashSet<String>();
		set.add("_id");
		set.add("workFolwID");
		set.add("R-squared");
		for (String key : document.keySet()) {
			if (!set.contains(key) && document != null) {
				resultMap.put(key, document.get(key));
			}
		}
		return resultMap;
	}

	public List<HashMap<String, Object>> catCorr(MongoDatabase database, String modelId) {
		List<HashMap<String, Object>> list = new ArrayList<>();
		List<Document> documents = MongoDBUtils.findByWorkFlowId(database, "modelCorrStats", modelId);
		for (Document document : documents) {
			HashMap<String, Object> resultMap = new HashMap<>();
			resultMap.put("features1", document.getString("features1"));
			resultMap.put("features2", document.getString("features2"));
			resultMap.put("relevance", document.getDouble("relevance"));
			list.add(resultMap);
		}

		return list;
	}

	public List<HashMap<String, Object>> catChiSqTest(MongoDatabase database, String modelId) {

		List<Document> documents = MongoDBUtils.findByWorkFlowId(database, "ModelWeights", modelId);
		List<HashMap<String, Object>> list = new ArrayList<>();
		for (Document document : documents) {
			HashMap<String, Object> resultMap = new HashMap<>();
			resultMap.put("fieldName", document.getString("fieldName"));
			resultMap.put("weights", document.getDouble("weights"));
			list.add(resultMap);
		}

		return list;
	}

	public String getDataSchema(String modelId) {
		return algorithmModelDAO.getDataSchema(modelId);
	}

	private void execSaving(String modelId) {
		this.updateExecState(modelId, DBConstant.TASK_INFO_EXEC_STATE_SAVING, DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
	}

	private void updateExecState(String modelId, Integer state, Integer lockStat) {
		TaskInfo execTaskInfo = new TaskInfo();
		execTaskInfo.setId(Integer.parseInt(modelId));
		execTaskInfo.setExecState(state);
		execTaskInfo.setHangStatus(lockStat);
		taskInfoMapper.updateByPrimaryKey(execTaskInfo);
	}

	private void flushMsgError(String errorMsg) {
		/*
		 * flush error message.
		 */
		error = true;
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public boolean isError() {
		return error;
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

}
