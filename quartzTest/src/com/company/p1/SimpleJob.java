package com.company.p1;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
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
public class SimpleJob implements Job {

    Logger _log = LoggerFactory.getLogger(SimpleJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // Say Hello to the World and display the date/time
        _log.info("Hello World! - " + new Date());
    }

    public static void main(String[] args) throws Exception{
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName("job1");
        jobDetail.setGroup("group1");
        jobDetail.setJobClass(SimpleJob.class);

        SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
        simpleTrigger.setName("simpleTrigger");
        simpleTrigger.setGroup("triggerGroup");
        simpleTrigger.setStartTime(new Date());
        simpleTrigger.setRepeatInterval(1000);
        simpleTrigger.setRepeatCount(100);

        sched.scheduleJob(jobDetail, simpleTrigger);

        sched.start();

    }

}
