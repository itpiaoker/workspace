package cn.com.carenet.scheduler.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.carenet.scheduler.constant.DataSourceConfigKey;
import cn.com.carenet.scheduler.constant.WebComponentsKeyConstant;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;

public class WebJSONLegality {
	final private static Logger LOG = LoggerFactory.getLogger(WebJSONLegality.class);
	final protected static String WEBELEMENT_MARK = "wf_";

	final private static List<String> operateModuleNameList = new ArrayList<>();
	final private static List<String> dataSourceModuleNameList = new ArrayList<>();
	final private static List<String> allDataSourceModuleNameList = new ArrayList<>();

	private String START_ID;
	private String END_ID;
	private Set<String> startGroupIds = new HashSet<>();
	private Set<String> endGroupIds = new HashSet<>();
	private Set<String> inputDataSourceIDs;
	private Set<String> outputDataSourceIDs;

	private String workFlowTypeName;
	private long timestampExpression;
	private String cronExpression;
	private boolean repeat;
	private String workFlowID;
	private boolean workFlowError = false;
	private String workFlowErrorMessage = "";
	private JSONObject validJson;
	private JSONObject datajsonObjects;

	static {
		operateModuleNameList.add(WebModuleNameConstant.storm);
		operateModuleNameList.add(WebModuleNameConstant.hadoop);
		operateModuleNameList.add(WebModuleNameConstant.unixShell);
		operateModuleNameList.add(WebModuleNameConstant.sparkCore);
		operateModuleNameList.add(WebModuleNameConstant.sparkStreaming);
		operateModuleNameList.add(WebModuleNameConstant.sparkSQL);
		operateModuleNameList.add(WebModuleNameConstant.sparkGraphx);
		operateModuleNameList.add(WebModuleNameConstant.greenPlumSQL);
		operateModuleNameList.add(WebModuleNameConstant.hiveQL);
		operateModuleNameList.add(WebModuleNameConstant.mysqlSQL);
		operateModuleNameList.add(WebModuleNameConstant.oracleSQL);
		operateModuleNameList.add(WebModuleNameConstant.sparkMLLib);
		operateModuleNameList.add(WebModuleNameConstant.LinerR);

		dataSourceModuleNameList.add(WebModuleNameConstant.hive);
		dataSourceModuleNameList.add(WebModuleNameConstant.ftp);
		dataSourceModuleNameList.add(WebModuleNameConstant.greenPlum);
		dataSourceModuleNameList.add(WebModuleNameConstant.mysql);
		dataSourceModuleNameList.add(WebModuleNameConstant.oracle);

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

	public WebJSONLegality(String json, String workFlowID) {
		this.workFlowID = workFlowID;
		// big map
		JSONObject jsonObjects = JSON.parseObject(json);
		/*
		 * now, find the elements which are not linked into the lines or only
		 * have half link (direct to start or end only).
		 */
		jsonObjects = dropUnlinkedElements(jsonObjects);
		if (this.START_ID == null) {
			workFlowError = true;
			workFlowErrorMessage = "Work flow does not have a [start], please specified a starter!";
			LOG.warn("ID:[{}] Cause by:{}", workFlowID, workFlowErrorMessage);
			return;
		} else if (this.END_ID == null) {
			workFlowError = true;
			workFlowErrorMessage = "Work flow does not have an [end], please specified an end!";
			LOG.warn("ID:[{}] Cause by:{}", workFlowID, workFlowErrorMessage);
			return;
		}
		jsonObjects = webMarkFilter(jsonObjects);
		datajsonObjects = jsonObjects;

	}

	public JSONObject getdataJsonObject() {
		return datajsonObjects;
	}

	public JSONObject getValidJson() {
		JSONObject jsonBuffer;
		jsonBuffer = markDataSourceType(datajsonObjects);

		if (!this.checkMultipleWorking(jsonBuffer)) {
			return null;
		}
		validJson = jsonBuffer;
		return this.validJson;
	}

	private boolean checkMultipleWorking(JSONObject jsonObjects) {
		Set<String> etlNameList = new HashSet<>();
		Set<String> operateModuleNameBuffer = new HashSet<>();

		for (Entry<String, Object> jsonEntry : jsonObjects.entrySet()) {
			String thisObjectID = jsonEntry.getKey();
			Object tempObj = jsonEntry.getValue();
			JSONObject subJSONObject = parseJSONObject(tempObj);
			String sourceName = subJSONObject.get(WebComponentsKeyConstant.sourceName).toString();
			/* multiple operation software */
			if (operateModuleNameList.contains(sourceName) && (!operateModuleNameBuffer.contains(sourceName))) {
				operateModuleNameBuffer.add(sourceName);
				if (operateModuleNameBuffer.size() > 1) {
					workFlowError = true;
					workFlowErrorMessage = "Forbidden: Work flow has mutiple porcessor software, please change it!";
					LOG.warn("ID:[{}] Cause by:{}", workFlowID, workFlowErrorMessage);
					return false;
				}
			}
			/* multiple Extract-Transform-Load */
			if (allDataSourceModuleNameList.contains(sourceName) && (!inputDataSourceIDs.contains(thisObjectID))) {
				JSONArray components = JSON.parseArray(subJSONObject.getString(WebComponentsKeyConstant.components));
				for (Object component : components) {
					String preOpID = this.parseJSONObject(component).getString(WebComponentsKeyConstant.preOpID);
					String upperSourceName = this.parseJSONObject(jsonObjects.get(preOpID))
							.getString(WebComponentsKeyConstant.sourceName);
					if (allDataSourceModuleNameList.contains(upperSourceName)) {
						if (dataSourceModuleNameList.contains(upperSourceName)) {
							if (!etlNameList.contains(upperSourceName))
								etlNameList.add(upperSourceName);
						} else if (dataSourceModuleNameList.contains(sourceName)) {
							if (!etlNameList.contains(sourceName))
								etlNameList.add(sourceName);
						}
					}
				}
			}
		}
		
		
		
		
		
		if (!operateModuleNameBuffer.isEmpty() && !etlNameList.isEmpty()) {
			workFlowError = true;
			workFlowErrorMessage = "Forbidden: Work flow has mutiple ETL process, please change it. Mutiple process can be only modified in transpose work flows.";
			LOG.warn("ID:[{}] Cause by:{}", workFlowID, workFlowErrorMessage);
			return false;
		}
		if (!operateModuleNameBuffer.isEmpty()) {
			for (String str : operateModuleNameBuffer) {
				workFlowTypeName = str;
				break;
			}
		} else if (!etlNameList.isEmpty()) {
			for (String str : etlNameList) {
				workFlowTypeName = str;
				break;
			}
		} else {
			workFlowError = true;
			workFlowErrorMessage = "This is an empty work flow , please change it!";
			LOG.warn("ID:[{}] Cause by:{}", workFlowID, workFlowErrorMessage);
			return false;
		}

		return true;
	}

	private JSONObject markDataSourceType(JSONObject jsonObjects) {
		inputDataSourceIDs = new HashSet<>();
		outputDataSourceIDs = new HashSet<>();
		/* mark output data source */
		JSONObject jsonObject = this.parseJSONObject(jsonObjects.get(END_ID));
		JSONArray outputComponents = JSON.parseArray(jsonObject.getString(WebComponentsKeyConstant.components));
		for (Object component : outputComponents) {
			outputDataSourceIDs.add(this.parseJSONObject(component).getString(WebComponentsKeyConstant.preOpID));
		}
		for (String outputDataSourceID : outputDataSourceIDs) {
			JSONObject subJsonObject = this.parseJSONObject(jsonObjects.remove(outputDataSourceID));
			subJsonObject.put(DataSourceConfigKey.SOURCE_TYPE, 1);
			jsonObjects.put(outputDataSourceID, subJsonObject);
		}

		/* mark input data source */
		for (Entry<String, Object> jsonEntry : jsonObjects.entrySet()) {
			String thisID = jsonEntry.getKey();
			if (thisID != START_ID) {
				JSONArray inputComponents = JSON.parseArray(
						parseJSONObject(jsonEntry.getValue()).getString(WebComponentsKeyConstant.components));
				for (Object component : inputComponents) {
					String preOpID = this.parseJSONObject(component).getString(WebComponentsKeyConstant.preOpID);
					if (preOpID.equals(START_ID)) {
						inputDataSourceIDs.add(thisID);
						break;
					}
				}
			}
		}
		for (String inputDataSourceID : inputDataSourceIDs) {
			JSONObject subJsonObject = this.parseJSONObject(jsonObjects.remove(inputDataSourceID));
			subJsonObject.put(DataSourceConfigKey.SOURCE_TYPE, 0);
			jsonObjects.put(inputDataSourceID, subJsonObject);
		}
		return jsonObjects;
	}

	private JSONObject webMarkFilter(JSONObject jsonObjects) {
		JSONObject newJSONObject = new JSONObject();
		for (Entry<String, Object> jsonEntry : jsonObjects.entrySet()) {
			Object tempObj = jsonEntry.getValue();
			JSONObject subJsonObjects = subWebMarkFilter(parseJSONObject(tempObj));
			newJSONObject.put(jsonEntry.getKey(), subJsonObjects);
		}
		return newJSONObject;
	}

	/**
	 * remove wf_ title except wf_name
	 * 
	 * @param jsonObject
	 * @return
	 */
	private JSONObject subWebMarkFilter(JSONObject jsonObject) {
		JSONObject newJSONObject = new JSONObject();
		for (Entry<String, Object> jsonEntry : jsonObject.entrySet()) {
			if (jsonEntry.getKey().equals(WebComponentsKeyConstant.sourceName)) {
				newJSONObject.put(jsonEntry.getKey(), jsonEntry.getValue());
			} else if (jsonEntry.getKey().equals(WebComponentsKeyConstant.components)) {
				JSONArray jsonArray = JSON.parseArray(jsonEntry.getValue().toString());
				JSONArray newJSONArray = new JSONArray();
				for (Object obj : jsonArray) {
					JSONObject newjsonMap = new JSONObject();
					JSONObject jsonMap = parseJSONObject(obj);
					for (Entry<String, Object> jsonMapEntry : jsonMap.entrySet()) {
						if (jsonMapEntry.getKey().equals(WebComponentsKeyConstant.preOpID)) {
							newjsonMap.put(WebComponentsKeyConstant.preOpID, jsonMapEntry.getValue());
						} else if (jsonMapEntry.getKey().indexOf(WEBELEMENT_MARK) != 0) {
							newjsonMap.put(jsonMapEntry.getKey(), jsonMapEntry.getValue());
						}
					}
					if (newjsonMap.size() > 0)
						newJSONArray.add(newjsonMap);
				}
				newJSONObject.put(jsonEntry.getKey(), newJSONArray);
			} else if (jsonEntry.getKey().indexOf(WEBELEMENT_MARK) != 0) {
				newJSONObject.put(jsonEntry.getKey(), jsonEntry.getValue());
			}
		}
		return newJSONObject;
	}

	protected JSONObject dropUnlinkedElements(JSONObject jsonObjects) {
		startGroupIds = new HashSet<>();
		endGroupIds = new HashSet<>();
		for (Entry<String, Object> jsonEntry : jsonObjects.entrySet()) {
			// get one of the module
			JSONObject subJSONObject = parseJSONObject(jsonEntry.getValue());
			String groupIDNow = jsonEntry.getKey();
			if (subJSONObject.getString(WebComponentsKeyConstant.sourceName).equals(WebModuleNameConstant.start)) {
				startGroupIds.add(groupIDNow);
				START_ID = groupIDNow;
				// get the time
				WorkFlowTimeControl wftc = WorkFlowTimeControl.newInstance(subJSONObject);
				cronExpression = wftc.getCronExpression();
				timestampExpression = wftc.getTimestampExpression();
				repeat = wftc.isRepeat();
			} else if (subJSONObject.getString(WebComponentsKeyConstant.sourceName).equals(WebModuleNameConstant.end)) {
				endGroupIds.add(groupIDNow);
				// starting to get all the elements from end line.
				digEndMark(jsonObjects, groupIDNow);
				END_ID = groupIDNow;
			}
			// get out the components
			digStartMarks(jsonObjects, subJSONObject, groupIDNow);
		}
		/*
		 * do the dig start mark again to make sure that some elements are not
		 * installed in start group
		 */
		for (Entry<String, Object> jsonEntry : jsonObjects.entrySet()) {
			// get one of the module
			JSONObject subJSONObject = parseJSONObject(jsonEntry.getValue());
			String groupIDNow = jsonEntry.getKey();
			// get out the components
			digStartMarks(jsonObjects, subJSONObject, groupIDNow);
		}
		/*
		 * now, we have two collections: elements from start and elements from
		 * end. drop the elements that are only exist in one collection or none
		 * collection.
		 */
		if (END_ID != null)
			startGroupIds.add(END_ID);
		startGroupIds.retainAll(endGroupIds);
		JSONObject newJSONObject = new JSONObject();
		for (String str : startGroupIds) {
			newJSONObject.put(str, jsonObjects.get(str));
		}
		return newJSONObject;
	}

	private void digStartMarks(JSONObject jsonObjects, JSONObject subJSONObject, String groupIDNow) {
		String componentsElement = subJSONObject.getString(WebComponentsKeyConstant.components);
		if (componentsElement != null) {
			if (!componentsElement.trim().equals("")) {
				/*
				 * if the compoents is not empty, go on to find the pre
				 * operation id
				 */
				JSONArray relationMapArray = JSON.parseArray(componentsElement);
				if (relationMapArray != null)
					for (Object obj : relationMapArray) {
						String sourceId = parseJSONObject(obj).getString(WebComponentsKeyConstant.preOpID);
						if (startGroupIds.contains(sourceId)) {
							startGroupIds.add(groupIDNow);
						}
						/*
						 * if the element is not an end, go on dig the elements
						 * linked from start for once. every element come to
						 * this step will check all the points for once, we are
						 * sure this won't left any element comes from start.
						 */
						digStartMark(jsonObjects);
					}
			}
		}
	}

	/**
	 * check all the elements, and find some of them are linked to the start
	 * 
	 * @param jsonObjects
	 */
	private void digStartMark(JSONObject jsonObjects) {
		for (Entry<String, Object> jsonEntry : jsonObjects.entrySet()) {
			JSONObject subJSONObject = parseJSONObject(jsonEntry.getValue());
			// get out the components
			String componentsElement = subJSONObject.getString(WebComponentsKeyConstant.components);
			if (componentsElement != null) {
				if (!componentsElement.trim().equals("")) {
					JSONArray relationMapArray = JSON.parseArray(componentsElement);
					if (relationMapArray != null)
						for (Object obj : relationMapArray) {
							String sourceId = parseJSONObject(obj).getString(WebComponentsKeyConstant.preOpID);
							String groupIDNow = jsonEntry.getKey();
							if (startGroupIds.contains(sourceId) && (!startGroupIds.contains(groupIDNow))) {
								startGroupIds.add(groupIDNow);
							}
						}
				}
			}
		}
	}

	/**
	 * dig all the IDs linked in the line of end.
	 * 
	 * @param jsonObjects
	 * @param thisId
	 */
	private void digEndMark(JSONObject jsonObjects, String thisId) {
		// use this id to get the element cuz. this id comes from end.
		JSONObject subJSONObject = parseJSONObject(jsonObjects.get(thisId));
		if (subJSONObject != null)
			if (subJSONObject.containsKey(WebComponentsKeyConstant.components)) {
				// get out the components
				String componentsElement = subJSONObject.getString(WebComponentsKeyConstant.components);
				if (componentsElement != null) {
					if (!componentsElement.trim().equals("")) {
						JSONArray relationMapArray = JSON.parseArray(componentsElement);
						if (relationMapArray.size() > 0) {
							for (Object obj : relationMapArray) {
								String sourceId = parseJSONObject(obj).getString(WebComponentsKeyConstant.preOpID);
								if (!endGroupIds.contains(sourceId)) {
									endGroupIds.add(sourceId);
									digEndMark(jsonObjects, sourceId);
								}
							}
						}
					}
				}
			}
	}

	private JSONObject parseJSONObject(Object obj) {
		if (obj instanceof JSONObject) {
			return (JSONObject) obj;
		}
		return (JSONObject) JSON.toJSON(obj);
	}

	/**
	 * getValidJson() must be called firstly
	 * 
	 * @return
	 */
	public String getWorkFlowTypeName() {
		return workFlowTypeName;
	}

	public long getTimestampExpression() {
		return timestampExpression;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public String getWorkFlowID() {
		return workFlowID;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public boolean isWorkFlowError() {
		return workFlowError;
	}

	public String getWorkFlowErrorMessage() {
		return workFlowErrorMessage;
	}

	public void setWorkFlowErrorMessage(String workFlowErrorMessage) {
		this.workFlowErrorMessage = workFlowErrorMessage;
	}
}
