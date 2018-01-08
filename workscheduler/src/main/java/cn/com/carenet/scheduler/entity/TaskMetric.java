package cn.com.carenet.scheduler.entity;

public class TaskMetric {
	private int taskId;
	private String kafkaTopic;
	private int execStat;
	private String typeName;
	private String topologyId;
	private String optionMap;
	private String extra;
	private String sparkAppId;
	private String sparkDriverHost;
	private String sparkJmxUrl;
	private String sparkJmxProxyUrl;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getKafkaTopic() {
		return kafkaTopic;
	}

	public void setKafkaTopic(String kafkaTopic) {
		this.kafkaTopic = kafkaTopic;
	}

	public int getExecStat() {
		return execStat;
	}

	public void setExecStat(int execStat) {
		this.execStat = execStat;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTopologyId() {
		return topologyId;
	}

	public void setTopologyId(String topologyId) {
		this.topologyId = topologyId;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getOptionMap() {
		return optionMap;
	}

	public void setOptionMap(String optionMap) {
		this.optionMap = optionMap;
	}

	public String getSparkAppId() {
		return sparkAppId;
	}

	public void setSparkAppId(String sparkAppId) {
		this.sparkAppId = sparkAppId;
	}

	public String getSparkDriverHost() {
		return sparkDriverHost;
	}

	public void setSparkDriverHost(String sparkDriverHost) {
		this.sparkDriverHost = sparkDriverHost;
	}

	public String getSparkJmxUrl() {
		return sparkJmxUrl;
	}

	public void setSparkJmxUrl(String sparkJmxUrl) {
		this.sparkJmxUrl = sparkJmxUrl;
	}

	public String getSparkJmxProxyUrl() {
		return sparkJmxProxyUrl;
	}

	public void setSparkJmxProxyUrl(String sparkJmxProxyUrl) {
		this.sparkJmxProxyUrl = sparkJmxProxyUrl;
	}
	
	

}
