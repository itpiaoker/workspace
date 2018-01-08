package cn.com.carenet.scheduler.bean.greenplum;

import java.io.Serializable;

public class GpDBBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1457411786323163887L;
	private String jdbcDB;
	private String jdbcTable;
	private String jdbcUrl;
	private String jdbcUser;
	private String jdbcPasswd;
	public String getJdbcDB() {
		return jdbcDB;
	}
	public void setJdbcDB(String jdbcDB) {
		this.jdbcDB = jdbcDB;
	}
	public String getJdbcTable() {
		return jdbcTable;
	}
	public void setJdbcTable(String jdbcTable) {
		this.jdbcTable = jdbcTable;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getJdbcUser() {
		return jdbcUser;
	}
	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}
	public String getJdbcPasswd() {
		return jdbcPasswd;
	}
	public void setJdbcPasswd(String jdbcPasswd) {
		this.jdbcPasswd = jdbcPasswd;
	}
}
