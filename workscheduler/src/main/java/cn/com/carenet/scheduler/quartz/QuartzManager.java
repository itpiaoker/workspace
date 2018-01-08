package cn.com.carenet.scheduler.quartz;

import org.quartz.SchedulerException;

import cn.com.carenet.scheduler.quartz.jobs.JobParameter;

/**
 * @Title Quartz管理类
 * @Description Cron表达式举例：<br/>
 *              在子表达式（分钟）里的“0/15”表示从第0分钟开始，每15分钟<br/>
 *              "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发<br/>
 *              "0 0 12 * * ?" 每天中午十二点触发<br/>
 *              "0 15 10 ? * *" 每天早上10：15触发<br/>
 *              "0 15 10 * * ?" 每天早上10：15触发<br/>
 *              "0 * 14 * * ?" 每天从下午2点开始到2点59分每分钟一次触发<br/>
 *              "0 0/5 14 * * ?" 每天从下午2点开始到2：55分结束每5分钟一次触发<br/>
 *              "0 0/5 14,18 * * ?" 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发<br/>
 *              "0 0-5 14 * * ?" 每天14:00至14:05每分钟一次触发<br/>
 *              "0 10,44 14 ? 3 WED" 三月的每周三的14：10和14：44触发<br/>
 *              "0 15 10 ? * MON-FRI" 每个周一、周二、周三、周四、周五的10：15触发<br/>
 * 
 * @Copyright carenet.com.cn
 * @author Sherard Lee
 * @version v20170505
 *
 */
public interface QuartzManager {
	
	void addNonReapeatJob(JobParameter param) throws SchedulerException;
	
	/**
	 * add crontab job by using the settings specified in JobParameter
	 * @param param
	 */
	void addCronJob(JobParameter param) throws SchedulerException;
	
	/**
	 * modify the job by using the settings specified in JobParameter
	 * @param param
	 */
	void modifyCronJob(JobParameter param) throws SchedulerException;
	
	/**
	 * remove the job by using the given name and group
	 * @param param
	 */
	void removeJob(JobParameter param) throws SchedulerException;
	
	/**
	 * pause the job by using the given name and group
	 * @param param
	 */
	void pauseJob(JobParameter param) throws SchedulerException;
	
	/**
	 * resume the job by using the given name and group
	 * @param param
	 */
	void resumeCronJob(JobParameter param) throws SchedulerException;
	/**
	 * 
	 * @param param
	 */
	boolean isJobExist(JobParameter param) throws SchedulerException;
}
