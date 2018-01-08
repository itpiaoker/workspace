package cn.com.carenet.scheduler.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import cn.com.carenet.scheduler.bean.DataBaseSQLBean;
import cn.com.carenet.scheduler.bean.DataSchema;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.OperateComponent;
import cn.com.carenet.scheduler.bean.OperateProp;
import cn.com.carenet.scheduler.bean.SingleMetaData;
import cn.com.carenet.scheduler.bean.spark.SparkEnvProp;
import cn.com.carenet.scheduler.bean.storm.TopologyEnvProp;
import cn.com.carenet.scheduler.utils.ConnectionTypeUtil;
import cn.com.carenet.scheduler.web.entity.Component;
import cn.com.carenet.scheduler.web.entity.DataRepository;

/**
 * 
 * @author Sherard Lee
 *
 */
public class PropTunnel {
	final private static List<String> dataSourceModuleNameList = new ArrayList<>();
	final private static List<String> metaOperationsBeanList = new ArrayList<>();

	private String id;
	private String wf_name;
	private String wf_group;
	private String dependents;
	private String dataSourceId;
	private String databaseName;
	private String tableName;
	private String recordDelimiter;
	/* field properties */
	/**
	 * {index:-1, text: '使用过滤条件'}, {index:0, text: '手机号脱敏'}, {index:1, text: '枚举值',
	 * input: true}, {index:2, text: '最大长度限制', input: true}
	 */
	private List<String> fieldFilter;
	private List<String> fieldFilterVal;
	private List<String> fieldName;
	private List<String> fieldRemove;

	private List<String> fieldType;
	private List<String> timeFormat;
	private List<String> asFieldName;

	private List<Component> components;
	private List<String> downBranches;

	/******* data source ********/
	/**
	 * datasourceDelimiter
	 */
	private String outSeparator;
	private String tmpTableName;
	private String ip;
	private Integer port;
	private Integer restPort;
	private String userName;
	private String password;
	/* folder path of file */
	private String url;
	private String urls;
	/* kafka settings */
	private String zkServers;
	private Integer kafkaDurationsSeconds;
	private String kafkaGroupID;
	private String kafkaPartitions;
	private String kafkaTopics;

	/* file out put settings */
	private String path;
	private Integer rotationFileSizeInMB;
	private Integer rotationTimeInSec;
	private String encoding;
	private String filePrifix;
	private String fileExtension;
	private Integer syncCount;
	private String rowKeyFields;
	private String znodeParent;
	private String metaStoreURI;
	private String timeAsPartitionField;
	private String partitionFields;
	/* elastic search */

	/******* operates ********/
	/* operate opinions */
	private String values;
	private String fieldNames;
	private String between;
	private String and;
	private String isSection;
	private String upSort;
	private String calToken;
	private String leftJoinSort;
	private List<String> independents;
	/* model canshu */
	private String algorithmModelID;
	private String algorithmID;
	private String algorithmModelName;
	private String algorithmModelType;
	private String algorithmIsTrain;
	private String algorithmIsSpecifiedDataFormat;
	private String algorithmDataFormat;
	private String algorithmLabelField;
	private String algorithmModelSavePath;
	private String algorithmRank;
	private String algorithmIterations;
	private String algorithmRegParam;
	private String algorithmSeed;
	private String algorithmStepSize;

	@SuppressWarnings("unused")
	private String keyPath;

	private Integer bolts_num;
	private Integer parallelismNum;
	private Integer tickTupleFreqSecs;

	private DataRepository dataRepository;

	private List<String> metaIDs;

	static {
		dataSourceModuleNameList.add(DataRepository.WF_NAME_HIVE);
		dataSourceModuleNameList.add(DataRepository.WF_NAME_FTP);
		dataSourceModuleNameList.add(DataRepository.WF_NAME_GREENPLUM);
		dataSourceModuleNameList.add(DataRepository.WF_NAME_MYSQL);
		dataSourceModuleNameList.add(DataRepository.WF_NAME_ORACLE);

		metaOperationsBeanList.add(DataRepository.WF_NAME_JOIN);
		metaOperationsBeanList.add(DataRepository.WF_NAME_SUM);
		metaOperationsBeanList.add(DataRepository.WF_NAME_COUNT);
		metaOperationsBeanList.add(DataRepository.WF_NAME_GROUPBY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_REMOVEFIELD);
		metaOperationsBeanList.add(DataRepository.WF_NAME_ALGORITHM_MODEL);
		metaOperationsBeanList.add(DataRepository.WF_NAME_LINER_R);
		metaOperationsBeanList.add(DataRepository.WF_NAME_ALS_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_KMEANS_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_SVM_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_BAYES_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_RANDOM_FOREST_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_DECISION_TREE_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_IS_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_GMG_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_PIC_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_FP_TREE_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_PCA_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_SVD_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_WORD_2_VEC_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_TF_IDF_KEY);
		metaOperationsBeanList.add(DataRepository.WF_NAME_APRIORI_KEY);
	}

	public DataSourceProp getDataSourceProp(String workFlowID, DataRepository dataRepository) {
		this.setDataRepository(dataRepository);
		String sourceName = dataRepository.getWf_name();
		DataSourceProp dataSourceProp = this.getPropsD(workFlowID);
		switch (sourceName) {
		case DataRepository.WF_NAME_KAFKA:
			dataSourceProp = this.kafkaDataSourceProp(dataSourceProp);
			break;
		case DataRepository.WF_NAME_LOCALFILE:
			dataSourceProp = this.localDataSourceProp(dataSourceProp);
			break;
		case DataRepository.WF_NAME_HDFS:
			dataSourceProp = this.hdfsDataSourceProp(dataSourceProp);
			break;
		case DataRepository.WF_NAME_FTP:
			dataSourceProp = this.ftpDataSourceProp(dataSourceProp);
			break;
		case DataRepository.WF_NAME_MYSQL:
		case DataRepository.WF_NAME_ORACLE:
		case DataRepository.WF_NAME_GREENPLUM:
			dataSourceProp = this.mysqlDataSourceProp(dataSourceProp);
			break;
		case DataRepository.WF_NAME_HIVE:
			dataSourceProp = this.hiveDataSourceProp(dataSourceProp);
			break;
		case DataRepository.WF_NAME_ELASTICSEARCH:
			dataSourceProp = this.esDataSourceProp(dataSourceProp);
			break;
		case DataRepository.WF_NAME_HBASE:
			dataSourceProp = this.hBaseDataSourceProp(dataSourceProp);
			break;
		case DataRepository.WF_NAME_REDIS:
			dataSourceProp = this.redisDataSourceProp(dataSourceProp);
			break;
		default:
			return null;
		}
		return dataSourceProp;
	}

