package cn.com.carenet.scheduler.json;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import cn.com.carenet.scheduler.bean.CommandSQLAllBean;
import cn.com.carenet.scheduler.bean.CommonOperateAllBean;
import cn.com.carenet.scheduler.bean.DataBaseSQLBean;
import cn.com.carenet.scheduler.bean.DataSchema;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.EtlAllBean;
import cn.com.carenet.scheduler.bean.OperateProp;
import cn.com.carenet.scheduler.bean.SingleMetaData;
import cn.com.carenet.scheduler.bean.storm.TopologyAllBeans;
import cn.com.carenet.scheduler.bean.storm.TopologyEnvProp;
import cn.com.carenet.scheduler.constant.SparkJobPropertiesBean;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.dao.WorkFlowDAO;
import cn.com.carenet.scheduler.entity.MetaDataMap;
import cn.com.carenet.scheduler.entity.SysDatasource;
import cn.com.carenet.scheduler.mapper.MetaDataMapMapper;
import cn.com.carenet.scheduler.mapper.SysDatasourceMapper;
import cn.com.carenet.scheduler.utils.ConnectionTypeUtil;
import cn.com.carenet.scheduler.web.JobConfigFileWriter;
import cn.com.carenet.scheduler.web.WorkFlowConfManager;

/**
 * 
 * @author Sherard Lee
 *
 */

@Service
@Deprecated
public class TaskDetailsManager {
//	final private static Logger LOG = LoggerFactory.getLogger(TaskDetailsManager.class);
	final private static List<String> commonOperateModuleNameList = new ArrayList<>();
	final private static List<String> sqlOperateModuleNameList = new ArrayList<>();
	final private static List<String> sparkModuleNameList = new ArrayList<>();
	final private static String updateSQL = "update TASK_DETAILS set TYPE_NAME=?,TASK_REPEAT=?,BIG_BEAN=?,DATA_SOURCES_INFOS=?,OPERATES_INFOS=?,FIELDS_INFOS=?,SQLS_INFOS=?,QUARTZ_TIME=?,QUARTZ_CRON=? where TASK_ID=?";
	final private static String insertSQL = "insert into TASK_DETAILS (TYPE_NAME,TASK_REPEAT,BIG_BEAN,DATA_SOURCES_INFOS,OPERATES_INFOS,FIELDS_INFOS,SQLS_INFOS,QUARTZ_TIME,QUARTZ_CRON,TASK_ID) values(?,?,?,?,?,?,?,?,?,?);";

	@Autowired
	private SparkJobPropertiesBean sparkJobPropertiesBean;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SysDatasourceMapper sysDatasourceMapper;
	@Autowired
	private MetaDataMapMapper metaDataMapMapper;
	@Autowired
	private WorkFlowDAO workFlowDAO;

	static {
		commonOperateModuleNameList.add(WebModuleNameConstant.hadoop);
		commonOperateModuleNameList.add(WebModuleNameConstant.sparkCore);
		commonOperateModuleNameList.add(WebModuleNameConstant.sparkStreaming);
		commonOperateModuleNameList.add(WebModuleNameConstant.sparkGraphx);

		sqlOperateModuleNameList.add(WebModuleNameConstant.unixShell);
		sqlOperateModuleNameList.add(WebModuleNameConstant.sparkSQL);
		sqlOperateModuleNameList.add(WebModuleNameConstant.greenPlumSQL);
		sqlOperateModuleNameList.add(WebModuleNameConstant.hiveQL);
		sqlOperateModuleNameList.add(WebModuleNameConstant.mysqlSQL);
		sqlOperateModuleNameList.add(WebModuleNameConstant.oracleSQL);

		sparkModuleNameList.add(WebModuleNameConstant.sparkCore);
		sparkModuleNameList.add(WebModuleNameConstant.sparkGraphx);
		sparkModuleNameList.add(WebModuleNameConstant.sparkMLLib);
		sparkModuleNameList.add(WebModuleNameConstant.sparkSQL);
		sparkModuleNameList.add(WebModuleNameConstant.sparkStreaming);
	}

