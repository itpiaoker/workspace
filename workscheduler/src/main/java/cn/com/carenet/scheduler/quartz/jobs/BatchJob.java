package cn.com.carenet.scheduler.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.carenet.scheduler.utils.ExecJobUtils;

@Service
public class BatchJob implements Job {
	final private static Logger LOG = LoggerFactory.getLogger(BatchJob.class);
	protected JobParameter jobParam;
	@Autowired
	private ExecJobUtils execJobUtils;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.jobParam = (JobParameter) context.getJobDetail().getJobDataMap().get(JobParameter.JOB_PARAM);
		if (jobParam != null) {
			LOG.debug("Staring job...{}", jobParam.getWorkFlowID());
			execJobUtils.execSingleJob(jobParam.getWorkFlowID());
		} else {
			LOG.warn("Work flow ID:{}. Execute an empty work!", jobParam.getWorkFlowID());
		}
	}

}
