package cn.com.carenet.scheduler.bean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * File name : JsonResult.java Author : yinsq Date : 2016年8月30日下午3:15:08 Desc :
 * Update History : 08/June/2017
 * <ul>
 * <li></li>
 * <li></li>
 * </ul>
 */
public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//错误码 200正常 500异常
	private int code;
	//错误描述
	private String desc;
	private List<T> datas;
	private T data;

	public JsonResult() {
		super();
		this.code = 200;
	}

	public JsonResult(int code) {
		super();
		this.code = code;
	}

	public JsonResult(int code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}

	@SuppressWarnings("unchecked")
	public JsonResult(String jsonStr, Class<T> clazz) {
		JsonResult<JSONObject> jsonResult = JSONObject.parseObject(jsonStr, JsonResult.class);

		this.code = jsonResult.getCode();
		this.desc = jsonResult.getDesc();

		if (jsonResult.getData() != null)
			this.data = JSONObject.parseObject(jsonResult.getData().toJSONString(), clazz);
		if (jsonResult.getDatas() != null)
			this.datas = JSONObject.parseArray(JSONObject.toJSONString(jsonResult.getDatas()), clazz);
	}

	@Override
	public String toString() {
		SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;
		return JSON.toJSONString(this, feature);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
