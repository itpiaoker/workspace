package cn.com.carenet.scheduler.bean;

public class CorrStats {
	/**  */
	private String workFlowId;
	/**  */
	private String corr;
	public String getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}
	public String getCorr() {
		return corr;
	}
	public void setCorr(String corr) {
		this.corr = corr;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CorrStats [workFlowId=");
		builder.append(workFlowId);
		builder.append(", corr=");
		builder.append(corr);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
