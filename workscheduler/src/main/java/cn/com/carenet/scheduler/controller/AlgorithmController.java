package cn.com.carenet.scheduler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import cn.com.carenet.scheduler.bean.JsonResult;
import cn.com.carenet.scheduler.constant.JobStatusConstant;
import cn.com.carenet.scheduler.service.AlgorithmModelService;
import cn.com.carenet.scheduler.service.WorkFlowService;

@RestController
@RequestMapping("/algorithmModel")
public class AlgorithmController {

	@Autowired
	private AlgorithmModelService algorithmModelService;
	@Autowired
	private WorkFlowService workFlowService;

	@RequestMapping("/save")
	public JsonResult<String> saveAction(String modelId) {
		algorithmModelService.update(modelId, JobStatusConstant.typeSave);

		JsonResult<String> jr = new JsonResult<String>();
		if (algorithmModelService.isError()) {
			jr.setCode(500);
			jr.setDesc(algorithmModelService.getErrorMsg());
		}
		return jr;
	}

	@RequestMapping("/update")
	public JsonResult<String> updateAction(String modelId) {
		return saveAction(modelId);
	}

	@RequestMapping("/delete")
	public JsonResult<String> deleteAction(String modelId) {
		algorithmModelService.update(modelId, JobStatusConstant.typeDelete);
		JsonResult<String> jr = new JsonResult<String>();
		if (algorithmModelService.isError()) {
			jr.setCode(500);
			jr.setDesc(algorithmModelService.getErrorMsg());
		}
		return jr;
	}

	@RequestMapping("/start")
	public JsonResult<String> startAction(String modelId) {
		workFlowService.start(modelId, JobStatusConstant.typeStart);
		JsonResult<String> jr = new JsonResult<String>();
		if (workFlowService.isError()) {
			jr.setCode(500);
			jr.setDesc(workFlowService.getErrorMsg());
		}
		return jr;
	}

	@RequestMapping("/stop")
	public JsonResult<String> stopAction(String modelId) {
		workFlowService.stop(modelId, JobStatusConstant.typeStop);
		JsonResult<String> jr = new JsonResult<String>();
		if (workFlowService.isError()) {
			jr.setCode(500);
			jr.setDesc(workFlowService.getErrorMsg());
		}
		return jr;
	}

	@RequestMapping("/catResult")
	public JsonResult<Map<String, Object>> catResultAction(String modelId) {
		// Map<String, String> result = new HashMap<>();
		// String message = null;
		JsonResult<Map<String, Object>> jr = new JsonResult<>();
		MongoClient client = new MongoClient("test004", 27017);
		MongoDatabase database = client.getDatabase("test");
		Map<String, Object> rsMaps = new HashMap<>();
		// List<Document> documents = algorithmModelService.catResult(modelId);
		Document document = algorithmModelService.catSummary(database, modelId);
		if(document!=null) {
			Double meanSquareError = document.getDouble("R-squared");
			Map<String, Object> summarys = algorithmModelService.getSummarys(document);
			rsMaps.put("meanSquareError", meanSquareError);
			rsMaps.put("summary", summarys);
		}
		List<HashMap<String, Object>> corrStats = algorithmModelService.catCorr(database, modelId);
		List<HashMap<String, Object>> chiSqTests = algorithmModelService.catChiSqTest(database, modelId);
		client.close();
		rsMaps.put("corr", corrStats);
		rsMaps.put("tTest", chiSqTests);
		jr.setData(rsMaps);
		return jr;
	}
}
