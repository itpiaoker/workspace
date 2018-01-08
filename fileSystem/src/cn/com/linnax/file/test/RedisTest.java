package cn.com.linnax.file.test;
//package com.file.test;
//
//import java.util.List;
//
//import net.sf.json.JSONObject;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import redis.clients.jedis.Jedis;
//
//import com.file.model.FilePO;
//import com.file.service.FileService;
//import com.file.service.RedisService;
//import com.file.util.RedisDB;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/applicationContext.xml")
//public class RedisTest {
//
//	@Autowired
//	RedisService redisService;
//	@Autowired
//	FileService fileService;
//
//	@Test
//	public void addList() throws Exception {
//		/*List<FilePO> list = fileService.queryAllFilePO();
//		redisService.addFileList(fileService.queryAllFilePO());*/
//		Jedis redis = RedisDB.getRedis();
//		redis.set("abc", "lisi");
//	}
//
//	@Test
//	public void addOne() throws Exception {
//		FilePO filePO = new FilePO();
//		filePO.setId(1);
//		filePO.setName("a");
//		redisService.addFileList(filePO);
//	}
//
////	@Test
////	public void queryAll() throws Exception {
////		redisService.queryAllFile();
////	}
////
//	@Test
//	public void queryFileById() throws Exception {
//		FilePO file = redisService.queryFileById("1");
//		System.out.println(file);
//	}
////
////	@Test
////	public void listLength() throws Exception {
////		Long len = redisService.listLength("file_list");
////		System.out.println(len);
////	}
//
//}
