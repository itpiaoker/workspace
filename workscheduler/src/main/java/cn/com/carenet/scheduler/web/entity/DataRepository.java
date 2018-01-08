package cn.com.carenet.scheduler.web.entity;

import java.util.List;

import cn.com.carenet.scheduler.constant.AlgorithmConstant;

/**
 * 
 * @author Sherard Lee
 * @since 22/Aug/2017
 */
public class DataRepository {
	final public static String WF_NAME_START = "start";
	final public static String WF_NAME_END = "end";
	final public static String WF_GROUP_INPUT = "1";
	final public static String WF_GROUP_OUTPUT = "5";
	final public static String WF_GROUP_OP = "3";
	/* data source */
	final public static String WF_NAME_KAFKA = "kafka";
	final public static String WF_NAME_LOCALFILE = "local";
	final public static String WF_NAME_HDFS = "hdfs";
	final public static String WF_NAME_ELASTICSEARCH = "es";
	final public static String WF_NAME_REDIS = "redis";
	final public static String WF_NAME_HBASE = "HBase";
	final public static String WF_NAME_GREENPLUM = "gp";
	final public static String WF_NAME_FTP = "ftp";
	final public static String WF_NAME_MYSQL = "mysql";
	final public static String WF_NAME_ORACLE = "oracle";
	final public static String WF_NAME_HIVE = "hive";

	/* operating module */
	final public static String WF_NAME_FILTER = "filter";
	final public static String WF_NAME_COUNT = "count";
	final public static String WF_NAME_GROUPBY = "group";
	final public static String WF_NAME_DISTINCT = "distinct";
	final public static String WF_NAME_SORTBY = "sortBy";
	final public static String WF_NAME_JOIN = "join";
	final public static String WF_NAME_SUM = "sum";
	final public static String WF_NAME_REMOVEFIELD = "removeField";
	final public static String WF_NAME_ALGORITHM_MODEL = "spark-mllib";
	final public static String WF_NAME_LINER_R = AlgorithmConstant.ALGORITHM_NAME_LINER_R_KEY;
	final public static String WF_NAME_LOGIC_R = AlgorithmConstant.ALGORITHM_NAME_LOGIC_R_KEY;
	public final static String WF_NAME_ALS_KEY = AlgorithmConstant.ALGORITHM_NAME_ALS_KEY;
	public final static String WF_NAME_KMEANS_KEY = AlgorithmConstant.ALGORITHM_NAME_KMEANS_KEY;
	public final static String WF_NAME_SVM_KEY = AlgorithmConstant.ALGORITHM_NAME_SVM_KEY;
	public final static String WF_NAME_BAYES_KEY = AlgorithmConstant.ALGORITHM_NAME_BAYES_KEY;
	public final static String WF_NAME_RANDOM_FOREST_KEY = AlgorithmConstant.ALGORITHM_NAME_R_FOREST_KEY;
	public final static String WF_NAME_RANDOM_FOREST_RETURN_KEY = AlgorithmConstant.ALGORITHM_NAME_R_FOREST_RETURN_KEY;
	public final static String WF_NAME_DECISION_TREE_KEY = AlgorithmConstant.ALGORITHM_NAME_DECISION_TREE_KEY;
	public final static String WF_NAME_DECISION_TREE_RETURN_KEY = AlgorithmConstant.ALGORITHM_NAME_DECISION_TREE_RETURN_KEY;
	public final static String WF_NAME_IS_KEY = AlgorithmConstant.ALGORITHM_NAME_IS_KEY;
	public final static String WF_NAME_GMG_KEY = AlgorithmConstant.ALGORITHM_NAME_GMG_KEY;
	public final static String WF_NAME_PIC_KEY = AlgorithmConstant.ALGORITHM_NAME_PIC_KEY;
	public final static String WF_NAME_FP_TREE_KEY = AlgorithmConstant.ALGORITHM_NAME_FP_TREE_KEY;
	public final static String WF_NAME_PCA_KEY = AlgorithmConstant.ALGORITHM_NAME_PCA_KEY;
	public final static String WF_NAME_SVD_KEY = AlgorithmConstant.ALGORITHM_NAME_SVD_KEY;
	public final static String WF_NAME_WORD_2_VEC_KEY = AlgorithmConstant.ALGORITHM_NAME_WORD_2_VEC_KEY;
	public final static String WF_NAME_TF_IDF_KEY = AlgorithmConstant.ALGORITHM_NAME_TF_IDF_KEY;
	public final static String WF_NAME_APRIORI_KEY = AlgorithmConstant.ALGORITHM_NAME_APRIORI_KEY;
	/* 模块_key */
	final public static String WF_NAME_STORM = "storm";
	final public static String WF_NAME_SPARKCORE = "spark-core";
	final public static String WF_NAME_SPARKSQL = "spark-sql";
	final public static String WF_NAME_SPARKSTREAMING = "spark-streaming";
	final public static String WF_NAME_SPARK_MLLIB = "spark-mllib";
	final public static String WF_NAME_SPARKGRAPHX = "spark-graphx";
	final public static String WF_NAME_GREENPLUMSQL = "gp-sql";
	final public static String WF_NAME_HIVEQL = "hive-sql";
	final public static String WF_NAME_MYSQLSQL = "mysql-sql";
	final public static String WF_NAME_ORACLESQL = "oracle-sql";
	final public static String WF_NAME_UNIXSHELL = "unixShell";
	final public static String WF_NAME_MAPREDUCE = "MapReduce";