	public OperateProp getOperateProp(String workFlowID, DataRepository dataRepository) {
		this.setDataRepository(dataRepository);
		String sourceName = dataRepository.getWf_name();
		OperateProp operateProp = this.getPropsO(workFlowID);
		switch (sourceName) {
		case DataRepository.WF_NAME_FILTER:
		case DataRepository.WF_NAME_COUNT:
		case DataRepository.WF_NAME_GROUPBY:
		case DataRepository.WF_NAME_DISTINCT:
		case DataRepository.WF_NAME_SORTBY:
		case DataRepository.WF_NAME_JOIN:
		case DataRepository.WF_NAME_SUM:
		case DataRepository.WF_NAME_REMOVEFIELD:
		case DataRepository.WF_NAME_ALGORITHM_MODEL:
		case DataRepository.WF_NAME_ALS_KEY:
		case DataRepository.WF_NAME_LINER_R:
		case DataRepository.WF_NAME_KMEANS_KEY:
		case DataRepository.WF_NAME_SVM_KEY:
		case DataRepository.WF_NAME_BAYES_KEY:
		case DataRepository.WF_NAME_RANDOM_FOREST_KEY:
		case DataRepository.WF_NAME_DECISION_TREE_KEY:
		case DataRepository.WF_NAME_IS_KEY:
		case DataRepository.WF_NAME_GMG_KEY:
		case DataRepository.WF_NAME_PIC_KEY:
		case DataRepository.WF_NAME_FP_TREE_KEY:
		case DataRepository.WF_NAME_PCA_KEY:
		case DataRepository.WF_NAME_SVD_KEY:
		case DataRepository.WF_NAME_WORD_2_VEC_KEY:
		case DataRepository.WF_NAME_TF_IDF_KEY:
		case DataRepository.WF_NAME_APRIORI_KEY:
			break;
		default:
			return null;
		}

		return operateProp;
	}

	private OperateProp getPropsO(String workFlowID) {

		OperateProp operateProp = new OperateProp();
		operateProp.setWorkFlowID(workFlowID);
		if (id != null)
			operateProp.setModuleID(id);
		if (wf_name != null)
			operateProp.setSourceName(wf_name);
		if (values != null)
			operateProp.setValues(values);
		if (fieldNames != null)
			operateProp.setFieldNames(fieldNames);
		if (between != null)
			operateProp.setBetween(between);
		if (and != null)
			operateProp.setAnd(and);
		if (isSection != null)
			operateProp.setSection(Boolean.parseBoolean(isSection));
		if (upSort != null)
			operateProp.setUpSort(Boolean.parseBoolean(upSort));
		if (leftJoinSort != null)
			operateProp.setLeftJoinSort(leftJoinSort);
		if (calToken != null)
			operateProp.setCalToken(calToken);
		if (bolts_num != null)
			operateProp.setTaskNum(bolts_num);
		if (parallelismNum != null)
			operateProp.setParallelismNum(parallelismNum);
		if (tickTupleFreqSecs != null)
			operateProp.setTickTupleFreqSecs(tickTupleFreqSecs);
		if (tableName != null)
			operateProp.setTempTableName(tableName);
		if (fieldName != null && !fieldName.isEmpty())
			operateProp.setNewFieldNames(fieldName);
		if (asFieldName != null && !asFieldName.isEmpty())
			operateProp.setAsFieldName(asFieldName);
		if (outSeparator != null)
			operateProp.setDatasouceDelimiter(outSeparator);
		if (recordDelimiter != null)
			operateProp.setRecordDelimiter(recordDelimiter);

		if (algorithmModelID != null)
			operateProp.setAlgorithmModelID(algorithmModelID);
		if (algorithmID != null)
			operateProp.setAlgorithmID(algorithmID);
		if (algorithmModelName != null)
			operateProp.setAlgorithmModelName(algorithmModelName);
		if (algorithmModelType != null)
			operateProp.setAlgorithmModelType(algorithmModelType);
		if (algorithmIsTrain != null)
			operateProp.setAlgorithmIsTrain(algorithmIsTrain);
		if (algorithmIsSpecifiedDataFormat != null)
			operateProp.setAlgorithmIsSpecifiedDataFormat(algorithmIsSpecifiedDataFormat);
		if (algorithmDataFormat != null)
			operateProp.setAlgorithmDataFormat(algorithmDataFormat);
		if (algorithmLabelField != null)
			operateProp.setAlgorithmLabelField(algorithmLabelField);
		if (algorithmModelSavePath != null)
			operateProp.setAlgorithmModelSavePath(algorithmModelSavePath);
		if (algorithmRank != null)
			operateProp.setAlgorithmIsTrain(algorithmRank);
		if (algorithmIterations != null)
			operateProp.setAlgorithmIterations(algorithmIterations);
		if (algorithmRegParam != null)
			operateProp.setAlgorithmRegParam(algorithmRegParam);
		if (algorithmSeed != null)
			operateProp.setAlgorithmSeed(algorithmSeed);
		if (algorithmStepSize != null)
			operateProp.setAlgorithmStepSize(algorithmStepSize);

		List<OperateComponent> opComponents = new ArrayList<>();
		if (components != null) {
			for (Component component : components) {
				String preOpID = component.getWf_sourceId();
				String sourcePri = component.getSourcePrimary();
				if (preOpID != null) {
					OperateComponent operateComponent = new OperateComponent();
					operateComponent.setPreOpID(preOpID);
					if (sourcePri != null) {
						operateComponent.setSourcePrimary(sourcePri);
					}
					opComponents.add(operateComponent);
				}
			}
		}
		operateProp.setComponents(opComponents);
		if (downBranches != null && !downBranches.isEmpty())
			operateProp.setDownBranches(downBranches);

		
		if (independents != null)
			operateProp.setIndependents(independents);
		
		
		return operateProp;
	}

	private DataSourceProp kafkaDataSourceProp(DataSourceProp dataSourceProp) {
		// dataSourceProp.setTmpTableName(kafkaTopics);
		String urls = ip + ":" + port;
		dataSourceProp.setUrls(urls);
		dataSourceProp.setKafkaTopics(databaseName);
		return dataSourceProp;
	}

	private DataSourceProp localDataSourceProp(DataSourceProp dataSourceProp) {
		if (port != null)
			dataSourceProp.setPort(port);
		return dataSourceProp;
	}

	private DataSourceProp hdfsDataSourceProp(DataSourceProp dataSourceProp) {
		String databaseName0;
		if (databaseName.startsWith("\\") || databaseName.startsWith("/")) {
			databaseName0 = databaseName;
		} else {
			databaseName0 = "/" + databaseName;
		}
		dataSourceProp.setPath(databaseName0);
		dataSourceProp.setUrls(String.format("hdfs://%s:%d%s", ip, port, databaseName0));
		return dataSourceProp;
	}

