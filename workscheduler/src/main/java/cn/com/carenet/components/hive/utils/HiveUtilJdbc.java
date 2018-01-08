package cn.com.carenet.components.hive.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

public class HiveUtilJdbc {
	final static Logger logger = LoggerFactory.getLogger(HiveUtilJdbc.class);
	final public static String mysql = "mysql";
	final public static String oracle = "oracle";
	final public static String TABLE = "TABLE";
	private String sourceName;
	private String driverClass;
	private String jdbcUrl;
	private String jdbcDB;
	private String jdbcUser;
	private String jdbcPasswd;
	private JdbcTemplate jdbcTemplate;
	private static int maxActive = 20;
	private static int initialSize = 1;
	private static long maxWait = 60000;
	private static int minIdle = 1;
	private static long timeBetweenEvictionRunsMillis = 60000;
	private static long minEvictableIdleTimeMillis = 300000;
	private static String validationQueryMysql = "SELECT 1";
	private static String validationQueryOracle = "SELECT 1 from dual";
	private static boolean testWhileIdle = true;
	private static boolean testOnBorrow = false;
	private static boolean testOnReturn = false;
	private static boolean poolPreparedStatements = true;
	private static int maxPoolPreparedStatementPerConnectionSize = 50;

	public HiveUtilJdbc(String sourceName, String jdbcUrl, String jdbcDB, String jdbcUser, String jdbcPasswd) {
		this.sourceName = sourceName;
		this.jdbcUrl = jdbcUrl;
		this.jdbcDB = jdbcDB;
		this.jdbcUser = jdbcUser;
		this.jdbcPasswd = jdbcPasswd;
		this.prepareDatasource();
	}

	private void prepareDatasource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUsername(jdbcUser);
		druidDataSource.setPassword(jdbcPasswd);

		Properties prop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/hiveDbConfig.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		String strProp;
		if ((strProp = prop.getProperty("jdbc.maxActive")) != null)
			if (StringUtils.isNumeric(strProp.trim()))
				maxActive = Integer.parseInt(strProp);
		if ((strProp = prop.getProperty("jdbc.initialSize")) != null)
			if (StringUtils.isNumeric(strProp.trim()))
				initialSize = Integer.parseInt(strProp);
		if ((strProp = prop.getProperty("jdbc.maxWait")) != null)
			if (StringUtils.isNumeric(strProp.trim()))
				maxWait = Long.parseLong(strProp);
		if ((strProp = prop.getProperty("jdbc.minIdle")) != null)
			if (StringUtils.isNumeric(strProp.trim()))
				minIdle = Integer.parseInt(strProp);
		if ((strProp = prop.getProperty("jdbc.timeBetweenEvictionRunsMillis")) != null)
			if (StringUtils.isNumeric(strProp.trim()))
				timeBetweenEvictionRunsMillis = Long.parseLong(strProp);
		if ((strProp = prop.getProperty("jdbc.minEvictableIdleTimeMillis")) != null)
			if (StringUtils.isNumeric(strProp.trim()))
				minEvictableIdleTimeMillis = Long.parseLong(strProp);
		if ((strProp = prop.getProperty("jdbc.validationQuery.mysql")) != null)
			validationQueryMysql = strProp;
		if ((strProp = prop.getProperty("jdbc.validationQuery.oracle")) != null)
			validationQueryOracle = strProp;
		if ((strProp = prop.getProperty("jdbc.testWhileIdle")) != null)
			if (StringUtils.startsWith(strProp.trim(), "false"))
				testWhileIdle = false;
		if ((strProp = prop.getProperty("jdbc.testOnBorrow")) != null)
			if (StringUtils.startsWith(strProp.trim(), "true"))
				testOnBorrow = true;
		if ((strProp = prop.getProperty("jdbc.testOnReturn")) != null)
			if (StringUtils.startsWith(strProp.trim(), "true"))
				testOnReturn = true;
		if ((strProp = prop.getProperty("jdbc.poolPreparedStatements.oracle")) != null)
			if (StringUtils.startsWith(strProp.trim(), "true"))
				poolPreparedStatements = true;
		if ((strProp = prop.getProperty("jdbc.maxPoolPreparedStatementPerConnectionSize.oracle")) != null)
			if (StringUtils.isNumeric(strProp.trim()))
				maxPoolPreparedStatementPerConnectionSize = Integer.parseInt(strProp);

		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMaxWait(maxWait);
		druidDataSource.setMinIdle(minIdle);
		druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		druidDataSource.setTestWhileIdle(testWhileIdle);
		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);
		StringBuffer strb;
		switch (sourceName) {
		case mysql:
			driverClass = "com.mysql.jdbc.Driver";
			druidDataSource.setValidationQuery(validationQueryMysql);
			strb = new StringBuffer();
			strb.append("jdbc:mysql://").append(jdbcUrl).append("/").append(jdbcDB)
					.append("?useSSL=false&rewriteBatchedStatements=true");
			druidDataSource.setUrl(strb.toString());
			break;
		case oracle:
			driverClass = "oracle.jdbc.driver.OracleDriver";
			druidDataSource.setValidationQuery(validationQueryOracle);
			druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
			druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
			strb = new StringBuffer();
			strb.append("jdbc:oracle:thin:@").append(jdbcUrl).append(":").append(jdbcDB);
			druidDataSource.setUrl(strb.toString());
			break;
		}
		druidDataSource.setDriverClassName(driverClass);
		jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(druidDataSource);
	}

	public static boolean isTableExist(JdbcTemplate jdbcTemplate, String tableName) throws Exception {
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		ResultSet tabs = null;
		try {
			DatabaseMetaData dbMetaData = conn.getMetaData();
			String[] types = { TABLE };
			tabs = dbMetaData.getTables(null, null, tableName, types);
			if (tabs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tabs.close();
			conn.close();
		}
		return false;
	}

	public static int createTable(JdbcTemplate jdbcTemplate, String tableName, Map<String, String> metaFields) {
		StringBuffer strb = new StringBuffer("");
		strb.append("CREATE TABLE ").append(tableName).append(" ( ");
		int i = 0;
		for (Entry<String, String> fieldEntry : metaFields.entrySet()) {
			if (i == 0)
				strb.append(fieldEntry.getKey()).append(" ").append(fieldEntry.getValue());
			else {
				strb.append(",").append(fieldEntry.getKey()).append(" ").append(fieldEntry.getValue());
			}
			i++;
		}
		strb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		try {
			jdbcTemplate.update(strb.toString());
			return 1;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
