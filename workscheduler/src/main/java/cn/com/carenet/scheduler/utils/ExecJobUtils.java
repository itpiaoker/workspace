package cn.com.carenet.scheduler.utils;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.com.carenet.components.ExtractTransformLoadJob;
import cn.com.carenet.components.greenplum.GreenPlumManager;
import cn.com.carenet.components.sql.run.Sqlwhile;
import cn.com.carenet.scheduler.bean.CommandSQLAllBean;
import cn.com.carenet.scheduler.bean.DataBaseSQLBean;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.constant.Constant;
import cn.com.carenet.scheduler.constant.DBConstant;
import cn.com.carenet.scheduler.constant.SparkJobPropertiesBean;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.dao.JobRunInfoDAO;
import cn.com.carenet.scheduler.dao.WorkFlowDAO;
import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.web.SparkConfNoAg;

@Service
public class ExecJobUtils {

	final private static Logger LOG = LoggerFactory.getLogger(ExecJobUtils.class);
	private static SparkJobPropertiesBean sparkJobPropertiesBean = PropertiesReader.getSparkJobPropertiesBean();
	@Autowired
	private WorkFlowDAO workFlowDAO;
	@Autowired
	private JobRunInfoDAO jobRunInfoDAO;
	@Autowired
	private GreenPlumManager greenPlumManager;
	@Autowired
	private ExtractTransformLoadJob etlJob;

