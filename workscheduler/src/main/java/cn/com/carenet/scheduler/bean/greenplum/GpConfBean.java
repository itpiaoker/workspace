package cn.com.carenet.scheduler.bean.greenplum;

import java.io.Serializable;
import java.util.Map;

import cn.com.carenet.scheduler.bean.DataSourceOptionsBean;

public class GpConfBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5909728708998864492L;
	private Map<String, DataSourceOptionsBean> dataSourceOptionsBeanMap;
	private GpSQLInfos gpSQLInfos;
	private GpDBBean gpDBBean;

	public Map<String, DataSourceOptionsBean> getDataSourceOptionsBeanMap() {
		return dataSourceOptionsBeanMap;
	}

	public void setDataSourceOptionsBeanMap(Map<String, DataSourceOptionsBean> dataSourceOptionsBeanMap) {
		this.dataSourceOptionsBeanMap = dataSourceOptionsBeanMap;
	}

	public GpSQLInfos getGpSQLInfos() {
		return gpSQLInfos;
	}

	public void setGpSQLInfos(GpSQLInfos gpSQLInfos) {
		this.gpSQLInfos = gpSQLInfos;
	}

	public GpDBBean getGpDBBean() {
		return gpDBBean;
	}

	public void setGpDBBean(GpDBBean gpDBBean) {
		this.gpDBBean = gpDBBean;
	}
}
