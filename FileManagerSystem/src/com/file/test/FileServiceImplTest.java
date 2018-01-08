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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-beans.xml","/applicationContext.xml"})
public class FileServiceImplTest {

	@Autowired
	FileService fileService;

	@Test
	public void queryAllFilesByPage() throws Exception {
		PageBean pageBean = new PageBean();
		pageBean.setBegin(0);
		pageBean.setEnd(300);
		List<Fileinfo> files = fileService.queryAllFilePO(pageBean);
		for (Fileinfo fileinfo : files) {
			System.out.println(fileinfo);
		}
		
	}
	
	@Test
	public void totalNumber() throws Exception{
		System.out.println(fileService.totalNumber());
	}
	
	
}