	private DataSourceProp ftpDataSourceProp(DataSourceProp dataSourceProp) {
		dataSourceProp.setPath(url);
		dataSourceProp.setEncoding(encoding);
		return dataSourceProp;
	}

	private DataSourceProp mysqlDataSourceProp(DataSourceProp dataSourceProp) {
		dataSourceProp.setTableName(tableName);

		String sourceName = dataSourceProp.getSourceName();
		if (sourceName != null) {
			String databaseType = "";
			switch (sourceName) {
			case DataRepository.WF_NAME_MYSQL:
				databaseType = ConnectionTypeUtil.DBTYPE_MYSQL;
				break;
			case DataRepository.WF_NAME_ORACLE:
				databaseType = ConnectionTypeUtil.DBTYPE_ORACLE;
				break;
			case DataRepository.WF_NAME_GREENPLUM:
				databaseType = ConnectionTypeUtil.DBTYPE_GP;
				break;
			}
			String url = ConnectionTypeUtil.getUrl(databaseType, ip, port, databaseName);
			if (url != null)
				dataSourceProp.setUrls(url);
		}

		return dataSourceProp;
	}

	private DataSourceProp hiveDataSourceProp(DataSourceProp dataSourceProp) {

		return dataSourceProp;
	}

	private DataSourceProp esDataSourceProp(DataSourceProp dataSourceProp) {
		dataSourceProp.setUrls(String.format("%s:%d", ip, port));

		return dataSourceProp;
	}

	private DataSourceProp hBaseDataSourceProp(DataSourceProp dataSourceProp) {
		dataSourceProp.setZkServers(zkServers);
		return dataSourceProp;
	}

	private DataSourceProp redisDataSourceProp(DataSourceProp dataSourceProp) {
		return dataSourceProp;
	}

	private DataSourceProp getPropsD(String workFlowID) {
		DataSourceProp dataSourceProp = new DataSourceProp();
		dataSourceProp.setWorkFlowID(workFlowID);
		dataSourceProp.setModuleID(id);
		dataSourceProp.setSourceName(wf_name);
		dataSourceProp.setDependents(dependents);
		/*
		 * input or output type
		 */
		if (DataRepository.WF_GROUP_INPUT.equals(wf_group)) {
			dataSourceProp.setPutType(DataSourceProp.TYPE_INPUT);
		} else if (DataRepository.WF_GROUP_OUTPUT.equals(wf_group)) {
			dataSourceProp.setPutType(DataSourceProp.TYPE_OUTPUT);
		}

		if (fieldRemove != null && !fieldRemove.isEmpty())
			dataSourceProp.setFieldRemove(fieldRemove);

		if (independents != null && !independents.isEmpty())
			dataSourceProp.setIndependents(independents);
		/*
		 * field name, field type, field filter, time format.
		 */
		if (fieldName != null && !fieldName.isEmpty()) {
			List<SingleMetaData> metaDatas = new ArrayList<>();
			for (int i = 0; i < fieldName.size(); i++) {
				SingleMetaData singleMetaData = new SingleMetaData();
				singleMetaData.setFieldName(fieldName.get(i));
				if (fieldFilter != null && fieldFilter.size() == fieldName.size()) {
					String filter = fieldFilter.get(i);
					switch (filter) {
					case DataRepository.WF_FIELD_FILTER_TEL:
						singleMetaData.setFieldFilter(SingleMetaData.WF_FIELD_FILTER_TEL);
						break;
					case DataRepository.WF_FIELD_FILTER_ENUM:
						singleMetaData.setFieldFilter(SingleMetaData.WF_FIELD_FILTER_ENUM);
						singleMetaData.setFieldFilterVal(fieldFilterVal.get(i));
						break;
					case DataRepository.WF_FIELD_FILTER_MAXLEN:
						singleMetaData.setFieldFilter(SingleMetaData.WF_FIELD_FILTER_MAXLEN);
						singleMetaData.setFieldFilterVal(fieldFilterVal.get(i));
						break;
					case DataRepository.WF_FIELD_FILTER_NONE:
					default:
						singleMetaData.setFieldFilter(SingleMetaData.WF_FIELD_FILTER_NONE);
						break;
					}
				}
				if (fieldType != null) {
					singleMetaData.setFieldType(fieldType.get(i));
				}
				if (timeFormat != null && timeFormat.size() == fieldName.size()) {
					if (timeFormat.get(i) != null) {
						singleMetaData.setDateFormat(timeFormat.get(i));
					}
				}
				singleMetaData.setColNum(i);
				metaDatas.add(singleMetaData);
			}
			dataSourceProp.setMetaDatas(metaDatas);
		}

		/*
		 * components: previous option ID, source primary ID, temporary table name.
		 */
		List<OperateComponent> opComponents = new ArrayList<>();
		if (components != null) {
			for (Component component : components) {

				String preOpID = component.getWf_sourceId();
				String sourcePri = component.getSourcePrimary();
				if (preOpID != null) {
					OperateComponent operateComponent = new OperateComponent();
					operateComponent.setPreOpID(preOpID);
					if (sourcePri != null) {
						operateComponent.setSourcePrimary(sourcePri);
					}
					opComponents.add(operateComponent);
				}
			}
		}
		dataSourceProp.setComponents(opComponents);

		if (dataSourceId != null)
			dataSourceProp.setDataSourceId(dataSourceId);
		if (ip != null)
			dataSourceProp.setIp(ip);
		if (port != null)
			dataSourceProp.setPort(port);
		if (restPort != null)
			dataSourceProp.setRestPort(restPort);
		if (downBranches != null)
			dataSourceProp.setDownBranches(downBranches);
		if (recordDelimiter != null)
			dataSourceProp.setRecordDelimiter(recordDelimiter);
		if (outSeparator != null)
			dataSourceProp.setDatasouceDelimiter(outSeparator);
		if (tableName != null)
			dataSourceProp.setTableName(tableName);
		if (tmpTableName != null)
			dataSourceProp.setTmpTableName(tmpTableName);
		if (zkServers != null)
			dataSourceProp.setZkServers(zkServers);
		if (znodeParent != null)
			dataSourceProp.setZnodeParent(znodeParent);
		if (kafkaGroupID != null)
			dataSourceProp.setKafkaGroupID(kafkaGroupID);
		if (kafkaPartitions != null)
			dataSourceProp.setKafkaPartitions(kafkaPartitions);
		if (kafkaTopics != null)
			dataSourceProp.setKafkaTopics(kafkaTopics);
		if (kafkaDurationsSeconds != null)
			dataSourceProp.setKafkaDurationsSeconds(kafkaDurationsSeconds);
		if (path != null)
			dataSourceProp.setPath(path);
		else if (url != null)
			dataSourceProp.setPath(url);
		if (urls != null)
			dataSourceProp.setUrls(urls);
		if (encoding != null)
			dataSourceProp.setEncoding(encoding);
		if (databaseName != null)
			dataSourceProp.setDataBaseName(databaseName);
		if (userName != null)
			dataSourceProp.setUserName(userName);
		if (password != null)
			dataSourceProp.setPassword(password);
		if (syncCount != null)
			dataSourceProp.setSyncCount(syncCount);
		if (rotationFileSizeInMB != null)
			dataSourceProp.setRotationFileSizeInMB(rotationFileSizeInMB);
		if (rotationTimeInSec != null)
			dataSourceProp.setRotationTimeInSec(rotationTimeInSec);
		if (filePrifix != null)
			dataSourceProp.setFilePrifix(filePrifix);
		if (fileExtension != null)
			dataSourceProp.setFileExtension(fileExtension);
		if (rowKeyFields != null)
			dataSourceProp.setRowKeyFields(rowKeyFields);
		if (metaStoreURI != null)
			dataSourceProp.setMetaStoreURI(metaStoreURI);
		if (partitionFields != null)
			dataSourceProp.setPartitionFields(partitionFields);
		if (timeAsPartitionField != null)
			dataSourceProp.setTimeAsPartitionField(Boolean.parseBoolean(timeAsPartitionField));
		if (bolts_num != null)
			dataSourceProp.setTaskNum(bolts_num);
		if (parallelismNum != null)
			dataSourceProp.setParallelismNum(parallelismNum);
		if (tickTupleFreqSecs != null)
			dataSourceProp.setTickTupleFreqSecs(tickTupleFreqSecs);
		return dataSourceProp;
	}

