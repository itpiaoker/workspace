package cn.com.carenet.components.hive.beans;

import java.io.Serializable;

public class DataSourceInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3712372235347669428L;
	final public static String number = "number";
	final public static String string = "string";
	final public static String date = "date";
	private String fieldName;
	/* choose from enum fieldTypes */
	private String fieldType;
	
	private int colNum;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
}
