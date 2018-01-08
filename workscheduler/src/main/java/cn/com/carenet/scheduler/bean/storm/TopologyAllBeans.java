package cn.com.carenet.scheduler.bean.storm;

import java.util.Map;

import cn.com.carenet.scheduler.bean.DataSchema;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.OperateProp;

public class TopologyAllBeans {
	
	private String workFlowID;
	private String workFlowType;
	private Map<String, DataSchema> dataSchemas;
	private Map<String, DataSourceProp> dataSources;
	private Map<String, OperateProp> operateOptions;
	private TopologyEnvProp topologyOptions;
	
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
	public Map<String, OperateProp> getOperateOptions() {
		return operateOptions;
	}
	public void setOperateOptions(Map<String, OperateProp> operateOptions) {
		this.operateOptions = operateOptions;
	}
	public TopologyEnvProp getTopologyOptions() {
		return topologyOptions;
	}
	public void setTopologyOptions(TopologyEnvProp topologyOptions) {
		this.topologyOptions = topologyOptions;
	}
}
