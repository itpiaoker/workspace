package cn.com.carenet.scheduler.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.carenet.scheduler.bean.DataBaseSQLBean;
import cn.com.carenet.scheduler.bean.DataSchema;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.OperateProp;
import cn.com.carenet.scheduler.bean.spark.SparkSQLInfos;
import cn.com.carenet.scheduler.bean.spark.SqlType;
import cn.com.carenet.scheduler.constant.Constant;
import cn.com.carenet.scheduler.constant.SparkJobPropertiesBean;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.mapper.TaskDetailsMapper;

public class JobConfigFileWriter {
	final private static Logger LOG = LoggerFactory.getLogger(JobConfigFileWriter.class);
	private static String workFlowType;
	private static String workFlowID;
	private static Map<String, DataSchema> dataSchemas;
	private static Map<String, DataSourceProp> dataSources;
	private static Map<String, OperateProp> operateOptions;
	private static DataBaseSQLBean dataBaseSQLBean;

	@Autowired
	private static TaskDetailsMapper taskDetailsMapper;
	
	@Deprecated
	public static void writeSparkJobConfigFile(WorkFlowConfManager workFlowConfManager,
			SparkJobPropertiesBean sparkJobPropertiesBean) {
		// TASK_ID
		workFlowID = workFlowConfManager.getWorkFlowID();
		// TYPE_NAME
		workFlowType = workFlowConfManager.getWorkFlowType();
		// FIELDS_INFOS
		dataSchemas = workFlowConfManager.getDataSchemas();
		// DATA_SOURCES_INFOS
		dataSources = workFlowConfManager.getDataSourceMap();
		// OPERATES_INFOS
		operateOptions = workFlowConfManager.getOperateMap();
		// SQLS_INFOS
		dataBaseSQLBean = workFlowConfManager.getDataBaseSQLBean();

		SparkConfNoAg.setSparkJobPropertiesBean(sparkJobPropertiesBean);
		SparkConfNoAg sparkConfUtils;
		switch (workFlowType) {
		case WebModuleNameConstant.sparkSQL:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_SQL_KEY);
			sparkConfUtils.updateSqlTypeProperty(sparkSqlSolver());
			break;
		case WebModuleNameConstant.sparkGraphx:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_GRAPHX_KEY);
			break;
		case WebModuleNameConstant.sparkMLLib:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_MLLIB_KEY);
			break;
		case WebModuleNameConstant.sparkStreaming:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_STREAMING_KEY);
			break;
		default:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_CORE_KEY);
		}

		try {
			sparkConfUtils.updateDataSourceProperty(dataSources, dataSchemas);
			sparkConfUtils.updateOperateTypeProperty(dataSources, operateOptions);
			sparkConfUtils.prepareFolder();
			sparkConfUtils.createDataSourceJsonFile();
			sparkConfUtils.createFieldTypeJsonFile();
			sparkConfUtils.createOperateJsonFile();
			sparkConfUtils.createSqlTypeJsonFile();
			sparkConfUtils.createapJsonFile();
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}

	}
	
	/**
	 * 	将spark job的信息写入到mysql数据库中
	 * 	不再生成配置文件
	 */
	public static TaskDetails writeSparkJobToSql(WorkFlowConfManager workFlowConfManager,
			SparkJobPropertiesBean sparkJobPropertiesBean) {
		
		TaskDetails record = new TaskDetails();
		
		// TASK_ID
		workFlowID = workFlowConfManager.getWorkFlowID();
		// TYPE_NAME
		workFlowType = workFlowConfManager.getWorkFlowType();
		// FIELDS_INFOS
		dataSchemas = workFlowConfManager.getDataSchemas();
		// DATA_SOURCES_INFOS
		dataSources = workFlowConfManager.getDataSourceMap();
		// OPERATES_INFOS
		operateOptions = workFlowConfManager.getOperateMap();
		// SQLS_INFOS
		dataBaseSQLBean = workFlowConfManager.getDataBaseSQLBean();
		

		SparkConfNoAg.setSparkJobPropertiesBean(sparkJobPropertiesBean);
		SparkConfNoAg sparkConfUtils;
		switch (workFlowType) {
		case WebModuleNameConstant.sparkSQL:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_SQL_KEY);
			sparkConfUtils.updateSqlTypeProperty(sparkSqlSolver());
			break;
		case WebModuleNameConstant.sparkGraphx:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_GRAPHX_KEY);
			break;
		case WebModuleNameConstant.sparkMLLib:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_MLLIB_KEY);
			break;
		case WebModuleNameConstant.sparkStreaming:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_STREAMING_KEY);
			break;
		default:
			sparkConfUtils = new SparkConfNoAg(workFlowID, Constant.MODULE_SPARK_CORE_KEY);
		}

		try {
			sparkConfUtils.updateDataSourceProperty(dataSources, dataSchemas);
			sparkConfUtils.updateOperateTypeProperty(dataSources, operateOptions);
			sparkConfUtils.updateFieldTypeProperty(dataSources, dataSchemas);
			
			record.setTaskId(workFlowID);
			JSONArray dssJson = (JSONArray) JSONObject.toJSON(sparkConfUtils.getDss());
			JSONArray fieldsJson = (JSONArray) JSONObject.toJSON(sparkConfUtils.getFieldTypes());
			JSONArray operatesJson = (JSONArray) JSONObject.toJSON(sparkConfUtils.getOperates());
			JSONArray sqlTypesJson = (JSONArray) JSONObject.toJSON(sparkConfUtils.getSqlTypes());
			if(dssJson != null){
				record.setDataSourcesInfos(dssJson.toJSONString());
			}
			if(fieldsJson != null){
				record.setFieldsInfos(fieldsJson.toJSONString());
			}
			if(operatesJson != null){
				record.setOperatesInfos(operatesJson.toJSONString());
			}
			if(sqlTypesJson != null){
				record.setSqlsInfos(sqlTypesJson.toJSONString());
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		
		return record;
		
	}

	private static SparkSQLInfos sparkSqlSolver() {
		SparkSQLInfos operateBean_SparkSQL = new SparkSQLInfos();
		List<SqlType> sQLs = new ArrayList<>();
		if (dataBaseSQLBean != null) {
			// sql 语句全部转成小写
			String sqlStrNeedCheck = dataBaseSQLBean.getScript();
			// 按字符[;]拆分多条sql语句
			String[] sqlStrs = sqlStrNeedCheck.split(";");
			if (sqlStrs != null && sqlStrs.length > 0) {
				for (int i = 0; i < sqlStrs.length; i++) {
					//
					SqlType sqlType = new SqlType();
					// 获取单条sql语句并去除前后空格
					String sqlStr = sqlStrs[i];
					sqlStr = sqlStr.trim();
					// 获取单条sql语句最后一个as字符所在的索引位置
					// 通过该索引位置获取临时表名 asTableName 并去除前后空格
					// 也就是说每一条sql语句的末尾临时表名 asTableName 是不能少的
					// 因为SparkSql需要先将数据集注册为临时表才可以使用sql语句处理数据
					// 在后面的sql语句或者drop语句有可能需要使用临时表名 asTableName
					int asPosIndex = sqlStr.lastIndexOf("as");
					String asTableName = "";
					String sqlT = "";
					if (sqlStr.startsWith("select")) {
						sqlT = sqlStr.substring("select".length());
						asTableName = sqlStr.substring(asPosIndex + "as".length(), sqlStr.length()).trim();
					} else if (sqlStr.startsWith("drop")) {
						sqlT = sqlStr.substring("drop".length());
					}

					sqlType.setSqlID("0");
					sqlType.setIsSave(String.valueOf(true));
					sqlType.setAsTableName(asTableName);
					sqlType.setSql(sqlStr);
					sqlType.setSqlType(sqlT);
					sQLs.add(sqlType);
				}
			}
		}
		operateBean_SparkSQL.setSQLs(sQLs);
		operateBean_SparkSQL.setOperateType(WebModuleNameConstant.sparkSQL);
		operateBean_SparkSQL.setOperateID(dataBaseSQLBean.getSourceTabID());
		return operateBean_SparkSQL;
	}
}
