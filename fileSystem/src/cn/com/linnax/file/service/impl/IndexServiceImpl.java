package cn.com.linnax.file.service.impl;
//package com.file.service.impl;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.Field.Index;
//import org.apache.lucene.document.Field.Store;
//import org.apache.lucene.index.CorruptIndexException;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.BooleanClause;
//import org.apache.lucene.search.BooleanQuery;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.NumericRangeQuery;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//import org.springframework.stereotype.Service;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//import com.file.model.FilePO;
//import com.file.model.PageBean;
//import com.file.service.IndexService;
//
//@Service("indexService")
//public class IndexServiceImpl implements IndexService {
//	private static Logger _log = Logger.getLogger(IndexServiceImpl.class);
//
//	public IndexWriter getIndexWriter(String indexPath) throws Exception {
//		File path = new File(indexPath);
//		// 指定索引库目录
//		Directory directory = FSDirectory.open(path);
//		// 将Document添加到索引库
//		Analyzer analyzer = new IKAnalyzer();
//		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_35, analyzer);
//		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
//		return indexWriter;
//	}
//
//	public IndexReader getIndexReader(String indexPath) throws Exception {
//		File path = new File(indexPath);
//		// 指定索引库目录
//		Directory directory = FSDirectory.open(path);
//		// 读索引库
//		IndexReader indexReader = IndexReader.open(directory, false);
//
//		return indexReader;
//	}
//
//	public void createIndex(String IndexPath, List<FilePO> fielList) {
//		if (fielList == null)
//			return;
//
//		IndexWriter indexWriter = null;
//
//		try {
//			indexWriter = getIndexWriter(IndexPath);
//		} catch (Exception e) {
//			_log.debug(e.getMessage());
//		}
//
//		for (FilePO file : fielList) {
//			if (file == null)
//				continue;
//			_log.info("============正在创建对象索引！！！=============="+file);
//			Document document = new Document();
//			document.add(new Field("id", file.getId() + "", Store.YES, Index.NOT_ANALYZED));
//			document.add(new Field("size", file.getSize() + "", Store.YES, Index.NOT_ANALYZED));
//			document.add(new Field("depth", file.getDepth() + "", Store.YES, Index.NOT_ANALYZED));
//			document.add(new Field("driver", file.getDriver(), Store.YES, Index.NOT_ANALYZED));
//			document.add(new Field("name", file.getName(), Store.YES, Index.ANALYZED));
//			document.add(new Field("path", file.getPath(), Store.YES, Index.ANALYZED));
//			document.add(new Field("parentPath", file.getParentPath(), Store.YES, Index.ANALYZED));
//			//
//			try {
//				indexWriter.addDocument(document);
//			} catch (CorruptIndexException e) {
//				_log.error(e.getMessage());
//			} catch (IOException e) {
//				_log.error(e.getMessage());
//			}
//		}
//
//		try {
//			indexWriter.close();
//		} catch (CorruptIndexException e) {
//			_log.error(e.getMessage());
//		} catch (IOException e) {
//			_log.error(e.getMessage());
//		}
//
//		_log.debug("对象索引创建完毕!");
//	}
//
//	private void checkParameter(String id, String name, String path, String parentPath, Integer minSize,
//			Integer maxSize, Integer minDepth, Integer maxDepth, String driver, String indexPath, PageBean pageBean) {
//
//	}
//
//	@Override
//	public List<FilePO> searchIndex(String id, String name, String path, String parentPath, Integer minSize,
//			Integer maxSize, Integer minDepth, Integer maxDepth, String driver, String indexPath, PageBean pageBean)
//			throws Exception {
//
//		if (id == null)
//			id = "";
//		if (name == null)
//			name = "";
//		if (path == null)
//			path = "";
//		if (parentPath == null)
//			parentPath = "";
//		if (minSize == null)
//			minSize = 0;
//		if (maxSize == null)
//			maxSize = 0;
//		if (minDepth == null)
//			minDepth = 0;
//		if (maxDepth == null)
//			maxDepth = 0;
//		if (driver == null)
//			driver = "";
//		if (pageBean == null)
//			new PageBean();
//
//		BooleanQuery booleanQuery = quaryClause(id, name, path, parentPath, minSize, maxSize, minDepth, maxDepth,
//				driver);
//
//		IndexReader indexReader = getIndexReader(indexPath);
//		// 创建IndexSearcher
//		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//
//		TopDocs topDocs = null;
//		ScoreDoc[] scoreDocs = null;
//		try {
//			topDocs = indexSearcher.search(booleanQuery, 10000);
//			if (topDocs == null)
//				return null;
//		} catch (IOException e) {
//			_log.error(e.getMessage());
//		}
//
//		scoreDocs = topDocs.scoreDocs;
//
//		// 查询起始记录位置
//		long begin = pageBean.getPageSize() * (pageBean.getCurrentPage() - 1);
//		// 查询终止记录位置
//		long end = Math.min(begin + pageBean.getPageSize(), scoreDocs.length);
//
//		List<FilePO> fileList = handleScoreDocs(indexSearcher, scoreDocs, begin, end);
//
//		try {
//			indexSearcher.close();
//		} catch (IOException e) {
//			_log.error(e.getMessage());
//		}
//
//		return fileList;
//	}
//	@Override
//	public List<FilePO> searchIndex(String indexPath,FilePO file, Integer minSize,Integer maxSize, Integer minDepth, Integer maxDepth, PageBean pageBean)
//					throws Exception {
//		
//		if (file.getName() == null)
//			file.setName("");
//		if (file.getPath() == null)
//			file.setPath("");
//		if (file.getParentPath() == null)
//			file.setParentPath("");
//		if (file.getDriver() == null)
//			file.setDriver("");
//		if (pageBean == null)
//			new PageBean();
//		
//		BooleanQuery booleanQuery = quaryClause(file, minSize, maxSize, minDepth, maxDepth);
//		
//		IndexReader indexReader = getIndexReader(indexPath);
//		// 创建IndexSearcher
//		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//		
//		TopDocs topDocs = null;
//		ScoreDoc[] scoreDocs = null;
//		try {
//			topDocs = indexSearcher.search(booleanQuery, 100000000);
//			if (topDocs == null)
//				return null;
//		} catch (IOException e) {
//			_log.error(e.getMessage());
//		}
//		
//		scoreDocs = topDocs.scoreDocs;
//		
//		// 查询起始记录位置
//		long begin = pageBean.getPageSize() * (pageBean.getCurrentPage() - 1);
//		// 查询终止记录位置
//		long end = Math.min(begin + pageBean.getPageSize(), scoreDocs.length);
//		
//		List<FilePO> fileList = handleScoreDocs(indexSearcher, scoreDocs, begin, end);
//		
//		try {
//			indexSearcher.close();
//		} catch (IOException e) {
//			_log.error(e.getMessage());
//		}
//		
//		return fileList;
//	}
//
//	private List<FilePO> handleScoreDocs(IndexSearcher indexSearcher, ScoreDoc[] scoreDocs, long begin, long end)
//			throws CorruptIndexException, IOException {
//		List<FilePO> fileList = new ArrayList<FilePO>();
//
//		for (long i = begin; i < end; i++) {
//			ScoreDoc scoreDoc = scoreDocs[(int)i];
//			int docID = scoreDoc.doc;
//			Document document = indexSearcher.doc(docID);
//			// 获取Document的相关域 get("域名") String
//			int documentId = Integer.parseInt(document.get("id"));
//			int documentSize = Integer.parseInt(document.get("size"));
//			int documentDepth = Integer.parseInt(document.get("depth"));
//			String documentFilePath = document.get("path");
//			String documentName = document.get("name");
//			String documentDriver = document.get("driver");
//			String documentParentPath = document.get("parentPath");
//
//			//
//			FilePO filePO = new FilePO();
//			filePO.setId(documentId);
//			filePO.setDriver(documentDriver);
//			filePO.setName(documentName);
//			filePO.setPath(documentFilePath);
//			filePO.setDepth(documentDepth);
//			filePO.setParentPath(documentParentPath);
//			filePO.setSize(documentSize);
//			//
//			fileList.add(filePO);
//		}
//		return fileList;
//	}
//
//	private BooleanQuery quaryClause(String id, String name, String path, String parentPath, Integer minSize,
//			Integer maxSize, Integer minDepth, Integer maxDepth, String driver) {
//		BooleanQuery booleanQuery = new BooleanQuery();
//
//		Query queryId = new TermQuery(new Term("id", id));
//		Query queryName = new TermQuery(new Term("name", name));
//		Query queryFilePath = new TermQuery(new Term("path", path));
//		Query queryParentPath = new TermQuery(new Term("parentPath", parentPath));
//		Query querySize = NumericRangeQuery.newIntRange("size", minSize, maxSize, true, true);
//		Query queryDepth = NumericRangeQuery.newIntRange("depth", minDepth, maxDepth, true, true);
//		Query queryDriver = new TermQuery(new Term("driver", driver));
//
//		BooleanClause idClause = new BooleanClause(queryId, BooleanClause.Occur.SHOULD);
//		BooleanClause nameClause = new BooleanClause(queryName, BooleanClause.Occur.SHOULD);
//		BooleanClause filePathClause = new BooleanClause(queryFilePath, BooleanClause.Occur.SHOULD);
//		BooleanClause parentPathClause = new BooleanClause(queryParentPath, BooleanClause.Occur.SHOULD);
//		BooleanClause sizeClause = new BooleanClause(querySize, BooleanClause.Occur.SHOULD);
//		BooleanClause depthClause = new BooleanClause(queryDepth, BooleanClause.Occur.SHOULD);
//		BooleanClause driverClause = new BooleanClause(queryDriver, BooleanClause.Occur.SHOULD);
//
//		booleanQuery.add(idClause);
//		booleanQuery.add(nameClause);
//		booleanQuery.add(filePathClause);
//		booleanQuery.add(parentPathClause);
//		booleanQuery.add(sizeClause);
//		booleanQuery.add(depthClause);
//		booleanQuery.add(driverClause);
//		return booleanQuery;
//	}
//	private BooleanQuery quaryClause(FilePO file, Integer minSize,Integer maxSize, Integer minDepth, Integer maxDepth) {
//		BooleanQuery booleanQuery = new BooleanQuery();
//		
//		Query queryId = new TermQuery(new Term("id", file.getId()+""));
//		Query queryName = new TermQuery(new Term("name", file.getName()));
//		Query queryFilePath = new TermQuery(new Term("path", file.getPath()));
//		Query queryParentPath = new TermQuery(new Term("parentPath", file.getParentPath()));
//		Query querySize = NumericRangeQuery.newIntRange("size", minSize, maxSize, true, true);
//		Query queryDepth = NumericRangeQuery.newIntRange("depth", minDepth, maxDepth, true, true);
//		Query queryDriver = new TermQuery(new Term("driver", file.getDriver()));
//		
//		BooleanClause idClause = new BooleanClause(queryId, BooleanClause.Occur.SHOULD);
//		BooleanClause nameClause = new BooleanClause(queryName, BooleanClause.Occur.SHOULD);
//		BooleanClause filePathClause = new BooleanClause(queryFilePath, BooleanClause.Occur.SHOULD);
//		BooleanClause parentPathClause = new BooleanClause(queryParentPath, BooleanClause.Occur.SHOULD);
//		BooleanClause sizeClause = new BooleanClause(querySize, BooleanClause.Occur.SHOULD);
//		BooleanClause depthClause = new BooleanClause(queryDepth, BooleanClause.Occur.SHOULD);
//		BooleanClause driverClause = new BooleanClause(queryDriver, BooleanClause.Occur.SHOULD);
//		
//		booleanQuery.add(idClause);
//		booleanQuery.add(nameClause);
//		booleanQuery.add(filePathClause);
//		booleanQuery.add(parentPathClause);
//		booleanQuery.add(sizeClause);
//		booleanQuery.add(depthClause);
//		booleanQuery.add(driverClause);
//		return booleanQuery;
//	}
//
//	@Override
//	public int countTotalHits(String id, String name, String path, String parentPath, int minSize, int maxSize,
//			int minDepth, int maxDepth, String driver, String indexPath, PageBean pageBean) {
//
//		checkParameter(id, name, path, parentPath, minSize, maxSize, minDepth, maxDepth, driver, indexPath, pageBean);
//
//		// 创建IndexSearcher
//		IndexSearcher indexSearcher = null;
//		try {
//			IndexReader indexReader = getIndexReader(indexPath);
//			// 创建IndexSearcher
//			indexSearcher = new IndexSearcher(indexReader);
//		} catch (Exception e) {
//			_log.error(e.getMessage());
//		}
//
//		BooleanQuery booleanQuery = quaryClause(id, name, indexPath, parentPath, minSize, maxSize, minDepth, maxDepth,
//				driver);
//
//		TopDocs topDocs = null;
//		try {
//			topDocs = indexSearcher.search(booleanQuery, 10000);
//		} catch (IOException e) {
//			_log.error(e.getMessage());
//		}
//
//		try {
//			indexSearcher.close();
//		} catch (IOException e) {
//			_log.error(e.getMessage());
//		}
//
//		_log.debug("查询到的总记录数===================" + topDocs.totalHits);
//		return topDocs.totalHits;
//	}
//
//	@Override
//	public void createIndex(String IndexPath, FilePO file) throws Exception {
//		if (file == null)
//			return;
//
//		IndexWriter indexWriter = null;
//
//		try {
//			indexWriter = getIndexWriter(IndexPath);
//		} catch (Exception e) {
//			_log.debug(e.getMessage());
//		}
//
//		Document document = new Document();
//		document.add(new Field("id", file.getId() + "", Store.YES, Index.NOT_ANALYZED));
//		document.add(new Field("size", file.getSize() + "", Store.YES, Index.NOT_ANALYZED));
//		document.add(new Field("depth", file.getDepth() + "", Store.YES, Index.NOT_ANALYZED));
//		document.add(new Field("driver", file.getDriver(), Store.YES, Index.NOT_ANALYZED));
//		
//		if(file.getName()!=null){
//			document.add(new Field("name", file.getName(), Store.YES, Index.ANALYZED));
//		}
//		if(file.getPath()!=null){
//			document.add(new Field("path", file.getPath(), Store.YES, Index.ANALYZED));
//		}
//		if(file.getParentPath()!=null){
//			document.add(new Field("parentPath", file.getParentPath(), Store.YES, Index.ANALYZED));
//		}
//		
//		//
//		try {
//			_log.info("============正在创建对象索引！！！=============="+file);
//			indexWriter.addDocument(document);
//		} catch (CorruptIndexException e) {
//			_log.error(e.getMessage());
//		} catch (IOException e) {
//			_log.error(e.getMessage());
//		}
//
//		try {
//			indexWriter.close();
//		} catch (CorruptIndexException e) {
//			_log.error(e.getMessage());
//		} catch (IOException e) {
//			_log.error(e.getMessage());
//		}
//
//		_log.debug(file+"==============对象索引创建完毕!");
//	}
//
//}
