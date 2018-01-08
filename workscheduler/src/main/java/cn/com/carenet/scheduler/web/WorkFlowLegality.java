package cn.com.carenet.scheduler.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.web.entity.Component;
import cn.com.carenet.scheduler.web.entity.DataRepository;

/**
 * 
 * @author Sherard Lee
 * @since 22/Aug/2017
 */
public class WorkFlowLegality {
	final private static Logger LOG = LoggerFactory.getLogger(WorkFlowLegality.class);
	final private static List<String> operateModuleNameList = new ArrayList<>();
	final private static List<String> allDataSourceModuleNameList = new ArrayList<>();

	private boolean workFlowError = false;
	private String workFlowErrorMsg;
	private String startID;
	private String endID;
	private Set<String> startGroup;
	private Set<String> endGroup;
	private String workFlowType;
	private Map<String, List<String>> downBranchesMap = new HashMap<>();

	static {
		operateModuleNameList.add(DataRepository.WF_NAME_STORM);
		operateModuleNameList.add(DataRepository.WF_NAME_MAPREDUCE);
		operateModuleNameList.add(DataRepository.WF_NAME_UNIXSHELL);
		operateModuleNameList.add(DataRepository.WF_NAME_SPARKCORE);
		operateModuleNameList.add(DataRepository.WF_NAME_SPARKSTREAMING);
		operateModuleNameList.add(DataRepository.WF_NAME_SPARKSQL);
		operateModuleNameList.add(DataRepository.WF_NAME_SPARKGRAPHX);
		operateModuleNameList.add(DataRepository.WF_NAME_GREENPLUMSQL);
		operateModuleNameList.add(DataRepository.WF_NAME_HIVEQL);
		operateModuleNameList.add(DataRepository.WF_NAME_MYSQLSQL);
		operateModuleNameList.add(DataRepository.WF_NAME_ORACLESQL);
		operateModuleNameList.add(DataRepository.WF_NAME_SPARK_MLLIB);

		allDataSourceModuleNameList.add(WebModuleNameConstant.hive);
		allDataSourceModuleNameList.add(WebModuleNameConstant.ftp);
		allDataSourceModuleNameList.add(WebModuleNameConstant.greenPlum);
		allDataSourceModuleNameList.add(WebModuleNameConstant.mysql);
		allDataSourceModuleNameList.add(WebModuleNameConstant.oracle);
		allDataSourceModuleNameList.add(WebModuleNameConstant.localFile);
		allDataSourceModuleNameList.add(WebModuleNameConstant.hdfs);
		allDataSourceModuleNameList.add(WebModuleNameConstant.hBase);
		allDataSourceModuleNameList.add(WebModuleNameConstant.kafka);
		allDataSourceModuleNameList.add(WebModuleNameConstant.redis);
	}

	/**
	 * To find out the start ID& end ID, if it doesn't exist, throw out the
	 * error message.<br>
	 * If this contains multiple types of work flow, throw out the error
	 * message.(we are not allowed the multi-works.<br>
	 * This will drop the unlinked elements.<br>
	 * This will add down branches IDs to elements.<br>
	 * This will find out the job's type.<br>
	 * 
	 * @param dataRepositories
	 * @return
	 */
	public Map<String, DataRepository> checkElements(Map<String, DataRepository> dataRepositories) {
		for (Entry<String, DataRepository> dataRepositoryEntry : dataRepositories.entrySet()) {
			String key = dataRepositoryEntry.getKey();
			DataRepository dataRepository = dataRepositoryEntry.getValue();
			if (DataRepository.WF_NAME_START.equals(dataRepository.getWf_name())) {
				startID = key;
				startGroup = new HashSet<>();
				startGroup.add(startID);
			} else if (DataRepository.WF_NAME_END.equals(dataRepository.getWf_name())) {
				endID = key;
				endGroup = new HashSet<>();
				endGroup.add(endID);
			}
		}
		if (startID == null || endID == null) {
			workFlowError = true;
			workFlowErrorMsg = "Work flow does not have a/an start/end please check it !";
			LOG.error("Cause by:{}", workFlowErrorMsg);
			return null;
		}
		dataRepositories = this.dropUnlinkedElements(dataRepositories);
		dataRepositories = addDownBranches(dataRepositories);
		if (!this.checkMultipleWorking(dataRepositories)) {
			return null;
		}

		return dataRepositories;
	}

