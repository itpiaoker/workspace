package cn.com.carenet.scheduler.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Sherard Lee
 * @since 25/MAY/2017
 */
public class DataSourceOptionsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7197816674815816675L;
	final private static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	final private static String DEFAULT_FIELD_DELIMITER = " ";
	final private static String DEFAULT_RECORD_DELIMITER = "\t\n";
	final private static int DEFAULT_TICK_TUPLE_FREQ = 0;
	final private static int DEFAULT_TASK_NUM = 1;
	final private static int DEFAULT_PARALLELISM_NUM = 1;

	private String workFlowID;
	/* 数据源ID */
	private String datasourceID;
	/* 数据源类型：mysql,redis,hdfs,ftp... */
	private String sourceName;
	/* 0,input；1,output */
	private int sourceType = -1;
	/* The Internet Position of this data source */
	private String ip;
	/* the IP's port of this data source */
	private int port;
	
	private String databaseName;

	private List<SingleMetaData> metaDatas;
	private List<OperateComponent> components;

	/* storm configurations */
	private int workerNum = DEFAULT_TASK_NUM;
	private int taskNum = DEFAULT_TASK_NUM;
	private int parallelismNum = DEFAULT_PARALLELISM_NUM;
	private int tickTupleFreqSecs = DEFAULT_TICK_TUPLE_FREQ;

	private String timeFormat = DEFAULT_TIME_FORMAT;
	private String recordDelimiter = DEFAULT_RECORD_DELIMITER;
	private String datasouceDelimiter = DEFAULT_FIELD_DELIMITER;
	private String tableName;

	/* kafka */
	private String zkServers;
	private int kafkaDurationsSeconds;
	private String kafkaGroupID;
	private String kafkaPartitions;
	private String kafkaTopics;

	/* local file, ftp, hdfs */
	private String sshHost;
	private String sshUser;
	private String sshPasswd;
	private String path;
	private int rotationFileSizeInMB;
	private int rotationTimeInSec;
	private String ftpUrl;
	private String ftpHost;
	private int ftpPort;
	private String ftpUserName;
	private String ftpPassword;
	private String ftpEncoding = "UTF-8";
	private String filePrifix;
	private String fileExtension = ".log";
	private int syncCount;

	/* relational database */
	private String jdbcDB;
	private String jdbcTable;
	private String jdbcType;
	private String jdbcUser;
	private String jdbcPasswd;
	private String jdbcDriver;

	/* elastic Search */
	private String esIndexName;
	private String esTypeName;
	private int esBoltFlushEntriesSize;
	private boolean esBoltTickTupleFlush;
	private boolean esBoltWriteAck;
	private boolean esIndexAutoCreate = true;
	private boolean esInputJson = true;

	/* redis */
	// es:id, hbase:rowkey,redis:field
	private String rowKeyFields;
	private String valueFields;
	private String redisHost;
	private int redisPort;
	private String redisPassword;
	private int redisTimeOut;

	/* hbase */
	private String hBaseColumnFamily;
	private String hBaseZkQuorum;
	private int hBaseZkPort = 2181;
	private int hBaseMasterPort = 16000;
	private String znodeParent = "/hbase-secure";

	/* hive */
	private String metaStoreURI;
	private String dbName;
	private boolean timeAsPartitionField;
	private int txnsPerBath = 100;
	private int batchSize = 15000;
	private int idleTimeout = 60000;
	private int callTimeout = 0;
	private int heartBeatInterval = 60;
	private boolean autoCreatePartitions;
	private int tickTupleInterval = 15;
	private String partitionFields;

	public String getWorkFlowID() {
		return workFlowID;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}

	public String getDatasourceID() {
		return datasourceID;
	}

	public void setDatasourceID(String datasourceID) {
		this.datasourceID = datasourceID;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
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

	public List<SingleMetaData> getMetaDatas() {
		return metaDatas;
	}

	public void setMetaDatas(List<SingleMetaData> metaDatas) {
		this.metaDatas = metaDatas;
	}

	public List<OperateComponent> getComponents() {
		return components;
	}

	public void setComponents(List<OperateComponent> components) {
		this.components = components;
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

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getZkServers() {
		return zkServers;
	}

	public void setZkServers(String zkServers) {
		this.zkServers = zkServers;
	}

	public int getKafkaDurationsSeconds() {
		return kafkaDurationsSeconds;
	}

	public void setKafkaDurationsSeconds(int kafkaDurationsSeconds) {
		this.kafkaDurationsSeconds = kafkaDurationsSeconds;
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

	public String getSshHost() {
		return sshHost;
	}

	public void setSshHost(String sshHost) {
		this.sshHost = sshHost;
	}

	public String getSshUser() {
		return sshUser;
	}

	public void setSshUser(String sshUser) {
		this.sshUser = sshUser;
	}

	public String getSshPasswd() {
		return sshPasswd;
	}

	public void setSshPasswd(String sshPasswd) {
		this.sshPasswd = sshPasswd;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public String getFtpUrl() {
		return ftpUrl;
	}

	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getFtpEncoding() {
		return ftpEncoding;
	}

	public void setFtpEncoding(String ftpEncoding) {
		this.ftpEncoding = ftpEncoding;
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

	public int getSyncCount() {
		return syncCount;
	}

	public void setSyncCount(int syncCount) {
		this.syncCount = syncCount;
	}

	public String getJdbcDB() {
		return jdbcDB;
	}

	public void setJdbcDB(String jdbcDB) {
		this.jdbcDB = jdbcDB;
	}

	public String getJdbcTable() {
		return jdbcTable;
	}

	public void setJdbcTable(String jdbcTable) {
		this.jdbcTable = jdbcTable;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJdbcUser() {
		return jdbcUser;
	}

	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}

	public String getJdbcPasswd() {
		return jdbcPasswd;
	}

	public void setJdbcPasswd(String jdbcPasswd) {
		this.jdbcPasswd = jdbcPasswd;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getEsIndexName() {
		return esIndexName;
	}

	public void setEsIndexName(String esIndexName) {
		this.esIndexName = esIndexName;
	}

	public String getEsTypeName() {
		return esTypeName;
	}

	public void setEsTypeName(String esTypeName) {
		this.esTypeName = esTypeName;
	}

	public int getEsBoltFlushEntriesSize() {
		return esBoltFlushEntriesSize;
	}

	public void setEsBoltFlushEntriesSize(int esBoltFlushEntriesSize) {
		this.esBoltFlushEntriesSize = esBoltFlushEntriesSize;
	}

	public boolean isEsBoltTickTupleFlush() {
		return esBoltTickTupleFlush;
	}

	public void setEsBoltTickTupleFlush(boolean esBoltTickTupleFlush) {
		this.esBoltTickTupleFlush = esBoltTickTupleFlush;
	}

	public boolean isEsBoltWriteAck() {
		return esBoltWriteAck;
	}

	public void setEsBoltWriteAck(boolean esBoltWriteAck) {
		this.esBoltWriteAck = esBoltWriteAck;
	}

	public boolean isEsIndexAutoCreate() {
		return esIndexAutoCreate;
	}

	public void setEsIndexAutoCreate(boolean esIndexAutoCreate) {
		this.esIndexAutoCreate = esIndexAutoCreate;
	}

	public boolean isEsInputJson() {
		return esInputJson;
	}

	public void setEsInputJson(boolean esInputJson) {
		this.esInputJson = esInputJson;
	}

	public String getRowKeyFields() {
		return rowKeyFields;
	}

	public void setRowKeyFields(String rowKeyFields) {
		this.rowKeyFields = rowKeyFields;
	}

	public String getValueFields() {
		return valueFields;
	}

	public void setValueFields(String valueFields) {
		this.valueFields = valueFields;
	}

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public String getRedisPassword() {
		return redisPassword;
	}

	public void setRedisPassword(String redisPassword) {
		this.redisPassword = redisPassword;
	}

	public int getRedisTimeOut() {
		return redisTimeOut;
	}

	public void setRedisTimeOut(int redisTimeOut) {
		this.redisTimeOut = redisTimeOut;
	}

	public String gethBaseColumnFamily() {
		return hBaseColumnFamily;
	}

	public void sethBaseColumnFamily(String hBaseColumnFamily) {
		this.hBaseColumnFamily = hBaseColumnFamily;
	}

	public String gethBaseZkQuorum() {
		return hBaseZkQuorum;
	}

	public void sethBaseZkQuorum(String hBaseZkQuorum) {
		this.hBaseZkQuorum = hBaseZkQuorum;
	}

	public int gethBaseZkPort() {
		return hBaseZkPort;
	}

	public void sethBaseZkPort(int hBaseZkPort) {
		this.hBaseZkPort = hBaseZkPort;
	}

	public int gethBaseMasterPort() {
		return hBaseMasterPort;
	}

	public void sethBaseMasterPort(int hBaseMasterPort) {
		this.hBaseMasterPort = hBaseMasterPort;
	}

	public String getZnodeParent() {
		return znodeParent;
	}

	public void setZnodeParent(String znodeParent) {
		this.znodeParent = znodeParent;
	}

	public String getMetaStoreURI() {
		return metaStoreURI;
	}

	public void setMetaStoreURI(String metaStoreURI) {
		this.metaStoreURI = metaStoreURI;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public boolean isTimeAsPartitionField() {
		return timeAsPartitionField;
	}

	public void setTimeAsPartitionField(boolean timeAsPartitionField) {
		this.timeAsPartitionField = timeAsPartitionField;
	}

	public int getTxnsPerBath() {
		return txnsPerBath;
	}

	public void setTxnsPerBath(int txnsPerBath) {
		this.txnsPerBath = txnsPerBath;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public int getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public int getCallTimeout() {
		return callTimeout;
	}

	public void setCallTimeout(int callTimeout) {
		this.callTimeout = callTimeout;
	}

	public int getHeartBeatInterval() {
		return heartBeatInterval;
	}

	public void setHeartBeatInterval(int heartBeatInterval) {
		this.heartBeatInterval = heartBeatInterval;
	}

	public boolean isAutoCreatePartitions() {
		return autoCreatePartitions;
	}

	public void setAutoCreatePartitions(boolean autoCreatePartitions) {
		this.autoCreatePartitions = autoCreatePartitions;
	}

	public int getTickTupleInterval() {
		return tickTupleInterval;
	}

	public void setTickTupleInterval(int tickTupleInterval) {
		this.tickTupleInterval = tickTupleInterval;
	}

	public String getPartitionFields() {
		return partitionFields;
	}

	public void setPartitionFields(String partitionFields) {
		this.partitionFields = partitionFields;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public int getWorkerNum() {
		return workerNum;
	}

	public void setWorkerNum(int workerNum) {
		this.workerNum = workerNum;
	}

}
