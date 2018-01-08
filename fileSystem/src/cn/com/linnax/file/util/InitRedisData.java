package cn.com.linnax.file.util;
//package com.file.util;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.file.model.FilePO;
//import com.file.model.PageBean;
//import com.file.service.FileService;
//import com.file.service.RedisService;
//
//public class InitRedisData {
//
//	private Logger _log = Logger.getLogger(InitRedisData.class);
//
//	RedisService redisService = null;
//
//	FileService fileService = null;
//
//	public static void main(String[] args) {
//		InitRedisData initRedisData = new InitRedisData();
//		initRedisData.init();
//	}
//
//	public void init() {
//		// 0 99 >begin=size*0--->begin=size*(current-1)
//		// 100 199---->begin=size*1
//		// 200 299---->begin=size*2
//		//
//		ApplicationContext context = new ClassPathXmlApplicationContext("/com/file/spring/applicationContext.xml");
//
//		fileService=context.getBean("fileService", FileService.class);
//		redisService=context.getBean("redisService", RedisService.class);
//
//		PageBean pageBean = new PageBean();
//		pageBean.setPageSize(10000);
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
//		long countter = 0;
//		for (int i = 1; i <= pageBean.getTotalPage(); i++) {
//			pageBean.setBegin(pageBean.getPageSize() * (i - 1));
//			List<FilePO> list = null;
//			try {
//				list = fileService.queryAllFilePO(pageBean);
//			} catch (Exception e) {
//				_log.error(e.getMessage());
//			}
//			for (FilePO filePO : list) {
//				redisService.addFileList(filePO);
//				countter++;
//				_log.info("正在初始化第" + countter + "条数据==================" + filePO);
//			}
//		}
//	}
//}
