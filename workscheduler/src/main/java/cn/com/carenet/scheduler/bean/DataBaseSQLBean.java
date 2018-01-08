package cn.com.carenet.scheduler.bean;

import java.io.Serializable;

/**
 * 
 * @author Sherard Lee
 * @since 11/JULY/2017
 */
public class DataBaseSQLBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4151034808237656499L;
	private String workFlowID;
	private String sourceTabID;
	private String script;
	private String dataBaseName;
	private String ip;
	private Integer port;
	private String urls;
	private String userName;
	private String password;

	public String getWorkFlowID() {
		return workFlowID;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getSourceTabID() {
		return sourceTabID;
	}

	public void setSourceTabID(String sourceTabID) {
		this.sourceTabID = sourceTabID;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
