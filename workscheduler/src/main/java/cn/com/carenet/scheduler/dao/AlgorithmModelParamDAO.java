package cn.com.carenet.scheduler.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.com.carenet.scheduler.bean.OperateOptionsBean;
import cn.com.carenet.scheduler.constant.AlgorithmConstant;
import cn.com.carenet.scheduler.entity.AlgorithmModelParam;

@Component("algorithmModelParamDAO")
public class AlgorithmModelParamDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public String add(String sql, String paramName, String paramValue, String modelId) {
		if(paramName != null){
			jdbcTemplate.update(sql, paramName, paramValue, "", modelId);
		}
		return "";
	}
	public String addParams(String modelId, Map<String, OperateOptionsBean> operateOptions) {
		String insertSQL = "insert into algorithm_model_param (param_name, param_value, data_type, model_id) values(?,?,?,?);";
		
		Set<Entry<String, OperateOptionsBean>> entrySet = operateOptions.entrySet();
		for (Entry<String, OperateOptionsBean> entry : entrySet) {
//			String key = entry.getKey();
			OperateOptionsBean optionsBean = entry.getValue();
			String algorithmModelID = optionsBean.getAlgorithmModelID();
			String algorithmID = optionsBean.getAlgorithmID();
			String algorithmModelName = optionsBean.getAlgorithmModelName();
			String algorithmModelType = optionsBean.getAlgorithmModelType();
			String isTrain = optionsBean.getAlgorithmIsTrain();
			String isSpecified = optionsBean.getAlgorithmIsSpecifiedDataFormat();
			String dataFormat = optionsBean.getAlgorithmDataFormat();
			String labelField = optionsBean.getAlgorithmLabelField();
			String algorithmModelSavePath = optionsBean.getAlgorithmModelSavePath();
			String algorithmRank = optionsBean.getAlgorithmRank();
			String iterations = optionsBean.getAlgorithmIterations();
			String algorithmRegParam = optionsBean.getAlgorithmRegParam();
			String algorithmSeed = optionsBean.getAlgorithmSeed();
			String algorithmStepSize = optionsBean.getAlgorithmStepSize();
			
			String paramName = "";
			String paramValue = "";
			
			
			if(algorithmID != null){
				paramName = AlgorithmConstant.algorithmID;
				paramValue = algorithmID;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(algorithmModelID != null){
				paramName = AlgorithmConstant.algorithmModelID;
				paramValue = algorithmModelID;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(isTrain != null){
				paramName = AlgorithmConstant.algorithmModelIsTrain;
				paramValue = isTrain;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(labelField != null){
				paramName = AlgorithmConstant.algorithmModelLabelField;
				paramValue = labelField;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(isSpecified != null){
				paramName = AlgorithmConstant.algorithmModelIsSpecifiedDataFormat;
				paramValue = isSpecified;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(dataFormat != null){
				paramName = AlgorithmConstant.algorithmModelDataFormat;
				paramValue = dataFormat;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(algorithmModelName != null){
				paramName = AlgorithmConstant.algorithmModelName;
				paramValue = algorithmModelName;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(algorithmModelSavePath != null){
				paramName = AlgorithmConstant.algorithmModelSavePath;
				paramValue = algorithmModelSavePath;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(algorithmModelType != null){
				paramName = AlgorithmConstant.algorithmModelType;
				paramValue = algorithmModelType;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(algorithmRank != null){
				paramName = AlgorithmConstant.algorithmModelRank;
				paramValue = algorithmRank;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(iterations != null){
				paramName = AlgorithmConstant.algorithmModelIterations;
				paramValue = iterations;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(algorithmRegParam != null){
				paramName = AlgorithmConstant.algorithmModelRegParam;
				paramValue = algorithmRegParam;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(algorithmSeed != null){
				paramName = AlgorithmConstant.algorithmModelSeed;
				paramValue = algorithmSeed;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			if(algorithmStepSize != null){
				paramName = AlgorithmConstant.algorithmModelStepSize;
				paramValue = algorithmStepSize;
				add(insertSQL, paramName, paramValue, modelId);
			}
			
			
			
		}
		return null;
	}
	
//	public String update(OperateOptionsBean algorithmParam) {
//		String updateSQL = "update task_details set type_name=?,task_repeat=?,big_bean=?,data_sources_infos=?,operates_infos=?,fields_infos=?,sqls_infos=?,quartz_time=?,quartz_cron=? where task_id=?";
//		jdbcTemplate.update(updateSQL, algorithmParam.getAlgorithmModelID());
//		return null;
//	}
	
	public String delete(String modelId) {
		String deleteSQL = "delete from algorithm_model_param where model_id = ?";
		jdbcTemplate.update(deleteSQL, modelId);
		return null;
	}
	
	public List<AlgorithmModelParam> select(String modelId) {
		String sql = "select * from algorithm_model_param where model_id = ?";
		List<AlgorithmModelParam> list = jdbcTemplate.query(sql, new Object[] { modelId }, new BeanPropertyRowMapper(AlgorithmModelParam.class));
		return list;
	}
	
	public String stop(String modelId) {
		return null;
	}
	
	public String catResult(String modelId) {
		return null;
	}
}
