package cn.com.carenet.components.sql.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * sql 增删改查
 * @author hdfs
 */
public class SqlJDBC {
	public static Statement stmt;
	public static Connection coon;
	public static ResultSet rs;
	private static Logger logger = LoggerFactory.getLogger(SqlJDBC.class);
	/**
	 *
	 * @param ljd
	 * @return 
	 */
	public static Connection getConnection(String jdbcUrl,String jdbcUser,String jdbcPasswd,String jdbcDB,String jdbcType) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sql_url = null;
		try {
			if(jdbcType.equals("mysql")){
				Class.forName("com.mysql.jdbc.Driver");
				sql_url =  sb.append("jdbc:mysql://").append(jdbcUrl).append("/").append(jdbcDB);
			}else if(jdbcType.equals("oracle")){
				Class.forName("oracle.jdbc,driver.OracleDriver"); 
				sql_url =  sb.append("jdbc:oracle://").append(jdbcUrl).append("/").append(jdbcDB);
			}
			coon = DriverManager.getConnection(sql_url.toString(), jdbcUser, jdbcPasswd);
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return coon;
	}
	public void create_table(String jdbcUrl,String jdbcUser,String jdbcPasswd,String jdbcDB,String sql,String jdbcType) {
		coon = getConnection(jdbcUrl,jdbcUser,jdbcPasswd,jdbcDB,jdbcType);
		try {
			stmt = (Statement)coon.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}
	public void select_table(String jdbcUrl,String jdbcUser,String jdbcPasswd,String jdbcDB,String sql,String jdbcType){
		coon = getConnection(jdbcUrl,jdbcUser,jdbcPasswd,jdbcDB,jdbcType);
		try {
			stmt = (Statement)coon.createStatement();
			rs = stmt.executeQuery(sql);
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}
	public void delete_table(String jdbcUrl,String jdbcUser,String jdbcPasswd,String jdbcDB,String sql,String jdbcType){
		coon = getConnection(jdbcUrl,jdbcUser,jdbcPasswd,jdbcDB,jdbcType);
		try {
			stmt = (Statement)coon.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			coon.close();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}
	public void updete_table(String jdbcUrl,String jdbcUser,String jdbcPasswd,String jdbcDB,String sql,String jdbcType){
		coon = getConnection(jdbcUrl,jdbcUser,jdbcPasswd,jdbcDB,jdbcType);
		try {
			stmt = (Statement)coon.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			coon.close();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}
	public void insert_table(String jdbcUrl,String jdbcUser,String jdbcPasswd,String jdbcDB,String sql,String jdbcType){
		coon = getConnection( jdbcUrl,jdbcUser,jdbcPasswd,jdbcDB,jdbcType);
		try {
			stmt = coon.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			coon.close();
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}
 }