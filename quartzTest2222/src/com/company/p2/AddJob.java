package com.company.p2;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Title: dynamic
 * Description:
 *
 * @author lianxy
 * @date 2017/11/25
 */
public class AddJob implements Job {

    Logger _log = LoggerFactory.getLogger(AddJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // Say Hello to the World and display the date/time
//        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String color = jobDataMap.getString("color");
        _log.info("Hello World! - " + new Date() + color);
    }

    public static void main(String[] args) throws Exception{
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        String jobName = "job4";
        String triggerName = "trigger4";
        String groupName = "group";

        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(jobName);
        jobDetail.setGroup(groupName);
        jobDetail.setJobClass(AddJob.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("color", "green");
        jobDetail.setJobDataMap(jobDataMap);


        SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
        simpleTrigger.setName(triggerName);
        simpleTrigger.setGroup(groupName);
        simpleTrigger.setStartTime(new Date());
        simpleTrigger.setRepeatInterval(1000);
        simpleTrigger.setRepeatCount(100);


        sched.addJob(jobDetail, false);
        sched.scheduleJob(simpleTrigger);

        sched.start();
        sched.start();

    }

}