	/******* storm ********/
	public TopologyEnvProp getTopologyOptionsBean(DataRepository moduleDataRepository) {
		this.dataRepository = moduleDataRepository;
		String debug = dataRepository.getDebug();
		String ackerNum = dataRepository.getAckerNum();
		Integer topology_workers = dataRepository.getTopology_workers();
		Integer maxParallelismNum = dataRepository.getMaxParallelismNum();
		Integer tickTupleFreqSecsForAll = dataRepository.getTickTupleFreqSecs();
		Integer topology_receiver_buffer_size = dataRepository.getTopology_receiver_buffer_size();
		Integer topology_transfer_buffer_size = dataRepository.getTopology_transfer_buffer_size();
		Integer topology_executor_receive_buffer_size = dataRepository.getTopology_executor_receive_buffer_size();
		Integer topology_executor_send_buffer_size = dataRepository.getTopology_executor_send_buffer_size();

		TopologyEnvProp topologyOptionsBean = new TopologyEnvProp();
		if (ackerNum != null)
			topologyOptionsBean.setAckerNum(Integer.parseInt(ackerNum));
		if (debug != null)
			topologyOptionsBean.setDebug(Boolean.parseBoolean(debug));
		if (topology_workers != null)
			topologyOptionsBean.setWorkerNum(topology_workers);
		if (maxParallelismNum != null)
			topologyOptionsBean.setMaxParallelismNum(maxParallelismNum);
		if (tickTupleFreqSecsForAll != null)
			topologyOptionsBean.setTickTupleFreqSecsForAll(tickTupleFreqSecsForAll);
		if (topology_receiver_buffer_size != null)
			topologyOptionsBean.setReceiverBufferSize(topology_receiver_buffer_size);
		if (topology_transfer_buffer_size != null)
			topologyOptionsBean.setTransferBufferSize(topology_transfer_buffer_size);
		if (topology_executor_receive_buffer_size != null)
			topologyOptionsBean.setExcutorReceiveBufferSize(topology_executor_receive_buffer_size);
		if (topology_executor_send_buffer_size != null)
			topologyOptionsBean.setExcutorSendBufferSize(topology_executor_send_buffer_size);

		return topologyOptionsBean;
	}

	public DataBaseSQLBean getSparkSqlBean(DataRepository moduleDataRepository) {
		DataBaseSQLBean dataBaseSQLBean = new DataBaseSQLBean();
		String sqlCommands = moduleDataRepository.getScript();
		dataBaseSQLBean.setScript(sqlCommands);
		return dataBaseSQLBean;
	}

	public DataBaseSQLBean getDataBaseSQLBean(DataRepository dataRepository) {
		this.dataRepository = dataRepository;
		DataBaseSQLBean dataBaseSQLBean = new DataBaseSQLBean();
		String ip = dataRepository.getIp();
		Integer port = dataRepository.getPort();
		String dataBaseName = dataRepository.getDatabaseName();
		String password = dataRepository.getPassword();
		String userName = dataRepository.getUserName();
		String sourceTabID = null;
		List<Component> components = dataRepository.getComponents();
		if (components != null && !components.isEmpty())
			sourceTabID = dataRepository.getComponents().get(0).getWf_sourceId();
		String script = dataRepository.getScript();
		if(script==null) {
			script = dataRepository.getSQL();
		}
		if (ip != null) {
			dataBaseSQLBean.setIp(ip);
		}
		if (port != null && port > 0) {
			dataBaseSQLBean.setPort(port);
		}
		if (dataBaseName != null) {
			dataBaseSQLBean.setDataBaseName(dataBaseName);
		}
		if (userName != null) {
			dataBaseSQLBean.setUserName(userName);
		}
		if (password != null) {
			dataBaseSQLBean.setPassword(password);
		}
		if (sourceTabID != null) {
			dataBaseSQLBean.setSourceTabID(sourceTabID);
		}
		if (script != null) {
			dataBaseSQLBean.setScript(script);
		}

		return dataBaseSQLBean;
	}

