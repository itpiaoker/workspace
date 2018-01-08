package cn.com.carenet.scheduler.bean;

import java.util.List;

public class DataSourceProp {
	final private static String DEFAULT_FIELD_DELIMITER = " ";
	final private static String DEFAULT_RECORD_DELIMITER = "\t\n";
	final private static int DEFAULT_TICK_TUPLE_FREQ = 0;
	final private static int DEFAULT_TASK_NUM = 1;
	final private static int DEFAULT_PARALLELISM_NUM = 1;

	final public static int TYPE_INPUT = 0;
	final public static int TYPE_OUTPUT = 1;
	final public static int TYPE_NONE = -1;

	private String workFlowID;
	/* the data source's ID */
	private String moduleID;
	/* type of data source：mysql,redis,hdfs,ftp... */
	private String sourceName;
	/* 0,input；1,output */
	private int putType = -1;
	private List<OperateComponent> components;
	private List<String> downBranches;

	private List<SingleMetaData> metaDatas;
	/**
	 * The number of place of which field should be removed. start with 0.
	 */
	private List<String> fieldRemove;
	private String recordDelimiter = DEFAULT_RECORD_DELIMITER;
	private String datasouceDelimiter = DEFAULT_FIELD_DELIMITER;
	private String tmpTableName;
	
	private String dataSourceId;

	private String zkServers;
	/* hbase:"/hbase-secure" kafka:"/brokers" */
	private String znodeParent;

	/* kafka */
	private String kafkaGroupID;
	private String kafkaPartitions;
	private String kafkaTopics;
	private int kafkaDurationsSeconds;

	/* local file, ftp, hdfs */
	private String path;
	/* The Internet Position of this data source */
	private String ip;
	/* the IP's port of this data source */
	private int port;
	private int restPort;
	/* es nodes, full url of hdfs, kafka nodes, */
	private String urls;
	private String encoding = "UTF-8";
	private String dataBaseName;
	private String tableName;
	private String userName;
	private String password;
	private String dependents;
	private List<String> independents;
	private int syncCount;
	private int rotationFileSizeInMB;
	private int rotationTimeInSec;
	private String filePrifix;
	private String fileExtension = ".log";

	/* redis */
	// es:id, hbase:rowkey,redis:field
	private String rowKeyFields;

	/* hive */
	private String metaStoreURI;
	private String partitionFields;
	private boolean timeAsPartitionField;

	/* storm configurations */
	private int taskNum = DEFAULT_TASK_NUM;
	private int parallelismNum = DEFAULT_PARALLELISM_NUM;
	private int tickTupleFreqSecs = DEFAULT_TICK_TUPLE_FREQ;

	public String getWorkFlowID() {
		return workFlowID;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}

	public String getModuleID() {
		return moduleID;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public int getPutType() {
		return putType;
	}

	public void setPutType(int putType) {
		this.putType = putType;
	}

	public List<OperateComponent> getComponents() {
		return components;
	}

	public void setComponents(List<OperateComponent> components) {
		this.components = components;
	}

	public List<SingleMetaData> getMetaDatas() {
		return metaDatas;
	}

	public void setMetaDatas(List<SingleMetaData> metaDatas) {
		this.metaDatas = metaDatas;
	}

	public String getRecordDelimiter() {
		return recordDelimiter;
	}

	public void setRecordDelimiter(String recordDelimiter) {
		this.recordDelimiter = recordDelimiter;
	}

	public String getDatasouceDelimiter() {
		return datasouceDelimiter;
	}

	public void setDatasouceDelimiter(String datasouceDelimiter) {
		this.datasouceDelimiter = datasouceDelimiter;
	}

	public String getTmpTableName() {
		return tmpTableName;
	}

	public void setTmpTableName(String tmpTableName) {
		this.tmpTableName = tmpTableName;
	}

	public String getZkServers() {
		return zkServers;
	}

	public void setZkServers(String zkServers) {
		this.zkServers = zkServers;
	}

	public String getZnodeParent() {
		return znodeParent;
	}

	public void setZnodeParent(String znodeParent) {
		this.znodeParent = znodeParent;
	}

	public String getKafkaGroupID() {
		return kafkaGroupID;
	}

	public void setKafkaGroupID(String kafkaGroupID) {
		this.kafkaGroupID = kafkaGroupID;
	}

	public String getKafkaPartitions() {
		return kafkaPartitions;
	}

	public void setKafkaPartitions(String kafkaPartitions) {
		this.kafkaPartitions = kafkaPartitions;
	}

	public String getKafkaTopics() {
		return kafkaTopics;
	}

	public void setKafkaTopics(String kafkaTopics) {
		this.kafkaTopics = kafkaTopics;
	}

	public int getKafkaDurationsSeconds() {
		return kafkaDurationsSeconds;
	}

	public void setKafkaDurationsSeconds(int kafkaDurationsSeconds) {
		this.kafkaDurationsSeconds = kafkaDurationsSeconds;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSyncCount() {
		return syncCount;
	}

	public void setSyncCount(int syncCount) {
		this.syncCount = syncCount;
	}

	public int getRotationFileSizeInMB() {
		return rotationFileSizeInMB;
	}

	public void setRotationFileSizeInMB(int rotationFileSizeInMB) {
		this.rotationFileSizeInMB = rotationFileSizeInMB;
	}

	public int getRotationTimeInSec() {
		return rotationTimeInSec;
	}

	public void setRotationTimeInSec(int rotationTimeInSec) {
		this.rotationTimeInSec = rotationTimeInSec;
	}

	public String getFilePrifix() {
		return filePrifix;
	}

	public void setFilePrifix(String filePrifix) {
		this.filePrifix = filePrifix;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getRowKeyFields() {
		return rowKeyFields;
	}

	public void setRowKeyFields(String rowKeyFields) {
		this.rowKeyFields = rowKeyFields;
	}

	public String getMetaStoreURI() {
		return metaStoreURI;
	}

	public void setMetaStoreURI(String metaStoreURI) {
		this.metaStoreURI = metaStoreURI;
	}

	public String getPartitionFields() {
		return partitionFields;
	}

	public void setPartitionFields(String partitionFields) {
		this.partitionFields = partitionFields;
	}

	public boolean isTimeAsPartitionField() {
		return timeAsPartitionField;
	}

	public void setTimeAsPartitionField(boolean timeAsPartitionField) {
		this.timeAsPartitionField = timeAsPartitionField;
	}

	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public int getParallelismNum() {
		return parallelismNum;
	}

	public void setParallelismNum(int parallelismNum) {
		this.parallelismNum = parallelismNum;
	}

	public int getTickTupleFreqSecs() {
		return tickTupleFreqSecs;
	}

	public void setTickTupleFreqSecs(int tickTupleFreqSecs) {
		this.tickTupleFreqSecs = tickTupleFreqSecs;
	}

	public List<String> getDownBranches() {
		return downBranches;
	}

	public void setDownBranches(List<String> downBranches) {
		this.downBranches = downBranches;
	}

	public List<String> getFieldRemove() {
		return fieldRemove;
	}

	public void setFieldRemove(List<String> fieldRemove) {
		this.fieldRemove = fieldRemove;
	}

	public int getRestPort() {
		return restPort;
	}

	public void setRestPort(int restPort) {
		this.restPort = restPort;
	}

	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	
	

	public List<String> getIndependents() {
		return independents;
	}

	public void setIndependents(List<String> independents) {
		this.independents = independents;
	}

	public void setDependents(String dependents) {
		this.dependents = dependents;
	}
	
	public String getDependents() {
		return dependents;
	}
}