	final public static String WF_FIELD_FILTER_NONE = "-1";
	final public static String WF_FIELD_FILTER_TEL = "0";
	final public static String WF_FIELD_FILTER_ENUM = "1";
	final public static String WF_FIELD_FILTER_MAXLEN = "2";
	/* cron job time info */
	private String type;
	private String repeat;
	private Integer minute;
	private Integer second;
	private Integer day_hour;
	private Integer day_minute;
	private Integer week;
	private Integer day;

	private String id;
	private String wf_name;
	private String wf_group;
	
	private String dataSourceId;
	private String databaseName;
	private String tableName;
	private String recordDelimiter;
	/* field properties */
	/**
	 * {index:-1, text: '使用过滤条件'}, {index:0, text: '手机号脱敏'}, {index:1, text:
	 * '枚举值', input: true}, {index:2, text: '最大长度限制', input: true}
	 */
	private List<String> fieldFilter;
	private List<String> fieldFilterVal;
	private List<String> fieldName;
	private List<String> fieldRemove;
	private List<String> fieldType;
	private List<String> timeFormat;
	private List<String> asFieldName;

	private List<Component> components;
	private List<String> downBranches;
	
	private String dependents;
	private List<String> independents;

	/******* data source ********/
	private String outSeparator;
	private String tmpTableName;
	private String ip;
	private Integer port;
	private Integer restPort;
	private String userName;
	private String password;
	/* folder path of file */
	private String url;
	private String urls;
	/* kafka settings */
	private String zkServers;
	private Integer kafkaDurationsSeconds;
	private String kafkaGroupID;
	private String kafkaPartitions;
	private String kafkaTopics;

	/* file out put settings */
	private String path;
	private Integer rotationFileSizeInMB;
	private Integer rotationTimeInSec;
	private String encoding;
	private String filePrifix;
	private String fileExtension;
	private Integer syncCount;
	private String rowKeyFields;
	private String znodeParent;
	private String metaStoreURI;
	private String timeAsPartitionField;
	private String partitionFields;
	
	
	/*spark 模型参数*/
	private String algorithmModelID;
	private String algorithmID;
	private String algorithmModelName;
	private String algorithmModelType;
	private String algorithmIsTrain;
	private String algorithmIsSpecifiedDataFormat;
	private String algorithmDataFormat;
	private String algorithmLabelField;
	private String algorithmModelSavePath;
	private String algorithmRank;
	private String algorithmIterations;
	private String algorithmRegParam;
	private String algorithmSeed;
	private String algorithmStepSize;


	/******* operates ********/
	/* operate opinions */
	private String values;
	private String fieldNames;
	private String between;
	private String and;
	private String isSection;
	private String upSort;
	private String calToken;
	private String leftJoinSort;

	private String keyPath;
	private String script;
	private String SQL;

	public String getSQL() {
		return SQL;
	}

