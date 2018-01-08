package cn.com.carenet.scheduler.constant;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/5/18
 */
public class Constant {
    /** 模块名称前缀_key */
    public static final String PREFIX_WINDOW_KEY = "window_";
    /** 模块名称前缀_key */
    public static final String PREFIX_WF_KEY = "wf_";


    /** 模块名称_key */
    public static final String MODULE_ID_KEY = "id";
    /** 模块_key */
    public static final String MODULE_NAME_KEY = "wf_name";
    /** 模块_key */
    public static final String MODULE_SOURCE_ID_KEY = "wf_sourceId";
    /** 模块_key */
    public static final String MODULE_TARGET_ID_KEY = "wf_targetId";
    /** 模块_key */
    public static final String MODULE_RELATIONS_KEY = "components";
    /** 模块_key */
    public static final String MODULE_STORM_KEY = "storm";
    /** 模块_key */
    public static final String MODULE_SPARK_CORE_KEY = "spark-core";
    /** 模块_key */
    public static final String MODULE_SPARK_SQL_KEY = "spark-sql";
    /** 模块_key */
    public static final String MODULE_SPARK_STREAMING_KEY = "spark-streaming";
    /** 模块_key */
    public static final String MODULE_SPARK_MLLIB_KEY = "spark-mllib";
    /** 模块_key */
    public static final String MODULE_SPARK_GRAPHX_KEY = "spark-graphx";
    /** 模块_key */
    public static final String MODULE_GP_KEY = "gp";
    
    public static final String LINER_R = "LinerR";


    /** 操作_key */
//    public static final List<String> isOperateComponent = new ArrayList<>();
    /** 操作_key */
    public static final String OPERATE_NAME_START_KEY = "start";
    /** 操作_key */
    public static final String OPERATE_NAME_END_KEY = "end";
    /** 操作_key */
    public static final String OPERATE_NAME_FILTER_KEY = "filter";
    /** 操作_key */
    public static final String OPERATE_NAME_JOIN_KEY = "join";
    /** 操作_key */
    public static final String OPERATE_NAME_GROUP_KEY = "group";
    /** 操作_key */
    public static final String OPERATE_NAME_DISTINCT_KEY = "distinct";
    /** 操作_key */
    public static final String OPERATE_NAME_COUNT_KEY = "count";
    /** 操作_key */
    public static final String OPERATE_NAME_SORT_KEY = "sort";


    /** 数据源_key */
    public static final String DATASOURCE_SOURCE_TYPE_KEY = "source_type";
    /** 数据源_key */
    public static final String DATASOURCE_SOURCE_NO_KEY = "source_no";
    /** 数据源_key */
    public static final String DATASOURCE_SOURCE_NAME_KEY = "source_name";
    /** 数据源_key */
    public static final String DATASOURCE_NAME_HDFS_KEY = "hdfs";
    /** HDFS_key */
    public static final String HDFS_URL_KEY = "hdfs";


    /** 数据源_key */
    public static final String DATASOURCE_NAME_KAFKA_KEY = "kafka";
    /** kafka_key */
    public static final String KAFKA_ZK_SERVERS_KEY = "zk_servers";
    /** kafka_key */
    public static final String KAFKA_BROKEYS_KEY = "kafka_brokers";
    /** kafka_key */
    public static final String KAFKA_TOPIC_KEY = "kafka_topics";
    /** kafka_key */
    public static final String KAFKA_PARTITIONS_KEY = "kafka_partitions";
    /** kafka_key */
    public static final String KAFKA_GROUP_ID_KEY = "kafka_groupID";
    /** kafka_key */
    public static final String KAFKA_DURATION_SECONDS_KEY = "kafka_durations_seconds";


