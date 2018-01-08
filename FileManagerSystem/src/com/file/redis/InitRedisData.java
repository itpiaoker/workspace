package com.file.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.file.model.Fileinfo;
import com.file.model.PageBean;
import com.file.service.FileService;
import com.file.service.RedisService;

public class InitRedisData implements Runnable{
    private int taskNum;
	@Autowired
	FileService fileService;
	@Autowired
	RedisService redisService;
    
    public InitRedisData(int num) {
        this.taskNum = num;
    }
     
    @Override
    public void run() {
        System.out.println("正在执行task "+taskNum);
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
        System.out.println("task "+taskNum+"执行完毕");
    }
}
