package cn.com.carenet.scheduler.entity;

public class TaskDetails {
	private String taskId;
	private String typeName;
	private int taskRepeat;
	private String bigBean;
	private String dataSourcesInfos;
	private String operatesInfos;
	private String fieldsInfos;
	private String sqlsInfos;
	private long quartzTime;
	private String quartzCron;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getTaskRepeat() {
		return taskRepeat;
	}
	public void setTaskRepeat(int taskRepeat) {
		this.taskRepeat = taskRepeat;
	}
	public String getBigBean() {
		return bigBean;
	}
	public void setBigBean(String bigBean) {
		this.bigBean = bigBean;
	}
	public String getDataSourcesInfos() {
		return dataSourcesInfos;
	}
	public void setDataSourcesInfos(String dataSourcesInfos) {
		this.dataSourcesInfos = dataSourcesInfos;
	}
	public String getOperatesInfos() {
		return operatesInfos;
	}
	public void setOperatesInfos(String operatesInfos) {
		this.operatesInfos = operatesInfos;
	}
	public String getFieldsInfos() {
		return fieldsInfos;
	}
	public void setFieldsInfos(String fieldsInfos) {
		this.fieldsInfos = fieldsInfos;
	}
	public String getSqlsInfos() {
		return sqlsInfos;
	}
	public void setSqlsInfos(String sqlsInfos) {
		this.sqlsInfos = sqlsInfos;
	}
	public long getQuartzTime() {
		return quartzTime;
	}
	public void setQuartzTime(long quartzTime) {
		this.quartzTime = quartzTime;
	}
	public String getQuartzCron() {
		return quartzCron;
	}
	public void setQuartzCron(String quartzCron) {
		this.quartzCron = quartzCron;
	}
}
