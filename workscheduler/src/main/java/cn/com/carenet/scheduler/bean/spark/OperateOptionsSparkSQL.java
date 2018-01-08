package cn.com.carenet.scheduler.bean.spark;


public class OperateOptionsSparkSQL{
	/**
	 * 
	 */
	private String SQLID;
	private String SQL;
	private boolean isSave;
	private String asTableName;
	
	public String getSQLID() {
		return SQLID;
	}
	public void setSQLID(String sQLID) {
		SQLID = sQLID;
	}
	public String getSQL() {
		return SQL;
	}
	public void setSQL(String sQL) {
		SQL = sQL;
	}
	public boolean isSave() {
		return isSave;
	}
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}
	public String getAsTableName() {
		return asTableName;
	}
	public void setAsTableName(String asTableName) {
		this.asTableName = asTableName;
	}

}
