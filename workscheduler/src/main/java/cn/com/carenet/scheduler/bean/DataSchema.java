package cn.com.carenet.scheduler.bean;

import java.io.Serializable;
import java.util.List;

public class DataSchema implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3091108332359996164L;
	final private static String DEFAULT_FIELD_DELIMITER = " ";
	final private static String DEFAULT_RECORD_DELIMITER = "\t\n";
	
	private String recordDelimiter = DEFAULT_RECORD_DELIMITER;
	private String datasouceDelimiter = DEFAULT_FIELD_DELIMITER;
	private String tmpTableName;
	private List<SingleMetaData> metaDatas;
	private String dataSourceID;
	/* -1:none; 0,input；1,output */
	private int putType = -1;
	@Deprecated
	private String timeFormat;
	
	public String getRecordDelimiter() {
		return recordDelimiter;
	}
	public void setRecordDelimiter(String recordDelimiter) {
		this.recordDelimiter = recordDelimiter;
	}
	public String getDatasouceDelimiter() {
		return datasouceDelimiter;
	}
	public void setDatasouceDelimiter(String datasouceDelimiter) {
		this.datasouceDelimiter = datasouceDelimiter;
	}
	public String getTableName() {
		return tmpTableName;
	}
	public void setTableName(String tableName) {
		this.tmpTableName = tableName;
	}
	public List<SingleMetaData> getMetaDatas() {
		return metaDatas;
	}
	public void setMetaDatas(List<SingleMetaData> metaDatas) {
		this.metaDatas = metaDatas;
	}
	public String getDataSourceID() {
		return dataSourceID;
	}
	public void setDataSourceID(String dataSourceID) {
		this.dataSourceID = dataSourceID;
	}
	public int getPutType() {
		return putType;
	}
	public void setPutType(int putType) {
		this.putType = putType;
	}
	public String getTimeFormat() {
		return timeFormat;
	}
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
	public String getTmpTableName() {
		return tmpTableName;
	}
	public void setTmpTableName(String tmpTableName) {
		this.tmpTableName = tmpTableName;
	}
	
}
