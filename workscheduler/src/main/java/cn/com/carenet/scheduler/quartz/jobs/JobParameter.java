package cn.com.carenet.scheduler.quartz.jobs;

import java.io.Serializable;
import java.nio.file.Path;

public class JobParameter implements Serializable{
	private static final long serialVersionUID = -4641873109057455643L;
	
	public static final String JOB_PARAM = "jobParam";
    private String	workFlowID;
    private String	typeName;
    private String	jobName;
    private String	jobGroup;
    //private String	jobTrigger;
    private String	status;
    private String	cronExpression;
    private long	timestampExpression;
    private long	startTimeStamp = System.currentTimeMillis();
    private Boolean	isSync = false;
    private String	description;
    private String	jobClassName;
    private String	commandStr;
    private Path	commandEnvironmentPath;
    private Object	informs;
    
    
	public String getWorkFlowID() {
		return workFlowID;
	}
	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Boolean getIsSync() {
		return isSync;
	}
	public void setIsSync(Boolean isSync) {
		this.isSync = isSync;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public Object getInforms() {
		return informs;
	}
	public void setInforms(Object informs) {
		this.informs = informs;
	}
	public Path getCommandEnvironmentPath() {
		return commandEnvironmentPath;
	}
	public void setCommandEnvironmentPath(Path commandEnvironmentPath) {
		this.commandEnvironmentPath = commandEnvironmentPath;
	}
	public String getCommandStr() {
		return commandStr;
	}
	
	/**
	 * <b>COMMAND LINE EXAMPLE:</b> spark-submit --master yarn-cluster --executor-memory 20G --num-executors 50 --class org.apache.spark.examples.SparkPi file://path/to/examples.jar
	 */
	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}
	public long getStartTimeStamp() {
		return startTimeStamp;
	}
	public void setStartTimeStamp(long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}
	public long getTimestampExpression() {
		return timestampExpression;
	}
	public void setTimestampExpression(long timestampExpression) {
		this.timestampExpression = timestampExpression;
	}
	
}
