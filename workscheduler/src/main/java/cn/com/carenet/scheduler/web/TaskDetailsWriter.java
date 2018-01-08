package cn.com.carenet.scheduler.web;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSON;

import cn.com.carenet.common.web.entity.TaskDatasource;
import cn.com.carenet.scheduler.bean.CommandSQLAllBean;
import cn.com.carenet.scheduler.bean.CommonOperateAllBean;
import cn.com.carenet.scheduler.bean.DataBaseSQLBean;
import cn.com.carenet.scheduler.bean.DataSchema;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.EtlAllBean;
import cn.com.carenet.scheduler.bean.OperateProp;
import cn.com.carenet.scheduler.bean.SingleMetaData;
import cn.com.carenet.scheduler.bean.spark.SparkAllProps;
import cn.com.carenet.scheduler.bean.spark.SparkEnvProp;
import cn.com.carenet.scheduler.bean.storm.TopologyAllBeans;
import cn.com.carenet.scheduler.bean.storm.TopologyEnvProp;
import cn.com.carenet.scheduler.constant.SparkJobPropertiesBean;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.dao.WorkFlowDAO;
import cn.com.carenet.scheduler.entity.MetaDataMap;
import cn.com.carenet.scheduler.entity.SysDatasource;
import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.mapper.MetaDataMapMapper;
import cn.com.carenet.scheduler.mapper.SysDatasourceMapper;
import cn.com.carenet.scheduler.mapper.TaskDatasourceMapper;
import cn.com.carenet.scheduler.mapper.TaskDetailsMapper;
import cn.com.carenet.scheduler.utils.ConnectionTypeUtil;
import cn.com.carenet.scheduler.web.JobConfigFileWriter;
import cn.com.carenet.scheduler.web.WorkFlowConfManager;
import cn.com.carenet.scheduler.web.entity.DataRepository;

/**
 * 
 * @author Sherard Lee
 *
 */

@Repository
public class TaskDetailsWriter {
	final private static Logger LOG = LoggerFactory.getLogger(TaskDetailsWriter.class);
	final private static List<String> commonOperateModuleNameList = new ArrayList<>();
	final private static List<String> sqlOperateModuleNameList = new ArrayList<>();
	final private static List<String> sparkModuleNameList = new ArrayList<>();

	@Autowired
	private SparkJobPropertiesBean sparkJobPropertiesBean;
	@Autowired
	private SysDatasourceMapper sysDatasourceMapper;
	@Autowired
	private MetaDataMapMapper metaDataMapMapper;
	@Autowired
	private WorkFlowDAO workFlowDAO;
	@Autowired
	private TaskDetailsMapper taskDetailsMapper;

	@Autowired
	private RelationDatasource relationDatasource;
	@Autowired

	private TaskDatasourceMapper taskDatasourceMapper;

	static {
		commonOperateModuleNameList.add(DataRepository.WF_NAME_MAPREDUCE);

		sqlOperateModuleNameList.add(DataRepository.WF_NAME_UNIXSHELL);
		sqlOperateModuleNameList.add(DataRepository.WF_NAME_GREENPLUMSQL);
		sqlOperateModuleNameList.add(DataRepository.WF_NAME_HIVEQL);
		sqlOperateModuleNameList.add(DataRepository.WF_NAME_MYSQLSQL);
		sqlOperateModuleNameList.add(DataRepository.WF_NAME_ORACLESQL);

		sparkModuleNameList.add(DataRepository.WF_NAME_SPARKCORE);
		sparkModuleNameList.add(DataRepository.WF_NAME_SPARKGRAPHX);
		sparkModuleNameList.add(DataRepository.WF_NAME_SPARK_MLLIB);
		sparkModuleNameList.add(DataRepository.WF_NAME_SPARKSQL);
		sparkModuleNameList.add(DataRepository.WF_NAME_SPARKSTREAMING);
	}

	public String insertTaskDetails(WorkFlowConfManager workFlowConfManager) {
		if (workFlowConfManager.isWorkFlowError()) {
			return workFlowConfManager.getWorkFlowErrorMsg();
		}
		this.updateDataStr(workFlowConfManager);
		return null;
	}

	public String updateTaskDetails(WorkFlowConfManager workFlowConfManager) {
		workFlowDAO.dropOldTimeInfo(workFlowConfManager.getWorkFlowID());
		return insertTaskDetails(workFlowConfManager);
	}

