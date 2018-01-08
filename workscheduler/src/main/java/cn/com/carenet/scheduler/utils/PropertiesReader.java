package cn.com.carenet.scheduler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carenet.scheduler.constant.SparkJobPropertiesBean;
import cn.com.carenet.scheduler.constant.StormJobProperties;

@SuppressWarnings("unused")
public class PropertiesReader {
	final static private Logger LOG = LoggerFactory.getLogger(PropertiesReader.class);
	final static public String PROPERTIES_FILE="carenet.scheduler.config.url";
	private static Properties prop;
	private static StormJobProperties stormJobPropertiesBean;
	private static SparkJobPropertiesBean sparkJobPropertiesBean;
	private static String hadoopJarUrl;
	private static org.apache.hadoop.conf.Configuration hdfsConfig = new org.apache.hadoop.conf.Configuration();
	private static int retryTimes=5;
	
	static {
		File jdbcConf = new File(System.getProperty(PROPERTIES_FILE));
		prop = new Properties();
		try {
			prop.load(new FileInputStream(jdbcConf));
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		String frameworkurl = prop.getProperty("job.storm.frameworkurl");
		String nimbusseeds = prop.getProperty("job.storm.nimbusseeds");
		String nimbusthriftport = prop.getProperty("job.storm.nimbusthriftport");

		String workflowsurl = prop.getProperty("job.spark.workflowsurl");
		String submitcmd = prop.getProperty("job.spark.submitcmd");
		String sparkframeworkurl = prop.getProperty("job.spark.frameworkurl");

		sparkJobPropertiesBean = new SparkJobPropertiesBean();
		stormJobPropertiesBean = new StormJobProperties();

		if (sparkframeworkurl != null)
			sparkJobPropertiesBean.setFrameworkurl(sparkframeworkurl);
		if (submitcmd != null)
			sparkJobPropertiesBean.setSubmitcmd(submitcmd);
		if (workflowsurl != null)
			sparkJobPropertiesBean.setWorkflowsurl(workflowsurl);
		if (frameworkurl != null)
			stormJobPropertiesBean.setFrameworkurl(frameworkurl);
		if (nimbusseeds != null)
			stormJobPropertiesBean.setNimbusseeds(nimbusseeds);
		if (nimbusthriftport != null)
			stormJobPropertiesBean.setNimbusthriftport(nimbusthriftport);
		String retryTimeStr = prop.getProperty("job.execute.retrytimes");
		if(retryTimeStr!=null){
			retryTimes = Integer.parseInt(retryTimeStr);
		}
		
		String hdfskeyFile = prop.getProperty("hdfs.keytab.file");
		String hdfsPrincipal = prop.getProperty("hdfs.kerberos.principal");
		String stormkeyFile = prop.getProperty("storm.keytab.file");
		String stormPrincipal = prop.getProperty("storm.kerberos.principal");
		String hivekeyFile = prop.getProperty("hive.keytab.file");
		String hivePrincipal = prop.getProperty("hive.kerberos.principal");
		if (hdfskeyFile != null) {
			hdfsConfig.set("hdfs.keytab.file", hdfskeyFile);
		}
		if (hdfsPrincipal != null) {
			hdfsConfig.set("hdfs.kerberos.principal", hdfsPrincipal);
		}
		hadoopJarUrl = prop.getProperty("job.hadoop.jarurl");

	}

	public static Properties getAppProperties() {
		return prop;
	}

	public static StormJobProperties getStormJobPropertiesBean() {
		return stormJobPropertiesBean;
	}

	public static SparkJobPropertiesBean getSparkJobPropertiesBean() {
		return sparkJobPropertiesBean;
	}

	public static org.apache.hadoop.conf.Configuration getHdfsConfig() {
		return hdfsConfig;
	}

	public static int getRetryTimes() {
		return retryTimes;
	}

	public static String getHadoopJarUrl() {
		return hadoopJarUrl;
	}
}
