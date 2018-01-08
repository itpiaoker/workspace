package cn.com.linnax.file.redis;
//package com.file.redis;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//
//import com.file.model.PageBean;
//import com.file.service.FileService;
//
//@Service("initRedisM")
//public class InitRedisDataManager {
//	
//	@Autowired
//	ThreadPoolTaskExecutor taskExecutor;
//	@Autowired
//	InitRedisData initRedis;
//	@Autowired
//	FileService fileService;
////	@Autowired
////	RedisServiceImpl redisService;
//	
//	public void initD() throws Exception{
//		
//		PageBean pageBean = new PageBean();
//		pageBean.setPageSize(10000);
//		//pageSize*(currentPage-1),pageSize*currentPage
//		//
//		int total = fileService.totalNumber();
//		int totalPage = total/pageBean.getPageSize()+1;
//		pageBean.setTotalPage(totalPage);
//		
//		//
////		redisService.addFileList("fileSize",total+"");
//		
//		for(int i=1;i <= pageBean.getTotalPage();i++){
//			pageBean.setCurrentPage(i);
//			initRedis.setTaskNum(i);
//			initRedis.setPageBean(pageBean);
//			initRedis.start();
//			initRedis.join();
//		}
//	}
//}