	public void setSQL(String sQL) {
		SQL = sQL;
	}

	/******* storm ********/
	private String debug;
	private String ackerNum;
	private Integer topology_workers;
	private Integer maxParallelismNum;
	private Integer tickTupleFreqSecsForAll;
	private Integer topology_receiver_buffer_size;
	private Integer topology_transfer_buffer_size;
	private Integer topology_executor_receive_buffer_size;
	private Integer topology_executor_send_buffer_size;

	private Integer bolts_num;
	private Integer parallelismNum;
	private Integer tickTupleFreqSecs;

	/********** spark **********/
	private Integer executor_cores;
	private Integer executor_memory;
	private Integer num_executors;
	/**
	 * org.apache.spark.serializer.KryoSerializer
	 * org.apache.spark.serializer.JavaSerializer
	 */
	private String spark_serializer;
	private Integer spark_default_parallelism;
	private Integer spark_reduce_maxSizeInFlight;
	private Integer spark_shuffle_file_buffer;
	private Double spark_shuffle_memoryFraction;
	private Double spark_storage_memoryFraction;
	/**
	 * org.apache.spark.sql.api.java.StructField
	 */
	private String registerKryoClasses;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	public Integer getDay_hour() {
		return day_hour;
	}

	public void setDay_hour(Integer day_hour) {
		this.day_hour = day_hour;
	}

	public Integer getDay_minute() {
		return day_minute;
	}

