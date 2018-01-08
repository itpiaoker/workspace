package cn.com.carenet.scheduler.bean;

import java.util.Map;

public class EtlAllBean {
	private String workFlowID;
	private String workFlowType;
	private Map<String, DataSchema> dataSchemas;
	private Map<String, DataSourceProp> dataSources;
	public String getWorkFlowID() {
		return workFlowID;
	}
	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}
	public String getWorkFlowType() {
		return workFlowType;
	}
	public void setWorkFlowType(String workFlowType) {
		this.workFlowType = workFlowType;
	}
	public Map<String, DataSchema> getDataSchemas() {
		return dataSchemas;
	}
	public void setDataSchemas(Map<String, DataSchema> dataSchemas) {
		this.dataSchemas = dataSchemas;
	}
	public Map<String, DataSourceProp> getDataSources() {
		return dataSources;
	}
	public void setDataSources(Map<String, DataSourceProp> dataSources) {
		this.dataSources = dataSources;
	}
}
