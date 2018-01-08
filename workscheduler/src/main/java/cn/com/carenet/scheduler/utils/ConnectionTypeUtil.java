package cn.com.carenet.scheduler.utils;

import org.apache.commons.lang.StringUtils;

public class ConnectionTypeUtil {
	public static final int SOURCE_TYPE_HBASE = 0;
	public static final int SOURCE_TYPE_ES = 1;
	public static final int SOURCE_TYPE_MYSQL = 2;
	public static final int SOURCE_TYPE_GP = 3;
	public static final int SOURCE_TYPE_ORACLE = 4;
	public static final int SOURCE_TYPE_HDFS = 5;
	public static final int SOURCE_TYPE_KAFKA = 8;
	public static final int SOURCE_TYPE_TEXT = 10;

	public static final String DBTYPE_HBASE = "0";
	public static final String DBTYPE_ELASTICSEARCH = "1";
	public static final String DBTYPE_MYSQL = "2";
	public static final String DBTYPE_GP = "3";
	public static final String DBTYPE_ORACLE = "4";
	public static final String DBTYPE_HDFS = "5";
	public static final String DBTYPE_POSTGRESQL = "6";
	public static final String DBTYPE_MSSQL = "7";
	public static final String DBTYPE_KAFKA = "8";
	public static final String DBTYPE_HIVE = "9";
	public static final String DBTYPE_TEXT = "10";

	// ['鏂囨湰', '鏁存暟', '娴偣鏁�', '鏃ユ湡/鏃堕棿', '甯冨皵鍊�'];
	public static final int COLUMN_TYPE_TEXT = 0;
	public static final int COLUMN_TYPE_INT = 1;
	public static final int COLUMN_TYPE_FLOAT = 2;
	public static final int COLUMN_TYPE_DATE = 3;
	public static final int COLUMN_TYPE_BOOLEAN = 4;

	public enum DatabaseType {
		/**
		 * 鍒濆 'HBase', 'ElasticSearch', 'MySql', 'GP', 'Oracle', 'HDFS',
		 * 'PostgreSQL', 'MSSQL' ,'hive', 'text', 'kafka'
		 */
		HBase(DBTYPE_HBASE, "HBase", ""), 
		ElasticSearch(DBTYPE_ELASTICSEARCH, "ElasticSearch", ""), 
		Hive(DBTYPE_HIVE, "Hive", "org.apache.hive.jdbc.HiveDriver"), 
		MySql(DBTYPE_MYSQL, "MySQL", "com.mysql.jdbc.Driver"), 
		GP(DBTYPE_GP, "GP", "org.postgresql.Driver"), 
		Oracle(DBTYPE_ORACLE, "Oracle", "oracle.jdbc.driver.OracleDriver"), 
		HDFS(DBTYPE_HDFS, "HDFS", ""), 
		PostgreSQL(DBTYPE_POSTGRESQL, "PostgreSQL", "org.postgresql.Driver"), 
		MSSQL(DBTYPE_MSSQL, "SQLServer", "com.microsoft.sqlserver.jdbc.SQLServerDriver"), 
		Kafka(DBTYPE_KAFKA, "Kafka", ""), 
		Text(DBTYPE_TEXT, "text", "");

		private String type;
		private String name;
		private String driver;

		private DatabaseType(String type, String name, String driver) {
			this.type = type;
			this.name = name;
			this.driver = driver;
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public String getDriver() {
			return driver;
		}
	}

	/**
	 * 鍒濆 'HBase', 'ElasticSearch', 'MySql', 'GP', 'Oracle', 'HDFS',
	 * 'PostgreSQL', 'MSSQL' ,'hive', 'text', 'kafka'
	 */
	public static String getUrl(String databaseType, String ip, int port, String databaseName) {
		switch (databaseType) {
		case DBTYPE_MYSQL:
			return "jdbc:mysql://" + ip + ":" + port + "/" + databaseName
					+ "?characterEncoding=utf8&tinyInt1isBit=false";
		case DBTYPE_GP:
			return "jdbc:postgresql://" + ip + ":" + port + "/" + databaseName;
		case DBTYPE_POSTGRESQL:
			return "jdbc:postgresql://" + ip + ":" + port + "/" + databaseName;
		case DBTYPE_ORACLE:
			return "jdbc:oracle:thin:@" + ip + ":" + port + ":" + databaseName;
		case DBTYPE_MSSQL:
			return "jdbc:sqlserver://" + ip + ":" + port + ";database=" + databaseName;
		case DBTYPE_HIVE:
			return "jdbc:hive2://" + ip + ":" + port + "/" + databaseName;
		case DBTYPE_HDFS:
			if(databaseName.indexOf('/')==0||databaseName.indexOf('\\')==0){
				databaseName=databaseName.substring(1);
			}
			return String.format("hdfs://%s:%d/%s", ip,port,databaseName);
		default:
			return null;
		}
	}

	public static String getDriver(String databaseType) {
		switch (databaseType) {
		case DBTYPE_MYSQL:
			return DatabaseType.MySql.getDriver();
		case DBTYPE_GP:
			return DatabaseType.GP.getDriver();
		case DBTYPE_POSTGRESQL:
			return DatabaseType.PostgreSQL.getDriver();
		case DBTYPE_ORACLE:
			return DatabaseType.Oracle.getDriver();
		case DBTYPE_MSSQL:
			return DatabaseType.MSSQL.getDriver();
		case DBTYPE_HIVE:
			return DatabaseType.Hive.getDriver();
		default:
			return null;
		}
	}

