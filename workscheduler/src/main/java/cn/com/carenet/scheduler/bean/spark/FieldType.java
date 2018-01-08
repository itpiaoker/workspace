package cn.com.carenet.scheduler.bean.spark;

import cn.com.carenet.scheduler.constant.Constant;

/**
 * Title: Description:
 *
 * @author lianxy
 * @date 2017/5/20
 */
public class FieldType {
	/**  */
	String workFlowID;
	/**  */
	String dataSourceID;
	/** 需要处理的字段名称 */
	String fieldName;
	/** 需要处理的字段数据类型 */
	String fieldType;
	/**  */
	String colNum;
	/** 日期格式 */
	String formatter;
	
	/**
	 * {index:-1, text: '使用过滤条件'}, 
	 * {index:0, text: '手机号脱敏'}, 
	 * {index:1, text:'枚举值', input: true}, 
	 * {index:2, text: '最大长度限制', input: true}
	 * 
	 */
	String fieldFilter;
	/**
	 * 条件值
	 * 枚举值："男,女,男变女，女变男"
	 * 最大长度限制："30"
	 */
	String fieldFilterVal;

	public String getWorkFlowID() {
		return workFlowID;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}

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

	public String getColNum() {
		return colNum;
	}

	public void setColNum(String colNum) {
		this.colNum = colNum;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public String getDataSourceID() {
		return dataSourceID;
	}

	public void setDataSourceID(String dataSourceID) {
		if (dataSourceID.startsWith(Constant.PREFIX_WINDOW_KEY)) {
			dataSourceID = dataSourceID.substring(Constant.PREFIX_WINDOW_KEY.length());
		}
		this.dataSourceID = dataSourceID;
	}

	public String getFieldFilter() {
		return fieldFilter;
	}

	public void setFieldFilter(String fieldFilter) {
		this.fieldFilter = fieldFilter;
	}

	public String getFieldFilterVal() {
		return fieldFilterVal;
	}

	public void setFieldFilterVal(String fieldFilterVal) {
		this.fieldFilterVal = fieldFilterVal;
	}
}
