package cn.com.carenet.scheduler.bean;

public class ChiSqTest {
	/**  */
	private String workFlowId;
	/**  */
	private String statistic;
	/**  */
	private String degreesFreedom;
	/**  */
	private String method;
	/**  */
	private String pValue;
	/**  */
	private String nullHypothesis;
	public String getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}
	public String getStatistic() {
		return statistic;
	}
	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}
	public String getDegreesFreedom() {
		return degreesFreedom;
	}
	public void setDegreesFreedom(String degreesFreedom) {
		this.degreesFreedom = degreesFreedom;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getpValue() {
		return pValue;
	}
	public void setpValue(String pValue) {
		this.pValue = pValue;
	}
	public String getNullHypothesis() {
		return nullHypothesis;
	}
	public void setNullHypothesis(String nullHypothesis) {
		this.nullHypothesis = nullHypothesis;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChiSqTest [workFlowId=");
		builder.append(workFlowId);
		builder.append(", statistic=");
		builder.append(statistic);
		builder.append(", degreesFreedom=");
		builder.append(degreesFreedom);
		builder.append(", method=");
		builder.append(method);
		builder.append(", pValue=");
		builder.append(pValue);
		builder.append(", nullHypothesis=");
		builder.append(nullHypothesis);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
