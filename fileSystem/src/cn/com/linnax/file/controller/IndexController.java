package cn.com.linnax.file.controller;
//package com.file.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONArray;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.file.model.PageBean;
//
//@Controller
//@RequestMapping("/index")
//public class IndexController {
//	@Autowired
//	private IndexService indexService;
//	
//	@RequestMapping("queryIndex")
//	public ModelAndView queryIndex(String id, String name, String path, String parentPath, Integer minSize,
//			Integer maxSize, Integer minDepth, Integer maxDepth, String driver, String indexPath, PageBean pageBean,HttpServletRequest request,HttpServletResponse response) {
//		List<FilePO> list=null;
//		JSONArray array =new JSONArray();
//		try {
//			list=indexService.searchIndex(id, name, path, parentPath, minSize, maxSize, minDepth, maxDepth, driver, indexPath, pageBean);
//			array.addAll(list);
//			response.getWriter().print(array.toString());
//			System.out.println(array);
//			
//			System.out.println("contentList===" + list.size());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	
//	@RequestMapping("searchIndex")
//	public ModelAndView searchIndex(FilePO file, Integer minSize,Integer maxSize, Integer minDepth, Integer maxDepth, PageBean pageBean,HttpServletRequest request,HttpServletResponse response) {
//		System.out.println("param==========="+file);
//		List<FilePO> list=null;
//		JSONArray array =new JSONArray();
//		try {
//			String indexPath=System.getProperty("catalina.home")+"/index";
//			list=indexService.searchIndex(indexPath, file, minSize, maxSize, minDepth, maxDepth, pageBean);
//			array.addAll(list);
//			response.getWriter().print(array.toString());
//			System.out.println(array);
//			
//			System.out.println("contentList===" + list.size());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	
//}