	public SparkEnvProp getSparkEnvProp(DataRepository moduleDataRepository) {
		Integer executorCores = moduleDataRepository.getExecutor_cores();
		Integer executorMemory = moduleDataRepository.getExecutor_memory();
		Integer numExecutors = moduleDataRepository.getNum_executors();
		Integer sparkDefaultParallelism = moduleDataRepository.getSpark_default_parallelism();
		Integer sparkReduceMaxSizeInFlight = moduleDataRepository.getSpark_reduce_maxSizeInFlight();
		Integer sparkShuffleFileBuffer = moduleDataRepository.getSpark_shuffle_file_buffer();
		Double sparkShuffleMemoryFraction = moduleDataRepository.getSpark_shuffle_memoryFraction();
		Double sparkStorageMemoryFraction = moduleDataRepository.getSpark_storage_memoryFraction();

		/**
		 * org.apache.spark.serializer.KryoSerializer
		 * org.apache.spark.serializer.JavaSerializer
		 */
		String sparkSerializer = moduleDataRepository.getSpark_serializer();
		/**
		 * org.apache.spark.sql.api.java.StructField
		 */
		String registerKryoClasses = moduleDataRepository.getRegisterKryoClasses();

		SparkEnvProp sparkEnvProp = new SparkEnvProp();
		if (executorCores != null)
			sparkEnvProp.setExecutorCores(executorCores);
		if (executorMemory != null)
			sparkEnvProp.setExecutorMemory(executorMemory);
		if (numExecutors != null)
			sparkEnvProp.setNumExecutors(numExecutors);
		if (sparkDefaultParallelism != null)
			sparkEnvProp.setSparkDefaultParallelism(sparkDefaultParallelism);
		if (sparkReduceMaxSizeInFlight != null)
			sparkEnvProp.setSparkReduceMaxSizeInFlight(sparkReduceMaxSizeInFlight);
		if (sparkShuffleFileBuffer != null)
			sparkEnvProp.setSparkShuffleFileBuffer(sparkShuffleFileBuffer);
		if (sparkShuffleMemoryFraction != null)
			sparkEnvProp.setSparkShuffleMemoryFraction(sparkShuffleMemoryFraction);
		if (sparkStorageMemoryFraction != null)
			sparkEnvProp.setSparkStorageMemoryFraction(sparkStorageMemoryFraction);
		if (sparkSerializer != null)
			sparkEnvProp.setSparkSerializer(sparkSerializer);
		if (registerKryoClasses != null)
			sparkEnvProp.setRegisterKryoClasses(registerKryoClasses);
		return sparkEnvProp;
	}

	/**
	 * add source primary ID into their components properties.
	 * 
	 * @param dataRepositories
	 * @return
	 */
	public Map<String, DataRepository> getTableSource(Map<String, DataRepository> dataRepositories) {
		metaIDs = new ArrayList<>();
		Map<String, DataRepository> newDataRepositories = new HashMap<>();
		for (Entry<String, DataRepository> dataRepositoryEntry : dataRepositories.entrySet()) {
			String key = dataRepositoryEntry.getKey();
			DataRepository dataRepository = dataRepositoryEntry.getValue();
			String group = dataRepository.getWf_group();
			// if this is not input,find out their source
			if (!DataRepository.WF_GROUP_INPUT.equals(group)) {
				List<Component> components = dataRepository.getComponents();
				if (components != null) {
					for (int index = 0; index < components.size(); index++) {
						Component component = components.get(index);
						String preOpID = component.getWf_sourceId();
						DataRepository preDataRepository = dataRepositories.get(preOpID);
						if (preDataRepository != null) {
							String preGroup = preDataRepository.getWf_group();
							String name = preDataRepository.getWf_name();
							if (DataRepository.WF_GROUP_INPUT.equals(preGroup)
									|| DataRepository.WF_GROUP_OUTPUT.equals(preGroup)
									|| metaOperationsBeanList.contains(name)) {
								component.setSourcePrimary(preOpID);
								components.remove(index);
								components.add(index, component);
								dataRepository.setComponents(components);
								if (!metaIDs.contains(preOpID))
									metaIDs.add(preOpID);
							} else {
								String sourcePrime = this.digSourcePrime(preOpID, dataRepositories);
								if (sourcePrime != null) {
									component.setSourcePrimary(sourcePrime);
									components.remove(index);
									components.add(index, component);
									dataRepository.setComponents(components);
									if (!metaIDs.contains(sourcePrime))
										metaIDs.add(sourcePrime);
								}
							}

						}
					}
				}
			} else {
				if (!metaIDs.contains(key))
					metaIDs.add(key);
			}
			newDataRepositories.put(key, dataRepository);
		}
		return newDataRepositories;
	}

	private String digSourcePrime(String sourceID, Map<String, DataRepository> dataRepositories) {
		DataRepository dataRepository = dataRepositories.get(sourceID);
		String group = dataRepository.getWf_group();
		if (!DataRepository.WF_GROUP_INPUT.equals(group)) {
			List<Component> components = dataRepository.getComponents();
			if (components != null) {
				for (int index = 0; index < components.size(); index++) {
					Component component = components.get(index);
					String preOpID = component.getWf_sourceId();
					DataRepository preDataRepository = dataRepositories.get(preOpID);
					if (preDataRepository != null) {
						String preGroup = preDataRepository.getWf_group();
						String name = preDataRepository.getWf_name();
						if (DataRepository.WF_GROUP_INPUT.equals(preGroup) || metaOperationsBeanList.contains(name)) {
							return preOpID;
						} else {
							return this.digSourcePrime(preOpID, dataRepositories);
						}
					}
				}
			}
		}
		return null;
	}

