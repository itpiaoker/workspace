package cn.com.carenet.scheduler.bean;

import java.util.List;
import java.util.Map;

public class TransposeInfos {
	private String workFlowID;
	private long timestampExpression;
	private String cronExpression;
	private boolean repeat;
	private Map<String, String> workFlowIdsDepend;
	/** 存储转置信息 */
	private Map<Integer, List<String>> transposeMapTree;
	public long getTimestampExpression() {
		return timestampExpression;
	}
	public void setTimestampExpression(long timestampExpression) {
		this.timestampExpression = timestampExpression;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public boolean isRepeat() {
		return repeat;
	}
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	public Map<String, String> getWorkFlowIdsDepend() {
		return workFlowIdsDepend;
	}
	public void setWorkFlowIdsDepend(Map<String, String> workFlowIdsDepend) {
		this.workFlowIdsDepend = workFlowIdsDepend;
	}
	public Map<Integer, List<String>> getTransposeMapTree() {
		return transposeMapTree;
	}
	public void setTransposeMapTree(Map<Integer, List<String>> transposeMapTree) {
		this.transposeMapTree = transposeMapTree;
	}
	public String getWorkFlowID() {
		return workFlowID;
	}
	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}
}
