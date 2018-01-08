package cn.com.carenet.scheduler.entity;

import java.util.List;

/**
  * Title: 
  * Description: 
  *
  * @author lianxy
  * date 2017/7/3
  */
public class TransposeTask {
    /**  */
    private int transposeID = -1;
    /**  */
    private int taskID = -1;
    private int parentID = -1;
    private int degree = -1;
    /**  */
    private List<Integer> dependIDS = null;

    public int getTransposeID() {
        return transposeID;
    }

    public void setTransposeID(int transposeID) {
        this.transposeID = transposeID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public List<Integer> getDependIDS() {
        return dependIDS;
    }

    public void setDependIDS(List<Integer> dependIDS) {
        this.dependIDS = dependIDS;
    }
}