	public void execSingleJob(String workFlowID) {
		boolean execStat = false;
		Date startTime = new Date();
		try {
			workFlowDAO.updateMetricRun(workFlowID);
			workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXECING,
					DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
			startTime = jobRunInfoDAO.insertNewStart(workFlowID);
			TaskDetails taskDetails = jobRunInfoDAO.selectTasksDetails(workFlowID);
			String jobType = taskDetails.getTypeName();
			LOG.debug("Workflow:{}, type:{}.", workFlowID, jobType);
			switch (jobType) {
			case WebModuleNameConstant.sparkCore:
			case WebModuleNameConstant.sparkSQL:
			case WebModuleNameConstant.sparkGraphx:
				execStat = execSparkJob(workFlowID, jobType);
				break;
			case WebModuleNameConstant.sparkMLLib:
				sparkJobPropertiesBean.setSubmitcmd(
						"spark-submit  --master yarn-client  --num-executors 4  --executor-cores 4 --executor-memory 4G --class cn.com.carenet.ModelTest");
				sparkJobPropertiesBean.setFrameworkurl(
						"/opt/workflows/framework/spark-product-1.0-SNAPSHOT-jar-with-dependencies.jar");
				execStat = execSparkJob(workFlowID, jobType);
				break;
			case WebModuleNameConstant.hadoop:
				execStat = this.execMapReduceJob(workFlowID);
				break;
			case WebModuleNameConstant.greenPlumSQL:
				execStat = execGreenPlumSQLJob(workFlowID);
				break;
			case WebModuleNameConstant.storm:
			case WebModuleNameConstant.sparkStreaming:
				break;
			default:
				execStat = execETLJob(workFlowID);
			}
			if (execStat) {
				jobRunInfoDAO.updateSuccessEnd(workFlowID, startTime);
				workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXEC_SUCCESS,
						DBConstant.TASK_INFO_HANG_STATUS_WAIT);
			} else {
				jobRunInfoDAO.updateFailedEnd(workFlowID, startTime);
				workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXEC_FAILED,
						DBConstant.TASK_INFO_HANG_STATUS_WAIT);
			}
			workFlowDAO.updateMetricStop(workFlowID);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			jobRunInfoDAO.updateFailedEnd(workFlowID, startTime);
			workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXEC_FAILED,
					DBConstant.TASK_INFO_HANG_STATUS_WAIT);
			workFlowDAO.updateMetricStop(workFlowID);
		}
	}

	public boolean execSparkJob(String workFlowID, String typeName) throws Exception {
		SparkConfNoAg.setSparkJobPropertiesBean(sparkJobPropertiesBean);
		SparkConfNoAg sparkConfUtils = new SparkConfNoAg(workFlowID, typeName);
		CmdExecutor cmdExecutor = new CmdExecutor();
		LOG.debug(sparkConfUtils.getJobSubmitFullStr());
		return cmdExecutor.execCommand(sparkConfUtils.getJobSubmitFullStr(), null);
	}

	@Async("sparkExecutor")
	public void execSparkStreamingJob(String workFlowID, String typeName) throws Exception {
		SparkConfNoAg.setSparkJobPropertiesBean(sparkJobPropertiesBean);
		SparkConfNoAg sparkConfUtils = new SparkConfNoAg(workFlowID, typeName);
		CmdExecutor cmdExecutor = new CmdExecutor();
		Date startTime = jobRunInfoDAO.insertNewStart(workFlowID);
		boolean execStat = cmdExecutor.execCommand(sparkConfUtils.getJobSubmitFullStr(), null);
		if (execStat) {
			workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXECING,
					DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
			workFlowDAO.updateMetricRun(workFlowID);
		} else {
			jobRunInfoDAO.updateFailedEnd(workFlowID, startTime);
			workFlowDAO.updateExecState(workFlowID, DBConstant.TASK_INFO_EXEC_STATE_EXEC_FAILED,
					DBConstant.TASK_INFO_HANG_STATUS_WAIT);
		}
	}

	public boolean execSparkMLlibJob(String workFlowID) throws Exception {
		SparkConfUtils.setSparkJobPropertiesBean(sparkJobPropertiesBean);
		SparkConfUtils sparkConfUtils = new SparkConfUtils(workFlowID, Constant.MODULE_SPARK_MLLIB_KEY);
		CmdExecutor cmdExecutor = new CmdExecutor();
		return cmdExecutor.execCommand(sparkConfUtils.getJobSubmitFullStr(), null);
	}

	/**
	 * 1.local file to hive 2.local to gp 3.local to ftp 4.hdfs to hive 5.hdfs to
	 * ftp 6.hdfs to gp 7.mysql to hive 8.hive to mysql 9.gp to local 10.ftp to hdfs
	 * 11.ftp to local
	 * 
	 * @param workFlowID
	 * @return
	 */
	public boolean execETLJob(String workFlowID) {
		try {
			return etlJob.execETL(workFlowID);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean execGreenPlumSQLJob(String workFlowID) {
		return greenPlumManager.execGreenPlumSQLJob(workFlowID);
	}

	public boolean execMysqlAndOracalJob(String workFlowID) {
		TaskDetails taskDetails = jobRunInfoDAO.selectTasksDetails(workFlowID);

		String allBean = taskDetails.getBigBean();
		CommandSQLAllBean commandSQLAllBean = JSON.parseObject(allBean, CommandSQLAllBean.class);

		DataBaseSQLBean sql = commandSQLAllBean.getDataBaseSQLBean();
		Map<String, DataSourceProp> dataSourceOptionsBeanMap = commandSQLAllBean.getDataSources();
		String db = sql.getDataBaseName();
		String user = sql.getUserName();
		String paseword = sql.getPassword();
		String url = sql.getUrls();
		String sqls = sql.getScript();
		String jdbcType = "";

		for (String dbsource : dataSourceOptionsBeanMap.keySet()) {
			DataSourceProp dataSourceBean = dataSourceOptionsBeanMap.get(dbsource);
			jdbcType = dataSourceBean.getSourceName();
		}
		LOG.debug("{},{},{}", sqls, db, url);
		try {
			@SuppressWarnings("unused")
			Sqlwhile my_sql = new Sqlwhile(url, user, paseword, db, sqls, jdbcType);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		return true;
	}

	public boolean execMapReduceJob(String workFlowID) {
		String fileName = PropertiesReader.getHadoopJarUrl();
		// String mainClass = "cn.com.carenet.bigdata.mapreduce.MapReduceApp";
		// String args[] = new String[] { fileName, workFlowID };
		LOG.debug("Starting map reduce... {}", workFlowID);
		try {
			// RunJar.main(args);
			CmdExecutor cmdExecutor = new CmdExecutor();
			String cmd = String.format("hadoop jar %s %s", fileName, workFlowID);
			boolean code = cmdExecutor.execCommand(cmd, null);
			return code;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public static void stopStreamingGraceFull(String appId) {
		try {
			CmdExecutor cmdExecutor = new CmdExecutor();
			String cmd = "yarn application -kill " + appId;
			LOG.debug("stop streaming cmd is \t {}", cmd);
			cmdExecutor.execCommand(cmd, null);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

}
