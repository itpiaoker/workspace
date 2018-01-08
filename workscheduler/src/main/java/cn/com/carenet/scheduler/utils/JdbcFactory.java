package cn.com.carenet.scheduler.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

public class JdbcFactory {
	final static private Logger LOG = LoggerFactory.getLogger(JdbcFactory.class);
	private static String jdbcUrl = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&rewriteBatchedStatements=true";
	private static String jdbcUser;
	private static String jdbcPasswd;
	private static String jdbcClassName;
	private static JdbcTemplate jdbcTemplate;
	static {
		try {
			Properties prop = PropertiesReader.getAppProperties();
			jdbcUrl = prop.getProperty("spring.datasource.url");
			jdbcUser = prop.getProperty("spring.datasource.username");
			jdbcPasswd = prop.getProperty("spring.datasource.password");
			jdbcClassName = prop.getProperty("spring.datasource.driver-class-name");

			DruidDataSource dataSource = new DruidDataSource();
			dataSource.setUrl(jdbcUrl);
			dataSource.setUsername(jdbcUser);
			dataSource.setPassword(jdbcPasswd);
			dataSource.setDriverClassName(jdbcClassName);

			jdbcTemplate = new JdbcTemplate();
			jdbcTemplate.setDataSource(dataSource);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