	public Map<String, DataSchema> getDataSchemas(Map<String, DataRepository> dataRepositories) {
		Map<String, DataSchema> dataSchemas = new HashMap<>();
		if (metaIDs != null && !metaIDs.isEmpty()) {
			for (int m = 0; m < metaIDs.size(); m++) {
				for (String metaID : metaIDs) {
					/*
					 * if data schemas do not contains this element, it should be added into
					 * schemas.
					 */
					DataRepository dataRepository = dataRepositories.get(metaID);
					this.setDataRepository(dataRepository);
					String sourceName = dataRepository.getWf_name();
					String group = dataRepository.getWf_group();
					DataSchema dataSchema = null;
					if (DataRepository.WF_GROUP_INPUT.equals(group)) {
						dataSchema = this.resolveInputSchema(dataSchemas);
					} else if (DataRepository.WF_GROUP_OUTPUT.equals(group)) {
						dataSchema = this.resolveOutputSchema(dataSchemas);
					} else if (metaOperationsBeanList.contains(sourceName)) {
						/*
						 * if this is an operate, go such branches.
						 */
						Component component0 = components.get(0);
						String sourcePrime = component0.getSourcePrimary();
						List<SingleMetaData> metadatas = null;
						if (dataSchemas.containsKey(sourcePrime)) {
							DataSchema primeDataSchema = dataSchemas.get(sourcePrime);
							metadatas = primeDataSchema.getMetaDatas();
						}
						if (metadatas != null) {
							switch (sourceName) {
							case DataRepository.WF_NAME_JOIN:
								dataSchema = this.resolveJoinMeta(dataSchemas);
								break;
							case DataRepository.WF_NAME_GROUPBY:
							case DataRepository.WF_NAME_ALGORITHM_MODEL:
							case DataRepository.WF_NAME_LINER_R:
							case DataRepository.WF_NAME_LOGIC_R:
							case DataRepository.WF_NAME_ALS_KEY:
							case DataRepository.WF_NAME_KMEANS_KEY:
							case DataRepository.WF_NAME_SVM_KEY:
							case DataRepository.WF_NAME_BAYES_KEY:
							case DataRepository.WF_NAME_RANDOM_FOREST_KEY:
							case DataRepository.WF_NAME_RANDOM_FOREST_RETURN_KEY:
							case DataRepository.WF_NAME_DECISION_TREE_KEY:
							case DataRepository.WF_NAME_DECISION_TREE_RETURN_KEY:
							case DataRepository.WF_NAME_IS_KEY:
							case DataRepository.WF_NAME_GMG_KEY:
							case DataRepository.WF_NAME_PIC_KEY:
							case DataRepository.WF_NAME_FP_TREE_KEY:
							case DataRepository.WF_NAME_PCA_KEY:
							case DataRepository.WF_NAME_SVD_KEY:
							case DataRepository.WF_NAME_WORD_2_VEC_KEY:
							case DataRepository.WF_NAME_TF_IDF_KEY:
							case DataRepository.WF_NAME_APRIORI_KEY:
								dataSchema = this.resolveGroup(dataSchemas, metadatas);
								break;
							case DataRepository.WF_NAME_REMOVEFIELD:
								dataSchema = this.resolveRemoveField(dataSchemas, metadatas);
								break;
							case DataRepository.WF_NAME_COUNT:
							case DataRepository.WF_NAME_SUM:
								dataSchema = resolveOneGroup(dataSchemas);
								break;
							}
						}
					}
					if (!dataSchemas.containsKey(metaID) && dataSchema != null) {
						dataSchemas.put(metaID, dataSchema);
					}
				}
			}
		} else {
			return null;
		}
		return dataSchemas;
	}

	private DataSchema resolveOutputSchema(Map<String, DataSchema> dataSchemas) {

		Component component = components.get(0);
		List<SingleMetaData> metaDatas = new ArrayList<>();
		if (component != null) {
			String sourcePrime = component.getSourcePrimary();
			if (dataSchemas.containsKey(sourcePrime)) {
				DataSchema dataSchemaPrime = dataSchemas.get(sourcePrime);
				String tmpTableName = dataSchemaPrime.getTmpTableName();
				List<SingleMetaData> primeMetaDatas = dataSchemaPrime.getMetaDatas();
				if (primeMetaDatas != null) {
					for (SingleMetaData primeSingleMetaData : primeMetaDatas) {
						int colNum = primeSingleMetaData.getColNum();
						String timeFormat = primeSingleMetaData.getDateFormat();
						String fieldName = primeSingleMetaData.getFieldName();
						String fieldType = primeSingleMetaData.getFieldType();
						SingleMetaData singleMetaData = new SingleMetaData();
						if (timeFormat != null)
							singleMetaData.setDateFormat(timeFormat);
						singleMetaData.setColNum(colNum);
						if (fieldName != null)
							singleMetaData.setFieldName(fieldName);
						if (fieldType != null) {
							singleMetaData.setFieldType(fieldType);
						} else {
							singleMetaData.setFieldType(SingleMetaData.TYPE_STRING);
						}
						metaDatas.add(singleMetaData);
					}
					DataSchema dataSchema = new DataSchema();
					dataSchema.setMetaDatas(metaDatas);
					dataSchema.setPutType(DataSourceProp.TYPE_OUTPUT);
					if (tmpTableName != null) {
						dataSchema.setTmpTableName(tmpTableName);
					}
					return dataSchema;
				}
			}
		}
		return null;
	}

	private DataSchema resolveInputSchema(Map<String, DataSchema> dataSchemas) {
		/*
		 * if this is an input data source, add directly.
		 */
		if (fieldName != null && !fieldName.isEmpty()) {
			DataSchema dataSchema = new DataSchema();
			List<SingleMetaData> metaDatas = new ArrayList<>();
			for (int i = 0; i < fieldName.size(); i++) {
				SingleMetaData singleMetaData = new SingleMetaData();
				singleMetaData.setFieldName(fieldName.get(i));
				if (fieldFilter != null && fieldFilter.size() == fieldName.size()) {
					String filter = fieldFilter.get(i);
					switch (filter) {
					case DataRepository.WF_FIELD_FILTER_TEL:
						singleMetaData.setFieldFilter(SingleMetaData.WF_FIELD_FILTER_TEL);
						break;
					case DataRepository.WF_FIELD_FILTER_ENUM:
						singleMetaData.setFieldFilter(SingleMetaData.WF_FIELD_FILTER_ENUM);
						singleMetaData.setFieldFilterVal(fieldFilterVal.get(i));
						break;
					case DataRepository.WF_FIELD_FILTER_MAXLEN:
						singleMetaData.setFieldFilter(SingleMetaData.WF_FIELD_FILTER_MAXLEN);
						singleMetaData.setFieldFilterVal(fieldFilterVal.get(i));
						break;
					case DataRepository.WF_FIELD_FILTER_NONE:
					default:
						singleMetaData.setFieldFilter(SingleMetaData.WF_FIELD_FILTER_NONE);
						break;
					}
				}
				if (fieldType != null) {
					singleMetaData.setFieldType(fieldType.get(i));
				}
				if (timeFormat != null && timeFormat.size() == fieldName.size()) {
					if (timeFormat.get(i) != null) {
						singleMetaData.setDateFormat(timeFormat.get(i));
					}
				}
				singleMetaData.setColNum(i);
				metaDatas.add(singleMetaData);
			}
			if (outSeparator != null)
				dataSchema.setDatasouceDelimiter(outSeparator);
			if (tableName != null)
				dataSchema.setTableName(tableName);
			if (tmpTableName != null)
				dataSchema.setTmpTableName(tmpTableName);
			if (kafkaTopics != null)
				dataSchema.setTableName(kafkaTopics);
			if (wf_group != null) {
				dataSchema.setPutType(DataSourceProp.TYPE_INPUT);
			}
			dataSchema.setMetaDatas(metaDatas);
			return dataSchema;
		}
		return null;
	}

