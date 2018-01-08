package com.file.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.file.model.Fileinfo;
import com.file.model.PageBean;
import com.file.service.FileService;
import com.file.service.RedisService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-beans.xml","/applicationContext.xml"})
public class RedisServiceTest {
	@Autowired
	FileService fileService;
	@Autowired
	RedisService redisService;
	@Test
	public void addFileList() throws Exception {
		
		PageBean pageBean = new PageBean();
		pageBean.setBegin(0);
		pageBean.setEnd(300);
		List<Fileinfo> fileList = fileService.queryAllFilePO(pageBean);
		for (Fileinfo filePO : fileList) {
			redisService.addFileList(filePO);
		}
	}
	
	@Test
	public void initRedisData() throws Exception {
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(100000);
		//pageSize*(currentPage-1),pageSize*currentPage
		//
		int total = fileService.totalNumber();
		int totalPage = total/pageBean.getPageSize()+1;
		
		//
		redisService.addFileList("fileSize",total+"");

		for (int i = 1; i <= totalPage; i++) {
			pageBean.setBegin(pageBean.getPageSize()*(i-1));
			pageBean.setEnd(pageBean.getPageSize()*i);
			List<Fileinfo> fileList = fileService.queryAllFilePO(pageBean);
			
			//for (Fileinfo filePO : fileList) {
				redisService.addFileList(fileList);
			//}
		}
	}
}
