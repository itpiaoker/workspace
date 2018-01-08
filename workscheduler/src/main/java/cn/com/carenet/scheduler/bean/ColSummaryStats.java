package cn.com.carenet.scheduler.bean;

import java.util.Map;

public class ColSummaryStats {
	/**  */
	private String workFlowId;
	/**  */
	private String count;
	/**  */
	private Map<String, String> max;
	/**  */
	private Map<String, String> min;
	/**  */
	private Map<String, String> mean;
	/**  */
	private Map<String, String> normL1;
	/**  */
	private Map<String, String> normL2;
	/**  */
	private Map<String, String> numNonzeros;
	/**  */
	private Map<String, String> variance;
	
	public String getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}

	public Map<String, String> getMax() {
		return max;
	}
	public void setMax(Map<String, String> max) {
		this.max = max;
	}
	public Map<String, String> getMin() {
		return min;
	}
	public void setMin(Map<String, String> min) {
		this.min = min;
	}
	public Map<String, String> getMean() {
		return mean;
	}
	public void setMean(Map<String, String> mean) {
		this.mean = mean;
	}
	public Map<String, String> getNormL1() {
		return normL1;
	}
	public void setNormL1(Map<String, String> normL1) {
		this.normL1 = normL1;
	}
	public Map<String, String> getNormL2() {
		return normL2;
	}
	public void setNormL2(Map<String, String> normL2) {
		this.normL2 = normL2;
	}
	public Map<String, String> getNumNonzeros() {
		return numNonzeros;
	}
	public void setNumNonzeros(Map<String, String> numNonzeros) {
		this.numNonzeros = numNonzeros;
	}
	public Map<String, String> getVariance() {
		return variance;
	}
	public void setVariance(Map<String, String> variance) {
		this.variance = variance;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColSummaryStats [workFlowId=");
		builder.append(workFlowId);
		builder.append(", count=");
		builder.append(count);
		builder.append(", max=");
		builder.append(max);
		builder.append(", min=");
		builder.append(min);
		builder.append(", mean=");
		builder.append(mean);
		builder.append(", normL1=");
		builder.append(normL1);
		builder.append(", normL2=");
		builder.append(normL2);
		builder.append(", numNonzeros=");
		builder.append(numNonzeros);
		builder.append(", variance=");
		builder.append(variance);
		builder.append("]");
		return builder.toString();
	}
	
	
}
