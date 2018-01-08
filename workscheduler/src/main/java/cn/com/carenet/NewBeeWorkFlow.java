package cn.com.carenet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.com.carenet.scheduler.utils.PropertiesReader;

//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan("cn.com.carenet.scheduler.mapper")
public class NewBeeWorkFlow {
	final private static Logger LOG = LoggerFactory.getLogger(NewBeeWorkFlow.class);

	public static void main(String[] args) {
		System.setProperty(PropertiesReader.PROPERTIES_FILE,
				new File(new File(ClassLoader.getSystemResource("").getPath()).getParent(),
						"config/application.properties").toString());
		System.setProperty(StdSchedulerFactory.PROPERTIES_FILE,
				new File(new File(ClassLoader.getSystemResource("").getPath()).getParent(), "config/quartz.properties")
						.toString());
		SpringApplication springApp= new SpringApplication(NewBeeWorkFlow.class);
		Properties defaultProperties = new Properties();
		try {
			defaultProperties.load(new FileInputStream(new File(System.getProperty(PropertiesReader.PROPERTIES_FILE))));
			springApp.setDefaultProperties(defaultProperties);
			springApp.run(args);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}
}