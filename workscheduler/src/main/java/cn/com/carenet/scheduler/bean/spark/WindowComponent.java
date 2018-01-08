package cn.com.carenet.scheduler.bean.spark;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/5/23
 */
public abstract class WindowComponent {
    /**  */
    protected String wfSourceId;
    /** */
    protected String wfTargetId;
    /** */
    public String getWfSourceId() {
        return wfSourceId;
    }

    public void setWfSourceId(String wfSourceId) {
        this.wfSourceId = wfSourceId;
    }

    public String getWfTargetId() {
        return wfTargetId;
    }

    public void setWfTargetId(String wfTargetId) {
        this.wfTargetId = wfTargetId;
    }
}
