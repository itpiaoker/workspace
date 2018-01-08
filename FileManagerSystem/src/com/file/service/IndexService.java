//package com.file.service;
//
//import java.util.List;
//
//import com.file.model.FilePO;
//import com.file.model.PageBean;
//
//public interface IndexService {
//
//	void createIndex(String IndexPath, List<FilePO> fielList) throws Exception;
//	
//	void createIndex(String IndexPath, FilePO file) throws Exception;
//
//	List<FilePO> searchIndex(String id, String name, String path, String parentPath, Integer minSize, Integer maxSize,
//			Integer minDepth, Integer maxDepth, String driver, String indexPath, PageBean pageBean) throws Exception;
//
//	int countTotalHits(String id, String name, String path, String parentPath, int minSize, int maxSize, int minDepth,
//			int maxDepth, String driver, String indexPath, PageBean pageBean) throws Exception;
//
//	List<FilePO> searchIndex(String indexPath,FilePO file, Integer minSize, Integer maxSize, Integer minDepth, Integer maxDepth,
//			PageBean pageBean) throws Exception;
//}