	/**
	 * 鍒濆 'HBase', 'ElasticSearch', 'MySql', 'GP', 'Oracle', 'HDFS',
	 * 'PostgreSQL', 'MSSQL' ,'hive', 'text', 'kafka'
	 */
	public static String getName(String databaseType) {
		switch (databaseType) {
		case DBTYPE_HBASE:
			return DatabaseType.HBase.getName();
		case DBTYPE_MYSQL:
			return DatabaseType.MySql.getName();
		case DBTYPE_GP:
			return DatabaseType.GP.getName();
		case DBTYPE_POSTGRESQL:
			return DatabaseType.PostgreSQL.getName();
		case DBTYPE_ORACLE:
			return DatabaseType.Oracle.getName();
		case DBTYPE_MSSQL:
			return DatabaseType.MSSQL.getName();
		case DBTYPE_HDFS:
			return DatabaseType.HDFS.getName();
		case DBTYPE_HIVE:
			return DatabaseType.Hive.getName();
		case DBTYPE_KAFKA:
			return DatabaseType.Kafka.getName();
		case DBTYPE_ELASTICSEARCH:
			return DatabaseType.ElasticSearch.getName();
		case DBTYPE_TEXT:
			return DatabaseType.Text.getName();
		default:
			return null;
		}
	}

	public static String getNullAble(String nullable) {
		if (("YES".equals(nullable)) || ("yes".equals(nullable)) || ("y".equals(nullable)) || ("Y".equals(nullable))
				|| ("f".equals(nullable))) {
			return "Y";
		}
		if (("NO".equals(nullable)) || ("N".equals(nullable)) || ("no".equals(nullable)) || ("n".equals(nullable))
				|| ("t".equals(nullable))) {
			return "N";
		}
		return null;
	}

	public static String formatDataType(String dataType, String precision, String scale) {

		if (dataType.contains("char")) {
			dataType = "String";
		} else if (dataType.contains("text")) {
			dataType = "String";
		} else if (dataType.contains("bigint")) {
			dataType = "Long";
		} else if (dataType.contains("int")) {
			dataType = "Integer";
		} else if (dataType.contains("float")) {
			dataType = "Float";
		} else if (dataType.contains("double")) {
			dataType = "Double";
		} else if (dataType.contains("number")) {
			if ((StringUtils.isNotEmpty(scale)) && (Integer.parseInt(scale) > 0))
				dataType = "BigDecimal";
			else if ((StringUtils.isNotEmpty(precision)) && (Integer.parseInt(precision) > 10)) {
				dataType = "Long";
			} else {
				dataType = "Integer";
			}
		} else if (dataType.contains("decimal")) {
			dataType = "BigDecimal";
		} else if (dataType.contains("date")) {
			dataType = "Date";
		} else if (dataType.contains("time")) {
			dataType = "Date";
		} else if (dataType.contains("blob")) {
			// dataType = "java.sql.Blob";
			dataType = "String";
		} else if (dataType.contains("clob")) {
			// dataType = "java.sql.Clob";
			dataType = "String";
		} else if (dataType.contains("numeric")) {
			dataType = "BigDecimal";
		} else {
			dataType = "java.lang.Object";
			;
		}

		return dataType;
	}

	public static String formatToJsType(String dataType) {
		if (dataType.contains("char")) {
			dataType = "string";
		} else if (dataType.contains("text")) {
			dataType = "string";
		} else if (dataType.contains("bigint")) {
			dataType = "number";
		} else if (dataType.contains("int")) {
			dataType = "number";
		} else if (dataType.contains("float")) {
			dataType = "number";
		} else if (dataType.contains("double")) {
			dataType = "number";
		} else if (dataType.contains("number")) {
			dataType = "number";
		} else if (dataType.contains("decimal")) {
			dataType = "number";
		} else if (dataType.contains("date")) {
			dataType = "date";
		} else if (dataType.contains("time")) {
			dataType = "date";
		} else if (dataType.contains("blob")) {
			dataType = "string";
		} else if (dataType.contains("clob")) {
			dataType = "string";
		} else if (dataType.contains("numeric")) {
			dataType = "number";
		} else {
			dataType = "object";
		}
		return dataType;
	}

	public static int formatToNumType(String dataType) {
		if (dataType.contains("char")) {
			return COLUMN_TYPE_TEXT;
		}
		if (dataType.contains("text")) {
			return COLUMN_TYPE_TEXT;
		}
		if (dataType.contains("bigint")) {
			return COLUMN_TYPE_INT;
		}
		if (dataType.contains("int")) {
			return COLUMN_TYPE_INT;
		}
		if (dataType.contains("float")) {
			return COLUMN_TYPE_FLOAT;
		}
		if (dataType.contains("double")) {
			return COLUMN_TYPE_FLOAT;
		}
		if (dataType.contains("number")) {
			return COLUMN_TYPE_FLOAT;
		}
		if (dataType.contains("decimal")) {
			return COLUMN_TYPE_FLOAT;
		}
		if (dataType.contains("date")) {
			return COLUMN_TYPE_DATE;
		}
		if (dataType.contains("time")) {
			return COLUMN_TYPE_DATE;
		}
		if (dataType.contains("blob")) {
			return COLUMN_TYPE_TEXT;
		}
		if (dataType.contains("clob")) {
			return COLUMN_TYPE_TEXT;
		}
		if (dataType.contains("numeric")) {
			return COLUMN_TYPE_FLOAT;
		}
		if (dataType.contains("boolean")) {
			return COLUMN_TYPE_BOOLEAN;
		}
		return COLUMN_TYPE_TEXT;
	}

	public static String columnNumToType(int _type) {
		switch (_type) {
		case COLUMN_TYPE_TEXT:
			return "text";
		case COLUMN_TYPE_INT:
			return "int";
		case COLUMN_TYPE_FLOAT:
			return "float";
		case COLUMN_TYPE_DATE:
			return "date";
		case COLUMN_TYPE_BOOLEAN:
			return "text";
		}
		return "text";
	}
}
