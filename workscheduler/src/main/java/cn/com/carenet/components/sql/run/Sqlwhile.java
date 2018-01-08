package cn.com.carenet.components.sql.run;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ljd
 *mysql操作
 */
public class Sqlwhile {
	private static Logger logger = LoggerFactory.getLogger(Sqlwhile.class);
	public static Statement stmt;
	public static Connection coon;
	public static ResultSet rs;
	
	public Sqlwhile() {
	}
	public  Sqlwhile(String url,String user,String password,String db,String sqls,String jdbcType) throws SQLException{
		coon = getConnection(url,user,password,db,jdbcType);
		String[] split = sqls.split(",");
		for (String sql : split) {
			stmt = (Statement)coon.createStatement();
			if(sql.startsWith("select")){
				rs = stmt.executeQuery(sql);
			}else {
				stmt.executeUpdate(sql);
			}
			stmt.close();
			rs.close();
		}
	}
	public static Connection getConnection(String url,String user,String password,String db,String jdbcType) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sql_url = null;
		try {
			if(jdbcType.equals("mysql")){
				Class.forName("com.mysql.jdbc.Driver");
				sql_url =  sb.append("jdbc:mysql://").append(url).append("/").append(db);
			}else if(jdbcType.equals("oracle")){
				Class.forName("oracle.jdbc,driver.OracleDriver"); 
				sql_url =  sb.append("jdbc:oracle://").append(url).append("/").append(db);
			}
			coon = DriverManager.getConnection(sql_url.toString(), user, password);
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return coon;
}
}