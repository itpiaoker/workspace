package cn.com.carenet.scheduler.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.carenet.scheduler.bean.DataBaseSQLBean;
import cn.com.carenet.scheduler.bean.DataSchema;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.OperateComponent;
import cn.com.carenet.scheduler.bean.OperateProp;
import cn.com.carenet.scheduler.bean.spark.SparkEnvProp;
import cn.com.carenet.scheduler.bean.storm.TopologyEnvProp;
import cn.com.carenet.scheduler.web.entity.Component;
import cn.com.carenet.scheduler.web.entity.DataRepository;

/**
 * 
 * @author Sherard Lee
 * @since 22/Aug/2017
 */
@Service
public class WorkFlowConfManager {
	final private static Logger LOG = LoggerFactory.getLogger(WorkFlowConfManager.class);
	final private static List<String> commonOperateModuleNameList = new ArrayList<>();
	final private static List<String> sqlOperateModuleNameList = new ArrayList<>();

	private DataRepository moduleDataRepository;

	private Set<String> kafkaTopics = new HashSet<>();
	private String workFlowID;
	private long timestampExpression;
	private String cronExpression;
	private boolean repeat = false;
	private boolean workFlowError = false;
	private String workFlowErrorMsg;
	private String workFlowType;
	private SparkEnvProp sparkEnvProp;
	private TopologyEnvProp topologyOptionsBean;
	private Map<String, OperateProp> operateMap;
	private Map<String, DataSourceProp> dataSourceMap;
	private Map<String, DataSchema> dataSchemas;
	private DataBaseSQLBean dataBaseSQLBean;

	static {
		commonOperateModuleNameList.add(DataRepository.WF_NAME_STORM);
		commonOperateModuleNameList.add(DataRepository.WF_NAME_MAPREDUCE);
		commonOperateModuleNameList.add(DataRepository.WF_NAME_SPARKCORE);
		commonOperateModuleNameList.add(DataRepository.WF_NAME_SPARKSTREAMING);
		commonOperateModuleNameList.add(DataRepository.WF_NAME_SPARKGRAPHX);
		commonOperateModuleNameList.add(DataRepository.WF_NAME_SPARK_MLLIB);

		sqlOperateModuleNameList.add(DataRepository.WF_NAME_UNIXSHELL);
		sqlOperateModuleNameList.add(DataRepository.WF_NAME_SPARKSQL);
		sqlOperateModuleNameList.add(DataRepository.WF_NAME_GREENPLUMSQL);
		sqlOperateModuleNameList.add(DataRepository.WF_NAME_HIVEQL);
		sqlOperateModuleNameList.add(DataRepository.WF_NAME_MYSQLSQL);
		sqlOperateModuleNameList.add(DataRepository.WF_NAME_ORACLESQL);
	}

	public void setJsonStr(String json) {
		this.generatePorps(json, this.workFlowID);
	}

	/**
	 * Generate properties by using JSON strs.
	 * 
	 * @param json
	 * @param workFlowID
	 */
	public void generatePorps(String json, String workFlowID) {

		this.workFlowError = false;
		this.workFlowErrorMsg = null;
		this.workFlowID = workFlowID;
		/* load json string into bean */
		JsonPropLoader jsonPropLoader = new JsonPropLoader();
		Map<String, DataRepository> dataRepositories = jsonPropLoader.loadJson(json);
		
		for(DataRepository dataSourceProp:dataRepositories.values()){
			System.err.println(dataSourceProp.getWf_name() +"  "+dataSourceProp.getDependents()+" "+dataSourceProp.getIndependents());
		}
		/* load the cron infomations from bean */
		WorkTimeMachine workTimeMachine = new WorkTimeMachine(dataRepositories);
		this.cronExpression = workTimeMachine.getCronExpression();
		this.timestampExpression = workTimeMachine.getTimestampExpression();
		this.repeat = workTimeMachine.isRepeat();
		/*
		 * check the work flow, and get the type of work flow, and get start id
		 */
		WorkFlowLegality workFlowLegality = new WorkFlowLegality();
		dataRepositories = workFlowLegality.checkElements(dataRepositories);
		if (dataRepositories == null || dataRepositories.isEmpty() || workFlowLegality.isWorkFlowError()) {
			workFlowError = true;
			workFlowErrorMsg = workFlowLegality.getWorkFlowErrorMsg();
			if (workFlowErrorMsg == null) {
				workFlowErrorMsg = "Warn: this is an empty work flow!";
			}
			return;
		}
		this.workFlowType = workFlowLegality.getWorkFlowType();
		String startID = workFlowLegality.getStartID();

		/*
		 * if the work flow is storm/spark/mapreduce job, the module should be removed
		 * and settings should put into moduleDataRepository.
		 */
		if (commonOperateModuleNameList.contains(workFlowType)) {
			dataRepositories = this.inputModulePlaceTransfer(startID, dataRepositories);
		}
		/*
		 * get source primary.
		 *
		 * list the input data sources' table data structures. find the point of which
		 * changed the data structure and new a details.
		 */
		dataRepositories = this.findTableSource(dataRepositories);
		/* start to transfer these settings into different groups */
		this.injectBeans(dataRepositories);
		if (DataRepository.WF_NAME_STORM.equals(workFlowType)) {
			this.topologyOptionsBean = this.generateTopologyEnvProp();
		} else if (DataRepository.WF_NAME_SPARKCORE.equals(workFlowType)
				|| DataRepository.WF_NAME_SPARKSTREAMING.equals(workFlowType)
				|| DataRepository.WF_NAME_SPARK_MLLIB.equals(workFlowType)) {
			this.sparkEnvProp = this.generateSparkEnvProp(dataRepositories);
		} else if (DataRepository.WF_NAME_SPARKSQL.equals(workFlowType)) {
			dataBaseSQLBean = this.generateDataBaseSQLBean(dataRepositories);
			this.sparkEnvProp = this.generateSparkEnvProp(dataRepositories);
		} else if (sqlOperateModuleNameList.contains(workFlowType)) {
			dataBaseSQLBean = generateDataBaseSQLBean(dataRepositories);
		}

	}

