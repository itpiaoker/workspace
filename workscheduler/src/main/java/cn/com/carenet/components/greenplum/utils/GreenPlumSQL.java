package cn.com.carenet.components.greenplum.utils;

import java.util.HashMap;

import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.alibaba.druid.pool.DruidDataSource;

import cn.com.carenet.scheduler.constant.Constant;

public class GreenPlumSQL {
	final private static Logger LOG = LoggerFactory.getLogger(GreenPlumSQL.class);
	private JdbcTemplate jdbcTemplate;
	
	public GreenPlumSQL(String url,String database,String user,String password){
		String jdbcStr = "jdbc:postgresql:://" + url + "/" + database;
		// String jdbcStr = "jdbc:pivotal:greenplum://" + fullUrl +
		// ";DatabaseName=" + database;
		LOG.debug("Connected to: {}.",jdbcStr);
		DruidDataSource datasource = new DruidDataSource();
		datasource.setDriverClassName(Constant.DRIVER_NAME_GP);
		datasource.setUrl(jdbcStr);
		datasource.setUsername(user);
		datasource.setPassword(password);
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	public void runSQLs(String sqls){
		for (String sql : StringUtils.splitPreserveAllTokens(sqls, ";")) {
			runSQL(sql);
		}
	}
	
	public boolean runSQL(String sql){
		try{
			jdbcTemplate.execute(sql);
		}catch(DataAccessException e){
			return false;
		}
		return true;
		
	}

	public void createTable(String table_name, HashMap<String, String> column_types) {
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE TABLE " + table_name);
		sql.append("(");
		Object[] List = column_types.keySet().toArray();

		for (int i = 0; i < List.length; i++) {
			String col = List[i].toString();
			String type = column_types.get(col);
			sql.append(col + "\t");
			sql.append(type);
			if (i != List.length - 1)
				sql.append(",");
		}
		sql.append(")");
		jdbcTemplate.execute(sql.toString());
	}

}
