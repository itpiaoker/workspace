package cn.com.carenet.scheduler.bean.spark;

import java.util.List;

import cn.com.carenet.scheduler.constant.Constant;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/5/18
 */
public class DataSource extends WindowComponent{
    /** 工作流ID */
    protected String workFlowID;
    /** 是输入还是输出数据源*/
    protected String sourceType;
    /** 数据源ID */
    protected String dataSourceID;
    /** 数据源顺序编号 */
    protected String sourceNo;
    /** 数据源名称 */
    protected String sourceName;
    /** hdfs数据目录 */
    private String hdfsUrl;
    /**  zookeeper集群节点ip:port */
    private String zkServers;
    /**  kafka集群节点ip:port */
    private String kafkaBrokers;
    /**  kafka分区个数 */
    private String kafkaPartitions;
    /**  kafka消费组ID*/
    private String kafkaGroupID;
    /**  kafka消费读取频率 */
    private String kafkaDurationsSeconds;
    /**  kafka主题 */
    private String kafkaTopics;
    /**  */
    private String jdbcDB;
    /**  */
    private String jdbcDriver;
    /**  */
    private String jdbcTable;
    /**  */
    private String jdbcUrl;
    /**  */
    private String jdbcUser;
    /**  */
    private String jdbcPasswd;
    
    private String esNodes;
    private String esPort = "9200";
    private String esIndexName;
    private String esIndexType;
    private String tableName;
    private String tempTableName;
    
    private String delimiter;
    
    /**  */
    private String jdbcType;
    
    /**
	 * The number of place of which field should be removed. start with 0.
	 */
	private List<String> fieldRemove;
	private List<String> independents;
	
	private String dependents;
	
    /**  */
    private String preOpIds;
	
	/** 下一个处理流程 */
	private String nextOpType;
    
    
    
    public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getWorkFlowID() {
        return workFlowID;
    }

    public void setWorkFlowID(String workFlowID) {
        this.workFlowID = workFlowID;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceNo() {
        return sourceNo;
    }

    public void setSourceNo(String sourceNo) {
        this.sourceNo = sourceNo;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getHdfsUrl() {
        return hdfsUrl;
    }

    public void setHdfsUrl(String hdfsUrl) {
        this.hdfsUrl = hdfsUrl;
    }

    public String getZkServers() {
        return zkServers;
    }

    public void setZkServers(String zkServers) {
        this.zkServers = zkServers;
    }

    public String getKafkaBrokers() {
        return kafkaBrokers;
    }

    public void setKafkaBrokers(String kafkaBrokers) {
        this.kafkaBrokers = kafkaBrokers;
    }

    public String getKafkaGroupID() {
        return kafkaGroupID;
    }

    public void setKafkaGroupID(String kafkaGroupID) {
        this.kafkaGroupID = kafkaGroupID;
    }

    public String getKafkaDurationsSeconds() {
        return kafkaDurationsSeconds;
    }

    public void setKafkaDurationsSeconds(String kafkaDurationsSeconds) {
        this.kafkaDurationsSeconds = kafkaDurationsSeconds;
    }

    public String getKafkaTopics() {
        return kafkaTopics;
    }

    public void setKafkaTopics(String kafkaTopics) {
        this.kafkaTopics = kafkaTopics;
    }

	public String getJdbcDB() {
		return jdbcDB;
	}

	public void setJdbcDB(String jdbcDB) {
		this.jdbcDB = jdbcDB;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getJdbcTable() {
		return jdbcTable;
	}

	public void setJdbcTable(String jdbcTable) {
		this.jdbcTable = jdbcTable;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcUser() {
		return jdbcUser;
	}

	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}

	public String getJdbcPasswd() {
		return jdbcPasswd;
	}

	public void setJdbcPasswd(String jdbcPasswd) {
		this.jdbcPasswd = jdbcPasswd;
	}

	public String getKafkaPartitions() {
		return kafkaPartitions;
	}

	public void setKafkaPartitions(String kafkaPartitions) {
		this.kafkaPartitions = kafkaPartitions;
	}

	public String getEsNodes() {
		return esNodes;
	}

	public void setEsNodes(String esNodes) {
		this.esNodes = esNodes;
	}

	public String getEsPort() {
		return esPort;
	}

	public void setEsPort(String esPort) {
		this.esPort = esPort;
	}

	public String getEsIndexName() {
		return esIndexName;
	}

	public void setEsIndexName(String esIndexName) {
		this.esIndexName = esIndexName;
	}

	public String getEsIndexType() {
		return esIndexType;
	}

	public void setEsIndexType(String esIndexType) {
		this.esIndexType = esIndexType;
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public List<String> getFieldRemove() {
		return fieldRemove;
	}

	public void setFieldRemove(List<String> fieldRemove) {
		this.fieldRemove = fieldRemove;
	}

	public String getPreOpIds() {
		return preOpIds;
	}

	public void setPreOpIds(String preOpIds) {
		this.preOpIds = preOpIds;
	}

	public String getNextOpType() {
		return nextOpType;
	}

	public void setNextOpType(String nextOpType) {
		this.nextOpType = nextOpType;
	}

	public String getTempTableName() {
		return tempTableName;
	}

	public void setTempTableName(String tempTableName) {
		this.tempTableName = tempTableName;
	}

	public void setIndependents(List<String> independents) {
		this.independents = independents;
	}
	
	public List<String> getIndependents() {
		return independents;
	}
	
	public void setDependents(String dependents) {
		this.dependents = dependents;
	}
	
	public String getDependents() {
		return dependents;
	}
	
}
