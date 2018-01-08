package cn.com.carenet.scheduler.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.carenet.scheduler.bean.DataSchema;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.OperateProp;
import cn.com.carenet.scheduler.bean.OperateComponent;
import cn.com.carenet.scheduler.bean.SingleMetaData;
import cn.com.carenet.scheduler.bean.spark.DataSource;
import cn.com.carenet.scheduler.bean.spark.FieldType;
import cn.com.carenet.scheduler.bean.spark.OperateType;
import cn.com.carenet.scheduler.bean.spark.SparkSQLInfos;
import cn.com.carenet.scheduler.bean.spark.SqlType;
import cn.com.carenet.scheduler.constant.Constant;
import cn.com.carenet.scheduler.constant.SparkJobPropertiesBean;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.utils.ConnectionTypeUtil;

public class SparkConfNoAg {

	final private static String operateJsonName = "ot.json";
	final private static String sqlTypeJsonName = "st.json";
	final private static String fieldTypeJsonName = "ft.json";
	final private static String dataSourceJsonFile = "ds.json";
	final private static String apJsonFile = "ap.json";

	final public static String kafka = "kafka";
	final public static String hdfs = "hdfs";
	final public static String mysql = "mysql";
	final public static String elasticSearch = "es";

	final private static Logger LOG = LoggerFactory.getLogger(SparkConfNoAg.class);
	/**  */
	private String workFlowID;

	private String typeName;

	/** 输入输出数据源集合 */
	private List<DataSource> dss = new ArrayList<DataSource>();
	/** 输入输出数据源字段信息集合 */
	private List<FieldType> fieldTypes = new ArrayList<FieldType>();
	/** 操作流程集合 */
	private List<OperateType> operates = new ArrayList<OperateType>();
	/** 操作流程集合 */
	private List<SqlType> sqlTypes = new ArrayList<SqlType>();

	private static String jobSubmitStr = "spark-submit --jars /usr/hdp/2.5.0.0-1245/spark/lib/spark-assembly-1.6.2.2.5.0.0-1245-hadoop2.7.3.2.5.0.0-1245.jar --jars /usr/hdp/2.5.0.0-1245/spark/lib/spark-examples-1.6.2.2.5.0.0-1245-hadoop2.7.3.2.5.0.0-1245.jar --jars  /usr/hdp/2.5.0.0-1245/spark/lib/elasticsearch-spark-13_2.10.jar --master yarn-client --driver-memory 500m --num-executors 4  --executor-cores 4 --executor-memory 4G  --class cn.com.carenet.spark.util.RunSparkTools";
	private static String sourceJarUrl = "/tmp/spark.jar";
	private static String sparkJobUrl = "/tmp/sparkjob/data";

	private static SparkJobPropertiesBean sparkJobPropertiesBean;

	private String jobNowUrl;
	private String jobSubmitFullStr;
	private Set<String> checkKeys = new HashSet<>();

	/** 构造函数 */
	public SparkConfNoAg(String workFlowID, String typeName) {
		jobSubmitStr = sparkJobPropertiesBean.getSubmitcmd();
		sourceJarUrl = sparkJobPropertiesBean.getFrameworkurl();
		sparkJobUrl = sparkJobPropertiesBean.getWorkflowsurl();
		this.workFlowID = workFlowID;
		this.typeName = typeName;
		jobNowUrl = Paths.get(sparkJobUrl, workFlowID + this.typeName).toString();
		String arg0Str;
		switch (this.typeName) {
		case Constant.MODULE_SPARK_CORE_KEY:
			arg0Str = "1";
			break;
		case Constant.MODULE_SPARK_STREAMING_KEY:
			arg0Str = "2";
			break;
		case Constant.MODULE_SPARK_SQL_KEY:
			arg0Str = "3";
			break;
		default:
			arg0Str = "-1";
		}
		StringBuffer strb = new StringBuffer("");
		strb.append(jobSubmitStr).append(" ").append(sourceJarUrl);
		strb.append(" ").append(workFlowID);
		//strb.append(" ").append(arg0Str).append(" ");
		//strb.append(Paths.get(jobNowUrl, "ds.json")).append(" ");
		//strb.append(Paths.get(jobNowUrl, "ft.json")).append(" ");
		//strb.append(Paths.get(jobNowUrl, "ot.json")).append(" ");
		//strb.append(Paths.get(jobNowUrl, "st.json")).append(" ");
		//strb.append(Paths.get(jobNowUrl, "ap.json"));
		jobSubmitFullStr = strb.toString();
		strb = null;

	}

