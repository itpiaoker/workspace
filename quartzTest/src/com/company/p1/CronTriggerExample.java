package com.company.p1;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Title:
 * Description:
 *
 * @author lianxy
 * @date 2017/11/25
 */
public class CronTriggerExample implements Job {

    Logger _log = LoggerFactory.getLogger(CronTriggerExample.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // Say Hello to the World and display the date/time
        _log.info("Hello World! - " + new Date());
    }

    public static void main(String[] args) throws Exception{
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName("job2");
        jobDetail.setGroup("group2");
        jobDetail.setJobClass(CronTriggerExample.class);

//        SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName("simpleTrigger");
        cronTrigger.setGroup("triggerGroup");
//        cronTrigger.setStartTime(new Date());
        cronTrigger.setCronExpression("0/5 * * * * ?");
//        cronTrigger.setRepeatInterval(1000);
//        cronTrigger.setRepeatCount(100);

        sched.scheduleJob(jobDetail, cronTrigger);

        sched.start();

    }

}
