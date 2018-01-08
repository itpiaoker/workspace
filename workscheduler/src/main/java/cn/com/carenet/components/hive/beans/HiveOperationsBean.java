package cn.com.carenet.components.hive.beans;

import java.util.Map;

import cn.com.carenet.scheduler.bean.DataSourceProp;

public class HiveOperationsBean {
	private Map<String, DataSourceProp> dataSourceInfos;

	/* hive */
	private String metaStoreURI = "thrift://localhost:9083";
	private String dbName = "default";
	private String hiveTableName;
	private String hiveJdbcUrl = "localhost:10000";

	public Map<String, DataSourceProp> getDataSourceInfos() {
		return dataSourceInfos;
	}

	public void setDataSourceInfos(Map<String, DataSourceProp> dataSourceInfos) {
		this.dataSourceInfos = dataSourceInfos;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getHiveJdbcUrl() {
		return hiveJdbcUrl;
	}

	public void setHiveJdbcUrl(String hiveJdbcUrl) {
		this.hiveJdbcUrl = hiveJdbcUrl;
	}

	public String getHiveTableName() {
		return hiveTableName;
	}

	public void setHiveTableName(String hiveTableName) {
		this.hiveTableName = hiveTableName;
	}

	public String getMetaStoreURI() {
		return metaStoreURI;
	}

	public void setMetaStoreURI(String metaStoreURI) {
		this.metaStoreURI = metaStoreURI;
	}
}
