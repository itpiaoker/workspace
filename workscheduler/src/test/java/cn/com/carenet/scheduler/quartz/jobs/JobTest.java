package cn.com.carenet.scheduler.quartz.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carenet.scheduler.quartz.jobs.JobParameter;


public class JobTest implements Job {
	final static Logger LOG = LoggerFactory.getLogger(JobTest.class);
	SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date d = new Date();
	    String returnstr = DateFormat.format(d);
	    JobParameter jobParam = (JobParameter) context.getJobDetail().getJobDataMap().get("jobParam");
		try {
			LOG.info("Job:[{}] is running, System Time:[{}] ",jobParam.getJobName(),returnstr);
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
		
	}
}