	public String insertTaskDetails(WorkFlowConfManager workFlowConfManager) {
		if (workFlowConfManager.isWorkFlowError()) {
			return workFlowConfManager.getWorkFlowErrorMsg();
		}
		updateDataStr(insertSQL, workFlowConfManager);
		return null;
	}

	public String updateTaskDetails(WorkFlowConfManager workFlowConfManager) {
		workFlowDAO.dropOldTimeInfo(workFlowConfManager.getWorkFlowID());
		if (workFlowConfManager.isWorkFlowError()) {
			return workFlowConfManager.getWorkFlowErrorMsg();
		}
		updateDataStr(updateSQL, workFlowConfManager);
		return null;
	}

	private void updateDataStr(String sql, WorkFlowConfManager workFlowConfManager) {
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

		if (repeat) {
			repeatMark = 1;
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
		} else {
			EtlAllBean etlAllBean = new EtlAllBean();
			etlAllBean.setWorkFlowID(workFlowID);
			etlAllBean.setWorkFlowType(workFlowType);
			etlAllBean.setDataSchemas(dataSchemas);
			etlAllBean.setDataSources(dataSources);
			bigBean = JSON.toJSONString(etlAllBean);
		}
		jdbcTemplate.update(sql, workFlowType, repeatMark, bigBean, JSON.toJSONString(dataSources),
				JSON.toJSONString(operateOptions), JSON.toJSONString(dataSchemas), JSON.toJSONString(dataBaseSQLBean),
				timestamp, cronExp, workFlowID);
		setOutputMetaDataMap(dataSources, dataSchemas);

		if (sparkModuleNameList.contains(workFlowType)) {
			JobConfigFileWriter.writeSparkJobConfigFile(workFlowConfManager, sparkJobPropertiesBean);
			JobConfigFileWriter.writeSparkJobToSql(workFlowConfManager, sparkJobPropertiesBean);
		}
	}

	private void setOutputMetaDataMap(Map<String, DataSourceProp> dataSources, Map<String, DataSchema> dataSchemas) {

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
					sysDatasource.setDatabaseName(dataSourceOptionsBean.getKafkaTopics());
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
					sysDatasource.setUrl(
							ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_MYSQL, dataSourceOptionsBean.getIp(),
									dataSourceOptionsBean.getPort(), dataSourceOptionsBean.getDataBaseName()));
					sysDatasource.setDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_MYSQL));
					break;
				case WebModuleNameConstant.oracle:
					sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_ORACLE);
					sysDatasource.setIp(dataSourceOptionsBean.getIp());
					sysDatasource.setPort(dataSourceOptionsBean.getPort());
					sysDatasource.setDatabaseName(dataSourceOptionsBean.getDataBaseName());
					sysDatasource.setUserName(dataSourceOptionsBean.getUserName());
					sysDatasource.setPassword(dataSourceOptionsBean.getPassword());
					sysDatasource.setUrl(
							ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_ORACLE, dataSourceOptionsBean.getIp(),
									dataSourceOptionsBean.getPort(), dataSourceOptionsBean.getDataBaseName()));
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
						for (SingleMetaData dataMeta : dataMetas) {
							MetaDataMap metaDataMap = new MetaDataMap();
							metaDataMap.setOrdernum(dataMeta.getColNum());
							metaDataMap.setColumnName(dataMeta.getFieldName());
							metaDataMap.setColumnType(ConnectionTypeUtil
									.columnNumToType(ConnectionTypeUtil.formatToNumType(dataMeta.getFieldType())));
							metaDataMap.setDateFormat(dataMeta.getDateFormat());
							metaDataMap.setTableName(sysDatasource.getDatabaseName());
							metaDataMap.setDataSourceId(dataSourceId);
							metaDataMaps.add(metaDataMap);
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
