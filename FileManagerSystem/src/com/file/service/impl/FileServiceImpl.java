package com.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.file.dao.FileinfoMapper;
import com.file.model.Fileinfo;
import com.file.model.FileinfoExample;
import com.file.model.PageBean;
import com.file.service.FileService;

@Service("fileService")
public class FileServiceImpl implements FileService{
	
	@Autowired
	FileinfoMapper fileinfoMapper;
	
	@Override
	public List<Fileinfo> queryAllFilePO(PageBean pageBean) throws Exception {
		FileinfoExample example = new FileinfoExample();
		if(pageBean != null){
			example.setLimitStart(pageBean.getBegin());
			example.setLimitEnd(pageBean.getEnd());
		}
		List<Fileinfo> list=fileinfoMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Fileinfo> queryAllFilePO() throws Exception {
		List<Fileinfo> list=fileinfoMapper.selectByExample(null);
		return list;
	}

	@Override
	public int totalNumber() throws Exception {
		return fileinfoMapper.totalNumber();
	}
//
//	@Override
//	public void updateFile(Fileinfo fileinfo) {
//		// TODO Auto-generated method stub
//		
//	}

}
