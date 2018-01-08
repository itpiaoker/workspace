package cn.com.linnax.file.service.impl;
//package com.file.service.impl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.Pipeline;
//
//import com.file.model.Fileinfo;
//import com.file.service.FileService;
//import com.file.service.RedisService;
//import com.file.util.Constant;
//import com.file.util.RedisDB;
//
//@Service("redisService")
//public class RedisServiceImpl implements RedisService {
//
//	@Autowired
//	FileService fileService;
//	
//	@Override
//	public Fileinfo queryFileById(String id) {
//		Jedis redis = RedisDB.getRedis();
//		Fileinfo fileinfo = null;
//		if(redis != null){
//			redis.select(5);
//			String json = redis.hget(Constant.REDIS_FILE_KEY, id);
//
//			JsonConfig config = new JsonConfig();
//			JSONObject jsonObject = JSONObject.fromObject(json, config);
//			fileinfo = (Fileinfo) JSONObject.toBean(jsonObject, Fileinfo.class);
//		}
//
//
//		return fileinfo;
//	}
//
//	@Override
//	public void addFileList(List<Fileinfo> files) {
//		
//		Map<String,Fileinfo> map = new HashMap<String, Fileinfo>();
//		
//		if (files != null && files.size() > 1){
//			Jedis redis = RedisDB.getRedis();
//			if(redis != null){
//				redis.select(5);
//				Pipeline p = redis.pipelined();
//				
//				for (Fileinfo file : files) {
//					map.clear();
//					JSONObject object = JSONObject.fromObject(file);
//					map.put(file.getId()+"", file);
//					
//					
//					p.hset(Constant.REDIS_FILE_KEY, file.getId()+"", object.toString());
//					p.hincrBy(Constant.REDIS_FILE_KEY, "realFileSie", 1);
//				}
//				
//				p.sync();
//				RedisDB.returnJedis(redis);
//			}
//		}		
//	}
//
//	@Override
//	public void addFileList(Fileinfo fileinfo) {
//		if (fileinfo != null){
//			JSONObject object = JSONObject.fromObject(fileinfo);
//			Jedis redis = RedisDB.getRedis();
//			if(redis != null){
//				redis.select(5);
//				//redis.hset(Constant.REDIS_FILE_KEY, fileinfo.getId()+"", object.toString());
//				Pipeline p = redis.pipelined();
//				p.hset(Constant.REDIS_FILE_KEY, fileinfo.getId()+"", object.toString());
//				p.hincrBy(Constant.REDIS_FILE_KEY, "realFileSie", 1);
//				p.sync();
//				RedisDB.returnJedis(redis);
//			}
//		}
//	}
//	
//	@Override
//	public void addFileList(String field, String value) {
//		if (value != null){
//			Jedis redis = RedisDB.getRedis();
//			if(redis != null){
//				redis.select(5);
//				redis.hset(Constant.REDIS_FILE_KEY, field, value);
//				RedisDB.returnJedis(redis);
//			}
//		}
//	}
//
//
////	@Override
////	public List<FilePO> queryAllFile(PageBean pageBean) {
////		List<FilePO> filePOs = new ArrayList<FilePO>();
////
////		Jedis redis = RedisDB.getRedis();
////		List<String> list = redis.lrange("file_list", 0, -1);
////		System.out.println(list);
////		JsonConfig config = new JsonConfig();
////		for (String json : list) {
////			JSONArray jsonArray = JSONArray.fromObject(json, config);
////			JSONObject jsonObject = jsonArray.getJSONObject(0);
////			FilePO filePO = (FilePO) JSONObject.toBean(jsonObject, FilePO.class);
////			System.out.println(filePO);
////			filePOs.add(filePO);
////		}
////
////		return filePOs;
////	}
//
////	@Override
////	public List<FilePO> queryAllFile() {
////		List<FilePO> filePOs = new ArrayList<FilePO>();
////
////		Jedis redis = RedisDB.getRedis();
////		List<String> list = redis.lrange("file_list", 0, -1);
////		System.out.println(list);
////		JsonConfig config = new JsonConfig();
////		for (String json : list) {
////			JSONArray jsonArray = JSONArray.fromObject(json, config);
////			JSONObject jsonObject = jsonArray.getJSONObject(0);
////			FilePO filePO = (FilePO) JSONObject.toBean(jsonObject, FilePO.class);
////			System.out.println(filePO);
////			filePOs.add(filePO);
////		}
////
////		return filePOs;
////	}
//
////	@Override
////	public long listLength(String listKey) {
////		Jedis redis = RedisDB.getRedis();
////		return redis.llen(listKey);
////	}
//
//}