	private void updateDataStr(WorkFlowConfManager workFlowConfManager) {
		String bigBean = "";
		String workFlowType;
		String workFlowID;
		long timestamp;
		String cronExp;
		boolean repeat;
		int repeatMark = 0;
		Map<String, DataSchema> dataSchemas;
		Map<String, DataSourceProp> dataSources;
		Map<String, OperateProp> operateOptions;
		DataBaseSQLBean dataBaseSQLBean;
		// TASK_ID
		workFlowID = workFlowConfManager.getWorkFlowID();
		// TYPE_NAME
		workFlowType = workFlowConfManager.getWorkFlowType();
		// QUARTZ_TIME
		timestamp = workFlowConfManager.getTimestampExpression();
		// QUARTZ_CRON
		cronExp = workFlowConfManager.getCronExpression();
		// TASK_REPEAT
		repeat = workFlowConfManager.isRepeat();
		// FIELDS_INFOS
		dataSchemas = workFlowConfManager.getDataSchemas();
		// DATA_SOURCES_INFOS
		dataSources = workFlowConfManager.getDataSourceMap();
		// OPERATES_INFOS
		operateOptions = workFlowConfManager.getOperateMap();
		// SQLS_INFOS
		dataBaseSQLBean = workFlowConfManager.getDataBaseSQLBean();

		TaskDetails taskDetailR = null;

		if (repeat) {
			repeatMark = 1;
			LOG.debug("This is a cron job. ");
		} else {
			repeatMark = 0;
		}
		
		if (workFlowType.equals(WebModuleNameConstant.storm)) {
			TopologyEnvProp topologyOptions = workFlowConfManager.getTopologyOptionsBean();
			TopologyAllBeans topologyAllBeans = new TopologyAllBeans();
			topologyAllBeans.setDataSchemas(dataSchemas);
			topologyAllBeans.setDataSources(dataSources);
			topologyAllBeans.setOperateOptions(operateOptions);
			topologyAllBeans.setTopologyOptions(topologyOptions);
			topologyAllBeans.setWorkFlowID(workFlowID);
			topologyAllBeans.setWorkFlowType(workFlowType);
			bigBean = JSON.toJSONString(topologyAllBeans);
		} else if (sqlOperateModuleNameList.contains(workFlowType)) {
			CommandSQLAllBean commandSQLAllBean = new CommandSQLAllBean();
			commandSQLAllBean.setWorkFlowID(workFlowID);
			commandSQLAllBean.setWorkFlowType(workFlowType);
			commandSQLAllBean.setDataBaseSQLBean(dataBaseSQLBean);
			commandSQLAllBean.setDataSchemas(dataSchemas);
			commandSQLAllBean.setDataSources(dataSources);
			bigBean = JSON.toJSONString(commandSQLAllBean);
		} else if (commonOperateModuleNameList.contains(workFlowType)) {
			CommonOperateAllBean commonOperateAllBean = new CommonOperateAllBean();
			commonOperateAllBean.setWorkFlowID(workFlowID);
			commonOperateAllBean.setWorkFlowType(workFlowType);
			commonOperateAllBean.setDataSchemas(dataSchemas);
			commonOperateAllBean.setDataSources(dataSources);
			commonOperateAllBean.setOperateOptions(operateOptions);
			bigBean = JSON.toJSONString(commonOperateAllBean);
		} else if (sparkModuleNameList.contains(workFlowType)) {
			SparkEnvProp sparkEnvProp = workFlowConfManager.getSparkEnvProp();
			SparkAllProps sparkAllProps = new SparkAllProps();
			sparkAllProps.setDataSchemas(dataSchemas);
			sparkAllProps.setDataSources(dataSources);
			sparkAllProps.setOperateOptions(operateOptions);
			sparkAllProps.setSparkEnvProp(sparkEnvProp);
			sparkAllProps.setWorkFlowID(workFlowID);
			sparkAllProps.setWorkFlowType(workFlowType);
			bigBean = JSON.toJSONString(sparkAllProps);
			taskDetailR = JobConfigFileWriter.writeSparkJobToSql(workFlowConfManager, sparkJobPropertiesBean);
		} else {
			EtlAllBean etlAllBean = new EtlAllBean();
			etlAllBean.setWorkFlowID(workFlowID);
			etlAllBean.setWorkFlowType(workFlowType);
			etlAllBean.setDataSchemas(dataSchemas);
			etlAllBean.setDataSources(dataSources);
			bigBean = JSON.toJSONString(etlAllBean);
		}

		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setTypeName(workFlowType);
		taskDetails.setTaskRepeat(repeatMark);
		taskDetails.setBigBean(bigBean);
		taskDetails.setDataSourcesInfos(JSON.toJSONString(dataSources));
		taskDetails.setOperatesInfos(JSON.toJSONString(operateOptions));
		taskDetails.setFieldsInfos(JSON.toJSONString(dataSchemas));
		if (taskDetailR != null) {
			taskDetails.setDataSourcesInfos(taskDetailR.getDataSourcesInfos());
			taskDetails.setOperatesInfos(taskDetailR.getOperatesInfos());
			taskDetails.setFieldsInfos(taskDetailR.getFieldsInfos());
			taskDetails.setSqlsInfos(taskDetailR.getSqlsInfos());
		}

		taskDetails.setQuartzTime(timestamp);
		taskDetails.setQuartzCron(cronExp);
		taskDetails.setTaskId(workFlowID);

		TaskDetails tmpTaskDetails = taskDetailsMapper.selectByPrimaryKey(workFlowID);
		if (tmpTaskDetails == null) {
			taskDetailsMapper.insert(taskDetails);
		} else {
			taskDetailsMapper.updateByPrimaryKey(taskDetails);
		}

		this.setOutputMetaDataMap(dataSources, dataSchemas);

		this.updateRelations(Integer.parseInt(workFlowID), dataSources);

	}

