package cn.com.carenet.scheduler.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.com.carenet.scheduler.entity.AlgorithmModel;

@Component("algorithmModelDAO")
public class AlgorithmModelDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String add(String modelId, String dataSources, String dataSchemas, String algorithmParam) {
		String insertSQL = "insert into algorithm_model (model_id, model_name, model_desc, model_infos, operates_infos, data_sources_infos, fields_infos) values (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertSQL, modelId, "", "", "", algorithmParam, dataSources, dataSchemas);
		return null;
	}
	
//	public String update(String modelId, String dataSources, String dataSchemas, String algorithmParam) {
//		String updateSQL = "update algorithm_model set operates_infos = ?, data_sources_infos = ?, fields_infos = ? where model_id = ?";
//		jdbcTemplate.update(updateSQL, algorithmParam, dataSources, dataSchemas, modelId);
//		return null;
//	}
	
	public String select(String modelId) {
		return null;
	}
	
	public String delete(String modelId) {
		String deleteSQL = "delete from algorithm_model where model_id = ?";
		jdbcTemplate.update(deleteSQL, modelId);
		return null;
	}
	
	public String start(String modelId) {
		return null;
	}
	
	public String stop(String modelId) {
		return null;
	}
	
	public String catResult(String modelId) {
		return null;
	}
	
	public String getDataSchema(String modelId) {
		String sql = "select * from algorithm_model where model_id = ?";
		String fieldsInfos = "";
		
		List<AlgorithmModel> list = jdbcTemplate.query(sql, new Object[] { modelId }, new BeanPropertyRowMapper<AlgorithmModel>(AlgorithmModel.class));
		AlgorithmModel algorithmModel = new AlgorithmModel();
		if(list != null && list.size() > 0){
			algorithmModel = list.get(0);
			if(algorithmModel != null){
				fieldsInfos = algorithmModel.getFieldsInfos();
			}
		}
		
		return fieldsInfos;
	}
}
