package cn.com.carenet.scheduler.json;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.com.carenet.scheduler.bean.DataBaseSQLBean;
import cn.com.carenet.scheduler.bean.DataSchema;
import cn.com.carenet.scheduler.bean.DataSourceOptionsBean;
import cn.com.carenet.scheduler.bean.OperateComponent;
import cn.com.carenet.scheduler.bean.OperateOptionsBean;
import cn.com.carenet.scheduler.bean.SingleMetaData;
import cn.com.carenet.scheduler.constant.AlgorithmConstant;
import cn.com.carenet.scheduler.constant.DataSourceConfigKey;
import cn.com.carenet.scheduler.constant.WebComponentsKeyConstant;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.constant.WebOperateConfigKeyConstant;

@Deprecated
public class WebJSONSpliter {
	final private static Logger LOG = LoggerFactory.getLogger(WebJSONSpliter.class);
	final private static List<String> allOperateModuleNameList = new ArrayList<>();
	final private static List<String> allDataSourceModuleNameList = new ArrayList<>();
	final private static List<String> commonOperateModuleNameList = new ArrayList<>();
	final private static List<String> sqlOperateModuleNameList = new ArrayList<>();
	final private static List<String> dataSourceModuleNameList = new ArrayList<>();
	final private static List<String> allOperationsBeanList = new ArrayList<>();
	final private static List<String> metaOperationsBeanList = new ArrayList<>();
	private String workFlowID;
	private String workFlowTypeName;
	private Map<String, OperateOptionsBean> operateMap;
	private Map<String, DataSourceOptionsBean> dataSourceMap;
	private Map<String, DataSchema> dataSchemas;
	private DataBaseSQLBean dataBaseSQLBean;
	private String workFlowErrorMessage;
	private boolean workFlowError = false;
	private long timestampExpression;
	private String cronExpression;
	private boolean repeat;
	private List<String> sourcePrimaries;
	private List<String> outputIDs;
	private Set<String> kafkaTopics = new HashSet<>();
	static {
		allOperateModuleNameList.add(WebModuleNameConstant.storm);
		allOperateModuleNameList.add(WebModuleNameConstant.hadoop);
		allOperateModuleNameList.add(WebModuleNameConstant.unixShell);
		allOperateModuleNameList.add(WebModuleNameConstant.sparkCore);
		allOperateModuleNameList.add(WebModuleNameConstant.sparkStreaming);
		allOperateModuleNameList.add(WebModuleNameConstant.sparkSQL);
		allOperateModuleNameList.add(WebModuleNameConstant.sparkGraphx);
		allOperateModuleNameList.add(WebModuleNameConstant.greenPlumSQL);
		allOperateModuleNameList.add(WebModuleNameConstant.hiveQL);
		allOperateModuleNameList.add(WebModuleNameConstant.mysqlSQL);
		allOperateModuleNameList.add(WebModuleNameConstant.oracleSQL);
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
		allDataSourceModuleNameList.add(WebModuleNameConstant.elasticSearch);
		commonOperateModuleNameList.add(WebModuleNameConstant.storm);
		commonOperateModuleNameList.add(WebModuleNameConstant.hadoop);
		commonOperateModuleNameList.add(WebModuleNameConstant.sparkCore);
		commonOperateModuleNameList.add(WebModuleNameConstant.sparkStreaming);
		commonOperateModuleNameList.add(WebModuleNameConstant.sparkGraphx);
		commonOperateModuleNameList.add(WebModuleNameConstant.LinerR);
		
		sqlOperateModuleNameList.add(WebModuleNameConstant.unixShell);
		sqlOperateModuleNameList.add(WebModuleNameConstant.sparkSQL);
		sqlOperateModuleNameList.add(WebModuleNameConstant.greenPlumSQL);
		sqlOperateModuleNameList.add(WebModuleNameConstant.hiveQL);
		sqlOperateModuleNameList.add(WebModuleNameConstant.mysqlSQL);
		sqlOperateModuleNameList.add(WebModuleNameConstant.oracleSQL);
		dataSourceModuleNameList.add(WebModuleNameConstant.hive);
		dataSourceModuleNameList.add(WebModuleNameConstant.ftp);
		dataSourceModuleNameList.add(WebModuleNameConstant.greenPlum);
		dataSourceModuleNameList.add(WebModuleNameConstant.mysql);
		dataSourceModuleNameList.add(WebModuleNameConstant.oracle);
		allOperationsBeanList.add(WebModuleNameConstant.arithmetic);
		allOperationsBeanList.add(WebModuleNameConstant.count);
		allOperationsBeanList.add(WebModuleNameConstant.distinct);
		allOperationsBeanList.add(WebModuleNameConstant.filter);
		allOperationsBeanList.add(WebModuleNameConstant.groupBy);
		allOperationsBeanList.add(WebModuleNameConstant.join);
		allOperationsBeanList.add(WebModuleNameConstant.removeField);
		allOperationsBeanList.add(WebModuleNameConstant.sortBy);
		allOperationsBeanList.add(WebModuleNameConstant.sum);
		allOperationsBeanList.add(WebModuleNameConstant.LinerR);
		metaOperationsBeanList.add(WebModuleNameConstant.join);
		metaOperationsBeanList.add(WebModuleNameConstant.sum);
		metaOperationsBeanList.add(WebModuleNameConstant.groupBy);
		metaOperationsBeanList.add(WebModuleNameConstant.removeField);
		metaOperationsBeanList.add(WebModuleNameConstant.arithmetic);
	}
	public WebJSONSpliter(String workFlowID) {
		this.workFlowID = workFlowID;
	}
	public void jsonToBean(String jsonStr) {
		WebJSONLegality wJsonLegality = new WebJSONLegality(jsonStr, workFlowID);
		if (wJsonLegality.isWorkFlowError()) {
			workFlowErrorMessage = wJsonLegality.getWorkFlowErrorMessage();
			workFlowError = true;
			return;
		}
		cronExpression = wJsonLegality.getCronExpression();
		timestampExpression = wJsonLegality.getTimestampExpression();
		repeat = wJsonLegality.isRepeat();
		JSONObject jSONObjects = wJsonLegality.getValidJson();
		workFlowTypeName = wJsonLegality.getWorkFlowTypeName();
		if (jSONObjects == null) {
			return;
		}
		if (commonOperateModuleNameList.contains(workFlowTypeName)) {
			jSONObjects = commonOperateModuleIDtransfer(jSONObjects);
		}
		digSourcePrimaryIDs(jSONObjects);
		dataSourceMap = new HashMap<>();
		getDataSourceOptions(jSONObjects);
		if (commonOperateModuleNameList.contains(workFlowTypeName)) {
			/* operate options can be used in this type only */
			operateMap = new HashMap<>();
			getOperateOptions(jSONObjects);
		}
		markSourcePrimary();
		setDataSchema();
		/* if the work flow rely on SQL like operation */
		if (sqlOperateModuleNameList.contains(workFlowTypeName)) {
			setSQLcommand(jSONObjects);
		}
		LOG.info(workFlowTypeName);
		LOG.debug("Data Source:{}", JSON.toJSONString(dataSourceMap));
		LOG.debug("Operates:{}", JSON.toJSONString(operateMap));
		LOG.debug("SQL Bean:{}", JSON.toJSONString(dataBaseSQLBean));
		LOG.debug("Data Schema: {}", JSON.toJSONString(dataSchemas));
	}
	public String getOptionMap() {
		JSONObject optionMap = new JSONObject();
		for (Entry<String, DataSourceOptionsBean> datasourceEntry : dataSourceMap.entrySet()) {
			String id = datasourceEntry.getKey();
			DataSourceOptionsBean dataSourceOptionsBean = datasourceEntry.getValue();
			String typeName = dataSourceOptionsBean.getSourceName();
			optionMap.put(id + typeName, typeName);
			optionMap.put(id, typeName + "_Buffer_Bolt");
		}
		for (Entry<String, OperateOptionsBean> operateEntry : operateMap.entrySet()) {
			String id = operateEntry.getKey();
			OperateOptionsBean operateOptionsBean = operateEntry.getValue();
			String typeName = operateOptionsBean.getOperateType();
			optionMap.put(id, typeName);
		}
		return optionMap.toJSONString();
	}
	private void setDataSchema() {
		dataSchemas = new HashMap<>();
		while (true) {
			for (String sourceID : sourcePrimaries) {
				DataSchema dataSchema = new DataSchema();
				if (dataSourceMap.containsKey(sourceID)) {
					DataSourceOptionsBean dataSourceOptionsBean = dataSourceMap.get(sourceID);
					dataSchema.setDatasouceDelimiter(dataSourceOptionsBean.getDatasouceDelimiter());
					dataSchema.setMetaDatas(dataSourceOptionsBean.getMetaDatas());
					dataSchema.setRecordDelimiter(dataSourceOptionsBean.getRecordDelimiter());
					dataSchema.setTableName(dataSourceOptionsBean.getTableName());
					dataSchema.setTimeFormat(dataSourceOptionsBean.getTimeFormat());
					List<SingleMetaData> newMetaDatas = new ArrayList<>();
					List<SingleMetaData> metaDatas = dataSourceOptionsBean.getMetaDatas();
					if(metaDatas != null){
						for (SingleMetaData singleMeta : metaDatas) {
							SingleMetaData newMeta = new SingleMetaData();
							newMeta.setColNum(singleMeta.getColNum());
							newMeta.setDateFormat(singleMeta.getDateFormat());
							newMeta.setFieldName(singleMeta.getFieldName());
							newMeta.setFieldType(singleMeta.getFieldType());
							newMetaDatas.add(newMeta);
						}
						dataSchema.setMetaDatas(newMetaDatas);
					}
					dataSchemas.put(sourceID, dataSchema);
				} else {
					OperateOptionsBean operateOptionsBean = operateMap.get(sourceID);
					String operateType = operateOptionsBean.getOperateType();
					String sourcePrimeID = "";
					switch (operateType) {
					case WebModuleNameConstant.groupBy:
						SingleMetaData metaData = operateOptionsBean.getMetaDatas().get(0);
						sourcePrimeID = operateOptionsBean.getComponents().get(0).getSourcePrimary();
						if (dataSourceMap.containsKey(sourcePrimeID)) {
							DataSourceOptionsBean dataSourceOptionsBean = dataSourceMap.get(sourcePrimeID);
							dataSchema.setDatasouceDelimiter(dataSourceOptionsBean.getDatasouceDelimiter());
							dataSchema.setRecordDelimiter(dataSourceOptionsBean.getRecordDelimiter());
							dataSchema.setTimeFormat(dataSourceOptionsBean.getTimeFormat());
							dataSchema.setTableName(operateOptionsBean.getTableName());
							List<SingleMetaData> newMetaDatas = new ArrayList<>();
							List<SingleMetaData> metaDatas = dataSourceOptionsBean.getMetaDatas();
							for (SingleMetaData singleMeta : metaDatas) {
								SingleMetaData newMeta = new SingleMetaData();
								newMeta.setColNum(singleMeta.getColNum());
								newMeta.setDateFormat(singleMeta.getDateFormat());
								newMeta.setFieldName(singleMeta.getFieldName());
								newMeta.setFieldType(singleMeta.getFieldType());
								if (singleMeta.getDateFormat() != null) {
									newMeta.setDateFormat(singleMeta.getDateFormat());
								}
								if (singleMeta.getFieldFilter() > 0) {
									newMeta.setFieldFilter(singleMeta.getFieldFilter());
									newMeta.setFieldFilterVal(singleMeta.getFieldFilterVal());
								} else {
									newMeta.setFieldFilter(singleMeta.getFieldFilter());
								}
								newMetaDatas.add(newMeta);
							}
							metaData.setColNum(metaDatas.size());
							newMetaDatas.add(metaData);
							dataSchema.setMetaDatas(newMetaDatas);
							dataSchemas.put(sourceID, dataSchema);
						} else if (dataSchemas.containsKey(sourcePrimeID)) {
							DataSchema dataSchemaBuffer = dataSchemas.get(sourcePrimeID);
							dataSchema.setDatasouceDelimiter(dataSchemaBuffer.getDatasouceDelimiter());
							dataSchema.setRecordDelimiter(dataSchemaBuffer.getRecordDelimiter());
							dataSchema.setTableName(operateOptionsBean.getTableName());
							List<SingleMetaData> newMetaDatas = new ArrayList<>();
							List<SingleMetaData> metaDatas = dataSchemaBuffer.getMetaDatas();
							for (SingleMetaData singleMeta : metaDatas) {
								SingleMetaData newMeta = new SingleMetaData();
								newMeta.setColNum(singleMeta.getColNum());
								newMeta.setDateFormat(singleMeta.getDateFormat());
								newMeta.setFieldName(singleMeta.getFieldName());
								newMeta.setFieldType(singleMeta.getFieldType());
								if (singleMeta.getDateFormat() != null) {
									newMeta.setDateFormat(singleMeta.getDateFormat());
								}
								if (singleMeta.getFieldFilter() > 0) {
									newMeta.setFieldFilter(singleMeta.getFieldFilter());
									newMeta.setFieldFilterVal(singleMeta.getFieldFilterVal());
								} else {
									newMeta.setFieldFilter(singleMeta.getFieldFilter());
								}
								newMetaDatas.add(newMeta);
							}
							metaData.setColNum(metaDatas.size());
							newMetaDatas.add(metaData);
							dataSchema.setMetaDatas(newMetaDatas);
							dataSchemas.put(sourceID, dataSchema);
						}
						break;
					case WebModuleNameConstant.removeField:
						sourcePrimeID = operateOptionsBean.getComponents().get(0).getSourcePrimary();
						if (dataSourceMap.containsKey(sourcePrimeID)) {
							DataSourceOptionsBean dataSourceOptionsBean = dataSourceMap.get(sourcePrimeID);
							dataSchema.setDatasouceDelimiter(dataSourceOptionsBean.getDatasouceDelimiter());
							dataSchema.setRecordDelimiter(dataSourceOptionsBean.getRecordDelimiter());
							dataSchema.setTimeFormat(dataSourceOptionsBean.getTimeFormat());
							dataSchema.setTableName(operateOptionsBean.getTableName());
							String[] fieldNames = StringUtils
									.splitPreserveAllTokens(operateOptionsBean.getFieldNames());
							Map<String, Integer> fieldTmpMap = new HashMap<>();
							List<SingleMetaData> metaDatas = dataSourceOptionsBean.getMetaDatas();
							for (SingleMetaData tmpMetaData : metaDatas) {
								fieldTmpMap.put(tmpMetaData.getFieldName(), tmpMetaData.getColNum());
							}
							List<Integer> removeFieldNum = new ArrayList<>();
							for (String str : fieldNames) {
								removeFieldNum.add(fieldTmpMap.get(StringUtils.splitPreserveAllTokens(str, ".", 2)[1]));
							}
						} else if (dataSchemas.containsKey(sourcePrimeID)) {
							DataSchema dataSchemaBuffer = dataSchemas.get(sourcePrimeID);
							dataSchema.setDatasouceDelimiter(dataSchemaBuffer.getDatasouceDelimiter());
							dataSchema.setRecordDelimiter(dataSchemaBuffer.getRecordDelimiter());
							dataSchema.setTimeFormat(dataSchemaBuffer.getTimeFormat());
							dataSchema.setTableName(operateOptionsBean.getTableName());
							String[] fieldNames = StringUtils
									.splitPreserveAllTokens(operateOptionsBean.getFieldNames());
							Map<String, Integer> fieldTmpMap = new HashMap<>();
							Map<Integer, String> fieldTmpMap2 = new HashMap<>();
							List<SingleMetaData> metaDatas = dataSchemaBuffer.getMetaDatas();
							for (SingleMetaData tmpMetaData : metaDatas) {
								fieldTmpMap.put(tmpMetaData.getFieldName(), tmpMetaData.getColNum());
								fieldTmpMap2.put(tmpMetaData.getColNum(), tmpMetaData.getFieldName());
							}
							List<Integer> removeFieldNum = new ArrayList<>();
							for (String str : fieldNames) {
								removeFieldNum.add(fieldTmpMap.get(StringUtils.splitPreserveAllTokens(str, ".", 2)[1]));
							}
							List<String> newFieldArray = new ArrayList<>();
							for (int i = 0; i < fieldTmpMap.size(); i++) {
								newFieldArray.add(fieldTmpMap2.get(i));
							}
							for (Entry<String, Integer> fieldTmpEntry : fieldTmpMap.entrySet()) {
								if (removeFieldNum.contains(fieldTmpEntry.getValue())) {
									newFieldArray.remove(fieldTmpEntry.getKey());
								}
							}
							List<SingleMetaData> newMetaDatas = new ArrayList<>();
							for (SingleMetaData singleMeta : metaDatas) {
								if (!removeFieldNum.contains(singleMeta.getColNum())) {
									SingleMetaData newMeta = new SingleMetaData();
									newMeta.setDateFormat(singleMeta.getDateFormat());
									newMeta.setFieldName(singleMeta.getFieldName());
									newMeta.setFieldType(singleMeta.getFieldType());
									newMeta.setColNum(newFieldArray.indexOf(singleMeta.getFieldName()));
									if (singleMeta.getDateFormat() != null) {
										newMeta.setDateFormat(singleMeta.getDateFormat());
									}
									if (singleMeta.getFieldFilter() > 0) {
										newMeta.setFieldFilter(singleMeta.getFieldFilter());
										newMeta.setFieldFilterVal(singleMeta.getFieldFilterVal());
									} else {
										newMeta.setFieldFilter(singleMeta.getFieldFilter());
									}
									newMetaDatas.add(newMeta);
								}
							}
							dataSchema.setMetaDatas(newMetaDatas);
							dataSchemas.put(sourceID, dataSchema);
						}
						break;
					default:
						dataSchema.setDatasouceDelimiter(operateOptionsBean.getDatasouceDelimiter());
						dataSchema.setRecordDelimiter(operateOptionsBean.getRecordDelimiter());
						dataSchema.setTableName(operateOptionsBean.getTableName());
						dataSchema.setTimeFormat(operateOptionsBean.getTimeFormat());
						List<SingleMetaData> newMetaDatas = new ArrayList<>();
						List<SingleMetaData> metaDatas = operateOptionsBean.getMetaDatas();
						for (SingleMetaData singleMeta : metaDatas) {
							SingleMetaData newMeta = new SingleMetaData();
							newMeta.setColNum(singleMeta.getColNum());
							newMeta.setDateFormat(singleMeta.getDateFormat());
							newMeta.setFieldName(singleMeta.getFieldName());
							newMeta.setFieldType(singleMeta.getFieldType());
							if (singleMeta.getDateFormat() != null) {
								newMeta.setDateFormat(singleMeta.getDateFormat());
							}
							if (singleMeta.getFieldFilter() > 0) {
								newMeta.setFieldFilter(singleMeta.getFieldFilter());
								newMeta.setFieldFilterVal(singleMeta.getFieldFilterVal());
							} else {
								newMeta.setFieldFilter(singleMeta.getFieldFilter());
							}
							newMetaDatas.add(newMeta);
						}
						dataSchema.setMetaDatas(newMetaDatas);
						dataSchemas.put(sourceID, dataSchema);
					}
				}
			}
			boolean jumpMark = true;
			for (String sourceID : sourcePrimaries) {
				if (!dataSchemas.containsKey(sourceID)) {
					jumpMark = false;
					break;
				}
			}
			if (jumpMark) {
				break;
			}
		}
	}
	