	private void updateRelations(Integer taskId, Map<String, DataSourceProp> dataSourceMap) {
		String inputs = relationDatasource.getInput(dataSourceMap);
		String outputs = relationDatasource.getOutput(dataSourceMap);
		TaskDatasource taskDatasource = new TaskDatasource();
		taskDatasource.setSysInputId(inputs);
		taskDatasource.setSysOutputId(outputs);
		taskDatasource.setTaskId(taskId);
		TaskDatasource resultTaskDatasource = taskDatasourceMapper.selectByTaskID(taskId);
		if (resultTaskDatasource != null) {
			taskDatasourceMapper.deleteByPrimaryKey(resultTaskDatasource.getId());
		}
		taskDatasourceMapper.insert(taskDatasource);

	}

	private void setOutputMetaDataMap(Map<String, DataSourceProp> dataSources, Map<String, DataSchema> dataSchemas) {
		if (dataSources != null) {
			for (Entry<String, DataSourceProp> dataSource : dataSources.entrySet()) {
				DataSourceProp dataSourceOptionsBean = dataSource.getValue();
				int sourceType = dataSourceOptionsBean.getPutType();
				if (sourceType == 1) {
					// output
					String sourceName = dataSourceOptionsBean.getSourceName();

					String sourcePrimary = dataSourceOptionsBean.getComponents().get(0).getSourcePrimary();
					DataSchema dataSchema = dataSchemas.get(sourcePrimary);

					SysDatasource sysDatasource = new SysDatasource();
					switch (sourceName) {
					case WebModuleNameConstant.kafka:
						sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_KAFKA);
						sysDatasource.setIp(dataSourceOptionsBean.getIp());
						sysDatasource.setPort(dataSourceOptionsBean.getPort());
						sysDatasource.setUrl(dataSourceOptionsBean.getZkServers());
						sysDatasource.setDatabaseName(dataSourceOptionsBean.getDataBaseName());
						sysDatasource.setSeparator(dataSourceOptionsBean.getDatasouceDelimiter());
						break;
					case WebModuleNameConstant.hdfs:
						sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_HDFS);
						sysDatasource.setIp(dataSourceOptionsBean.getIp());
						sysDatasource.setPort(dataSourceOptionsBean.getPort());
						sysDatasource.setDatabaseName(dataSourceOptionsBean.getDataBaseName());
						sysDatasource.setUrl(
								ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_HDFS, dataSourceOptionsBean.getIp(),
										dataSourceOptionsBean.getPort(), dataSourceOptionsBean.getDataBaseName()));
						sysDatasource.setSeparator(dataSourceOptionsBean.getDatasouceDelimiter());
						break;
					case WebModuleNameConstant.mysql:
						sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_MYSQL);
						sysDatasource.setIp(dataSourceOptionsBean.getIp());
						sysDatasource.setPort(dataSourceOptionsBean.getPort());
						sysDatasource.setDatabaseName(dataSourceOptionsBean.getDataBaseName());
						sysDatasource.setUserName(dataSourceOptionsBean.getUserName());
						sysDatasource.setPassword(dataSourceOptionsBean.getPassword());
						sysDatasource.setUrl(ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_MYSQL,
								dataSourceOptionsBean.getIp(), dataSourceOptionsBean.getPort(),
								dataSourceOptionsBean.getDataBaseName()));
						sysDatasource.setDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_MYSQL));
						break;
					case WebModuleNameConstant.oracle:
						sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_ORACLE);
						sysDatasource.setIp(dataSourceOptionsBean.getIp());
						sysDatasource.setPort(dataSourceOptionsBean.getPort());
						sysDatasource.setDatabaseName(dataSourceOptionsBean.getDataBaseName());
						sysDatasource.setUserName(dataSourceOptionsBean.getUserName());
						sysDatasource.setPassword(dataSourceOptionsBean.getPassword());
						sysDatasource.setUrl(ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_ORACLE,
								dataSourceOptionsBean.getIp(), dataSourceOptionsBean.getPort(),
								dataSourceOptionsBean.getDataBaseName()));
						sysDatasource.setDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_ORACLE));
						break;
					case WebModuleNameConstant.greenPlum:
						sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_GP);
						sysDatasource.setIp(dataSourceOptionsBean.getIp());
						sysDatasource.setPort(dataSourceOptionsBean.getPort());
						sysDatasource.setDatabaseName(dataSourceOptionsBean.getDataBaseName());
						sysDatasource.setUserName(dataSourceOptionsBean.getUserName());
						sysDatasource.setPassword(dataSourceOptionsBean.getPassword());
						sysDatasource.setUrl(
								ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_GP, dataSourceOptionsBean.getIp(),
										dataSourceOptionsBean.getPort(), dataSourceOptionsBean.getDataBaseName()));
						sysDatasource.setDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_GP));
						break;
					case WebModuleNameConstant.hive:
						sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_HIVE);
						sysDatasource.setIp(dataSourceOptionsBean.getIp());
						sysDatasource.setPort(dataSourceOptionsBean.getPort());
						sysDatasource.setDatabaseName(dataSourceOptionsBean.getDataBaseName());
						sysDatasource.setUserName(dataSourceOptionsBean.getUserName());
						sysDatasource.setPassword(dataSourceOptionsBean.getPassword());
						sysDatasource.setUrl(
								ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_HIVE, dataSourceOptionsBean.getIp(),
										dataSourceOptionsBean.getPort(), dataSourceOptionsBean.getDataBaseName()));
						sysDatasource.setDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_HIVE));
						break;
					case WebModuleNameConstant.hBase:
						sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_HBASE);
						sysDatasource.setIp(dataSourceOptionsBean.getIp());
						sysDatasource.setPort(dataSourceOptionsBean.getPort());
						sysDatasource.setDatabaseName(dataSourceOptionsBean.getDataBaseName());
						sysDatasource.setUrl(dataSourceOptionsBean.getUrls());
						break;
					case WebModuleNameConstant.elasticSearch:
						sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_ELASTICSEARCH);
						sysDatasource.setIp(dataSourceOptionsBean.getIp());
						sysDatasource.setPort(dataSourceOptionsBean.getPort());
						sysDatasource.setDatabaseName(dataSourceOptionsBean.getDataBaseName());
						sysDatasource.setUrl(
								String.format("%s:%d", dataSourceOptionsBean.getIp(), dataSourceOptionsBean.getPort()));
						break;
					}
					SysDatasource resultSysDatasource = sysDatasourceMapper.selectOne(sysDatasource);
					if (resultSysDatasource == null) {
						sysDatasourceMapper.insert(sysDatasource);
						int dataSourceId = sysDatasource.getId();
						List<MetaDataMap> metaDataMaps = new ArrayList<>();
						if (sourceName.equals(WebModuleNameConstant.hdfs)
								|| sourceName.equals(WebModuleNameConstant.kafka)) {
							List<SingleMetaData> dataMetas = dataSchema.getMetaDatas();
							if (dataMetas != null) {
								for (SingleMetaData dataMeta : dataMetas) {
									MetaDataMap metaDataMap = new MetaDataMap();
									metaDataMap.setOrdernum(dataMeta.getColNum());
									metaDataMap.setColumnName(dataMeta.getFieldName());
									metaDataMap.setColumnType(ConnectionTypeUtil.columnNumToType(
											ConnectionTypeUtil.formatToNumType(dataMeta.getFieldType())));
									metaDataMap.setDateFormat(dataMeta.getDateFormat());
									metaDataMap.setTableName(sysDatasource.getDatabaseName());
									metaDataMap.setDataSourceId(dataSourceId);
									metaDataMaps.add(metaDataMap);
								}
							}
						}
						if (!metaDataMaps.isEmpty()) {
							metaDataMapMapper.insertBatch(metaDataMaps);
						}
					}
				}
			}
		}
	}
}
