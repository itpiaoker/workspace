package cn.com.carenet.components.hive.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

public class HiveUtilHiveJdbc {
	final static Logger LOG = LoggerFactory.getLogger(HiveUtilHiveJdbc.class);

	private static int maxActive = 20;
	private static int initialSize = 1;
	private static long maxWait = 60000;
	private static int minIdle = 1;
	private static long timeBetweenEvictionRunsMillis = 60000;
	private static long minEvictableIdleTimeMillis = 300000;
	private static String validationQuery = "SELECT 1";
	private static boolean testWhileIdle = true;
	private static boolean testOnBorrow = false;
	private static boolean testOnReturn = false;

	final static public String driverClass = "org.apache.hive.jdbc.HiveDriver";
	final static public String hiveJdbcPrefix = "jdbc:hive2://";

	public JdbcTemplate prepareHiveJdbc(String jdbcUrl, String jdbcDB) {
		DruidDataSource druidDataSource = new DruidDataSource();
		Properties prop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/hiveDbConfig.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			LOG.info(e.getMessage());
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
		if ((strProp = prop.getProperty("jdbc.validationQuery.hive")) != null)
			validationQuery = strProp;
		if ((strProp = prop.getProperty("jdbc.testWhileIdle")) != null)
			if (StringUtils.startsWith(strProp.trim(), "false"))
				testWhileIdle = false;
		if ((strProp = prop.getProperty("jdbc.testOnBorrow")) != null)
			if (StringUtils.startsWith(strProp.trim(), "true"))
				testOnBorrow = true;
		if ((strProp = prop.getProperty("jdbc.testOnReturn")) != null)
			if (StringUtils.startsWith(strProp.trim(), "true"))
				testOnReturn = true;

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
		druidDataSource.setValidationQuery(validationQuery);
		strb = new StringBuffer();
		strb.append(hiveJdbcPrefix).append(jdbcUrl).append("/").append(jdbcDB);
		druidDataSource.setUrl(strb.toString());

		druidDataSource.setDriverClassName(driverClass);
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(druidDataSource);
		return jdbcTemplate;
	}
}
