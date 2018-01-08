package cn.com.carenet.scheduler.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.carenet.scheduler.constant.DataSourceConfigKey;
import cn.com.carenet.scheduler.web.entity.DataRepository;

/**
 * 
 * @author Sherard Lee
 * @since 22/Aug/2017
 */
public class JsonPropLoader {

	public Map<String, DataRepository> loadJson(String json) {
		json = this.arraysTransfer(json);
		Map<String, DataRepository> dataRepositories = new HashMap<>();
		JSONObject jsonObjects = JSON.parseObject(json);
		for (Entry<String, Object> jsonObject : jsonObjects.entrySet()) {
			String key = jsonObject.getKey();
			String value = jsonObject.getValue().toString();
			DataRepository dataRepository = JSON.toJavaObject(JSON.parseObject(value), DataRepository.class);
			dataRepositories.put(key, dataRepository);
		}
		return dataRepositories;
	}

	private String arraysTransfer(String json) {
		JSONObject jsonObjects = JSON.parseObject(json);
		JSONObject newJSONObject = new JSONObject();
		for (Entry<String, Object> jsonObject : jsonObjects.entrySet()) {
			String key = jsonObject.getKey();
			String value = jsonObject.getValue().toString();
			JSONObject subJsonObject = JSON.parseObject(value);

			Object fieldNames = subJsonObject.remove(DataSourceConfigKey.META_DATA_FIELD_NAMES);
			if (fieldNames != null) {
				subJsonObject.put(DataSourceConfigKey.META_DATA_FIELD_NAMES, arrayTransfer(fieldNames.toString()));
			}
			Object asFieldName = subJsonObject.remove(DataSourceConfigKey.META_DATA_FIELD_ASNAME);
			if (asFieldName != null) {
				subJsonObject.put(DataSourceConfigKey.META_DATA_FIELD_ASNAME, arrayTransfer(asFieldName.toString()));
			}
			Object fieldTypes = subJsonObject.getString(DataSourceConfigKey.META_DATA_FIELD_TYPES);
			if (fieldTypes != null) {
				subJsonObject.put(DataSourceConfigKey.META_DATA_FIELD_TYPES, arrayTransfer(fieldTypes.toString()));
			}

			Object fieldFilter = subJsonObject.getString(DataSourceConfigKey.META_DATA_FIELD_FILTER);
			if (fieldFilter != null) {
				subJsonObject.put(DataSourceConfigKey.META_DATA_FIELD_FILTER, arrayTransfer(fieldFilter.toString()));
			}

			Object fieldFilterVal = subJsonObject.getString(DataSourceConfigKey.META_DATA_FIELD_FILTER_VAL);
			if (fieldFilterVal != null) {
				subJsonObject.put(DataSourceConfigKey.META_DATA_FIELD_FILTER_VAL,
						arrayTransfer(fieldFilterVal.toString()));
			}

			Object fieldRemove = subJsonObject.getString(DataSourceConfigKey.META_DATA_FIELD_REMOVE);
			if (fieldRemove != null) {
				subJsonObject.put(DataSourceConfigKey.META_DATA_FIELD_REMOVE, arrayTransfer(fieldRemove.toString()));
			}

			Object timeFormat = subJsonObject.getString(DataSourceConfigKey.META_DATA_FIELD_TIME_FORMAT);
			if (timeFormat != null) {
				subJsonObject.put(DataSourceConfigKey.META_DATA_FIELD_TIME_FORMAT,
						arrayTransfer(timeFormat.toString()));
			}

			Object independents = subJsonObject.getString(DataSourceConfigKey.META_DATA_FIELD_INDEPENDENTS);
			if (independents != null) {
				subJsonObject.put(DataSourceConfigKey.META_DATA_FIELD_INDEPENDENTS,
						this.splitTransfer(independents.toString()));
			}

			newJSONObject.put(key, subJsonObject);
		}
		return newJSONObject.toJSONString();
	}

	private JSONArray arrayTransfer(String values) {
		if (values.indexOf('[') < 0) {
			JSONArray array = new JSONArray();
			array.add(values);
			return array;
		} else {
			return JSON.parseArray(values);
		}
	}

	private JSONArray splitTransfer(String values) {
		if (values.indexOf('[') < 0) {
			String[] valueArray = StringUtils.splitPreserveAllTokens(values, ",");
			JSONArray array = new JSONArray();
			for (String value : valueArray) {
				array.add(value);
			}
			return array;
		} else {
			return JSON.parseArray(values);
		}
	}
}