	public Map<String, JSONObject> getOptionMap() {
		LOG.debug("Get the option Map for topics.");
		Map<String, JSONObject> optionMaps = new HashMap<>();

		if (dataSourceMap != null) {
			for (Entry<String, DataSourceProp> datasourceEntry : dataSourceMap.entrySet()) {
				String id = datasourceEntry.getKey();
				DataSourceProp dataSource = datasourceEntry.getValue();
				String typeName = dataSource.getSourceName();
				String topic = dataSource.getKafkaTopics();
				if (topic != null) {
					if (optionMaps.containsKey(topic)) {
						JSONObject jsonObject = optionMaps.get(topic);
						jsonObject.put(id, typeName);
						jsonObject.put(id + typeName, typeName);
						jsonObject.put(id, typeName + "_Buffer_Bolt");
						optionMaps.put(topic, jsonObject);
					} else {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put(id, typeName);
						jsonObject.put(id + typeName, typeName);
						jsonObject.put(id, typeName + "_Buffer_Bolt");
						optionMaps.put(topic, jsonObject);
					}
				} else {
					List<OperateComponent> components = dataSource.getComponents();
					if (components != null) {
						for (OperateComponent component : components) {
							String sourcePrime = component.getSourcePrimary();
							if (sourcePrime != null) {
								List<String> topicNames = this.digTopicName(sourcePrime);
								if (topicNames != null) {
									for (String topicName : topicNames) {
										if (optionMaps.containsKey(topicName)) {
											JSONObject jsonObject = optionMaps.get(topicName);
											jsonObject.put(id, typeName);
											optionMaps.put(topicName, jsonObject);
										} else {
											JSONObject jsonObject = new JSONObject();
											jsonObject.put(id, typeName);
											optionMaps.put(topicName, jsonObject);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (operateMap != null) {
			for (Entry<String, OperateProp> operateEntry : operateMap.entrySet()) {
				String id = operateEntry.getKey();
				OperateProp operateOptionsBean = operateEntry.getValue();
				String typeName = operateOptionsBean.getSourceName();
				List<OperateComponent> components = operateOptionsBean.getComponents();
				if (components != null) {
					for (OperateComponent component : components) {
						String sourcePrime = component.getSourcePrimary();
						if (sourcePrime != null) {
							List<String> topicNames = this.digTopicName(sourcePrime);
							if (topicNames != null) {
								for (String topicName : topicNames) {
									if (optionMaps.containsKey(topicName)) {
										JSONObject jsonObject = optionMaps.get(topicName);
										jsonObject.put(id, typeName);
										optionMaps.put(topicName, jsonObject);
									} else {
										JSONObject jsonObject = new JSONObject();
										jsonObject.put(id, typeName);
										optionMaps.put(topicName, jsonObject);
									}
								}
							}
						}
					}
				}
			}
		}

		return optionMaps;
	}

	private List<String> digTopicName(String sourcePrime) {
		List<String> topicNames = null;
		if (sourcePrime != null) {
			if (operateMap.containsKey(sourcePrime)) {
				OperateProp primeOperateProp = operateMap.get(sourcePrime);
				List<OperateComponent> primeComponents = primeOperateProp.getComponents();
				for (OperateComponent primeComponent : primeComponents) {
					String newSourcePrime = primeComponent.getSourcePrimary();
					topicNames = this.digTopicName(newSourcePrime);
				}
			} else if (dataSourceMap.containsKey(sourcePrime)) {
				DataSourceProp primeDataSourceProp = dataSourceMap.get(sourcePrime);
				String primeTopic = primeDataSourceProp.getKafkaTopics();
				topicNames = new ArrayList<>();
				topicNames.add(primeTopic);
			}
		}
		return topicNames;
	}

	/**
	 * modified the source primary then, load meta datas
	 * 
	 * @param dataRepositories
	 * @return
	 */
	private Map<String, DataRepository> findTableSource(Map<String, DataRepository> dataRepositories) {
		PropTunnel tunnel = new PropTunnel();
		Map<String, DataRepository> newDataRepositories = tunnel.getTableSource(dataRepositories);
		this.dataSchemas = tunnel.getDataSchemas(dataRepositories);
		return newDataRepositories;
	}

	private Map<String, DataRepository> inputModulePlaceTransfer(String startID,
			Map<String, DataRepository> dataRepositories) {
		List<String> moduleIDs = new ArrayList<>();
		DataRepository startRepo = dataRepositories.get(startID);
		List<String> datasourceIDs = startRepo.getDownBranches();
		for (String datasourceID : datasourceIDs) {
			/* datasouce */
			/* module */
			List<String> newDatasourceDownIDs = new ArrayList<>();
			DataRepository datasource = dataRepositories.get(datasourceID);

			List<String> moduleNameIDs = datasource.getDownBranches();
			for (String moduleNameID : moduleNameIDs) {
				if (!moduleIDs.contains(moduleNameID)) {
					moduleIDs.add(moduleNameID);
				}
				/* module */
				DataRepository dataRepository = dataRepositories.get(moduleNameID);
				/* operate */
				List<String> operateIDs = dataRepository.getDownBranches();
				for (String operateID : operateIDs) {
					DataRepository operateDataRepository = dataRepositories.get(operateID);
					List<Component> newComponents = new ArrayList<>();
					List<Component> components = operateDataRepository.getComponents();
					for (Component component : components) {
						String moduleUperID = component.getWf_sourceId();
						if (moduleUperID.equals(moduleNameID)) {
							component.setWf_sourceId(datasourceID);
							if (!newDatasourceDownIDs.contains(operateID)) {
								newDatasourceDownIDs.add(operateID);
							}
						}
						newComponents.add(component);
					}
					operateDataRepository.setComponents(newComponents);
					dataRepositories.put(operateID, operateDataRepository);
				}
			}
			datasource.setDownBranches(newDatasourceDownIDs);
		}
		String settings = "";
		for (String moduleID : moduleIDs) {
			DataRepository dataRepository = dataRepositories.get(moduleID);
			String newSettings = JSON.toJSONString(dataRepository);
			if (settings.length() < newSettings.length()) {
				settings = newSettings;
			}
		}
		moduleDataRepository = JSON.toJavaObject(JSON.parseObject(settings), DataRepository.class);

		for (String moduleID : moduleIDs) {
			dataRepositories.remove(moduleID);
		}
		return dataRepositories;
	}

	/**
	 * set data sources and operates
	 * 
	 * @param dataRepositories
	 */
	private void injectBeans(Map<String, DataRepository> dataRepositories) {
		PropTunnel tunnel = new PropTunnel();
		/*
		 * find out data source one by one
		 */
		dataSourceMap = new HashMap<>();
		for (Entry<String, DataRepository> dataRepositoryEntry : dataRepositories.entrySet()) {
			String key = dataRepositoryEntry.getKey();
			DataRepository dataRepository = dataRepositoryEntry.getValue();
			DataSourceProp dataSourceProp = tunnel.getDataSourceProp(workFlowID, dataRepository);
			if (dataSourceProp != null) {
				dataSourceMap.put(key, dataSourceProp);
			}
			String sourceName = dataRepository.getWf_name();
			if (sourceName != null) {
				if (DataRepository.WF_NAME_KAFKA.equals(sourceName)) {
					String topic = dataSourceProp.getKafkaTopics();
					if (topic != null)
						kafkaTopics.add(topic);
				}
			}
		}
		/*
		 * find out operates one by one
		 */
		operateMap = new HashMap<>();
		for (Entry<String, DataRepository> dataRepositoryEntry : dataRepositories.entrySet()) {
			String key = dataRepositoryEntry.getKey();
			DataRepository dataRepository = dataRepositoryEntry.getValue();
			OperateProp operateProp = tunnel.getOperateProp(workFlowID, dataRepository);

			if (operateProp != null) {
				operateMap.put(key, operateProp);
			}
		}
	}

	private DataBaseSQLBean generateDataBaseSQLBean(Map<String, DataRepository> dataRepositories) {
		for (Entry<String, DataRepository> dataRepositoryEntry : dataRepositories.entrySet()) {
			DataRepository dataRepository = dataRepositoryEntry.getValue();
			String sourceName = dataRepository.getWf_name();
			if (sourceName != null && sqlOperateModuleNameList.contains(sourceName)) {
				PropTunnel propTunnel = new PropTunnel();
				DataBaseSQLBean dataBaseSQLBean = propTunnel.getDataBaseSQLBean(dataRepository);
				dataBaseSQLBean.setWorkFlowID(workFlowID);
				return dataBaseSQLBean;
			}
		}
		return null;
	}

	private TopologyEnvProp generateTopologyEnvProp() {
		PropTunnel propTunnel = new PropTunnel();
		TopologyEnvProp topologyOptionsBean = propTunnel.getTopologyOptionsBean(moduleDataRepository);
		return topologyOptionsBean;
	}

	/**
	 * get spark configs from the module
	 * 
	 * @param dataRepositories
	 * @return
	 */
	private SparkEnvProp generateSparkEnvProp(Map<String, DataRepository> dataRepositories) {
		for (Entry<String, DataRepository> dataRepositoryEntry : dataRepositories.entrySet()) {
			String sourceName = dataRepositoryEntry.getValue().getWf_name();
			switch (sourceName) {
			case DataRepository.WF_NAME_SPARKCORE:
			case DataRepository.WF_NAME_SPARKSQL:
			case DataRepository.WF_NAME_SPARKSTREAMING:
			case DataRepository.WF_NAME_SPARK_MLLIB:
			case DataRepository.WF_NAME_SPARKGRAPHX:
				moduleDataRepository = dataRepositoryEntry.getValue();
				break;
			}
		}
		PropTunnel propTunnel = new PropTunnel();
		SparkEnvProp sparkEnvProp = propTunnel.getSparkEnvProp(moduleDataRepository);
		sparkEnvProp.setWorkFlowID(workFlowID);
		return sparkEnvProp;
	}

	public String getWorkFlowID() {
		return workFlowID;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}

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

	public boolean isWorkFlowError() {
		return workFlowError;
	}

	public void setWorkFlowError(boolean workFlowError) {
		this.workFlowError = workFlowError;
	}

	public String getWorkFlowErrorMsg() {
		return workFlowErrorMsg;
	}

	public void setWorkFlowErrorMsg(String workFlowErrorMsg) {
		this.workFlowErrorMsg = workFlowErrorMsg;
	}

	public String getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(String workFlowType) {
		this.workFlowType = workFlowType;
	}

	public TopologyEnvProp getTopologyOptionsBean() {
		return topologyOptionsBean;
	}

	public void setTopologyOptionsBean(TopologyEnvProp topologyOptionsBean) {
		this.topologyOptionsBean = topologyOptionsBean;
	}

	public Map<String, OperateProp> getOperateMap() {
		return operateMap;
	}

	public void setOperateMap(Map<String, OperateProp> operateMap) {
		this.operateMap = operateMap;
	}

	public Map<String, DataSourceProp> getDataSourceMap() {
		return dataSourceMap;
	}

	public void setDataSourceMap(Map<String, DataSourceProp> dataSourceMap) {
		this.dataSourceMap = dataSourceMap;
	}

	public Map<String, DataSchema> getDataSchemas() {
		return dataSchemas;
	}

	public void setDataSchemas(Map<String, DataSchema> dataSchemas) {
		this.dataSchemas = dataSchemas;
	}

	public DataBaseSQLBean getDataBaseSQLBean() {
		return dataBaseSQLBean;
	}

	public void setDataBaseSQLBean(DataBaseSQLBean dataBaseSQLBean) {
		this.dataBaseSQLBean = dataBaseSQLBean;
	}

	public SparkEnvProp getSparkEnvProp() {
		return sparkEnvProp;
	}

	public void setSparkEnvProp(SparkEnvProp sparkEnvProp) {
		this.sparkEnvProp = sparkEnvProp;
	}

	public Set<String> getKafkaTopics() {
		return kafkaTopics;
	}

	@Override
	public String toString() {
		return "WorkFlowConfManager [moduleDataRepository=" + moduleDataRepository + ", kafkaTopics=" + kafkaTopics
				+ ", workFlowID=" + workFlowID + ", timestampExpression=" + timestampExpression + ", cronExpression="
				+ cronExpression + ", repeat=" + repeat + ", workFlowError=" + workFlowError + ", workFlowErrorMsg="
				+ workFlowErrorMsg + ", workFlowType=" + workFlowType + ", sparkEnvProp=" + sparkEnvProp
				+ ", topologyOptionsBean=" + topologyOptionsBean + ", operateMap=" + operateMap + ", dataSourceMap="
				+ dataSourceMap + ", dataSchemas=" + dataSchemas + ", dataBaseSQLBean=" + dataBaseSQLBean + "]";
	}

}
