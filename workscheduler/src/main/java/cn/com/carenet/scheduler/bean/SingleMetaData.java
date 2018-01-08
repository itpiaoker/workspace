package cn.com.carenet.scheduler.bean;

import java.io.Serializable;

public class SingleMetaData implements Serializable {

	private static final long serialVersionUID = -3712372235347669428L;
	final public static String TYPE_DOUBLE = "double";
	final public static String TYPE_INTEGER = "int";
	final public static String TYPE_STRING = "string";
	final public static String TYPE_DATE = "date";
	final public static int WF_FIELD_FILTER_NONE = -1;
	final public static int WF_FIELD_FILTER_TEL = 0;
	final public static int WF_FIELD_FILTER_ENUM = 1;
	final public static int WF_FIELD_FILTER_MAXLEN = 2;
	private String fieldName;
	/* choose from enum fieldTypes */
	private String fieldType;
	private int colNum;
	private String dateFormat;
	/*
	 * -1:none; 0:telephone number transfer; 1:enum; 2:limited length
	 */
	private int fieldFilter;

	private String fieldFilterVal;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getFieldFilter() {
		return fieldFilter;
	}

	public void setFieldFilter(int fieldFilter) {
		this.fieldFilter = fieldFilter;
	}

	public String getFieldFilterVal() {
		return fieldFilterVal;
	}

	public void setFieldFilterVal(String fieldFilterVal) {
		this.fieldFilterVal = fieldFilterVal;
	}

}
