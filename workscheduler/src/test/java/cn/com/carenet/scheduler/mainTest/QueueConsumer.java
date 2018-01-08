package cn.com.carenet.scheduler.mainTest;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
/**
 * 
 * @author Sherard Lee
 * @since 26/April/2017
 */
public class QueueConsumer {
	final static Logger LOG = LoggerFactory.getLogger(QueueConsumer.class);
	private BlockingQueue<Map<String,String>> statusQueue = new ArrayBlockingQueue<>(100);
	private JdbcTemplate dataTemplate;
	
	private boolean deamonSwitcher = true;
	
	
	
	public void runDeamon() throws InterruptedException{
		while(deamonSwitcher){
			if(!statusQueue.isEmpty()){
				Map<String,String> myStatus = statusQueue.poll();
				LOG.info("TopologyID:{},ModifiedType:{}",myStatus.get("id"),myStatus.get("type"));
			} else {
				SqlRowSet sqlRowSet = dataTemplate.queryForRowSet("select id,taskName from etl_taskinfo");
				while(sqlRowSet.next()){
		            String id = sqlRowSet.getString(1);
		            String name = sqlRowSet.getString(2);
		            LOG.info("Reading database: TopologyID:{},TaskName:{}",id,name);
		        }
				
				Thread.sleep(10000);
			}
		}
			
	}

	public void putQueue(Map<String,String> status) throws InterruptedException{
		this.statusQueue.put(status);
	}


	public boolean isDeamonSwitcher() {
		return deamonSwitcher;
	}

	public void setDeamonSwitcher(boolean deamonSwitcher) {
		this.deamonSwitcher = deamonSwitcher;
	}

	public JdbcTemplate getDataTemplate() {
		return dataTemplate;
	}

	public void setDataTemplate(JdbcTemplate dataTemplate) {
		this.dataTemplate = dataTemplate;
	}

}
