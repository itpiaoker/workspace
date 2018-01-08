package cn.com.carenet.scheduler.constant;

public class DataSourceConfigKey {
	/* module unique id */
	final public static String DATASOURCE_ID = "datasourceID";
	final public static String SOURCE_TYPE = "sourceType";
	/* data source informations */
	final public static String TABLE_NAME = "tableName";
	// record delimiter is not use in web page any more
	final public static String RECORD_DELIMITER = "recordDelimiter";
	final public static String IP = "ip";
	final public static String PORT = "port";
	final public static String DATA_BASENAME = "databaseName";

	final public static String DATASOUCE_DELIMITER = "datasouceDelimiter";
	final public static String META_DATA_FIELD_TIME_FORMAT = "timeFormat";
	final public static String META_DATA_FIELD_NAMES = "fieldName";
	final public static String META_DATA_FIELD_TYPES = "fieldType";
	final public static String META_DATA_FIELD_FILTER = "fieldFilter";
	final public static String META_DATA_FIELD_FILTER_VAL = "fieldFilterVal";
	final public static String META_DATA_FIELD_REMOVE = "fieldRemove";
	final public static String META_DATA_FIELD_ASNAME = "asFieldName";
	final public static String META_DATA_FIELD_INDEPENDENTS = "independents";
	
	/* this is the storm properties in data source */
	final public static String WORKER_NUM = "workerNum";
	final public static String TASK_NUM = "workerNum";
	final public static String PARALLELISM_NUM = "parallelismNum";
	final public static String TICK_TUPLE_FREQ_SECS = "tickTupleFreqSecs";

	/* kafka settings */
	final public static String ZK_SERVERS = "zkServers";
	final public static String KAFKA_DURATIONS_SECONDS = "kafkaDurationsSeconds";
	final public static String KAFKA_GROUP_ID = "kafkaGroupID";
	final public static String KAFKA_PARTITIONS = "kafkaPartitions";
	final public static String KAFKA_TOPICS = "kafkaTopics";

	/* file out put settings */
	final public static String SSH_IP = "sshIp";
	final public static String SSH_USER = "sshUser";
	final public static String SSH_PWD = "sshPwd";
	final public static String PATH = "path";
	final public static String ROTATION_FILE_SIZE_IN_MB = "rotationFileSizeInMB";
	final public static String ROTATION_TIME_IN_SEC = "rotationTimeInSec";
	final public static String FTP_URL = "ftpUrl";
	final public static String FTP_HOST = "ftpHost";
	final public static String FTP_PORT = "ftpPort";
	final public static String FTP_USER_NAME = "ftpUserName";
	final public static String FTP_PASSWORD = "ftpPassword";
	final public static String FTP_ENCODING = "ftpEncoding";
	final public static String FILE_PRIFIX = "filePrifix";
	final public static String FILE_EXTENSION = "fileExtension";
	final public static String SYNC_COUNT = "syncCount";
	final public static String JDBC_DB = "jdbcDB";
	final public static String JDBC_URL = "jdbcUrl";
	final public static String JDBC_USER = "jdbcUser";
	final public static String JDBC_PASSWD = "jdbcPasswd";
	final public static String rowKeyFields = "rowKeyFields";
	final public static String valueFields = "valueFields";
	final public static String REDIS_IP = "redisHost";
	final public static String REDIS_PORT = "redisPort";
	final public static String REDIS_PASSWORD = "redisPassword";
	final public static String REDIS_TIMEOUT = "redisTimeOut";
	final public static String HBASE_COLUMN_FAMILY = "hBaseColumnFamily";
	final public static String hBaseZkQuorum = "hBaseZkQuorum";
	final public static String hBaseZkPort = "hBaseZkPort";
	final public static String hBaseMasterPort = "hBaseMasterPort";
	final public static String znodeParent = "znodeParent";
	final public static String metaStoreURI = "metaStoreURI";
	final public static String dbName = "dbName";
	final public static String timeAsPartitionField = "timeAsPartitionField";
	final public static String txnsPerBath = "txnsPerBath";
	final public static String batchSize = "batchSize";
	final public static String idleTimeout = "idleTimeout";
	final public static String callTimeout = "callTimeout";
	final public static String HEART_BEAT_INTERVAL = "heartBeatInterval";
	final public static String AUTO_CREATE_PARTITIONS = "autoCreatePartitions";
	final public static String TICK_TUPLE_INTERVAL = "tickTupleInterval";
	final public static String PARTITION_FIELDS = "partitionFields";
	final public static String ES_INDEX_NAME = "esIndexName";
	final public static String ES_TYPE_NAME = "esTypeName";
	final public static String ES_BOLT_FLUSH_ENTRIES_SIZE = "es.storm.bolt.flush.entries.size";
	final public static String ES_BOLT_TICK_TUPLE_FLUSH = "es.storm.bolt.tick.tuple.flush";
	final public static String ES_BOLT_WRITE_ACK = "es.storm.bolt.write.ack";
	final public static String ES_INDEX_AUTO_CREATE = "es.index.auto.create";

}
