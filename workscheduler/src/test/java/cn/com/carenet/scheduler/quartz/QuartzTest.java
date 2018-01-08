package cn.com.carenet.scheduler.quartz;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carenet.scheduler.quartz.jobs.JobParameter;
import cn.com.carenet.scheduler.quartz.jobs.JobTest;

public class QuartzTest {
	final static Logger LOG = LoggerFactory.getLogger(QuartzTest.class);
	static QuartzManagerImpl quartzJob = new QuartzManagerImpl();
	public QuartzTest(){
		
	}
	public static void main(String[] args) {
		try {
			runJob();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//argsTest();
	}
	public static void runJob() throws SchedulerException{
		
		JobParameter param = new JobParameter();
		JobParameter param2 = new JobParameter();
		param.setCronExpression("0/10 * * * * ?");
		param.setJobName("timer");
		param.setJobGroup("taskId");
		//param.setJobTrigger("timer");
		param.setJobClassName(JobTest.class.getName());
		
		param2.setCronExpression("/5 * * * * ?");
		param2.setJobName("timer1");
		param2.setJobGroup("taskId");
		//param2.setJobTrigger("timer1");
		param2.setJobClassName(JobTest.class.getName());
		
		
		quartzJob.addCronJob(param);
		quartzJob.addCronJob(param2);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			LOG.info(e.getMessage());
		}
		quartzJob.pauseJob(param);
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			LOG.info(e.getMessage());
		}
		quartzJob.resumeCronJob(param);
		//param.setStartTimeStamp(System.currentTimeMillis());
		//quartzJob.modifyJobTime(param);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			LOG.info(e.getMessage());
		}
		quartzJob.removeJob(param);
		quartzJob.removeJob(param2);
		
	}
//	protected static void argsTest(){
//		String filePath = "e:/pagefile/测试密码.txt";
//		if(filePath.indexOf("hdfs:")>=0){
//			org.apache.hadoop.fs.Path hdfsPath = new org.apache.hadoop.fs.Path(filePath);
//			filePath = hdfsPath.toString();
//		}else if(filePath.indexOf("file:")>=0){
//			filePath = Paths.get(filePath.substring(7)).toString();
//		}else{
//			filePath = Paths.get(filePath).toString();
//		}
//		
//		System.out.println(filePath);
//		
//		Date time= new Date();
//		System.out.println(time.toString());
//	}
}
