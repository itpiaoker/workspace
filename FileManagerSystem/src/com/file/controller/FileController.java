//package com.file.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import net.sf.json.JSONArray;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.file.model.FilePO;
//import com.file.model.Fileinfo;
//import com.file.model.PageBean;
//import com.file.service.FileService;
//
//@Controller
//@RequestMapping("/file")
//public class FileController {
//
//	@Autowired
//	private FileService fileService;
//
//	@RequestMapping("queryAllFile")
//	public ModelAndView queryContentByProgramId(PageBean pageBean) {
//		ModelAndView mv = new ModelAndView();
//		int totalPage;
//		try {
////			 int count=fileService.queryAllFilePO();
////			 if(count%pageBean.getPageSize()==0){
////			 totalPage=count/pageBean.getPageSize();
////			 }else{
////			 totalPage=count/pageBean.getPageSize()+1;
////			 }
////			 pageBean.setTotalPage(totalPage);
////			 pageBean.setBegin((pageBean.getCurrentPage()-1)*pageBean.getPageSize());
////			 System.out.println("count==="+count);
////			 System.out.println("totalPage==="+totalPage);
//			List<Fileinfo> fileList = fileService.queryAllFilePO(pageBean);
//			System.out.println("contentList===" + fileList.size());
////			mv.addObject("contentList", fileList);
////			mv.addObject("pageBean", pageBean);
////			mv.setViewName("/index");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mv;
//	}
//	
//	@RequestMapping("queryAllJsonFile")
//	public ModelAndView queryAllJsonFile(HttpServletRequest request,HttpServletResponse response,PageBean pageBean) {
////		HttpSession session=request.getSession(true);
////		
//		System.out.println("param==========="+pageBean);
////		
////		PageBean pb=(PageBean) session.getAttribute("pageBean");
////		if(pb==null){
////			session.setAttribute("pageBean", pb);
////		}else{
////			if(pageBean.isIsChangePageBean()){
////				pb=pageBean;
////				pb.setIsChangePageBean(false);
////				session.setAttribute("pageBean", pb);
////			}
////		}
//
//		ModelAndView mv = new ModelAndView();
//		JSONArray array =new JSONArray();
//		long totalPage;
//		try {
//			 long count=fileService.totalNumber();
//			 if(count%pageBean.getPageSize()==0){
//			 totalPage=count/pageBean.getPageSize();
//			 }else{
//			 totalPage=count/pageBean.getPageSize()+1;
//			 }
//			 pageBean.setTotalPage(totalPage);
//			 pageBean.setBegin((pageBean.getCurrentPage()-1)*pageBean.getPageSize());
//			 System.out.println("count==="+count);
//			 System.out.println("totalPage==="+totalPage);
//			List<Fileinfo> fileList = fileService.queryAllFilePO(pageBean);
//			array.addAll(fileList);
//			response.getWriter().print(array.toString());
//			System.out.println(array);
//			
//			System.out.println("contentList===" + fileList.size());
////			mv.addObject("contentList", fileList);
////			mv.addObject("pageBean", pageBean);
////			mv.setViewName("/index");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//}
