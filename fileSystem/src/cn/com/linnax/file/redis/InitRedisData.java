package cn.com.linnax.file.redis;
//package com.file.redis;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Service;
//
//import com.file.model.Fileinfo;
//import com.file.model.PageBean;
//import com.file.service.FileService;
//import com.file.service.RedisService;
//@Service("initRedis")
//public class InitRedisData extends Thread{
//    PageBean pageBean;
//    int taskNum;
//    
//	public PageBean getPageBean() {
//		return pageBean;
//	}
//
//	public void setPageBean(PageBean pageBean) {
//		this.pageBean = pageBean;
//	}
//
//	public int getTaskNum() {
//		return taskNum;
//	}
//
//	public void setTaskNum(int taskNum) {
//		this.taskNum = taskNum;
//	}
//
//	@Autowired
//	ThreadPoolTaskExecutor taskExecutor;
//	@Autowired
//	FileService fileService;
////	@Autowired
////	RedisService redisService;
//    
//	@Override
//    public void run() {
//
//		try {
//			
//			init();
//	        System.out.println("task "+taskNum+"执行完毕");
//	        
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//    }
//	
//	private void init() throws Exception{
//		pageBean.setBegin(pageBean.getPageSize() * (pageBean.getCurrentPage()-1));
//		pageBean.setEnd(pageBean.getPageSize() * pageBean.getCurrentPage());
//		List<Fileinfo> fileList = fileService.queryAllFilePO(pageBean);
////		redisService.addFileList(fileList);
//	}
//}