	private DataSchema resolveRemoveField(Map<String, DataSchema> dataSchemas, List<SingleMetaData> metadatas) {
		DataSchema dataSchema = new DataSchema();
		List<SingleMetaData> metaDatas = new ArrayList<>();
		/*
		 * remove the field
		 */
		String[] fieldsNames = StringUtils.splitPreserveAllTokens(fieldNames, ",");
		List<String> removeFields = new ArrayList<>();
		for (String field : fieldsNames) {
			String[] newField = StringUtils.splitPreserveAllTokens(field, ".", 2);
			removeFields.add(newField[1]);
		}
		if (metadatas != null) {
			/*
			 * add fields, not be removed, into linked list to keep the sequence.
			 */
			List<Map<String, String>> fieldNamesTypes = new LinkedList<>();
			for (int i = 0; i < metadatas.size(); i++) {
				for (SingleMetaData metadata : metadatas) {
					int fieldNumNow = metadata.getColNum();
					if (fieldNumNow == i) {
						String fieldNameNow = metadata.getFieldName();
						String fieldTypeNow = metadata.getFieldType();
						String timeFormatNow = metadata.getDateFormat();
						Map<String, String> map = new HashMap<>();
						map.put("fieldName", fieldNameNow);
						map.put("fieldType", fieldTypeNow);
						if (timeFormatNow != null)
							map.put("fieldType", fieldTypeNow);
						if (!removeFields.contains(fieldNameNow)) {
							fieldNamesTypes.add(map);
						}
					}
				}
			}
			/*
			 * release fields informations into pojo.
			 */
			for (int i = 0; i < fieldNamesTypes.size(); i++) {
				Map<String, String> fieldNamesType = fieldNamesTypes.get(i);
				String fieldName = fieldNamesType.get("fieldName");
				String fieldType = fieldNamesType.get("fieldType");
				String dateFormat = fieldNamesType.get("timeFormat");
				SingleMetaData metadata = new SingleMetaData();
				metadata.setFieldName(fieldName);
				metadata.setFieldType(fieldType);
				metadata.setColNum(i);
				if (dateFormat != null)
					metadata.setDateFormat(dateFormat);
				metaDatas.add(metadata);
			}

			dataSchema.setMetaDatas(metaDatas);
			if (outSeparator != null)
				dataSchema.setDatasouceDelimiter(outSeparator);
			if (tableName != null)
				dataSchema.setTableName(tableName);
			if (wf_group != null) {
				switch (wf_group) {
				case DataRepository.WF_GROUP_INPUT:
					dataSchema.setPutType(DataSourceProp.TYPE_INPUT);
					break;
				case DataRepository.WF_GROUP_OUTPUT:
					dataSchema.setPutType(DataSourceProp.TYPE_OUTPUT);
					break;
				case DataRepository.WF_GROUP_OP:
					dataSchema.setPutType(DataSourceProp.TYPE_NONE);
					break;
				}
			}
		}
		return dataSchema;
	}

	private DataSchema resolveOneGroup(Map<String, DataSchema> dataSchemas) {
		DataSchema dataSchema = new DataSchema();
		List<SingleMetaData> metaDatas = new ArrayList<>();
		List<String> fieldName = dataRepository.getFieldName();
		SingleMetaData singleMetaData = new SingleMetaData();
		singleMetaData.setFieldName(fieldName.get(0));
		singleMetaData.setFieldType(SingleMetaData.TYPE_DOUBLE);
		singleMetaData.setColNum(0);
		metaDatas.add(singleMetaData);
		dataSchema.setMetaDatas(metaDatas);
		if (outSeparator != null)
			dataSchema.setDatasouceDelimiter(outSeparator);
		if (tableName != null)
			dataSchema.setTableName(tableName);
		if (wf_group != null) {
			switch (wf_group) {
			case DataRepository.WF_GROUP_INPUT:
				dataSchema.setPutType(DataSourceProp.TYPE_INPUT);
				break;
			case DataRepository.WF_GROUP_OUTPUT:
				dataSchema.setPutType(DataSourceProp.TYPE_OUTPUT);
				break;
			case DataRepository.WF_GROUP_OP:
				dataSchema.setPutType(DataSourceProp.TYPE_NONE);
				break;
			}
		}
		return dataSchema;
	}

	private DataSchema resolveGroup(Map<String, DataSchema> dataSchemas, List<SingleMetaData> metadatas) {
		DataSchema dataSchema = null;
		List<SingleMetaData> metaDatas = new ArrayList<>();
		if (metadatas != null) {
			for (SingleMetaData metadata : metadatas) {
				SingleMetaData metaData = new SingleMetaData();
				metaData.setColNum(metadata.getColNum());
				if (metadata.getDateFormat() != null) {
					metaData.setDateFormat(metadata.getDateFormat());
				}
				metaData.setFieldName(metadata.getFieldName());
				metaData.setFieldType(metadata.getFieldType());
				metaDatas.add(metaData);
			}
			SingleMetaData metaData = new SingleMetaData();
			metaData.setColNum(metadatas.size());
			metaData.setFieldType(SingleMetaData.TYPE_STRING);
			if (fieldName != null) {
				metaData.setFieldName(fieldName.get(0));
			} else {
				metaData.setFieldName("group");
			}
			metaDatas.add(metaData);
			dataSchema = new DataSchema();
			dataSchema.setMetaDatas(metaDatas);
			if (outSeparator != null)
				dataSchema.setDatasouceDelimiter(outSeparator);
			if (tableName != null)
				dataSchema.setTableName(tableName);
			if (wf_group != null) {
				switch (wf_group) {
				case DataRepository.WF_GROUP_INPUT:
					dataSchema.setPutType(DataSourceProp.TYPE_INPUT);
					break;
				case DataRepository.WF_GROUP_OUTPUT:
					dataSchema.setPutType(DataSourceProp.TYPE_OUTPUT);
					break;
				case DataRepository.WF_GROUP_OP:
					dataSchema.setPutType(DataSourceProp.TYPE_NONE);
					break;
				}
			}
		}
		return dataSchema;
	}

