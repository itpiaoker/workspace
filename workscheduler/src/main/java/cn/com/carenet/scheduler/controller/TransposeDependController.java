package cn.com.carenet.scheduler.controller;

import cn.com.carenet.scheduler.bean.JsonResult;
import cn.com.carenet.scheduler.service.TransposeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/7/3
 */
@RestController
@RequestMapping("/iwork/transpose")
public class TransposeDependController {
    final static Logger LOG = LoggerFactory.getLogger(TransposeDependController.class);
    @Autowired
    private TransposeService transposeService;
    
    
    @RequestMapping("/update")
    public JsonResult<String> updateAction(String workFlowID) {
    	transposeService.update(workFlowID);
        return null;
    }
    
    @RequestMapping("/start")
    public JsonResult<String> startAction(String workFlowID) {
    	transposeService.start(workFlowID);
        return null;
    }
    
    @RequestMapping("/stop")
    public JsonResult<String> stopAction(String workFlowID) {
    	transposeService.stop(workFlowID);
    	return null;
    }

}
