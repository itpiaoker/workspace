package cn.com.carenet.scheduler.entity;

import java.util.Date;

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * date 2017/7/3
  */
public class TransposeInfo {
    /**  */
    private String id = "";
    /**  */
    private int execState = -1;
    /**  */
    private int deleteFlag = -1;
    /**  */
    private String transposeInfo = null;
    /**  */
    private java.util.Date createTime = null;
    /**  */
    private java.util.Date startTime = null;
    /**  */
    private java.util.Date endTime = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getExecState() {
        return execState;
    }

    public void setExecState(int execState) {
        this.execState = execState;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getTransposeInfo() {
        return transposeInfo;
    }

    public void setTransposeInfo(String transposeInfo) {
        this.transposeInfo = transposeInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransposeInfo [id=");
		builder.append(id);
		builder.append(", execState=");
		builder.append(execState);
		builder.append(", deleteFlag=");
		builder.append(deleteFlag);
		builder.append(", transposeInfo=");
		builder.append(transposeInfo);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append("]");
		return builder.toString();
	}
    
    
    
}