	public SparkConfNoAg prepareFolder() {
		try {
			createFolder(jobNowUrl);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return this;
	}

	public void updateDataSourceProperty(Map<String, DataSourceProp> dataSources, Map<String, DataSchema> dataSchemas) {
		if(dataSources != null){
			for (Entry<String, DataSourceProp> dataSourceEntry : dataSources.entrySet()) {
				String uniqueID = dataSourceEntry.getKey();
				DataSourceProp dataSourceOptionsBean = dataSourceEntry.getValue();
				String sourceName = dataSourceOptionsBean.getSourceName();
				DataSource dataSource = new DataSource();
				switch (sourceName) {
				case kafka:
					dataSource.setZkServers(dataSourceOptionsBean.getZkServers());
					dataSource.setKafkaBrokers(
							String.format("%s:%d", dataSourceOptionsBean.getIp(), dataSourceOptionsBean.getPort()));
					dataSource.setKafkaDurationsSeconds(String.valueOf(dataSourceOptionsBean.getKafkaDurationsSeconds()));
					dataSource.setKafkaGroupID(dataSourceOptionsBean.getKafkaGroupID());
					dataSource.setKafkaPartitions(dataSourceOptionsBean.getKafkaPartitions());
					dataSource.setKafkaTopics(dataSourceOptionsBean.getKafkaTopics());
					break;
				case hdfs:
					dataSource.setHdfsUrl(
							ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_HDFS, dataSourceOptionsBean.getIp(),
									dataSourceOptionsBean.getPort(), dataSourceOptionsBean.getDataBaseName()));
					break;
				case mysql:
					dataSource.setJdbcDB(dataSourceOptionsBean.getDataBaseName());
					dataSource.setJdbcDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_MYSQL));
					dataSource.setJdbcTable(dataSourceOptionsBean.getTableName());
					dataSource.setJdbcUrl(
							ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_MYSQL, dataSourceOptionsBean.getIp(),
									dataSourceOptionsBean.getPort(), dataSourceOptionsBean.getDataBaseName()));
					dataSource.setJdbcUser(dataSourceOptionsBean.getUserName());
					dataSource.setJdbcPasswd(dataSourceOptionsBean.getPassword());
					break;
				case elasticSearch:
					dataSource.setEsIndexName(dataSourceOptionsBean.getDataBaseName());
					dataSource.setEsIndexType(dataSourceOptionsBean.getTableName());
					dataSource.setEsNodes(dataSourceOptionsBean.getIp());
					dataSource.setEsPort(String.valueOf(dataSourceOptionsBean.getRestPort()));
					break;
				}


				dataSource.setTableName(dataSourceOptionsBean.getDataBaseName());
				dataSource.setTempTableName(dataSourceOptionsBean.getTmpTableName());
				dataSource.setDelimiter(dataSourceOptionsBean.getDatasouceDelimiter());
				dataSource.setDataSourceID(uniqueID);
				dataSource.setSourceName(dataSourceOptionsBean.getSourceName());
				dataSource.setWorkFlowID(workFlowID);
				dataSource.setSourceType(String.valueOf(dataSourceOptionsBean.getPutType()));
				dataSource.setDependents(dataSourceOptionsBean.getDependents());
				
				
				List<String> independents = dataSourceOptionsBean.getIndependents();
				if (independents != null)
					dataSource.setIndependents(independents);
				
				
				
				List<String> fieldRemove = dataSourceOptionsBean.getFieldRemove();
				if (fieldRemove != null)
					dataSource.setFieldRemove(fieldRemove);
				List<OperateComponent> components = dataSourceOptionsBean.getComponents();
				if(components != null && components.size() > 0){
					OperateComponent component = components.get(0);
					String preOpIDStr = component.getPreOpID();
					String preOpID = preOpIDStr.substring(Constant.PREFIX_WINDOW_KEY.length(), preOpIDStr.length());
					dataSource.setPreOpIds(preOpID);
				}
				
