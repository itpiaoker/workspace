package cn.com.carenet.scheduler.constant;

import java.io.Serializable;

public class WebModuleNameConstant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 461818811897216910L;
	/* data source */
	final public static String kafka = "kafka";
	final public static String localFile = "local";
	final public static String hdfs = "hdfs";
	final public static String elasticSearch = "es";
	final public static String redis = "redis";
	final public static String hBase = "HBase";
	final public static String greenPlum = "gp";
	final public static String ftp = "ftp";
	final public static String mysql = "mysql";
	final public static String oracle = "oracle";
	final public static String hive = "hive";

	/* operating module */
	final public static String filter = "filter";
	final public static String count = "count";
	final public static String groupBy = "group";
	final public static String distinct = "distinct";
	final public static String sortBy = "sortBy";
	final public static String join = "join";
	final public static String arithmetic = "arithmetic";
	final public static String sum = "sum";
	final public static String removeField = "removeField";

	/** 模块_key */
	final public static String storm = "storm";
	final public static String sparkCore = "spark-core";
	final public static String sparkSQL = "spark-sql";
	final public static String sparkStreaming = "spark-streaming";
	final public static String sparkMLLib = "spark-mllib";
	final public static String sparkGraphx = "spark-graphx";
	final public static String greenPlumSQL = "gp-sql";
	final public static String hiveQL = "hive-sql";
	final public static String mysqlSQL = "mysql-sql";
	final public static String oracleSQL = "oracle-sql";
	final public static String unixShell = "unixShell";
	final public static String hadoop = "MapReduce";
	
	final public static String LinerR = "LinerR";
	
	final public static String transpose = "transpose";
	
	final public static String start = "start";
	final public static String end = "end";

}
