package cn.com.carenet.scheduler.entity;

import java.util.List;

public class AlgorithmModelParam {
	/**  */
	private Integer opetareId;
	/**  */
	private Integer paramId;
	/**  */
	private String paramName;
	/**  */
	private String paramValue;
	/**  */
	private String dataType;
	/**  */
	private String modelId;
	/**  */
	private List<String> preOperateId;
	/**  */
	private List<String> dataSourceId;
	
	
	
	public Integer getParamId() {
		return paramId;
	}
	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public Integer getOpetareId() {
		return opetareId;
	}
	public void setOpetareId(Integer opetareId) {
		this.opetareId = opetareId;
	}

	public List<String> getPreOperateId() {
		return preOperateId;
	}
	public void setPreOperateId(List<String> preOperateId) {
		this.preOperateId = preOperateId;
	}
	public List<String> getDataSourceId() {
		return dataSourceId;
	}
	public void setDataSourceId(List<String> dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AlgorithmModelParam [opetareId=");
		builder.append(opetareId);
		builder.append(", paramId=");
		builder.append(paramId);
		builder.append(", paramName=");
		builder.append(paramName);
		builder.append(", paramValue=");
		builder.append(paramValue);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", modelId=");
		builder.append(modelId);
		builder.append(", preOperateId=");
		builder.append(preOperateId);
		builder.append(", dataSourceId=");
		builder.append(dataSourceId);
		builder.append("]");
		return builder.toString();
	}


	
	
}
