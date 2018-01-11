package com.company.p2;

import org.quartz.*;
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
    String jobName;
    String groupName;
    public SimpleJob(){}
    public SimpleJob(String jName, String gName){
        this.jobName = jName;
        this.groupName = gName;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            System.out.println("FireInstanceId\t" + context.getFireInstanceId());
            System.out.println("fireCount\t" + context.getRefireCount());
            System.out.println("Result\t" + context.getResult());
            Scheduler scheduler = context.getScheduler();
            JobDataMap jobDataMap = context.getMergedJobDataMap();
            String jobName = jobDataMap.getString("jobName");
            String groupName = jobDataMap.getString("groupName");
            _log.info("$jobName\t" +  "$groupName");
            System.out.println("NumberOfJobs\t" + scheduler.getMetaData().getNumberOfJobsExecuted());
//            JobKey jobKey = new JobKey(jobName, groupName);
//            scheduler.deleteJob(jobKey);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) throws Exception{
//        SchedulerFactory sf = new StdSchedulerFactory();
//        Scheduler sched = sf.getScheduler();
//
//        JobDetailImpl jobDetail = new JobDetailImpl();
//        jobDetail.setName("job1");
//        jobDetail.setGroup("group1");
//        jobDetail.setJobClass(SimpleJob.class);
//
//        SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
//        simpleTrigger.setName("simpleTrigger");
//        simpleTrigger.setGroup("triggerGroup");
//        simpleTrigger.setStartTime(new Date());
//        simpleTrigger.setRepeatInterval(1000);
//        simpleTrigger.setRepeatCount(100);
//
//        sched.scheduleJob(jobDetail, simpleTrigger);
//
//        sched.start();
//
//    }

}
