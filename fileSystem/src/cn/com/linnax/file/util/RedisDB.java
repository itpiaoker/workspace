//package cn.com.linnax.file.util;
//
//import org.apache.log4j.Logger;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//public class RedisDB {
//
//	private static Logger log = Logger.getLogger(RedisDB.class);
//
//	private static JedisPool pool;
//
//	public static void initPool() {
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxWaitMillis(-1);
//		pool = new JedisPool(config, RedisConstant.RS_URL, RedisConstant.RS_PORT, RedisConstant.RS_CONNECTION_TIMEOUT);
//	}
//
//	public static Jedis getRedis() {
//		Jedis jedis = null;
//		try {
//			if(pool == null) {
//				initPool();
//			}
//			jedis = pool.getResource();
////			jedis.auth(RedisConstant.RS_PASSWORD);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return jedis;
//	}
//
//	public static void returnJedis(Jedis jedis) {
//		if (jedis != null) {
//			pool.returnResource(jedis);
//		}
//	}
//
//}