	private boolean checkMultipleWorking(Map<String, DataRepository> dataRepositories) {
		DataRepository startDataRepository = dataRepositories.get(startID);
		List<String> startDownBranches = startDataRepository.getDownBranches();
		String existModuleName = null;
		if (startDownBranches != null) {
			for (String startDownBranch : startDownBranches) {
				DataRepository nowDataRepository = dataRepositories.get(startDownBranch);
				String sourceName = nowDataRepository.getWf_name();
				switch (sourceName) {
				case DataRepository.WF_NAME_UNIXSHELL:
				case DataRepository.WF_NAME_GREENPLUMSQL:
				case DataRepository.WF_NAME_HIVEQL:
				case DataRepository.WF_NAME_MYSQLSQL:
				case DataRepository.WF_NAME_ORACLESQL:
					existModuleName = sourceName;
					break;
				}
				List<String> moduleIDs = nowDataRepository.getDownBranches();
				for (String moduleID : moduleIDs) {
					DataRepository moduleDataRepository = dataRepositories.get(moduleID);
					String moduleName = moduleDataRepository.getWf_name();

					if (operateModuleNameList.contains(moduleName)) {
						/* multiple operation software */
						if (existModuleName == null || existModuleName.trim().equals("")) {
							existModuleName = moduleName;
						} else {
							if (!existModuleName.equals(moduleName)) {
								workFlowError = true;
								workFlowErrorMsg = "Work flow does not allow multiple types of work, please combine them in transpose work flow!";
								LOG.error("Cause by:{}", workFlowErrorMsg);
								return false;
							}
						}
					} else if (allDataSourceModuleNameList.contains(moduleName)) {
						/* multiple Extract-Transform-Load */
						existModuleName = moduleName;
					}
				}
			}
			this.workFlowType = existModuleName;
		}
		return true;
	}

	private Map<String, DataRepository> addDownBranches(Map<String, DataRepository> dataRepositories) {
		for (Entry<String, List<String>> downBranchesEntry : downBranchesMap.entrySet()) {
			String key = downBranchesEntry.getKey();
			List<String> downBranches = downBranchesEntry.getValue();
			if (dataRepositories.containsKey(key)) {
				DataRepository dataRepository = dataRepositories.remove(key);
				dataRepository.setDownBranches(downBranches);
				dataRepositories.put(key, dataRepository);
			}
		}

		return dataRepositories;
	}

	private Map<String, DataRepository> dropUnlinkedElements(Map<String, DataRepository> dataRepositories) {
		this.digStartGroup(dataRepositories);
		this.digEndGroup(dataRepositories);
		startGroup.retainAll(endGroup);
		for (String id : startGroup) {
			if (!dataRepositories.containsKey(id)) {
				dataRepositories.remove(id);
			}
		}
		return dataRepositories;
	}

	private void digStartGroup(Map<String, DataRepository> dataRepositories) {
		for (int i = 0; i < dataRepositories.size(); i++) {
			for (Entry<String, DataRepository> dataRepositoryEntry : dataRepositories.entrySet()) {
				String key = dataRepositoryEntry.getKey();
				DataRepository dataRepository = dataRepositoryEntry.getValue();
				List<Component> components = dataRepository.getComponents();
				if (components != null && !components.isEmpty()) {
					for (Component component : components) {
						String souceID = component.getWf_sourceId();
						if (souceID != null) {
							if (startGroup.contains(souceID)) {
								if (!startGroup.contains(key)) {
									startGroup.add(key);
								}
								if (downBranchesMap.containsKey(souceID)) {
									List<String> branches = downBranchesMap.get(souceID);
									if (!branches.contains(key)) {
										branches.add(key);
										downBranchesMap.remove(souceID);
										downBranchesMap.put(souceID, branches);
									}
								} else {
									List<String> branches = new ArrayList<>();
									branches.add(key);
									downBranchesMap.put(souceID, branches);
								}
							} else {
								if (dataRepositories.containsKey(souceID)) {
									this.stackStartGroup(dataRepositories, souceID);
								}
							}
						}
					}
				}
			}
		}
	}

	private void stackStartGroup(Map<String, DataRepository> dataRepositories, String key) {
		DataRepository dataRepository = dataRepositories.get(key);
		List<Component> components = dataRepository.getComponents();
		if (components != null && !components.isEmpty()) {
			for (Component component : components) {
				String souceID = component.getWf_sourceId();
				if (souceID != null) {
					if (startGroup.contains(souceID)) {
						if (!startGroup.contains(key)) {
							startGroup.add(key);
						}
						if (downBranchesMap.containsKey(souceID)) {
							List<String> branches = downBranchesMap.remove(souceID);
							if (!branches.contains(key)) {
								branches.add(key);
								downBranchesMap.put(souceID, branches);
							}
						} else {
							List<String> branches = new ArrayList<>();
							branches.add(key);
							downBranchesMap.put(souceID, branches);
						}
					} else {
						if (dataRepositories.containsKey(souceID)) {
							this.stackStartGroup(dataRepositories, souceID);
						}
					}
				}
			}
		}
	}

	private void digEndGroup(Map<String, DataRepository> dataRepositories) {
		this.stackEndGroup(dataRepositories, endID);
	}

	private void stackEndGroup(Map<String, DataRepository> dataRepositories, String id) {
		List<Component> endComponents = dataRepositories.get(id).getComponents();
		if (endComponents != null) {
			for (Component endComponent : endComponents) {
				String sourceID0 = endComponent.getWf_sourceId();
				if (dataRepositories.containsKey(sourceID0)) {
					endGroup.add(sourceID0);
					this.stackEndGroup(dataRepositories, sourceID0);
				}
			}
		}
	}

	public boolean isWorkFlowError() {
		return workFlowError;
	}

	public String getWorkFlowErrorMsg() {
		return workFlowErrorMsg;
	}

	public String getWorkFlowType() {
		return workFlowType;
	}

	public String getStartID() {
		return startID;
	}
}
