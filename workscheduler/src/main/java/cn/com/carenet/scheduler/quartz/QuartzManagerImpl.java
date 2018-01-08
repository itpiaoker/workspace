package cn.com.carenet.scheduler.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class QuartzManagerImpl implements QuartzManager {
	final static Logger LOG = LoggerFactory.getLogger(QuartzManagerImpl.class);
	@Autowired
	private Scheduler scheduler;
	/**
	 * when the Class is call in the first time in container, start the
	 * schedule.
	 */

	@Override
	public void addCronJob(JobParameter param) throws SchedulerException {
		try {
			// 构建job信息
			@SuppressWarnings("unchecked")
			JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(param.getJobClassName()))
					.withIdentity(param.getJobName(), param.getJobGroup()).build();
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(param.getCronExpression());
			// 按cronExpression表达式构建trigger,set the starting time
			CronTrigger trigger = TriggerBuilder.newTrigger().startNow()
					.withIdentity(param.getJobName(), param.getJobGroup()).withSchedule(scheduleBuilder).build();
			// 放入参数，运行时的方法可以获取
			jobDetail.getJobDataMap().put(JobParameter.JOB_PARAM, param);
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage());
		}
	}

	@Override
	public void modifyCronJob(JobParameter param) throws SchedulerException {
		LOG.debug("Modified the job:[JobName:{}, GroupName{}]", param.getJobName(), param.getJobGroup());
		TriggerKey triggerKey = TriggerKey.triggerKey(param.getJobName(), param.getJobGroup());
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(param.getCronExpression());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		trigger = trigger.getTriggerBuilder().startNow().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		scheduler.rescheduleJob(triggerKey, trigger);
	}

	@Override
	public void removeJob(JobParameter param) throws SchedulerException {
		JobKey jobKey = new JobKey(param.getJobName(), param.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	@Override
	public void pauseJob(JobParameter param) throws SchedulerException {
		JobKey jobKey = new JobKey(param.getJobName(), param.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	@Override
	public void resumeCronJob(JobParameter param) throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(param.getJobName(), param.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		trigger = trigger.getTriggerBuilder().startNow().withIdentity(triggerKey).build();
		scheduler.rescheduleJob(triggerKey, trigger);
	}

	@Override
	public void addNonReapeatJob(JobParameter param) throws SchedulerException {
		try {
			@SuppressWarnings("unchecked")
			JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(param.getJobClassName()))
					.withIdentity(param.getJobName(), param.getJobGroup()).build();

			TriggerKey triggerKey = TriggerKey.triggerKey(param.getJobName(), param.getJobGroup());
			long milliseconds = param.getTimestampExpression() - System.currentTimeMillis();
			if (milliseconds < 500) {
				milliseconds = 500;
			}
			SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInMilliseconds(milliseconds).withRepeatCount(0);// 重复执行的次数，因为加入任务的时候马上执行了，所以不需要重复，否则会多一次。

			SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow()
					.withSchedule(scheduleBuilder).build();
			jobDetail.getJobDataMap().put(JobParameter.JOB_PARAM, param);

			scheduler.scheduleJob(jobDetail, simpleTrigger);

		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage());
		}

	}

	@Override
	public boolean isJobExist(JobParameter param) throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(param.getJobName(), param.getJobGroup());
		return scheduler.checkExists(triggerKey);
	}

}