	private DataSchema resolveJoinMeta(Map<String, DataSchema> dataSchemas) {
		DataSchema dataSchema = null;
		List<SingleMetaData> metaDatas = new ArrayList<>();
		/*
		 * table and id
		 */
		Map<String, String> tableID = new HashMap<>();
		boolean exist = true;
		/*
		 * get the source prime's meta data
		 */
		for (Component component : components) {
			String sourcePrimeJoin = component.getSourcePrimary();
			if (!dataSchemas.containsKey(sourcePrimeJoin)) {
				exist = false;
				break;
			} else {
				String primeTable = dataSchemas.get(sourcePrimeJoin).getTableName();
				if (!tableID.containsKey(primeTable)) {
					tableID.put(primeTable, sourcePrimeJoin);
				}
			}
		}
		if (exist) {
			dataSchema = new DataSchema();
			int index = 0;
			List<String> tables = new ArrayList<>();
			if (leftJoinSort != null) {
				String[] joinTableFields = StringUtils.splitPreserveAllTokens(leftJoinSort, ",");
				for (String joinTableField : joinTableFields) {
					String[] tableFields = StringUtils.splitPreserveAllTokens(joinTableField, ".");
					String tableNow = tableFields[0];
					if (tableNow != null && !tables.contains(tableNow)) {
						tables.add(tableNow);
					}
				}
			}
			for (int i = 0; i < tables.size(); i++) {
				String aTable = tables.get(i);
				String id = tableID.get(aTable);
				if (dataSchemas.containsKey(id)) {
					DataSchema tempDataSchema = dataSchemas.get(id);
					List<SingleMetaData> tmpmetadatas = tempDataSchema.getMetaDatas();
					Map<String, String> fieldShouldRename = new HashMap<>();
					for (int j = 0; j < fieldName.size(); j++) {
						String[] asTableFields = StringUtils.split(fieldName.get(j), ".");
						if (asTableFields != null && asTableFields.length > 0 && asTableFields[0] != null
								&& aTable.equals(asTableFields[0])) {
							fieldShouldRename.put(asTableFields[1], asFieldName.get(j));
						}
					}
					for (int k = 0; k < tmpmetadatas.size(); k++) {
						SingleMetaData singleMetaData = new SingleMetaData();
						SingleMetaData tmpSingleMetaData = tmpmetadatas.get(k);
						String tempFieldName = tmpSingleMetaData.getFieldName();
						if (fieldShouldRename.containsKey(tempFieldName)) {
							singleMetaData.setFieldName(fieldShouldRename.get(tempFieldName));
						} else {
							singleMetaData.setFieldName(tempFieldName);
						}
						String tmpFieldType = tmpSingleMetaData.getFieldType();
						singleMetaData.setFieldType(tmpFieldType);
						singleMetaData.setColNum(index);
						index++;
						String tmpTimeFormat = tmpSingleMetaData.getDateFormat();
						if (tmpTimeFormat != null) {
							singleMetaData.setDateFormat(tmpTimeFormat);
						}
						metaDatas.add(singleMetaData);
					}
				}
			}
			dataSchema.setMetaDatas(metaDatas);
			if (tableName != null)
				dataSchema.setTableName(tableName);
			if (wf_group != null) {
				switch (wf_group) {
				case DataRepository.WF_GROUP_INPUT:
					dataSchema.setPutType(DataSourceProp.TYPE_INPUT);
					break;
				case DataRepository.WF_GROUP_OUTPUT:
					dataSchema.setPutType(DataSourceProp.TYPE_OUTPUT);
					break;
				case DataRepository.WF_GROUP_OP:
					dataSchema.setPutType(DataSourceProp.TYPE_NONE);
					break;
				}
			}

		}
		return dataSchema;
	}

	private void setDataRepository(DataRepository dataRepository) {
		this.dataRepository = dataRepository;

		id = dataRepository.getId();
		wf_name = dataRepository.getWf_name();
		wf_group = dataRepository.getWf_group();
		dataSourceId = dataRepository.getDataSourceId();
		databaseName = dataRepository.getDatabaseName();
		tableName = dataRepository.getTableName();
		recordDelimiter = dataRepository.getRecordDelimiter();
		fieldFilter = dataRepository.getFieldFilter();
		fieldFilterVal = dataRepository.getFieldFilterVal();
		fieldName = dataRepository.getFieldName();
		fieldRemove = dataRepository.getFieldRemove();
		independents = dataRepository.getIndependents();
		dependents = dataRepository.getDependents();
		fieldType = dataRepository.getFieldType();
		timeFormat = dataRepository.getTimeFormat();
		asFieldName = dataRepository.getAsFieldName();
		components = dataRepository.getComponents();
		downBranches = dataRepository.getDownBranches();
		outSeparator = dataRepository.getOutSeparator();
		tmpTableName = dataRepository.getTmpTableName();
		ip = dataRepository.getIp();
		port = dataRepository.getPort();
		restPort = dataRepository.getRestPort();
		userName = dataRepository.getUserName();
		password = dataRepository.getPassword();
		url = dataRepository.getUrl();
		urls = dataRepository.getUrls();
		zkServers = dataRepository.getZkServers();
		kafkaDurationsSeconds = dataRepository.getKafkaDurationsSeconds();
		kafkaGroupID = dataRepository.getKafkaGroupID();
		kafkaPartitions = dataRepository.getKafkaPartitions();
		kafkaTopics = dataRepository.getKafkaTopics();
		path = dataRepository.getPath();
		rotationFileSizeInMB = dataRepository.getRotationFileSizeInMB();
		rotationTimeInSec = dataRepository.getRotationTimeInSec();
		encoding = dataRepository.getEncoding();
		filePrifix = dataRepository.getFilePrifix();
		fileExtension = dataRepository.getFileExtension();
		syncCount = dataRepository.getSyncCount();
		rowKeyFields = dataRepository.getRowKeyFields();
		znodeParent = dataRepository.getZnodeParent();
		metaStoreURI = dataRepository.getMetaStoreURI();
		timeAsPartitionField = dataRepository.getTimeAsPartitionField();
		partitionFields = dataRepository.getPartitionFields();
		values = dataRepository.getValues();
		fieldNames = dataRepository.getFieldNames();
		between = dataRepository.getBetween();
		and = dataRepository.getAnd();
		isSection = dataRepository.getIsSection();
		upSort = dataRepository.getUpSort();
		calToken = dataRepository.getCalToken();
		leftJoinSort = dataRepository.getLeftJoinSort();
		independents = dataRepository.getIndependents();
		keyPath = dataRepository.getKeyPath();

		bolts_num = dataRepository.getBolts_num();
		parallelismNum = dataRepository.getParallelismNum();
		tickTupleFreqSecs = dataRepository.getTickTupleFreqSecs();

		algorithmModelID = dataRepository.getAlgorithmModelID();
		algorithmID = dataRepository.getAlgorithmID();
		algorithmModelName = dataRepository.getAlgorithmModelName();
		algorithmModelType = dataRepository.getAlgorithmModelType();
		algorithmIsTrain = dataRepository.getAlgorithmIsTrain();
		algorithmIsSpecifiedDataFormat = dataRepository.getAlgorithmIsSpecifiedDataFormat();
		algorithmDataFormat = dataRepository.getAlgorithmDataFormat();
		algorithmLabelField = dataRepository.getAlgorithmLabelField();
		algorithmModelSavePath = dataRepository.getAlgorithmModelSavePath();
		algorithmRank = dataRepository.getAlgorithmRank();
		algorithmIterations = dataRepository.getAlgorithmIterations();
		algorithmRegParam = dataRepository.getAlgorithmRegParam();
		algorithmSeed = dataRepository.getAlgorithmSeed();
		algorithmStepSize = dataRepository.getAlgorithmStepSize();
	}
}
