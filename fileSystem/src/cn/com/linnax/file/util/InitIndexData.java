package cn.com.linnax.file.util;
//package com.file.util;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.file.model.FilePO;
//import com.file.model.PageBean;
//import com.file.service.FileService;
//import com.file.service.IndexService;
//
//public class InitIndexData {
//	private static Logger _log = Logger.getLogger(InitIndexData.class);
//	
//	private static IndexService indexService;
//	
//	private static FileService fileService;
//	
//	public static void main(String[] args) {
//		InitIndexData.init("G:\\apache-tomcat-6.0.29---2\\index");
//	}
//	public static void init(String indexPath){
//		
//		ApplicationContext context = new ClassPathXmlApplicationContext("/com/file/spring/applicationContext.xml");
//
//		fileService=context.getBean("fileService", FileService.class);
//		indexService=context.getBean("indexService", IndexService.class);
//		
//		PageBean pageBean = new PageBean();
//		pageBean.setPageSize(10);
//		pageBean.setEnd(pageBean.getPageSize());
//
//		long count = 0;
//		try {
//			count = 20;
////			count = fileService.totalNumber();
//		} catch (Exception e) {
//			_log.error(e.getMessage());
//		}
//
//		if ((count % pageBean.getPageSize()) == 0) {
//			pageBean.setTotalPage(count / pageBean.getPageSize());
//		} else {
//			pageBean.setTotalPage(count / pageBean.getPageSize() + 1);
//		}
//
//		List<FilePO> fielList = null;
//		for (int i = 1; i <= pageBean.getTotalPage(); i++) {
//			pageBean.setBegin(pageBean.getPageSize() * (i - 1));
//			
//			try {
//				fielList = fileService.queryAllFilePO(pageBean);
//			} catch (Exception e) {
//				_log.error(e.getMessage(), e);
//			}
//			
//			if(fielList==null) continue;
//			
//			try {
//				for (FilePO file : fielList) {
//					if(file==null) continue;
//					if(!file.isNeedIndex()) continue;
//					indexService.createIndex(indexPath, file);
//					file.setNeedIndex(false);
//					fileService.updateFile(file);
//				}
//			} catch (Exception e) {
//				_log.error(e.getMessage(), e);
//			}
//			
//			_log.info("============初始化索引完毕！！！==============");
//			
//		}
//	}
//}
