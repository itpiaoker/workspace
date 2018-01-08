package cn.com.carenet.scheduler.bean.spark;

import java.util.List;

import cn.com.carenet.scheduler.bean.OperateComponent;
/**
 * 
 * @author Sherard Lee
 * @since 31/May/2017
 */
public class SparkSQLInfos{
	/**
	 * 
	 */
	private String operateID;
	private String operateType;
	private List<SqlType> SQLs;
	private List<OperateComponent> components;
	public String getOperateID() {
		return operateID;
	}
	public void setOperateID(String operateID) {
		this.operateID = operateID;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public List<SqlType> getSQLs() {
		return SQLs;
	}
	public void setSQLs(List<SqlType> sQLs) {
		SQLs = sQLs;
	}
	public List<OperateComponent> getComponents() {
		return components;
	}
	public void setComponents(List<OperateComponent> components) {
		this.components = components;
	}

}
