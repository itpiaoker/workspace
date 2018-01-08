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
//import com.file.model.Fileinfo;
//import com.file.model.PageBean;
//import com.file.service.FileService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/spring-beans.xml","/applicationContext.xml"})
//public class FileServiceImplTest {
//
//	Logger _log = Logger.getLogger(FileServiceImplTest.class);
//	
//	@Autowired
//	FileService fileService;
//
//	@Test
//	public void queryAllFiles() throws Exception {
//		PageBean pageBean = new PageBean();
//		pageBean.setBegin(0);
//		pageBean.setEnd(300);
//		
//		Fileinfo fileinfo = new Fileinfo();
//		fileinfo.setName("java");
//		
//		List<Fileinfo> files = fileService.queryAllFilePO(pageBean,fileinfo);
//		for (Fileinfo file : files) {
//			System.out.println(file);
//		}
//		
//	}
//	
//	@Test
//	public void queryAllFilesByPage() throws Exception {
//		
//		PageBean pageBean = new PageBean();
//		pageBean.setPageSize(10);
//		//pageSize*(currentPage-1),pageSize*currentPage
//		//
//		int total = fileService.totalNumber();
//		int totalPage = total/pageBean.getPageSize()+1;
//		
////		List<Integer> list = new ArrayList<Integer>();
//		
//		
//		for (int i = 1; i <= totalPage; i++) {
//			System.out.println(i);
//			
//			pageBean.setBegin(pageBean.getPageSize()*(i-1));
//			pageBean.setEnd(pageBean.getPageSize()*i);
//			
//			List<Fileinfo> fileList = fileService.queryAllFilePO(pageBean);
//			
//			for (Fileinfo fileinfo : fileList) {
//				_log.info(fileinfo);
////				list.add(fileinfo.getId());
//			}
//			
////			fileService.deleteFileList(list);
//			
////			list.clear();
//			
//		}
//	}
//	
//	@Test
//	public void totalNumber() throws Exception{
//		System.out.println(fileService.totalNumber());
//	}
//	
//	
//}
