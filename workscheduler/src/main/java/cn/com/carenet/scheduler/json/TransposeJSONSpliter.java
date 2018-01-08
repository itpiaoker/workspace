package cn.com.carenet.scheduler.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.carenet.scheduler.constant.Constant;
import cn.com.carenet.scheduler.constant.WebComponentsKeyConstant;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;

public class TransposeJSONSpliter {
	final protected static String start = "start";
	final protected static String end = "end";
	private String json;
	private long timestampExpression;
	private String cronExpression;
	private boolean repeat;
	
	@SuppressWarnings("unused")
	private String startID;
	private String endID;
	
	/** 存储转置信息 */
	public Map<String, String> workFlowIdsDepend = new HashMap<>();
	/** 存储转置信息 */
	public List<String> transposeIds = new ArrayList<>();
	/** 存储转置信息 */
	public Map<String, List<String>> componentIdSourceIds = new HashMap<>();
	/** 存储转置信息 */
	public Map<Integer, List<String>> transposeMapTree = new HashMap<>();
	
	private String workFlowID;
	
	public TransposeJSONSpliter(String json,String workFlowID){
		this.json= json;
		this.workFlowID = workFlowID;
	}
	
	/**
	 * To get trees, this method must be called.
	 */
	public void buildTransposeMapTree() {
		WebJSONLegality webJSONLegality = new WebJSONLegality(json,workFlowID);
		JSONObject jsonObjects = webJSONLegality.getdataJsonObject();
		timestampExpression = webJSONLegality.getTimestampExpression();
		cronExpression = webJSONLegality.getCronExpression();
		repeat = webJSONLegality.isRepeat();
		
		this.parsedTransposeJson(jsonObjects);
		List<String> list = componentIdSourceIds.get(endID);
		String string = list.get(0);
		createExecTree(string, 0);
	}
	
	public void parsedTransposeJson(JSONObject jsonObjects) {
		for (Entry<String, Object> jsonEntry : jsonObjects.entrySet()) {
			String idStr = jsonEntry.getKey();
			String componentID = idStr.substring(Constant.PREFIX_WINDOW_KEY.length(), idStr.length());
			Object value = jsonEntry.getValue();
			JSONObject sonJsonObjects = JSON.parseObject(value.toString());
			String isTranspose = sonJsonObjects.getString("isTranspose");
			String wf_name = sonJsonObjects.getString(WebComponentsKeyConstant.sourceName);
			String workFlowID = sonJsonObjects.getString("workFlowID");
			Object components = sonJsonObjects.get(WebComponentsKeyConstant.components);
			
			if(wf_name != null && WebModuleNameConstant.start.equals(wf_name)){
				startID = componentID;
			} else if(wf_name != null && "end".equals(wf_name)){
				endID = componentID;
			}
			
			if(componentID != null && workFlowID != null && !"1".equals(isTranspose)){
				workFlowIdsDepend.put(componentID, workFlowID);
			}
			
			if(isTranspose != null && "1".equals(isTranspose)){
				transposeIds.add(componentID);
			}
			
			if(components != null){
				JSONArray subJsonObjects = JSON.parseArray(components.toString());
				for (Object subObject : subJsonObjects) {
					JSONObject subsubJsonObjects = JSON.parseObject(subObject.toString());
					String sourceIdStr = subsubJsonObjects.getString(WebComponentsKeyConstant.preOpID);
					String sourceID = sourceIdStr.substring(Constant.PREFIX_WINDOW_KEY.length(), sourceIdStr.length());
//					transposeWorkFlowIds.put(componentID, sourceID);
					if(componentIdSourceIds.containsKey(componentID)){
						List<String> list = componentIdSourceIds.get(componentID);
						list.add(sourceID);
					} else {
						ArrayList<String> list = new ArrayList<>();
						list.add(sourceID);
						componentIdSourceIds.put(componentID, list);
					}
				}
			}
		}
	}
	public void createExecTree(String id, int d) {
		int depth = d;
		
		if(transposeMapTree.containsKey(depth)){
			List<String> list2 = transposeMapTree.get(depth);
			list2.add(id);
		} else {
			ArrayList<String> l = new ArrayList<>();
			l.add(id);
			transposeMapTree.put(depth, l);
		}
		
		
		if(!componentIdSourceIds.containsKey(id)){
			return;
		} else {
			depth ++;
			List<String> list = componentIdSourceIds.get(id);
			for (String string : list) {
				createExecTree(string, depth);
			}
		}
	}

	public long getTimestampExpression() {
		return timestampExpression;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public Map<Integer, List<String>> getTransposeMapTree() {
		return transposeMapTree;
	}

	public Map<String, String> getWorkFlowIdsDepend() {
		return workFlowIdsDepend;
	}
}
