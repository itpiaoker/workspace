package cn.com.carenet.scheduler.entity;

public class AlgorithmModel {
	/**  */
	private Integer modelId;
	/**  */
	private String modelName;
	/**  */
	private String modelDesc;
	/**  */
	private String modelInfos;
	/**  */
	private String operatesInfos;
	/**  */
	private String fieldsInfos;
	/**  */
	private String dataSourcesInfos;
	
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelDesc() {
		return modelDesc;
	}
	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}
	public String getModelInfos() {
		return modelInfos;
	}
	public void setModelInfos(String modelInfos) {
		this.modelInfos = modelInfos;
	}
	
	public String getOperatesInfos() {
		return operatesInfos;
	}
	public void setOperatesInfos(String operatesInfos) {
		this.operatesInfos = operatesInfos;
	}
	public String getFieldsInfos() {
		return fieldsInfos;
	}
	public void setFieldsInfos(String fieldsInfos) {
		this.fieldsInfos = fieldsInfos;
	}
	public String getDataSourcesInfos() {
		return dataSourcesInfos;
	}
	public void setDataSourcesInfos(String dataSourcesInfos) {
		this.dataSourcesInfos = dataSourcesInfos;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AlgorithmModel [modelId=");
		builder.append(modelId);
		builder.append(", modelName=");
		builder.append(modelName);
		builder.append(", modelDesc=");
		builder.append(modelDesc);
		builder.append(", modelInfos=");
		builder.append(modelInfos);
		builder.append(", operatesInfos=");
		builder.append(operatesInfos);
		builder.append(", fieldsInfos=");
		builder.append(fieldsInfos);
		builder.append(", dataSourcesInfos=");
		builder.append(dataSourcesInfos);
		builder.append("]");
		return builder.toString();
	}

	
	
}
