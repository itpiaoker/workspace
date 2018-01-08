package cn.com.carenet.scheduler.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.carenet.scheduler.quartz.QuartzManagerImpl;

public class QuartzFactory {
	final private static Logger LOG = LoggerFactory.getLogger(QuartzFactory.class);
	//singleton
	public static QuartzManagerImpl quartzJob = new QuartzManagerImpl();
	static{
		LOG.debug("Initialized Quartz.");
	}
	public static QuartzManagerImpl getQuartzjob() {
		return quartzJob;
	}
	
}
