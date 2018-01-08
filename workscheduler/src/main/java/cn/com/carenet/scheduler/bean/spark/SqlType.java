package cn.com.carenet.scheduler.bean.spark;

import cn.com.carenet.scheduler.constant.Constant;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/5/20
 */
public class SqlType {
    /**  */
    private String workFlowID;
    /**  */
    private String dataSourceID;
    /**  */
    private String sqlID;
    /**  */
    private String tableName;
    /**  */
    private String asTableName;
    /**  */
    private String sqlType;
    /**  */
    private String isSave;
    /**  */
    private String sql;

    public String getWorkFlowID() {
        return workFlowID;
    }

    public void setWorkFlowID(String workFlowID) {
        this.workFlowID = workFlowID;
    }

    public String getSqlID() {
        return sqlID;
    }

    public void setSqlID(String sqlID) {
        this.sqlID = sqlID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAsTableName() {
        return asTableName;
    }

    public void setAsTableName(String asTableName) {
        this.asTableName = asTableName;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getIsSave() {
        return isSave;
    }

    public void setIsSave(String isSave) {
        this.isSave = isSave;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

	public String getDataSourceID() {
		return dataSourceID;
	}

	public void setDataSourceID(String dataSourceID) {
		if(dataSourceID.startsWith(Constant.PREFIX_WINDOW_KEY)){
			dataSourceID = dataSourceID.substring(Constant.PREFIX_WINDOW_KEY.length());
		}
		this.dataSourceID = dataSourceID;
	}
}