    /** 数据源_key */
    public static final String DATASOURCE_NAME_MYSQL = "mysql";
    /** mysql_key */
    public static final String MYSQL_DB_KEY = "mysql_database";
    /** mysql_key */
    public static final String MYSQL_DRIVER_KEY = "mysql_driver";
    /** mysql_key */
    public static final String MYSQL_TABLE_KEY = "mysql_table";
    /** mysql_key */
    public static final String MYSQL_URL_KEY = "mysql_url";
    /** mysql_key */
    public static final String MYSQL_USER_KEY = "mysql_user";
    /** mysql_key */
    public static final String MYSQL_PASSWD_KEY = "mysql_passwd";
    /** mysql_key */
    public static final String MYSQL_SQL_KEY = "mysql_sql";


    /** 数据源_key */
    public static final String ES_NAME_ORACLE = "oracle";
    /** 数据源_key */
    public static final String ES_INDEX_NAME_KEY = "esIndexName";
    /** 数据源_key */
    public static final String ES_TYPE_NAME_KEY = "esIndexType";
    /** 数据源_key */
    public static final String ES_NODES = "esNodes";
    /** 数据源_key */
    public static final String ES_PORT = "esPort";
    /** 数据字段 */
    public static final String FIELD_TYPE_KEY = "fields";
    /** 数据字段_key */
    public static final String FIELD_TYPE_FIELD_NAME_KEY = "field_name";
    /** 数据字段_key */
    public static final String FIELD_TYPE_FIELD_TYPE_KEY = "field_type";
    /** 数据字段_key */
    public static final String FIELD_TYPE_FIELD_COLNUM_KEY = "col_num";
    /** 数据字段_key */
    public static final String FIELD_TYPE_FIELD_FORMATTER_KEY = "formatter";

    /** sqlType */
    public static final String SQL_TYPE_KEY = "sql_types";
    /** sqlType */
    public static final String SQL_TYPE_SQL_ID_KEY = "sql_id";
    /** sqlType */
    public static final String SQL_TYPE__TABLE_NAME_KEY = "table_name";
    /** sqlType */
    public static final String SQL_TYPE_AS_TABLE_NAME_KEY = "as_table_name";
    /** sqlType */
    public static final String SQL_TYPE_SQL_TYPE_KEY = "sql_type";
    /** sqlType */
    public static final String SQL_TYPE_IS_SAVE_KEY = "is_save";
    /** sqlType */
    public static final String SQL_TYPE_SQL_KEY = "sql";


    /** 文件存放位置 */
    public static final String FILE_OPERATE_TYPE_DIR_KEY = "operateType.dir";
    /** 文件存放位置 */
    public static final String FILE_DATA_SOURCE_DIR_KEY = "dataSource.dir";
    /** 文件存放位置 */
    public static final String FILE_FIELD_TYPE_DIR_KEY = "fieldType.dir";
    /** 文件存放位置 */
    public static final String FILE_SQL_TYPE_SQL_DIR_KEY = "sqlType.dir";
    /** 文件存放位置 */
    public static final String FILE_OPERATE_TYPE_DIR_VALUE = "D:\\0Acarenet\\项目\\工作流\\ot.json";
    /** 文件存放位置 */
    public static final String FILE_DATA_SOURCE_DIR_VALUE = "D:\\0Acarenet\\项目\\工作流\\ds.json";
    /** 文件存放位置 */
    public static final String FILE_FIELD_TYPE_DIR_VALUE = "D:\\0Acarenet\\项目\\工作流\\ft.json";
    /** 文件存放位置 */
    public static final String FILE_SQL_TYPE_SQL_DIR_VALUE = "D:\\0Acarenet\\项目\\工作流\\st.json";
    
    final public static String DRIVER_NAME_MYSQL = "com.mysql.jdbc.Driver";
    final public static String DRIVER_NAME_ORACLE = "oracle.jdbc.driver.OracleDriver";
//    final public static String DRIVER_NAME_GP = "com.pivotal.jdbc.GreenplumDriver";
    final public static String DRIVER_NAME_GP = "org.postgresql.Driver";
}
