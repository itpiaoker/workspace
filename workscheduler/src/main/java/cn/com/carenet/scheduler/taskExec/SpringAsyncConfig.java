package cn.com.carenet.scheduler.taskExec;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class SpringAsyncConfig {
	private int corePoolSize = 10;
	private int maxPoolSize = 20;
	private int queueCapacity = 25;

	@Bean(name = "stormExecutor")
	public Executor stormExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(corePoolSize);
		taskExecutor.setMaxPoolSize(maxPoolSize);
		taskExecutor.setQueueCapacity(queueCapacity);
		taskExecutor.setThreadNamePrefix("STORM_JOB-");
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Bean(name = "sparkExecutor")
	public Executor sparkExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(corePoolSize);
		taskExecutor.setMaxPoolSize(maxPoolSize);
		taskExecutor.setQueueCapacity(queueCapacity);
		taskExecutor.setThreadNamePrefix("SPARK_JOB-");
		taskExecutor.initialize();
		return taskExecutor;
	}
}
