package com.company.p2;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Title: dynamic
 * Description:
 *
 * @author lianxy
 * @date 2017/11/25
 */
public class JobsManager{
    Logger _log = LoggerFactory.getLogger(JobsManager.class);
    static StdSchedulerFactory sf = new StdSchedulerFactory();

    public static void addNonRepeatJob(SimpleJob job, String trigName, String trigGroupName, Date time) throws Exception {

        Scheduler sched = sf.getScheduler();

        TriggerKey triggerKey = new TriggerKey(trigName, trigGroupName);
        if(!sched.checkExists(triggerKey)){
            //
            String triggerName = trigName;
            SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
            simpleTrigger.setName(triggerName);
            simpleTrigger.setGroup(trigGroupName);
//            simpleTrigger.setRepeatInterval(Integer.MAX_VALUE);
            simpleTrigger.setRepeatInterval(2000);
            simpleTrigger.setRepeatCount(10);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date d = sdf.parse("2017-12-04 15:02:00");
            simpleTrigger.setStartTime(time);


            JobKey jobKey = new JobKey(job.jobName, job.groupName);
            if(!sched.checkExists(jobKey)){
                JobDetailImpl jobDetail = new JobDetailImpl();
                jobDetail.setName(job.jobName);
                //        jobDetail.setGroup(job.groupName);
                jobDetail.setJobClass(job.getClass());
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("jobName", job.jobName);
                jobDataMap.put("groupName", job.groupName);
                jobDetail.setJobDataMap(jobDataMap);
                //
                sched.scheduleJob(jobDetail, simpleTrigger);

//                sched.addJob(jobDetail, false);

            }

        }


    }


    public static void addRepeatJob(SimpleJob job, String trigName, String trigGroupName) throws Exception {

        Scheduler sched = sf.getScheduler();

        TriggerKey triggerKey = new TriggerKey(trigName, trigGroupName);
        if(!sched.checkExists(triggerKey)){
            //
            String triggerName = trigName;
            CronTriggerImpl cronTrigger = new CronTriggerImpl();
            cronTrigger.setName("simpleTrigger");
            cronTrigger.setGroup("triggerGroup");
    //        cronTrigger.setStartTime(new Date());
//            cronTrigger.setCronExpression("0/10 * * * * ?");
    //        cronTrigger.setRepeatInterval(1000);
    //        cronTrigger.setRepeatCount(100);
    //        cronTrigger.getEndTime()


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse("2017-12-04 15:02:00");
//            simpleTrigger.setStartTime(d);

//            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                    .withIntervalInMilliseconds(0).withRepeatCount(0);// 重复执行的次数，因为加入任务的时候马上执行了，所以不需要重复，否则会多一次。


            //
            JobKey jobKey = new JobKey(job.jobName, job.groupName);
            if(!sched.checkExists(jobKey)){
                JobDetailImpl jobDetail = new JobDetailImpl();
                jobDetail.setName(job.jobName);
                //        jobDetail.setGroup(job.groupName);
                jobDetail.setJobClass(job.getClass());
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("jobName", job.jobName);
                jobDataMap.put("groupName", job.groupName);
                jobDetail.setJobDataMap(jobDataMap);
                //
//                sched.scheduleJob(jobDetail, simpleTrigger);
            }

        }

//        sched.addJob(jobDetail, false);

    }

    public static void main(String[] args)  throws Exception {
        SimpleJob j1 = new SimpleJob("j1", "g1");
        SimpleJob j2 = new SimpleJob("j2", "g1");
        SimpleJob j3 = new SimpleJob("j3", "g1");

        addNonRepeatJob(j1, "t1", "tg1", new Date());
        addNonRepeatJob(j2, "t2", "tg1", new Date());
        addNonRepeatJob(j3, "t3", "tg1", new Date());

        Scheduler scheduler = sf.getScheduler();



//        System.out.println("NumberOfJobs\t" + scheduler.getMetaData().getNumberOfJobsExecuted());
//        System.out.println("Version\t" + scheduler.getMetaData().getVersion());

        if (!scheduler.isShutdown()) {
            scheduler.start();
        }

    }



}
