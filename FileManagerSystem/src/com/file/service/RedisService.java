package com.file.service;

import java.util.List;

import com.file.model.Fileinfo;


public interface RedisService {
	public Fileinfo queryFileById(String id);

	public void addFileList(List<Fileinfo> files);

//	public List<FilePO> queryAllFile(PageBean pageBean);

	public void addFileList(Fileinfo fileinfo);
	
	public void addFileList(String field, String value);

//	public List<FilePO> queryAllFile();

//	public long listLength(String listKey);
}
