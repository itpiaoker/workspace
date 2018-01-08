package cn.com.carenet.scheduler.controller;

import cn.com.carenet.scheduler.bean.JsonResult;
import cn.com.carenet.scheduler.constant.JobStatusConstant;
import cn.com.carenet.scheduler.service.WorkFlowService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: Description:
 *
 * @author lianxy
 * @date 2017/5/19
 * @fix Sherard Lee
 */

@RestController
@RequestMapping("/iwork")
public class WorkFlowController {
	final static Logger LOG = LoggerFactory.getLogger(WorkFlowController.class);
	@Autowired
	private WorkFlowService workFlowService;

	@RequestMapping("/save")
	public JsonResult<String> saveAction(String workFlowID) {
		workFlowService.update(workFlowID, JobStatusConstant.typeSave);
		JsonResult<String> jr = new JsonResult<String>();
		if (workFlowService.isError()) {
			jr.setCode(500);
			jr.setDesc(workFlowService.getErrorMsg());
		}
		return jr;
	}

	@RequestMapping("/update")
	public JsonResult<String> updateAction(String workFlowID) {
		return saveAction(workFlowID);
	}

	@RequestMapping("/start")
	public JsonResult<String> startAction(String workFlowID) {
		workFlowService.start(workFlowID, JobStatusConstant.typeStart);
		JsonResult<String> jr = new JsonResult<String>();
		if (workFlowService.isError()) {
			jr.setCode(500);
			jr.setDesc(workFlowService.getErrorMsg());
		}
		return jr;
	}

	@RequestMapping("/stop")
	public JsonResult<String> stopAction(String workFlowID) {
		workFlowService.stop(workFlowID, JobStatusConstant.typeStop);
		JsonResult<String> jr = new JsonResult<String>();
		if (workFlowService.isError()) {
			jr.setCode(500);
			jr.setDesc(workFlowService.getErrorMsg());
		}
		return jr;
	}

	@RequestMapping("/delete")
	public JsonResult<String> deleteAction(String workFlowID) {
		String message = workFlowService.delete(workFlowID, JobStatusConstant.typeDelete);
		JsonResult<String> jr = new JsonResult<String>();
		if (message != null) {
			jr.setCode(500);
			jr.setDesc(message);
		}
		return jr;
	}

	/*
	 * @RequestMapping("/file") public JsonResult<String> urlAction() {
	 * JsonResult<String> jr = new JsonResult<String>();
	 * jr.setData(System.getProperty("user.dir")); return jr; }
	 */
}
