package cn.com.carenet.scheduler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConfigLoader {
	final private static Logger LOG = LoggerFactory.getLogger(DbConfigLoader.class);
	private static String mysqlUrl = "localhost:3306";
	private static String mysqlDb = "mysqldb";
	private static String mysqlUser = "root";
	private static String mysqlPassword = "root";
	private static String logHost;
	private static String logPort;
	private static String logUser;
	private static String logPasswd;
	private static String logDb;
	private static String logTable;
	private static String logBufferTable;
	private static String statusTable;

	static {
		Properties prop = new Properties();
		InputStream in;
		try {
			in = new FileInputStream(new File(System.getProperty(PropertiesReader.PROPERTIES_FILE)));
			prop.load(in);
			in.close();
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		mysqlUrl = prop.getProperty("spring.datasource.url");
		mysqlUser = prop.getProperty("spring.datasource.username");
		mysqlPassword = prop.getProperty("spring.datasource.password");
		logHost = prop.getProperty("mongodb.log.host");
		logPort = prop.getProperty("mongodb.log.port");
		logUser = prop.getProperty("mongodb.log.user");
		logPasswd = prop.getProperty("mongodb.log.passwd");
		logDb = prop.getProperty("mongodb.log.db");
		logTable = prop.getProperty("mongodb.log.table");
		logBufferTable = prop.getProperty("mongodb.log.buffertable");
		statusTable = prop.getProperty("mongodb.log.statustable");
	}

	public static String getMysqlUrl() {
		return mysqlUrl;
	}

	public static String getMysqlDb() {
		return mysqlDb;
	}

	public static String getMysqlUser() {
		return mysqlUser;
	}

	public static String getMysqlPassword() {
		return mysqlPassword;
	}

	public static String getLogHost() {
		return logHost;
	}

	public static String getLogPort() {
		return logPort;
	}
	public static String getLogUser() {
		return logUser;
	}

	public static String getLogPasswd() {
		return logPasswd;
	}

	public static String getLogTable() {
		return logTable;
	}

	public static String getLogDb() {
		return logDb;
	}

	public static String getLogBufferTable() {
		return logBufferTable;
	}

	public static String getStatusTable() {
		return statusTable;
	}

	public static void setStatusTable(String statusTable) {
		DbConfigLoader.statusTable = statusTable;
	}
}