	public void setDay_minute(Integer day_minute) {
		this.day_minute = day_minute;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWf_name() {
		return wf_name;
	}

	public void setWf_name(String wf_name) {
		this.wf_name = wf_name;
	}

	public String getWf_group() {
		return wf_group;
	}

	public void setWf_group(String wf_group) {
		this.wf_group = wf_group;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRecordDelimiter() {
		return recordDelimiter;
	}

	public void setRecordDelimiter(String recordDelimiter) {
		this.recordDelimiter = recordDelimiter;
	}

	public List<String> getFieldFilter() {
		return fieldFilter;
	}

	public void setFieldFilter(List<String> fieldFilter) {
		this.fieldFilter = fieldFilter;
	}

	public List<String> getFieldFilterVal() {
		return fieldFilterVal;
	}

	public void setFieldFilterVal(List<String> fieldFilterVal) {
		this.fieldFilterVal = fieldFilterVal;
	}

	public List<String> getFieldName() {
		return fieldName;
	}

	public void setFieldName(List<String> fieldName) {
		this.fieldName = fieldName;
	}

	public List<String> getFieldRemove() {
		return fieldRemove;
	}

	public void setFieldRemove(List<String> fieldRemove) {
		this.fieldRemove = fieldRemove;
	}

	public List<String> getFieldType() {
		return fieldType;
	}

	public void setFieldType(List<String> fieldType) {
		this.fieldType = fieldType;
	}

	public List<String> getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(List<String> timeFormat) {
		this.timeFormat = timeFormat;
	}

	public List<String> getAsFieldName() {
		return asFieldName;
	}

	public void setAsFieldName(List<String> asFieldName) {
		this.asFieldName = asFieldName;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public List<String> getDownBranches() {
		return downBranches;
	}

	public void setDownBranches(List<String> downBranches) {
		this.downBranches = downBranches;
	}

	public String getOutSeparator() {
		return outSeparator;
	}

	public void setOutSeparator(String outSeparator) {
		this.outSeparator = outSeparator;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getRestPort() {
		return restPort;
	}

	public void setRestPort(Integer restPort) {
		this.restPort = restPort;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getZkServers() {
		return zkServers;
	}

	public void setZkServers(String zkServers) {
		this.zkServers = zkServers;
	}

	public Integer getKafkaDurationsSeconds() {
		return kafkaDurationsSeconds;
	}

	public void setKafkaDurationsSeconds(Integer kafkaDurationsSeconds) {
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getRotationFileSizeInMB() {
		return rotationFileSizeInMB;
	}

	public void setRotationFileSizeInMB(Integer rotationFileSizeInMB) {
		this.rotationFileSizeInMB = rotationFileSizeInMB;
	}

	public Integer getRotationTimeInSec() {
		return rotationTimeInSec;
	}

	public void setRotationTimeInSec(Integer rotationTimeInSec) {
		this.rotationTimeInSec = rotationTimeInSec;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
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

	public Integer getSyncCount() {
		return syncCount;
	}

	public void setSyncCount(Integer syncCount) {
		this.syncCount = syncCount;
	}

	public String getRowKeyFields() {
		return rowKeyFields;
	}

	public void setRowKeyFields(String rowKeyFields) {
		this.rowKeyFields = rowKeyFields;
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

	public String getTimeAsPartitionField() {
		return timeAsPartitionField;
	}

	public void setTimeAsPartitionField(String timeAsPartitionField) {
		this.timeAsPartitionField = timeAsPartitionField;
	}

	public String getPartitionFields() {
		return partitionFields;
	}

	public void setPartitionFields(String partitionFields) {
		this.partitionFields = partitionFields;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
	}

	public String getBetween() {
		return between;
	}

	public void setBetween(String between) {
		this.between = between;
	}

	public String getAnd() {
		return and;
	}

	public void setAnd(String and) {
		this.and = and;
	}

	public String getIsSection() {
		return isSection;
	}

	public void setIsSection(String isSection) {
		this.isSection = isSection;
	}

	public String getUpSort() {
		return upSort;
	}

	public void setUpSort(String upSort) {
		this.upSort = upSort;
	}

	public String getCalToken() {
		return calToken;
	}

	public void setCalToken(String calToken) {
		this.calToken = calToken;
	}

	public String getLeftJoinSort() {
		return leftJoinSort;
	}

	public void setLeftJoinSort(String leftJoinSort) {
		this.leftJoinSort = leftJoinSort;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	public String getAckerNum() {
		return ackerNum;
	}

	public void setAckerNum(String ackerNum) {
		this.ackerNum = ackerNum;
	}

	public Integer getTopology_workers() {
		return topology_workers;
	}

	public void setTopology_workers(Integer topology_workers) {
		this.topology_workers = topology_workers;
	}

	public Integer getMaxParallelismNum() {
		return maxParallelismNum;
	}

	public void setMaxParallelismNum(Integer maxParallelismNum) {
		this.maxParallelismNum = maxParallelismNum;
	}

	public Integer getTickTupleFreqSecsForAll() {
		return tickTupleFreqSecsForAll;
	}

	public void setTickTupleFreqSecsForAll(Integer tickTupleFreqSecsForAll) {
		this.tickTupleFreqSecsForAll = tickTupleFreqSecsForAll;
	}

	public Integer getTopology_receiver_buffer_size() {
		return topology_receiver_buffer_size;
	}

	public void setTopology_receiver_buffer_size(Integer topology_receiver_buffer_size) {
		this.topology_receiver_buffer_size = topology_receiver_buffer_size;
	}

	public Integer getTopology_transfer_buffer_size() {
		return topology_transfer_buffer_size;
	}

	public void setTopology_transfer_buffer_size(Integer topology_transfer_buffer_size) {
		this.topology_transfer_buffer_size = topology_transfer_buffer_size;
	}

	public Integer getTopology_executor_receive_buffer_size() {
		return topology_executor_receive_buffer_size;
	}

	public void setTopology_executor_receive_buffer_size(Integer topology_executor_receive_buffer_size) {
		this.topology_executor_receive_buffer_size = topology_executor_receive_buffer_size;
	}

	public Integer getTopology_executor_send_buffer_size() {
		return topology_executor_send_buffer_size;
	}

	public void setTopology_executor_send_buffer_size(Integer topology_executor_send_buffer_size) {
		this.topology_executor_send_buffer_size = topology_executor_send_buffer_size;
	}

	public Integer getBolts_num() {
		return bolts_num;
	}

	public void setBolts_num(Integer bolts_num) {
		this.bolts_num = bolts_num;
	}

	public Integer getParallelismNum() {
		return parallelismNum;
	}

	public void setParallelismNum(Integer parallelismNum) {
		this.parallelismNum = parallelismNum;
	}

	public Integer getTickTupleFreqSecs() {
		return tickTupleFreqSecs;
	}

	public void setTickTupleFreqSecs(Integer tickTupleFreqSecs) {
		this.tickTupleFreqSecs = tickTupleFreqSecs;
	}

	public Integer getExecutor_cores() {
		return executor_cores;
	}

	public void setExecutor_cores(Integer executor_cores) {
		this.executor_cores = executor_cores;
	}

	public Integer getExecutor_memory() {
		return executor_memory;
	}

	public void setExecutor_memory(Integer executor_memory) {
		this.executor_memory = executor_memory;
	}

	public Integer getNum_executors() {
		return num_executors;
	}

	public void setNum_executors(Integer num_executors) {
		this.num_executors = num_executors;
	}

	public String getSpark_serializer() {
		return spark_serializer;
	}

	public void setSpark_serializer(String spark_serializer) {
		this.spark_serializer = spark_serializer;
	}

	public Integer getSpark_default_parallelism() {
		return spark_default_parallelism;
	}

	public void setSpark_default_parallelism(Integer spark_default_parallelism) {
		this.spark_default_parallelism = spark_default_parallelism;
	}

	public Integer getSpark_reduce_maxSizeInFlight() {
		return spark_reduce_maxSizeInFlight;
	}

	public void setSpark_reduce_maxSizeInFlight(Integer spark_reduce_maxSizeInFlight) {
		this.spark_reduce_maxSizeInFlight = spark_reduce_maxSizeInFlight;
	}

	public Integer getSpark_shuffle_file_buffer() {
		return spark_shuffle_file_buffer;
	}

	public void setSpark_shuffle_file_buffer(Integer spark_shuffle_file_buffer) {
		this.spark_shuffle_file_buffer = spark_shuffle_file_buffer;
	}

	public Double getSpark_shuffle_memoryFraction() {
		return spark_shuffle_memoryFraction;
	}

	public void setSpark_shuffle_memoryFraction(Double spark_shuffle_memoryFraction) {
		this.spark_shuffle_memoryFraction = spark_shuffle_memoryFraction;
	}

	public Double getSpark_storage_memoryFraction() {
		return spark_storage_memoryFraction;
	}

	public void setSpark_storage_memoryFraction(Double spark_storage_memoryFraction) {
		this.spark_storage_memoryFraction = spark_storage_memoryFraction;
	}

	public String getRegisterKryoClasses() {
		return registerKryoClasses;
	}

	public void setRegisterKryoClasses(String registerKryoClasses) {
		this.registerKryoClasses = registerKryoClasses;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTmpTableName() {
		return tmpTableName;
	}

	public void setTmpTableName(String tmpTableName) {
		this.tmpTableName = tmpTableName;
	}

	public String getAlgorithmModelID() {
		return algorithmModelID;
	}

	public void setAlgorithmModelID(String algorithmModelID) {
		this.algorithmModelID = algorithmModelID;
	}

	public String getAlgorithmID() {
		return algorithmID;
	}

	public void setAlgorithmID(String algorithmID) {
		this.algorithmID = algorithmID;
	}

	public String getAlgorithmModelName() {
		return algorithmModelName;
	}

	public void setAlgorithmModelName(String algorithmModelName) {
		this.algorithmModelName = algorithmModelName;
	}

	public String getAlgorithmModelType() {
		return algorithmModelType;
	}

	public void setAlgorithmModelType(String algorithmModelType) {
		this.algorithmModelType = algorithmModelType;
	}

	public String getAlgorithmIsTrain() {
		return algorithmIsTrain;
	}

	public void setAlgorithmIsTrain(String algorithmIsTrain) {
		this.algorithmIsTrain = algorithmIsTrain;
	}

	public String getAlgorithmIsSpecifiedDataFormat() {
		return algorithmIsSpecifiedDataFormat;
	}

	public void setAlgorithmIsSpecifiedDataFormat(String algorithmIsSpecifiedDataFormat) {
		this.algorithmIsSpecifiedDataFormat = algorithmIsSpecifiedDataFormat;
	}

	public String getAlgorithmDataFormat() {
		return algorithmDataFormat;
	}

	public void setAlgorithmDataFormat(String algorithmDataFormat) {
		this.algorithmDataFormat = algorithmDataFormat;
	}

	public String getAlgorithmLabelField() {
		return algorithmLabelField;
	}

	public void setAlgorithmLabelField(String algorithmLabelField) {
		this.algorithmLabelField = algorithmLabelField;
	}

	public String getAlgorithmModelSavePath() {
		return algorithmModelSavePath;
	}

	public void setAlgorithmModelSavePath(String algorithmModelSavePath) {
		this.algorithmModelSavePath = algorithmModelSavePath;
	}

	public String getAlgorithmRank() {
		return algorithmRank;
	}

	public void setAlgorithmRank(String algorithmRank) {
		this.algorithmRank = algorithmRank;
	}

	public String getAlgorithmIterations() {
		return algorithmIterations;
	}

	public void setAlgorithmIterations(String algorithmIterations) {
		this.algorithmIterations = algorithmIterations;
	}

	public String getAlgorithmRegParam() {
		return algorithmRegParam;
	}

	public void setAlgorithmRegParam(String algorithmRegParam) {
		this.algorithmRegParam = algorithmRegParam;
	}

	public String getAlgorithmSeed() {
		return algorithmSeed;
	}

	public void setAlgorithmSeed(String algorithmSeed) {
		this.algorithmSeed = algorithmSeed;
	}

	public String getAlgorithmStepSize() {
		return algorithmStepSize;
	}

	public void setAlgorithmStepSize(String algorithmStepSize) {
		this.algorithmStepSize = algorithmStepSize;
	}

	public static String getWfNameEnd() {
		return WF_NAME_END;
	}

	public static String getWfGroupInput() {
		return WF_GROUP_INPUT;
	}

	public static String getWfGroupOutput() {
		return WF_GROUP_OUTPUT;
	}

	public static String getWfGroupOp() {
		return WF_GROUP_OP;
	}

	public static String getWfNameKafka() {
		return WF_NAME_KAFKA;
	}

	public static String getWfNameLocalfile() {
		return WF_NAME_LOCALFILE;
	}

	public static String getWfNameHdfs() {
		return WF_NAME_HDFS;
	}

	public static String getWfNameElasticsearch() {
		return WF_NAME_ELASTICSEARCH;
	}

	public static String getWfNameHbase() {
		return WF_NAME_HBASE;
	}

	public static String getWfNameGreenplum() {
		return WF_NAME_GREENPLUM;
	}

	public static String getWfNameFtp() {
		return WF_NAME_FTP;
	}

	public static String getWfNameMysql() {
		return WF_NAME_MYSQL;
	}

	public static String getWfNameOracle() {
		return WF_NAME_ORACLE;
	}

	public static String getWfNameHive() {
		return WF_NAME_HIVE;
	}

	public static String getWfNameFilter() {
		return WF_NAME_FILTER;
	}

	public static String getWfNameCount() {
		return WF_NAME_COUNT;
	}

	public static String getWfNameGroupby() {
		return WF_NAME_GROUPBY;
	}

	public static String getWfNameDistinct() {
		return WF_NAME_DISTINCT;
	}

	public static String getWfNameJoin() {
		return WF_NAME_JOIN;
	}

	public static String getWfNameArithmetic() {
		return WF_NAME_ALGORITHM_MODEL;
	}

	public static String getWfNameGreenplumsql() {
		return WF_NAME_GREENPLUMSQL;
	}

	public static String getWfNameHiveql() {
		return WF_NAME_HIVEQL;
	}

	public static String getWfNameMysqlsql() {
		return WF_NAME_MYSQLSQL;
	}

	public static String getWfNameOraclesql() {
		return WF_NAME_ORACLESQL;
	}

	public static String getWfNameLinerR() {
		return WF_NAME_LINER_R;
	}

	public static String getWfNameMapreduce() {
		return WF_NAME_MAPREDUCE;
	}

	public static String getWfFieldFilterNone() {
		return WF_FIELD_FILTER_NONE;
	}

	public static String getWfFieldFilterTel() {
		return WF_FIELD_FILTER_TEL;
	}

	public static String getWfFieldFilterEnum() {
		return WF_FIELD_FILTER_ENUM;
	}

	public static String getWfFieldFilterMaxlen() {
		return WF_FIELD_FILTER_MAXLEN;
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
