package cn.com.carenet.scheduler.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "job.spark")
@Component
public class SparkJobPropertiesBean {
	private String submitcmd;
	private String frameworkurl;
	private String workflowsurl;
	public String getSubmitcmd() {
		return submitcmd;
	}
	public void setSubmitcmd(String submitcmd) {
		this.submitcmd = submitcmd;
	}
	public String getFrameworkurl() {
		return frameworkurl;
	}
	public void setFrameworkurl(String frameworkurl) {
		this.frameworkurl = frameworkurl;
	}
	public String getWorkflowsurl() {
		return workflowsurl;
	}
	public void setWorkflowsurl(String workflowsurl) {
		this.workflowsurl = workflowsurl;
	}
}
