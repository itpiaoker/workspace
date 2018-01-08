//package cn.com.linnax.file.controller;
//
//import cn.com.linnax.file.model.Fileinfo;
//import cn.com.linnax.file.model.PageBean;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping
//public class FileController {
//
//	private Logger _log = Logger.getLogger(FileController.class);
//
//	@Autowired
////	private FileService fileService;
////
////	@RequestMapping("queryAllFile")
////	public ModelAndView queryContentByProgramId( ,@requestbody student ) {  json.stringify(s)
////		ModelAndView mv = new ModelAndView();
////		int totalPage;
////		try {
//////			 int count=fileService.queryAllFilePO();
//////			 if(count%pageBean.getPageSize()==0){
//////			 totalPage=count/pageBean.getPageSize();
//////			 }else{
//////			 totalPage=count/pageBean.getPageSize()+1;
//////			 }
//////			 pageBean.setTotalPage(totalPage);
//////			 pageBean.setBegin((pageBean.getCurrentPage()-1)*pageBean.getPageSize());
//////			 System.out.println("count==="+count);
//////			 System.out.println("totalPage==="+totalPage);
////			List<Fileinfo> fileList = fileService.queryAllFilePO(pageBean);
////			System.out.println("contentList===" + fileList.size());
//////			mv.addObject("contentList", fileList);
//////			mv.addObject("pageBean", pageBean);
//////			mv.setViewName("/index");
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////		return mv;
////	}
//
//	@ResponseBody
//	@RequestMapping("queryAllJsonFile")
//	public Map<String, Object> queryAllJsonFile(HttpServletRequest request,PageBean pageBean,Fileinfo fileinfo) throws Exception {
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
//			totalNumber = fileService.totalNumber();
//			session.setAttribute("totalNumber", totalNumber);
//getpamrater()
//		}
//
//
//		//
//		ModelAndView mv = new ModelAndView();
////		JSONArray array =new JSONArray();
//		long totalPage;
//		List<Fileinfo> fileList = null;
//		try {
//
////			 if(count%pageBean.getPageSize()==0){
////			 totalPage=count/pageBean.getPageSize();
////			 }else{
////			 totalPage=count/pageBean.getPageSize()+1;
////			 }
////			 pageBean.setTotalPage(totalPage);
////			 pageBean.setBegin((pageBean.getCurrentPage()-1)*pageBean.getPageSize());
////			 System.out.println("count==="+count);
////			 System.out.println("totalPage==="+totalPage);
//			fileList = fileService.queryAllFilePO(pageBean,fileinfo);
////			array.addAll(fileList);
////			response.getWriter().print(array.toString());
////			System.out.println(array);
//
////			System.out.println("contentList===" + fileList.size());
////			mv.addObject("contentList", fileList);
////			mv.addObject("pageBean", pageBean);
////			mv.setViewName("/index");
////			mv.setViewName(viewName);
//
//			resultMap.put("rows", fileList);
//			resultMap.put("page", pageBean.getPage());
//			resultMap.put("total", totalNumber);
//			return resultMap;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultMap;
//
//
//	}
//}
