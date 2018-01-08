package cn.com.linnax.file.test;
//package com.file.test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.file.model.FilePO;
//import com.file.model.PageBean;
//import com.file.service.FileService;
//import com.file.service.IndexService;
//import com.file.service.RedisService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/com/file/spring/applicationContext.xml")
//public class IndexServiceImplTest {
//	private static Logger _log = Logger.getLogger(IndexServiceImplTest.class);
//	@Autowired
//	IndexService indexService;
//	@Autowired
//	FileService fileService;
//	@Autowired
//	RedisService redisService;
//
//	@Test
//	public void testCreateIndex() throws Exception {
//		List<FilePO> files = fileService.queryAllFilePO();
//		indexService.createIndex("D:/index", files);
//	}
//	
//	@Test
//	public void initIndex(){
//		PageBean pageBean = new PageBean();
//		pageBean.setPageSize(10);
//		pageBean.setEnd(pageBean.getPageSize());
//
//		long count = 0;
//		try {
//			count = 2;
//			count = fileService.totalNumber();
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
//					indexService.createIndex("G:\\apache-tomcat-6.0.29---2\\index", file);
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
//
//	@Test
//	public void testSearchIndex() throws Exception {
//		String indexPath = "D:\\apache-tomcat-6.0.41---2\\index";
//		FilePO file=new FilePO();
//		file.setName("boxed");
//		PageBean pageBean = new PageBean();
//		int minSize=0;
//		int maxSize=0;
//		int minDepth=0;
//		int maxDepth=0;
//		List<FilePO> files = indexService.searchIndex(indexPath, file, minSize, maxSize, minDepth, maxDepth, pageBean);
//		System.out.println(files.size());
//		for (FilePO filePO : files) {
//			System.out.println(filePO);
//		}
//	}
//
//}
