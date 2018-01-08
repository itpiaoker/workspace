package cn.com.carenet.components.hive.beans;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Sherard Lee
 * @since 03/JULY/2017
 */
public class DataSourceOptions implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7197816674815816675L;
	final private static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	final private static String DEFAULT_FIELD_DELIMITER = " ";
	final private static String DEFAULT_RECORD_DELIMITER = "\t\n";
	
	private String datasourceID;
	/* 数据源类型：mysql,redis,hdfs,ftp...*/
	private String sourceName;
	/* 输入还是输出源 0,输入；1,输出 */
	private int sourceType;
	
	/* spark: 数据源编号*/
	private String sourceNo;
	
	private String recordDelimiter = DEFAULT_RECORD_DELIMITER;
	private List<DataSourceInfo> dataSourceInfos;
	private String datasouceDelimiter = DEFAULT_FIELD_DELIMITER;
	private String tableName;
	private String timeFormat = DEFAULT_TIME_FORMAT;
	
	/* local file, ftp, hdfs*/
	private String sshHost;
	private String sshUser;
	private String sshPasswd;
	private String path;
	private int rotationFileSizeInMB;
	private int rotationTimeInSec;
	private String ftpUrl;
	private String ftpHost;
	private int ftpPort;
	private String ftpUserName;
	private String ftpPassword;
	private String ftpEncoding = "UTF-8";
	private String hdfsUrl;
	private String filePrifix;
	private String fileExtension = ".log";
	private int syncCount;
	
	/* relational database */
	private String jdbcDB;
	private String jdbcTable;
	private String jdbcType;
	private String jdbcUrl;
	private String jdbcUser;
	private String jdbcPasswd;
	private String jdbcDriver;
	public String getDatasourceID() {
		return datasourceID;
	}
	public void setDatasourceID(String datasourceID) {
		this.datasourceID = datasourceID;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public int getSourceType() {
		return sourceType;
	}
	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceNo() {
		return sourceNo;
	}
	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}
	public String getRecordDelimiter() {
		return recordDelimiter;
	}
	public void setRecordDelimiter(String recordDelimiter) {
		this.recordDelimiter = recordDelimiter;
	}
	public List<DataSourceInfo> getDataSourceInfos() {
		return dataSourceInfos;
	}
	public void setDataSourceInfos(List<DataSourceInfo> dataSourceInfos) {
		this.dataSourceInfos = dataSourceInfos;
	}
	public String getDatasouceDelimiter() {
		return datasouceDelimiter;
	}
	public void setDatasouceDelimiter(String datasouceDelimiter) {
		this.datasouceDelimiter = datasouceDelimiter;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTimeFormat() {
		return timeFormat;
	}
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
	public String getSshHost() {
		return sshHost;
	}
	public void setSshHost(String sshHost) {
		this.sshHost = sshHost;
	}
	public String getSshUser() {
		return sshUser;
	}
	public void setSshUser(String sshUser) {
		this.sshUser = sshUser;
	}
	public String getSshPasswd() {
		return sshPasswd;
	}
	public void setSshPasswd(String sshPasswd) {
		this.sshPasswd = sshPasswd;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getRotationFileSizeInMB() {
		return rotationFileSizeInMB;
	}
	public void setRotationFileSizeInMB(int rotationFileSizeInMB) {
		this.rotationFileSizeInMB = rotationFileSizeInMB;
	}
	public int getRotationTimeInSec() {
		return rotationTimeInSec;
	}
	public void setRotationTimeInSec(int rotationTimeInSec) {
		this.rotationTimeInSec = rotationTimeInSec;
	}
	public String getFtpUrl() {
		return ftpUrl;
	}
	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
	public String getFtpHost() {
		return ftpHost;
	}
	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}
	public int getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpUserName() {
		return ftpUserName;
	}
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public String getFtpEncoding() {
		return ftpEncoding;
	}
	public void setFtpEncoding(String ftpEncoding) {
		this.ftpEncoding = ftpEncoding;
	}
	public String getHdfsUrl() {
		return hdfsUrl;
	}
	public void setHdfsUrl(String hdfsUrl) {
		this.hdfsUrl = hdfsUrl;
	}
	public String getFilePrifix() {
		return filePrifix;
	}
	public void setFilePrifix(String filePrifix) {
		this.filePrifix = filePrifix;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public int getSyncCount() {
		return syncCount;
	}
	public void setSyncCount(int syncCount) {
		this.syncCount = syncCount;
	}
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
	public String getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
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
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

}
