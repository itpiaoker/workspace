package com.file.service;

import java.util.List;

import com.file.model.Fileinfo;
import com.file.model.PageBean;

public interface FileService {
	
	public List<Fileinfo> queryAllFilePO(PageBean pageBean) throws Exception;

	public List<Fileinfo> queryAllFilePO() throws Exception;

	public int totalNumber() throws Exception;
	
//	public void updateFile(Fileinfo fileinfo);
}
