package cn.com.carenet.scheduler.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Sherard Lee
 * @since 25/May/2017
 */
@Deprecated
public class OperateOptionsBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -105179613445326290L;
	final private static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	final private static String DEFAULT_FIELD_DELIMITER = " ";
	final private static String DEFAULT_RECORD_DELIMITER = "\t\n";
	final private static int DEFAULT_TICK_TUPLE_FREQ = 0;
	final private static int DEFAULT_WORKER_NUM = 1;
	final private static int DEFAULT_PARALLELISM_NUM = 1;
	/* 组件id */
	private String operateID;
	/* 组件操作类型 */
	private String operateType;
	/* 关键词，逗号分割 */
	private String values;
	/* 需要处理的字段（逗号分割） */
	private String fieldNames;
	private String between;
	private String and;
	/* 是否在区间内 */
	private boolean isSection = true;
	/* whether up or down in sort */
	private boolean upSort = true;
	/* storm: bolt's worker's number */
	private int workerNum = DEFAULT_WORKER_NUM;
	/* storm: bolt's thread's number */
	private int parallelismNum = DEFAULT_PARALLELISM_NUM;
	/* storm: tick tuple time in seconds */
	private int tickTupleFreqSecs = DEFAULT_TICK_TUPLE_FREQ;
	/*
	 * components record the last elements' informations including id(preOpID),
	 * which data source comes from(sourcePrimary/streamID), groupMethod(in
	 * storm)
	 */
	private List<OperateComponent> components;
	/* which work flow does this element belongs to */
	private String workFlowID;
	/*
	 * what kind of tool does this element belongs to
	 * (spark-core/storm/spark-streaming/spark-sql...)
	 */
	private String typeName;
	/* a list includes all the columns' informations */
	private List<SingleMetaData> metaDatas;
	private String datasouceDelimiter = DEFAULT_FIELD_DELIMITER;
	private String recordDelimiter = DEFAULT_RECORD_DELIMITER;
	/*
	 * if this operation produce a new data structure, tableName must be set and
	 * dataSourceInfo must be specified
	 */
	private String tableName;
	/* specify the format of time in data */
	private String timeFormat = DEFAULT_TIME_FORMAT;
	/* arithmetic calculating tokens */
	private String calToken;

	
	/**  */
	private String algorithmModelID;
	private String algorithmID;
	private String algorithmModelName;
	private String algorithmModelType;
	private String algorithmIsTrain;
	private String algorithmIsSpecifiedDataFormat;
	private String algorithmDataFormat;
	private String algorithmLabelField;
	private String algorithmModelSavePath;
	private String algorithmRank;
	private String algorithmIterations;
	private String algorithmRegParam;
	private String algorithmSeed;
	private String algorithmStepSize;
	
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

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
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

	public boolean isSection() {
		return isSection;
	}

	public void setSection(boolean isSection) {
		this.isSection = isSection;
	}

	public int getWorkerNum() {
		return workerNum;
	}

	public void setWorkerNum(int workerNum) {
		this.workerNum = workerNum;
	}

	public int getParallelismNum() {
		return parallelismNum;
	}

	public void setParallelismNum(int parallelismNum) {
		this.parallelismNum = parallelismNum;
	}

	public int getTickTupleFreqSecs() {
		return tickTupleFreqSecs;
	}

	public void setTickTupleFreqSecs(int tickTupleFreqSecs) {
		this.tickTupleFreqSecs = tickTupleFreqSecs;
	}

	public List<OperateComponent> getComponents() {
		return components;
	}

	public void setComponents(List<OperateComponent> components) {
		this.components = components;
	}

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

	public String getDatasouceDelimiter() {
		return datasouceDelimiter;
	}

	public void setDatasouceDelimiter(String datasouceDelimiter) {
		this.datasouceDelimiter = datasouceDelimiter;
	}

	public String getRecordDelimiter() {
		return recordDelimiter;
	}

	public void setRecordDelimiter(String recordDelimiter) {
		this.recordDelimiter = recordDelimiter;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getCalToken() {
		return calToken;
	}

	public void setCalToken(String calToken) {
		this.calToken = calToken;
	}

	public boolean isUpSort() {
		return upSort;
	}

	public void setUpSort(boolean upSort) {
		this.upSort = upSort;
	}

	public List<SingleMetaData> getMetaDatas() {
		return metaDatas;
	}

	public void setMetaDatas(List<SingleMetaData> metaDatas) {
		this.metaDatas = metaDatas;
	}

	public String getAlgorithmModelID() {
		return algorithmModelID;
	}

	public void setAlgorithmModelID(String algorithmModelID) {
		this.algorithmModelID = algorithmModelID;
	}

	public String getAlgorithmID() {
		return algorithmID;
	}

	public void setAlgorithmID(String algorithmID) {
		this.algorithmID = algorithmID;
	}

	public String getAlgorithmModelName() {
		return algorithmModelName;
	}

	public void setAlgorithmModelName(String algorithmModelName) {
		this.algorithmModelName = algorithmModelName;
	}

	public String getAlgorithmModelType() {
		return algorithmModelType;
	}

	public void setAlgorithmModelType(String algorithmModelType) {
		this.algorithmModelType = algorithmModelType;
	}

	public String getAlgorithmIsTrain() {
		return algorithmIsTrain;
	}

	public void setAlgorithmIsTrain(String algorithmIsTrain) {
		this.algorithmIsTrain = algorithmIsTrain;
	}

	public String getAlgorithmIsSpecifiedDataFormat() {
		return algorithmIsSpecifiedDataFormat;
	}

	public void setAlgorithmIsSpecifiedDataFormat(String algorithmIsSpecifiedDataFormat) {
		this.algorithmIsSpecifiedDataFormat = algorithmIsSpecifiedDataFormat;
	}

	public String getAlgorithmDataFormat() {
		return algorithmDataFormat;
	}

	public void setAlgorithmDataFormat(String algorithmDataFormat) {
		this.algorithmDataFormat = algorithmDataFormat;
	}

	public String getAlgorithmLabelField() {
		return algorithmLabelField;
	}

	public void setAlgorithmLabelField(String algorithmLabelField) {
		this.algorithmLabelField = algorithmLabelField;
	}

	public String getAlgorithmModelSavePath() {
		return algorithmModelSavePath;
	}

	public void setAlgorithmModelSavePath(String algorithmModelSavePath) {
		this.algorithmModelSavePath = algorithmModelSavePath;
	}

	public String getAlgorithmRank() {
		return algorithmRank;
	}

	public void setAlgorithmRank(String algorithmRank) {
		this.algorithmRank = algorithmRank;
	}

	public String getAlgorithmIterations() {
		return algorithmIterations;
	}

	public void setAlgorithmIterations(String algorithmIterations) {
		this.algorithmIterations = algorithmIterations;
	}

	public String getAlgorithmRegParam() {
		return algorithmRegParam;
	}

	public void setAlgorithmRegParam(String algorithmRegParam) {
		this.algorithmRegParam = algorithmRegParam;
	}

	public String getAlgorithmSeed() {
		return algorithmSeed;
	}

	public void setAlgorithmSeed(String algorithmSeed) {
		this.algorithmSeed = algorithmSeed;
	}

	public String getAlgorithmStepSize() {
		return algorithmStepSize;
	}

	public void setAlgorithmStepSize(String algorithmStepSize) {
		this.algorithmStepSize = algorithmStepSize;
	}
}
