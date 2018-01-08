//package cn.com.linnax.file.controller;
//
//import cn.com.linnax.file.model.PageBean;
//import cn.com.linnax.file.model.XiDongWangWithBLOBs;
//import cn.com.linnax.file.service.XiDongWangService;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/xiDongWang")
//public class XiDongWangController {
//
//	private Logger _log = Logger.getLogger(XiDongWangController.class);
//
//	@Autowired
//	private XiDongWangService xiDongWangService;
//
//	@ResponseBody
//	@RequestMapping("queryAllJsonFile")
//	public Map<String, Object> queryAllJsonFile(HttpServletRequest request,PageBean pageBean,XiDongWangWithBLOBs xiDongWang) throws Exception {
//
//		_log.info("param==========="+pageBean);
//
//		if(pageBean == null){
//			pageBean = new PageBean();
//			pageBean.setRows(10);
//			pageBean.setPage(1);
//		}
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//
//		HttpSession session=request.getSession(true);
//		Integer totalNumber = (Integer) session.getAttribute("totalNumber");
//		if(totalNumber == null){
//			session.removeAttribute("totalNumber");
//			totalNumber = xiDongWangService.totalNumber();
//			session.setAttribute("totalNumber", totalNumber);
//		}
//
//		//
//		List<XiDongWangWithBLOBs> fileList = null;
//		try {
//
//			fileList = xiDongWangService.queryAllFiles(pageBean,xiDongWang);
//
//			resultMap.put("rows", fileList);
//			resultMap.put("page", pageBean.getPage());
//			resultMap.put("total", totalNumber);
//			return resultMap;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return resultMap;
//
//	}
//}
