package cn.com.carenet.scheduler.bean.spark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.carenet.scheduler.constant.Constant;

/**
 * Title: Description:
 *
 * @author lianxy
 * @date 2017/5/18
 */
public class OperateType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7032677897458560481L;
	/** 工作流ID */
	private String workFlowID;
	private String componentID;
	/** 当前处理流程ID */
	private String operateID;
	/** 当前处理流程名称 */
	private String operateType;
	/** 上一个处理流程 */
	// private String preOpType;
	private List<String> preOpType = new ArrayList<String>();
	/** 下一个处理流程 */
	// private String nextOpType;
	private String nextOpType;
	private String between;
	private String and;
	/** 默认处理的数据是某一个区间之内的 */
	private String isSection = "true";
	/** 默认是升序排序 */
	private String isAscending = "true";
	/** 默认跳过此处理流程 */
	private String isSkip = "true";
	/** 数据源ID集合 */
	private List<String> dataSourceID = new ArrayList<String>();
	/** 在单个处理流程中目前只支持单字段过滤 */
	/** 多字段组合成一个key进行处理 */
	/** 比如: 分组,去重,过滤 */
	private List<String> values = new ArrayList<String>();

	private String fieldNames;

	private String groupID;

	private String tableName;

	private String groupFieldName;

	private String tmpTableName;
	
	private String algorithmModelID;
	
	private List<String> leftJoinSort;
	
	private List<String> independents = new ArrayList<String>();
	
	public String getWorkFlowID() {
		return workFlowID;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}

	public String getComponentID() {
		return componentID;
	}
	
	/** ljd 原始数据之间的分隔符 */
	private String splitSysmbol;

	public String getSplitSysmbol() {
		return splitSysmbol;
	}

	public void setSplitSysmbol(String splitSysmbol) {
		this.splitSysmbol = splitSysmbol;
	}

	public void setComponentID(String componentID) {
		if (componentID.startsWith(Constant.PREFIX_WINDOW_KEY)) {
			componentID = componentID.substring(Constant.PREFIX_WINDOW_KEY.length());
		}
		this.componentID = componentID;
	}

	public String getOperateID() {
		return operateID;
	}

	public void setOperateID(String operateID) {
		if (operateID.startsWith(Constant.PREFIX_WINDOW_KEY)) {
			operateID = operateID.substring(Constant.PREFIX_WINDOW_KEY.length());
		}
		this.operateID = operateID;
	}

	public List<String> getPreOpType() {
		return preOpType;
	}

	public void setPreOpType(List<String> preOpType) {
		List<String> newpreOpType = new ArrayList<>();
		for (String preOpTypea : preOpType) {
			if (preOpTypea.startsWith(Constant.PREFIX_WINDOW_KEY)) {
				preOpTypea = preOpTypea.substring(Constant.PREFIX_WINDOW_KEY.length());
			}
			newpreOpType.add(preOpTypea);
		}
		this.preOpType = newpreOpType;
	}



	public String getNextOpType() {
		return nextOpType;
	}

	public void setNextOpType(String nextOpType) {
		this.nextOpType = nextOpType;
	}

	public String getBetween() {
		return between;
	}

	public void setBetween(String between) {
		this.between = between;
	}

	public String getAnd() {
		return and;
	}

	public void setAnd(String and) {
		this.and = and;
	}

	public String getIsSection() {
		return isSection;
	}

	public void setIsSection(String isSection) {
		this.isSection = isSection;
	}

	public String getIsAscending() {
		return isAscending;
	}

	public void setIsAscending(String isAscending) {
		this.isAscending = isAscending;
	}

	public String getIsSkip() {
		return isSkip;
	}

	public void setIsSkip(String isSkip) {
		this.isSkip = isSkip;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public List<String> getDataSourceID() {
		return dataSourceID;
	}

	public void setDataSourceID(List<String> dataSourceID) {
		List<String> newSourceID = new ArrayList<>();
		for (String sourceIDa : dataSourceID) {
			if (sourceIDa.startsWith(Constant.PREFIX_WINDOW_KEY)) {
				sourceIDa = sourceIDa.substring(Constant.PREFIX_WINDOW_KEY.length());
			}
			newSourceID.add(sourceIDa);
		}
		this.dataSourceID = newSourceID;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getGroupFieldName() {
		return groupFieldName;
	}

	public void setGroupFieldName(String groupFieldName) {
		this.groupFieldName = groupFieldName;
	}

	public String getTmpTableName() {
		return tmpTableName;
	}

	public void setTmpTableName(String tmpTableName) {
		this.tmpTableName = tmpTableName;
	}

	public List<String> getLeftJoinSort() {
		return leftJoinSort;
	}

	public void setLeftJoinSort(List<String> leftJoinSort) {
		this.leftJoinSort = leftJoinSort;
	}

	public String getAlgorithmModelID() {
		return algorithmModelID;
	}

	public void setAlgorithmModelID(String algorithmModelID) {
		this.algorithmModelID = algorithmModelID;
	}

	public List<String> getIndependents() {
		return independents;
	}

	public void setIndependents(List<String> independents) {
		this.independents = independents;
	}


	
	
}
