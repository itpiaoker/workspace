package cn.com.carenet.scheduler.bean;

import java.io.Serializable;

public class OperateComponent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7775205035432866468L;
	private String preOpID;
	private String sourcePrimary;
	private String tmpTableName;
	
	public String getPreOpID() {
		return preOpID;
	}
	public void setPreOpID(String preOpID) {
		this.preOpID = preOpID;
	}
	public String getSourcePrimary() {
		return sourcePrimary;
	}
	public void setSourcePrimary(String sourcePrimary) {
		this.sourcePrimary = sourcePrimary;
	}
	public String getStreamID() {
		return tmpTableName;
	}
	public void setStreamID(String streamID) {
		this.tmpTableName = streamID;
	}
}
