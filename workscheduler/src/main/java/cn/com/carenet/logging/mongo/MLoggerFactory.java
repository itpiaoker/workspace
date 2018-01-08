package cn.com.carenet.logging.mongo;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MLoggerFactory {
	public static ILoggerFactory getILoggerFactory() {
		return LoggerFactory.getILoggerFactory();
	}

	public static Logger getLogger(String name) {
		return LoggerFactory.getLogger(name);
	}

	public static MLogger getLogger(Class<?> clazz) {
		return new MLogger(clazz);
	}
}