	private void setSQLcommand(JSONObject jSONObjects) {
		dataBaseSQLBean = new DataBaseSQLBean();
		for (Entry<String, Object> jSONEntry : jSONObjects.entrySet()) {
			String thisID = jSONEntry.getKey();
			JSONObject jsonObject = JSON.parseObject(jSONEntry.getValue().toString());
			String thisSourceName = jsonObject.getString(WebComponentsKeyConstant.sourceName);
			String sqls;
			if (sqlOperateModuleNameList.contains(thisSourceName)) {
				switch (thisSourceName) {
				case WebModuleNameConstant.sparkSQL:
					sqls = jsonObject.getString(WebOperateConfigKeyConstant.SQL);
					break;
				case WebModuleNameConstant.greenPlumSQL:
					sqls = jsonObject.getString(WebOperateConfigKeyConstant.gpSQLs);
					break;
				case WebModuleNameConstant.unixShell:
					sqls = jsonObject.getString(WebOperateConfigKeyConstant.commands);
					break;
				default:
					sqls = jsonObject.getString(WebOperateConfigKeyConstant.SQLs);
				}
				dataBaseSQLBean.setWorkFlowID(workFlowID);
				dataBaseSQLBean.setSourceTabID(thisID);
				dataBaseSQLBean.setScript(sqls);
				String jdbcUrl = jsonObject.getString(DataSourceConfigKey.JDBC_URL);
				String jdbcDB = jsonObject.getString(DataSourceConfigKey.JDBC_DB);
				String jdbcUser = jsonObject.getString(DataSourceConfigKey.JDBC_USER);
				String jdbcPasswd = jsonObject.getString(DataSourceConfigKey.JDBC_PASSWD);
				if (jdbcUrl != null)
					dataBaseSQLBean.setIp(jdbcUrl);
				if (jdbcDB != null)
					dataBaseSQLBean.setDataBaseName(jdbcDB);
				if (jdbcUser != null)
					dataBaseSQLBean.setUserName(jdbcUser);
				if (jdbcPasswd != null)
					dataBaseSQLBean.setPassword(jdbcPasswd);
				break;
			}
		}
	}
	private void markSourcePrimary() {
		for (String outputID : outputIDs) {
			DataSourceOptionsBean dataSourceOptionsBean = dataSourceMap.get(outputID);
			List<OperateComponent> components = dataSourceOptionsBean.getComponents();
			for (OperateComponent upperComponent : components)
				upperComponent.setSourcePrimary(digOutSourcePrimary(upperComponent));
		}
		if (operateMap != null && !operateMap.isEmpty()) {
			for (Entry<String, OperateOptionsBean> operateEntry : operateMap.entrySet()) {
				for (OperateComponent upperComponent : operateEntry.getValue().getComponents()) {
					String primaryID = digOutSourcePrimary(upperComponent);
					upperComponent.setSourcePrimary(primaryID);
				}
			}
		}
	}
	private String digOutSourcePrimary(OperateComponent component) {
		String sourcePrimaryID = null;
		String preOpID = component.getPreOpID();
		if (dataSourceMap.containsKey(preOpID)) {
			if (dataSourceMap.get(preOpID).getSourceType() == 0) {
				return preOpID;
			} else {
				List<OperateComponent> preComponents = dataSourceMap.get(preOpID).getComponents();
				for (OperateComponent upperComponent : preComponents) {
					return digOutSourcePrimary(upperComponent);
				}
			}
		} else if (operateMap != null && operateMap.containsKey(preOpID)) {
			String operateType = operateMap.get(preOpID).getOperateType();
			if (metaOperationsBeanList.contains(operateType)) {
				return preOpID;
			} else {
				List<OperateComponent> preComponents = operateMap.get(preOpID).getComponents();
				for (OperateComponent upperComponent : preComponents) {
					return digOutSourcePrimary(upperComponent);
				}
			}
		}
		return sourcePrimaryID;
	}
	private void digSourcePrimaryIDs(JSONObject jSONObjects) {
		sourcePrimaries = new ArrayList<>();
		outputIDs = new ArrayList<>();
		for (Entry<String, Object> jSONEntry : jSONObjects.entrySet()) {
			String thisID = jSONEntry.getKey();
			JSONObject subJSONObject = JSON.parseObject(jSONEntry.getValue().toString());
			String sourceName = subJSONObject.getString(WebComponentsKeyConstant.sourceName);
			if (metaOperationsBeanList.contains(sourceName)) {
				sourcePrimaries.add(thisID);
			} else if (allDataSourceModuleNameList.contains(sourceName)) {
				String sourceType = subJSONObject.getString(DataSourceConfigKey.SOURCE_TYPE);
				if (sourceType.equals("0")) {
					sourcePrimaries.add(thisID);
				} else {
					outputIDs.add(thisID);
				}
			}
		}
	}
	private void getDataSourceOptions(JSONObject jSONObjects) {
		for (Entry<String, Object> jSONEntry : jSONObjects.entrySet()) {
			String thisID = jSONEntry.getKey();
			JSONObject subJSONObject = JSON.parseObject(jSONEntry.getValue().toString());
			String sourceName = subJSONObject.getString(WebComponentsKeyConstant.sourceName);
			if (allDataSourceModuleNameList.contains(sourceName)) {
				DataSourceOptionsBean dataSourceOptionsBean = new DataSourceOptionsBean();
				dataSourceOptionsBean.setWorkFlowID(workFlowID);
				dataSourceOptionsBean.setDatasourceID(thisID);
				dataSourceOptionsBean.setSourceName(sourceName);
				for (Entry<String, Object> optionEntry : subJSONObject.entrySet()) {
					String optionName = optionEntry.getKey();
					String optionValue = optionEntry.getValue().toString();
					if (optionValue != null && !optionValue.trim().equals("")) {
						int optionNum = 0;
						if (StringUtils.isNumeric(optionValue)) {
							optionNum = Integer.parseInt(optionValue);
						}
						switch (optionName) {
						case DataSourceConfigKey.META_DATA_FIELD_NAMES:
							if (optionValue.indexOf('[') > -1) {
								JSONArray fieldNameStr = JSON.parseArray(optionValue);
								JSONArray fieldTypeStr = JSON
										.parseArray(subJSONObject.getString(DataSourceConfigKey.META_DATA_FIELD_TYPES));
								JSONArray fieldFilterStr = JSON.parseArray(
										subJSONObject.getString(DataSourceConfigKey.META_DATA_FIELD_FILTER));
								JSONArray fieldFilterValStr = JSON.parseArray(
										subJSONObject.getString(DataSourceConfigKey.META_DATA_FIELD_FILTER_VAL));
								String timeFormat = subJSONObject.getString(DataSourceConfigKey.META_DATA_FIELD_TIME_FORMAT);
								JSONArray timeFormatStr = null;
								if (timeFormat != null && !timeFormat.trim().equals("")) {
									timeFormatStr = JSON.parseArray(timeFormat);
								}
								List<SingleMetaData> dataSourceInfos = new ArrayList<>();
								if (fieldNameStr.size() > 0)
									for (int i = 0; i < fieldNameStr.size(); i++) {
										SingleMetaData operateOptionsDataSourceInfo = new SingleMetaData();
										operateOptionsDataSourceInfo.setColNum(i);
										operateOptionsDataSourceInfo.setFieldName(fieldNameStr.getString(i));
										operateOptionsDataSourceInfo.setFieldType(fieldTypeStr.getString(i));
										if (timeFormatStr != null) {
											String timeF = timeFormatStr.getString(i);
											if (timeF != null && !timeF.trim().equals("")) {
												operateOptionsDataSourceInfo.setDateFormat(timeF);
											}
										}
										int filter = fieldFilterStr.getInteger(i);
										String filterVal = fieldFilterValStr.getString(i);
										switch (filter) {
										case 0:
											operateOptionsDataSourceInfo.setFieldFilter(0);
											break;
										case 1:
											if (filterVal != null && !filterVal.trim().equals("")) {
												operateOptionsDataSourceInfo.setFieldFilter(1);
												operateOptionsDataSourceInfo.setFieldFilterVal(filterVal);
											} else {
												operateOptionsDataSourceInfo.setFieldFilter(-1);
											}
											break;
										case 2:
											if (filterVal != null && !filterVal.trim().equals("")) {
												operateOptionsDataSourceInfo.setFieldFilter(2);
												operateOptionsDataSourceInfo.setFieldFilterVal(filterVal);
											} else {
												operateOptionsDataSourceInfo.setFieldFilter(-1);
											}
											break;
										default:
											operateOptionsDataSourceInfo.setFieldFilter(-1);
											break;
										}
										dataSourceInfos.add(operateOptionsDataSourceInfo);
									}
								dataSourceOptionsBean.setMetaDatas(dataSourceInfos);
							} else {
								List<SingleMetaData> dataSourceInfos = new ArrayList<>();
								SingleMetaData operateOptionsDataSourceInfo = new SingleMetaData();
								operateOptionsDataSourceInfo.setColNum(0);
								operateOptionsDataSourceInfo.setFieldName(optionValue);
								operateOptionsDataSourceInfo.setFieldType(
										subJSONObject.getString(DataSourceConfigKey.META_DATA_FIELD_TYPES));
								String timeFormat = subJSONObject.getString(DataSourceConfigKey.META_DATA_FIELD_TIME_FORMAT);
								if (timeFormat != null && !timeFormat.trim().equals("")) {
									operateOptionsDataSourceInfo.setDateFormat(timeFormat);
								}
								int filter = subJSONObject.getInteger(DataSourceConfigKey.META_DATA_FIELD_FILTER);
								String filterVal = subJSONObject
										.getString(DataSourceConfigKey.META_DATA_FIELD_FILTER_VAL);
								switch (filter) {
								case 0:
									operateOptionsDataSourceInfo.setFieldFilter(0);
									break;
								case 1:
									if (filterVal != null && !filterVal.trim().equals("")) {
										operateOptionsDataSourceInfo.setFieldFilter(1);
										operateOptionsDataSourceInfo.setFieldFilterVal(filterVal);
									} else {
										operateOptionsDataSourceInfo.setFieldFilter(-1);
									}
									break;
								case 2:
									if (filterVal != null && !filterVal.trim().equals("")) {
										operateOptionsDataSourceInfo.setFieldFilter(2);
										operateOptionsDataSourceInfo.setFieldFilterVal(filterVal);
									} else {
										operateOptionsDataSourceInfo.setFieldFilter(-1);
									}
									break;
								default:
									operateOptionsDataSourceInfo.setFieldFilter(-1);
									break;
								}
								dataSourceInfos.add(operateOptionsDataSourceInfo);
								dataSourceOptionsBean.setMetaDatas(dataSourceInfos);
							}
							break;
						case WebOperateConfigKeyConstant.components:
							List<OperateComponent> operateComponents = new ArrayList<>();
							JSONArray components = JSON.parseArray(optionValue);
							for (Object component : components) {
								OperateComponent operateComponent = new OperateComponent();
								JSONObject mapComponent = JSON.parseObject(component.toString());
								String preOpID = mapComponent.getString(WebComponentsKeyConstant.preOpID);
								if (preOpID != null) {
									operateComponent.setPreOpID(preOpID);
									if (sourcePrimaries.contains(preOpID)) {
										operateComponent.setSourcePrimary(preOpID);
									}
								}
								operateComponents.add(operateComponent);
							}
							dataSourceOptionsBean.setComponents(operateComponents);
							break;
						case DataSourceConfigKey.SOURCE_TYPE:
							dataSourceOptionsBean.setSourceType(optionNum);
							break;
						case DataSourceConfigKey.DATA_BASENAME:
							dataSourceOptionsBean.setDatabaseName(optionValue);
							break;
						case DataSourceConfigKey.TABLE_NAME:
							dataSourceOptionsBean.setTableName(optionValue);
							break;
						case DataSourceConfigKey.RECORD_DELIMITER:
							dataSourceOptionsBean.setRecordDelimiter(optionValue);
							break;
						case DataSourceConfigKey.DATASOUCE_DELIMITER:
							dataSourceOptionsBean.setDatasouceDelimiter(optionValue);
							break;
						case DataSourceConfigKey.TASK_NUM:
							dataSourceOptionsBean.setTaskNum(optionNum);
							break;
						case DataSourceConfigKey.PARALLELISM_NUM:
							dataSourceOptionsBean.setParallelismNum(optionNum);
							break;
						case DataSourceConfigKey.TICK_TUPLE_FREQ_SECS:
							dataSourceOptionsBean.setTickTupleFreqSecs(optionNum);
							break;
						case DataSourceConfigKey.ZK_SERVERS:
							dataSourceOptionsBean.setZkServers(optionValue);
							break;
						case DataSourceConfigKey.IP:
							dataSourceOptionsBean.setIp(optionValue);
							break;
						case DataSourceConfigKey.PORT:
							dataSourceOptionsBean.setPort(optionNum);
							break;
						case DataSourceConfigKey.KAFKA_DURATIONS_SECONDS:
							dataSourceOptionsBean.setKafkaDurationsSeconds(optionNum);
							break;
						case DataSourceConfigKey.KAFKA_GROUP_ID:
							dataSourceOptionsBean.setKafkaGroupID(optionValue);
							break;
						case DataSourceConfigKey.KAFKA_PARTITIONS:
							dataSourceOptionsBean.setKafkaPartitions(optionValue);
							break;
						case DataSourceConfigKey.KAFKA_TOPICS:
							kafkaTopics.add(optionValue);
							dataSourceOptionsBean.setKafkaTopics(optionValue);
							break;
						case DataSourceConfigKey.SSH_IP:
							dataSourceOptionsBean.setSshHost(optionValue);
							break;
						case DataSourceConfigKey.SSH_USER:
							dataSourceOptionsBean.setSshUser(optionValue);
							break;
						case DataSourceConfigKey.SSH_PWD:
							dataSourceOptionsBean.setSshPasswd(optionValue);
							break;
						case DataSourceConfigKey.PATH:
							dataSourceOptionsBean.setPath(optionValue);
							break;
						case DataSourceConfigKey.ROTATION_FILE_SIZE_IN_MB:
							dataSourceOptionsBean.setRotationFileSizeInMB(optionNum);
							break;
						case DataSourceConfigKey.ROTATION_TIME_IN_SEC:
							dataSourceOptionsBean.setRotationTimeInSec(optionNum);
							break;
						case DataSourceConfigKey.FTP_URL:
							dataSourceOptionsBean.setFtpUrl(optionValue);
							break;
						case DataSourceConfigKey.FTP_HOST:
							dataSourceOptionsBean.setFtpHost(optionValue);
							break;
						case DataSourceConfigKey.FTP_PORT:
							dataSourceOptionsBean.setFtpPort(optionNum);
							break;
						case DataSourceConfigKey.FTP_USER_NAME:
							dataSourceOptionsBean.setFtpUserName(optionValue);
							break;
						case DataSourceConfigKey.FTP_PASSWORD:
							dataSourceOptionsBean.setFtpPassword(optionValue);
							break;
						case DataSourceConfigKey.FTP_ENCODING:
							dataSourceOptionsBean.setFtpEncoding(optionValue);
							break;
						case DataSourceConfigKey.FILE_PRIFIX:
							dataSourceOptionsBean.setFilePrifix(optionValue);
							break;
						case DataSourceConfigKey.FILE_EXTENSION:
							dataSourceOptionsBean.setFileExtension(optionValue);
							break;
						case DataSourceConfigKey.SYNC_COUNT:
							dataSourceOptionsBean.setSyncCount(optionNum);
							break;
						case DataSourceConfigKey.JDBC_DB:
							dataSourceOptionsBean.setJdbcDB(optionValue);
							break;
						case DataSourceConfigKey.JDBC_USER:
							dataSourceOptionsBean.setJdbcUser(optionValue);
							break;
						case DataSourceConfigKey.JDBC_PASSWD:
							dataSourceOptionsBean.setJdbcPasswd(optionValue);
							break;
						case DataSourceConfigKey.rowKeyFields:
							if (optionValue.indexOf('[') > -1) {
								JSONArray jsonArray = JSON.parseArray(optionValue);
								StringBuffer strb = new StringBuffer("");
								if (jsonArray.size() > 0) {
									for (int i = 0; i < jsonArray.size(); i++) {
										strb.append(jsonArray.get(i));
										if (i < jsonArray.size() - 1) {
											strb.append(",");
										}
									}
									optionValue = strb.toString();
								}
							}
							dataSourceOptionsBean.setRowKeyFields(optionValue);
							break;
						case DataSourceConfigKey.valueFields:
							dataSourceOptionsBean.setValueFields(optionValue);
							break;
						case DataSourceConfigKey.REDIS_IP:
							dataSourceOptionsBean.setRedisHost(optionValue);
							break;
						case DataSourceConfigKey.REDIS_PORT:
							dataSourceOptionsBean.setRedisPort(optionNum);
							break;
						case DataSourceConfigKey.REDIS_PASSWORD:
							dataSourceOptionsBean.setRedisPassword(optionValue);
							break;
						case DataSourceConfigKey.REDIS_TIMEOUT:
							dataSourceOptionsBean.setRedisTimeOut(optionNum);
							break;
						case DataSourceConfigKey.HBASE_COLUMN_FAMILY:
							dataSourceOptionsBean.sethBaseColumnFamily(optionValue);
							break;
						case DataSourceConfigKey.hBaseZkQuorum:
							dataSourceOptionsBean.sethBaseZkQuorum(optionValue);
							break;
						case DataSourceConfigKey.hBaseZkPort:
							dataSourceOptionsBean.sethBaseZkPort(optionNum);
							break;
						case DataSourceConfigKey.hBaseMasterPort:
							dataSourceOptionsBean.sethBaseMasterPort(optionNum);
							break;
						case DataSourceConfigKey.znodeParent:
							dataSourceOptionsBean.setZnodeParent(optionValue);
							break;
						case DataSourceConfigKey.metaStoreURI:
							dataSourceOptionsBean.setMetaStoreURI(optionValue);
							break;
						case DataSourceConfigKey.dbName:
							dataSourceOptionsBean.setDbName(optionValue);
							break;
						case DataSourceConfigKey.timeAsPartitionField:
							dataSourceOptionsBean.setTimeAsPartitionField(Boolean.parseBoolean(optionValue));
							break;
						case DataSourceConfigKey.txnsPerBath:
							dataSourceOptionsBean.setTxnsPerBath(optionNum);
							break;
						case DataSourceConfigKey.batchSize:
							dataSourceOptionsBean.setBatchSize(optionNum);
							break;
						case DataSourceConfigKey.idleTimeout:
							dataSourceOptionsBean.setIdleTimeout(optionNum);
							break;
						case DataSourceConfigKey.callTimeout:
							dataSourceOptionsBean.setCallTimeout(optionNum);
							break;
						case DataSourceConfigKey.HEART_BEAT_INTERVAL:
							dataSourceOptionsBean.setHeartBeatInterval(optionNum);
							break;
						case DataSourceConfigKey.AUTO_CREATE_PARTITIONS:
							dataSourceOptionsBean.setAutoCreatePartitions(Boolean.parseBoolean(optionValue));
							break;
						case DataSourceConfigKey.TICK_TUPLE_INTERVAL:
							dataSourceOptionsBean.setTickTupleInterval(optionNum);
							break;
						case DataSourceConfigKey.PARTITION_FIELDS:
							dataSourceOptionsBean.setPartitionFields(optionValue);
							break;
						case DataSourceConfigKey.ES_INDEX_NAME:
							dataSourceOptionsBean.setEsIndexName(optionValue);
							break;
						case DataSourceConfigKey.ES_TYPE_NAME:
							dataSourceOptionsBean.setEsTypeName(optionValue);
							break;
						case DataSourceConfigKey.ES_BOLT_FLUSH_ENTRIES_SIZE:
							dataSourceOptionsBean.setEsBoltFlushEntriesSize(optionNum);
							break;
						case DataSourceConfigKey.ES_BOLT_TICK_TUPLE_FLUSH:
							dataSourceOptionsBean.setEsBoltTickTupleFlush(Boolean.parseBoolean(optionValue));
							break;
						case DataSourceConfigKey.ES_BOLT_WRITE_ACK:
							dataSourceOptionsBean.setEsBoltWriteAck(Boolean.parseBoolean(optionValue));
							break;
						case DataSourceConfigKey.ES_INDEX_AUTO_CREATE:
							dataSourceOptionsBean.setEsIndexAutoCreate(Boolean.parseBoolean(optionValue));
							break;
						}
					}
				}
				dataSourceMap.put(thisID, dataSourceOptionsBean);
			}
		}
	}
	private void getOperateOptions(JSONObject jSONObjects) {
		for (Entry<String, Object> jSONEntry : jSONObjects.entrySet()) {
			String thisID = jSONEntry.getKey();
			JSONObject subJSONObject = JSON.parseObject(jSONEntry.getValue().toString());
			String sourceName = subJSONObject.getString(WebComponentsKeyConstant.sourceName);
			if (allOperationsBeanList.contains(sourceName)) {
				OperateOptionsBean operateOptionsBean = new OperateOptionsBean();
				operateOptionsBean.setWorkFlowID(workFlowID);
				operateOptionsBean.setOperateID(thisID);
				operateOptionsBean.setTypeName(workFlowTypeName);
				operateOptionsBean.setOperateType(sourceName);
				for (Entry<String, Object> optionEntry : subJSONObject.entrySet()) {
					String optionName = optionEntry.getKey();
					String optionValue = optionEntry.getValue().toString();
					if (optionValue != null && !optionValue.trim().equals("")) {
						int optionNum = 0;
						if (StringUtils.isNumeric(optionValue)) {
							optionNum = Integer.parseInt(optionValue);
						}
						switch (optionName) {
						case WebOperateConfigKeyConstant.operateType:
							operateOptionsBean.setOperateType(optionValue);
							break;
						case WebOperateConfigKeyConstant.parallelismNum:
							operateOptionsBean.setParallelismNum(optionNum);
							break;
						case WebOperateConfigKeyConstant.tickTupleFreqSecs:
							operateOptionsBean.setTickTupleFreqSecs(optionNum);
							break;
						case WebOperateConfigKeyConstant.isSection:
							operateOptionsBean.setSection(Boolean.parseBoolean(optionValue));
							break;
						case WebOperateConfigKeyConstant.and:
							operateOptionsBean.setAnd(optionValue);
							break;
						case WebOperateConfigKeyConstant.between:
							operateOptionsBean.setBetween(optionValue);
							break;
						case WebOperateConfigKeyConstant.values:
							operateOptionsBean.setValues(optionValue);
							break;
						case WebOperateConfigKeyConstant.operateFieldNames:
						case WebOperateConfigKeyConstant.leftJoinSort:
							operateOptionsBean.setFieldNames(optionValue);
							break;
						case WebOperateConfigKeyConstant.calToken:
							operateOptionsBean.setCalToken(optionValue);
							break;
						case WebOperateConfigKeyConstant.upSort:
							operateOptionsBean.setUpSort(Boolean.parseBoolean(optionValue));
							break;
							
							//	算法操作参数解析
							case AlgorithmConstant.algorithmID:
								operateOptionsBean.setAlgorithmID(optionValue);
								break;
							case AlgorithmConstant.algorithmModelID:
								operateOptionsBean.setAlgorithmModelID(optionValue);
								break;
							case AlgorithmConstant.algorithmModelName:
								operateOptionsBean.setAlgorithmModelName(optionValue);
								break;
							case AlgorithmConstant.algorithmModelSavePath:
								operateOptionsBean.setAlgorithmModelSavePath(optionValue);
								break;
							case AlgorithmConstant.algorithmModelType:
								operateOptionsBean.setAlgorithmModelType(optionValue);
								break;
							case AlgorithmConstant.algorithmModelIsTrain:
								operateOptionsBean.setAlgorithmIsTrain(optionValue);
								break;
							case AlgorithmConstant.algorithmModelIsSpecifiedDataFormat:
								operateOptionsBean.setAlgorithmIsSpecifiedDataFormat(optionValue);
								break;
							case AlgorithmConstant.algorithmModelDataFormat:
								operateOptionsBean.setAlgorithmDataFormat(optionValue);
								break;
							case AlgorithmConstant.algorithmModelIterations:
								operateOptionsBean.setAlgorithmIterations(optionValue);
								break;
							case AlgorithmConstant.algorithmModelLabelField:
								operateOptionsBean.setAlgorithmLabelField(optionValue);
								break;
							case AlgorithmConstant.algorithmModelRank:
								operateOptionsBean.setAlgorithmRank(optionValue);
								break;
							case AlgorithmConstant.algorithmModelRegParam:
								operateOptionsBean.setAlgorithmRegParam(optionValue);
								break;
							case AlgorithmConstant.algorithmModelSeed:
								operateOptionsBean.setAlgorithmSeed(optionValue);
								break;
							case AlgorithmConstant.algorithmModelStepSize:
								operateOptionsBean.setAlgorithmStepSize(optionValue);
								break;
								
						case WebOperateConfigKeyConstant.components:
							List<OperateComponent> operateComponents = new ArrayList<>();
							JSONArray components = JSON.parseArray(optionValue);
							for (Object component : components) {
								OperateComponent operateComponent = new OperateComponent();
								JSONObject mapComponent = JSON.parseObject(component.toString());
								String preOpID = mapComponent.getString(WebComponentsKeyConstant.preOpID);
								if (preOpID != null) {
									operateComponent.setPreOpID(preOpID);
									if (sourcePrimaries.contains(preOpID)) {
										operateComponent.setSourcePrimary(preOpID);
									}
								}
								operateComponents.add(operateComponent);
							}
							operateOptionsBean.setComponents(operateComponents);
							break;
						case DataSourceConfigKey.TABLE_NAME:
							operateOptionsBean.setTableName(optionValue);
							break;
						case DataSourceConfigKey.DATASOUCE_DELIMITER:
							operateOptionsBean.setDatasouceDelimiter(optionValue);
							break;
						case DataSourceConfigKey.META_DATA_FIELD_TIME_FORMAT:
							operateOptionsBean.setTimeFormat(optionValue);
							break;
						case DataSourceConfigKey.META_DATA_FIELD_NAMES:
							if (optionValue.indexOf('[') > -1) {
								JSONArray fieldNameStr = JSON.parseArray(optionValue);
								JSONArray fieldTypeStr = JSON
										.parseArray(subJSONObject.getString(DataSourceConfigKey.META_DATA_FIELD_TYPES));
								List<SingleMetaData> dataSourceInfos = new ArrayList<>();
								if (fieldNameStr.size() > 0)
									for (int i = 0; i < fieldNameStr.size(); i++) {
										SingleMetaData operateOptionsDataSourceInfo = new SingleMetaData();
										operateOptionsDataSourceInfo.setColNum(i);
										operateOptionsDataSourceInfo.setFieldName(fieldNameStr.getString(i));
										operateOptionsDataSourceInfo.setFieldType(fieldTypeStr.getString(i));
										dataSourceInfos.add(operateOptionsDataSourceInfo);
									}
								operateOptionsBean.setMetaDatas(dataSourceInfos);
							} else {
								List<SingleMetaData> dataSourceInfos = new ArrayList<>();
								SingleMetaData operateOptionsDataSourceInfo = new SingleMetaData();
								operateOptionsDataSourceInfo.setColNum(0);
								operateOptionsDataSourceInfo.setFieldName(optionValue);
								operateOptionsDataSourceInfo.setFieldType(
										subJSONObject.getString(DataSourceConfigKey.META_DATA_FIELD_TYPES));
								dataSourceInfos.add(operateOptionsDataSourceInfo);
								operateOptionsBean.setMetaDatas(dataSourceInfos);
							}
							break;
						}
					}
				}
				operateMap.put(thisID, operateOptionsBean);
			}
		}
	}
	/**
	 * exchange storm/spark-core with data source place.
	 * 
	 * @param jSONObjects
	 * @return
	 */
	private JSONObject commonOperateModuleIDtransfer(JSONObject jSONObjects) {
		String topologyOpinions = new String();
		/* key:module ID , value: datasource ID */
		Map<String, String> needTransferID = new HashMap<>();
		for (Entry<String, Object> jSONEntry : jSONObjects.entrySet()) {
			String thisID = jSONEntry.getKey();
			JSONObject subJSONObject = JSON.parseObject(jSONEntry.getValue().toString());
			String sourceName = subJSONObject.getString(WebComponentsKeyConstant.sourceName);
			if (commonOperateModuleNameList.contains(sourceName)) {
				String componentsStr = subJSONObject.getString(WebComponentsKeyConstant.components);
				JSONArray componentsArray = JSON.parseArray(componentsStr);
				for (Object componentObj : componentsArray) {
					JSONObject component = JSON.parseObject(componentObj.toString());
					String datasourceID = component.getString(WebComponentsKeyConstant.preOpID);
					needTransferID.put(thisID, datasourceID);
					break;
				}
				if (sourceName.equals(WebModuleNameConstant.storm)) {
					if (jSONEntry.getValue().toString().length() > topologyOpinions.length()) {
						topologyOpinions = jSONEntry.getValue().toString();
					}
				}
			}
		}
		/* start to transfer input data source ID with module */
		Map<String, Map<String, Integer>> needChangePreOp = new HashMap<>();
		Map<String, Integer> preOpMap;
		for (Entry<String, Object> jSONEntry : jSONObjects.entrySet()) {
			String thisID = jSONEntry.getKey();
			JSONObject subJSONObject = JSON.parseObject(jSONEntry.getValue().toString());
			String componentsStr = subJSONObject.getString(WebComponentsKeyConstant.components);
			JSONArray componentsArray = JSON.parseArray(componentsStr);
			if (componentsArray != null && !componentsArray.isEmpty())
				for (Object componentObj : componentsArray) {
					JSONObject component = JSON.parseObject(componentObj.toString());
					String preOpID = component.getString(WebComponentsKeyConstant.preOpID);
					int index = componentsArray.indexOf(componentObj);
					if (needTransferID.containsKey(preOpID)) {
						if (!needChangePreOp.containsKey(thisID)) {
							preOpMap = new HashMap<>();
						} else {
							preOpMap = needChangePreOp.get(thisID);
						}
						preOpMap.put(preOpID, index);
						needChangePreOp.put(thisID, preOpMap);
					}
				}
		}
		for (Entry<String, Map<String, Integer>> opID : needChangePreOp.entrySet()) {
			String thisID = opID.getKey();
			preOpMap = opID.getValue();
			int index = 0;
			String preOpID = "";
			for (Entry<String, Integer> preOpEntry : preOpMap.entrySet()) {
				preOpID = preOpEntry.getKey();
				index = preOpEntry.getValue();
				JSONObject newSubJSONObject = JSON.parseObject(jSONObjects.remove(thisID).toString());
				JSONArray newComponentsArray = JSON
						.parseArray(newSubJSONObject.remove(WebComponentsKeyConstant.components).toString());
				JSONObject newComponent = JSON.parseObject(newComponentsArray.get(index).toString());
				newComponent.remove(WebComponentsKeyConstant.preOpID);
				newComponent.put(WebComponentsKeyConstant.preOpID, needTransferID.get(preOpID));
				newComponentsArray.add(index, newComponent);
				newComponentsArray.remove(index + 1);
				newSubJSONObject.put(WebComponentsKeyConstant.components, newComponentsArray);
				jSONObjects.put(thisID, newSubJSONObject);
			}
		}
		return jSONObjects;
	}
	public boolean isRepeat() {
		return repeat;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public long getTimestampExpression() {
		return timestampExpression;
	}
	public boolean isWorkFlowError() {
		return workFlowError;
	}
	public String getWorkFlowErrorMessage() {
		return workFlowErrorMessage;
	}
	public DataBaseSQLBean getDataBaseSQLBean() {
		return dataBaseSQLBean;
	}
	public Map<String, DataSourceOptionsBean> getDataSourceMap() {
		return dataSourceMap;
	}
	public Map<String, OperateOptionsBean> getOperateMap() {
		return operateMap;
	}
	public String getWorkFlowID() {
		return workFlowID;
	}
	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}
	public Map<String, DataSchema> getDataSchemas() {
		return dataSchemas;
	}
	public String getWorkFlowTypeName() {
		return workFlowTypeName;
	}
	public Set<String> getKafkaTopics() {
		return kafkaTopics;
	}
}