				List<String> downBranches = dataSourceOptionsBean.getDownBranches();
				for (String downBranche : downBranches) {
					String preOpID = downBranche.substring(Constant.PREFIX_WINDOW_KEY.length(), downBranche.length());
					dataSource.setNextOpType(preOpID);
					break;
				}
				//
				dss.add(dataSource);
			}
		}
	}

	public void updateFieldTypeProperty(Map<String, DataSourceProp> dataSourceMap, Map<String, DataSchema> dataSchemas) {
		
		for (Entry<String, DataSchema> dataSchemasEntry : dataSchemas.entrySet()) {
			String uniqueID = dataSchemasEntry.getKey();
			DataSchema dataSchema = dataSchemasEntry.getValue();
			if(dataSchema.getMetaDatas() != null && !dataSchema.getMetaDatas().isEmpty() && (!checkKeys.contains(uniqueID))){
				for (SingleMetaData fieldOptionInfos : dataSchema.getMetaDatas()) {
					FieldType fieldType = new FieldType();
					fieldType.setColNum(String.valueOf(fieldOptionInfos.getColNum()));
					fieldType.setFieldName(fieldOptionInfos.getFieldName());
					fieldType.setFieldType(fieldOptionInfos.getFieldType());
					if (fieldOptionInfos.getDateFormat() != null) {
						fieldType.setFormatter(fieldOptionInfos.getDateFormat());
					}
					fieldType.setFieldFilter(String.valueOf(fieldOptionInfos.getFieldFilter()));
					if (fieldOptionInfos.getFieldFilterVal() != null) {
						fieldType.setFieldFilterVal(fieldOptionInfos.getFieldFilterVal());
					}
					fieldType.setWorkFlowID(workFlowID);
					fieldType.setDataSourceID(uniqueID);
					fieldTypes.add(fieldType);
				}
			}
			
		}
		
		
		
		
		
//		for (Entry<String, DataSourceProp> dataSourceEntry : dataSourceMap.entrySet()) {
//			String uniqueID = dataSourceEntry.getKey();
//			DataSourceProp dataSourceOptionsBean = dataSourceEntry.getValue();
//			if (dataSourceOptionsBean.getMetaDatas() != null && !dataSourceOptionsBean.getMetaDatas().isEmpty()
//					&& (!checkKeys.contains(uniqueID))) {
//				checkKeys.add(uniqueID);
//				for (SingleMetaData operateOptionsDataSourceInfo : dataSourceOptionsBean.getMetaDatas()) {
//					FieldType fieldType = new FieldType();
//					fieldType.setColNum(String.valueOf(operateOptionsDataSourceInfo.getColNum()));
//					fieldType.setFieldName(operateOptionsDataSourceInfo.getFieldName());
//					fieldType.setFieldType(operateOptionsDataSourceInfo.getFieldType());
//					if (operateOptionsDataSourceInfo.getDateFormat() != null) {
//						fieldType.setFormatter(operateOptionsDataSourceInfo.getDateFormat());
//					}
//					fieldType.setFieldFilter(String.valueOf(operateOptionsDataSourceInfo.getFieldFilter()));
//					if (operateOptionsDataSourceInfo.getFieldFilterVal() != null) {
//						fieldType.setFieldFilterVal(operateOptionsDataSourceInfo.getFieldFilterVal());
//					}
//					fieldType.setWorkFlowID(workFlowID);
//					fieldType.setDataSourceID(uniqueID);
//					fieldTypes.add(fieldType);
//				}
//			}
//		}
		
		
	}

	public void updateOperateTypeProperty(Map<String, DataSourceProp> dataSourceMap,
			Map<String, OperateProp> operateMap) {
		
		if(operateMap != null){
			for (Entry<String, OperateProp> operateEntry : operateMap.entrySet()) {
				String operateUniqueID = operateEntry.getKey();
				OperateProp operateOptionsBean = operateEntry.getValue();
				OperateType operateType = new OperateType();
				operateType.setAnd(operateOptionsBean.getAnd());
				operateType.setBetween(operateOptionsBean.getBetween());
				operateType.setIsSection(String.valueOf(operateOptionsBean.isSection()));
				operateType.setOperateID(operateUniqueID);
//				operateOptionsBean.getmo
//				downBranche.substring(Constant.PREFIX_WINDOW_KEY.length(), downBranche.length());
				operateType.setAlgorithmModelID(operateOptionsBean.getAlgorithmModelID());
				operateType.setComponentID(operateUniqueID);
				String operateTypeName = operateOptionsBean.getSourceName();
				if (operateTypeName.equals(WebModuleNameConstant.groupBy)) {
					operateType.setTableName(operateOptionsBean.getTempTableName());
					operateType.setGroupFieldName(operateOptionsBean.getFieldNames());
				}
				operateType.setOperateType(operateOptionsBean.getSourceName());
				String valueStr = operateOptionsBean.getValues();
				if (valueStr != null) {
					List<String> values = new ArrayList<>();
					for (String value : StringUtils.splitPreserveAllTokens(valueStr, ",")) {
						values.add(value);
					}
					operateType.setValues(values);
				}
				String leftJoinSortStr = operateOptionsBean.getLeftJoinSort();
				if (leftJoinSortStr != null) {
					List<String> leftJoinSortStrs = new ArrayList<>();
					for (String value : StringUtils.splitPreserveAllTokens(leftJoinSortStr, ",")) {
						leftJoinSortStrs.add(value);
					}
					operateType.setLeftJoinSort(leftJoinSortStrs);
				}
				operateType.setWorkFlowID(workFlowID);
				operateType.setIsAscending(String.valueOf(operateOptionsBean.isUpSort()));
				List<String> preOpType = new ArrayList<>();
				List<String> sourceID = new ArrayList<String>();
				for (OperateComponent component : operateOptionsBean.getComponents()) {
					preOpType.add(component.getPreOpID());
					sourceID.add(component.getSourcePrimary());
				}
				operateType.setDataSourceID(sourceID);
				operateType.setPreOpType(preOpType);
				operateType.setFieldNames(operateOptionsBean.getFieldNames());
				operateType.setTmpTableName(operateOptionsBean.getTempTableName());
				if (operateOptionsBean.getFieldNames() != null) {

				}
				
				List<String> downBranches = operateOptionsBean.getDownBranches();
				for (String downBranche : downBranches) {
					String preOpID = downBranche.substring(Constant.PREFIX_WINDOW_KEY.length(), downBranche.length());
					operateType.setNextOpType(preOpID);
					break;
				}
				
				List<String> independents = operateOptionsBean.getIndependents();
				operateType.setIndependents(independents);
				operates.add(operateType);
			}
		}

	}


	public void updateSqlTypeProperty(SparkSQLInfos sparkSQLInfos) {
		for (SqlType fakeSqlType : sparkSQLInfos.getSQLs()) {
			SqlType sqlType = new SqlType();
			sqlType.setWorkFlowID(workFlowID);
			sqlType.setAsTableName(fakeSqlType.getAsTableName());
			sqlType.setIsSave(fakeSqlType.getIsSave());
			sqlType.setSql(fakeSqlType.getSql());
			sqlType.setSqlID(fakeSqlType.getSqlID());
			sqlTypes.add(sqlType);
		}
	}

	public void createDataSourceJsonFile() throws IOException {
		createJsonFile(Paths.get(jobNowUrl, dataSourceJsonFile).toString(), dss);
	}

	public void createFieldTypeJsonFile() throws IOException {
		createJsonFile(Paths.get(jobNowUrl, fieldTypeJsonName).toString(), fieldTypes);
	}

	public void createSqlTypeJsonFile() throws IOException {
		createJsonFile(Paths.get(jobNowUrl, sqlTypeJsonName).toString(), sqlTypes);
	}

	public void createOperateJsonFile() throws IOException {
		createJsonFile(Paths.get(jobNowUrl, operateJsonName).toString(), operates);
	}

	public void createapJsonFile() throws IOException {
		createJsonFile(Paths.get(jobNowUrl, apJsonFile).toString(), operates);
	}

	public void createJsonFile(String fileName, Object object) throws IOException {
		File file = new File(fileName);
		FileWriter writer = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		JSONArray o = (JSONArray) JSONObject.toJSON(object);
		bufferedWriter.write(o.toJSONString());
		bufferedWriter.flush();
		bufferedWriter.close();
	}

	private static void createFolder(String folderName) throws IOException {
		File folder = new File(folderName);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	@Deprecated
	protected static void copyToFolder(String src, String des) throws IOException {
		File file1 = new File(src);
		File file2 = new File(des);
		if (!file2.exists()) {
			file2.mkdirs();
		}
		fileCopy(file1.getPath(), Paths.get(des, file1.getName()).toString());
	}

	/**
	 * copy a file by using file channel.
	 * 
	 * @param src
	 *            source of the file
	 * @param des
	 *            absolute url of the destination including file name;
	 * @throws IOException
	 */
	private static void fileCopy(String src, String des) throws IOException {
		FileInputStream fileInput = null;
		FileOutputStream fileOutput = null;
		FileChannel fileChannel_in = null;
		FileChannel fileChannel_out = null;
		fileInput = new FileInputStream(src);
		fileOutput = new FileOutputStream(des);
		fileChannel_in = fileInput.getChannel();
		fileChannel_out = fileOutput.getChannel();
		fileChannel_in.transferTo(0, fileChannel_in.size(), fileChannel_out);
		if (fileInput != null)
			fileInput.close();
		if (fileOutput != null)
			fileOutput.close();
		if (fileChannel_in != null)
			fileChannel_in.close();
		if (fileChannel_out != null)
			fileChannel_out.close();
	}

	public String getJobSubmitFullStr() {
		return jobSubmitFullStr;
	}

	public static void setSparkJobPropertiesBean(SparkJobPropertiesBean sparkJobPropertiesBean) {
		SparkConfNoAg.sparkJobPropertiesBean = sparkJobPropertiesBean;
	}

	public List<DataSource> getDss() {
		return dss;
	}

	public List<FieldType> getFieldTypes() {
		return fieldTypes;
	}

	public List<OperateType> getOperates() {
		return operates;
	}

	public List<SqlType> getSqlTypes() {
		return sqlTypes;
	}
}
