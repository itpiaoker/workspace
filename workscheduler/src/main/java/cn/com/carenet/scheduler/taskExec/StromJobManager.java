package cn.com.carenet.scheduler.taskExec;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.storm.Config;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.ClusterSummary;
import org.apache.storm.generated.NotAliveException;
import org.apache.storm.generated.TopologySummary;
import org.apache.storm.generated.Nimbus.Client;
import org.apache.storm.thrift.TException;
import org.apache.storm.utils.NimbusClient;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.com.carenet.common.web.entity.TaskInfo;
import cn.com.carenet.scheduler.constant.DBConstant;
import cn.com.carenet.scheduler.constant.StormJobProperties;
import cn.com.carenet.scheduler.dao.JobRunInfoDAO;
import cn.com.carenet.scheduler.entity.TaskMetric;
import cn.com.carenet.scheduler.mapper.TaskInfoMapper;
import cn.com.carenet.scheduler.mapper.TaskMetricMapper;
import cn.com.carenet.scheduler.utils.CmdExecutor;
import cn.com.carenet.scheduler.utils.PropertiesReader;

@Service
public class StromJobManager {
	final static Logger LOG = LoggerFactory.getLogger(StromJobManager.class);

	final private static String SUBMIT_PREFIX = "storm jar";
	final private static String STORM_MAIN_CLASS_NAME = "cn.com.carenet.bigdata.storm.EnhancerTopology";
	final private static String STORM_JAR_NAME = "carenetNewBeeStorm.jar";
	final private static String DEFAULT_TEST_NIMBUS_SEEDS = "[\"localhost\"]";
	final private static int DEFAULT_TEST_NIMBUS_Thrift_PORT = 6627;
	final private static String DEFAULT_TEST_TOPOLOGY_NAME = "TopologyApp";
	final private static String DEFAULT_TEST_JAR_URL = "/tmp/storm-framework.jar";

	@Autowired
	private JobRunInfoDAO jobRunInfoDAO;
	@Autowired
	private TaskInfoMapper taskInfoMapper;
	@Autowired
	private TaskMetricMapper taskMetricMapper;
	@Autowired
	private StormJobProperties stormJobPropertiesBean;

	private static String nimbusSeeds = DEFAULT_TEST_NIMBUS_SEEDS;
	private static int numbusThriftPort = DEFAULT_TEST_NIMBUS_Thrift_PORT;
	private static String workFlowsUrl;

	private String topologyName = DEFAULT_TEST_TOPOLOGY_NAME;
	private String workFlowID;

	private static String topologyJarUrl = DEFAULT_TEST_JAR_URL;

	private NimbusClient nimbus;
	@SuppressWarnings("rawtypes")
	private static Map storm_conf;

	@SuppressWarnings("unchecked")
	public StromJobManager() {
		stormJobPropertiesBean = PropertiesReader.getStormJobPropertiesBean();
		workFlowsUrl = stormJobPropertiesBean.getFrameworkurl();
		nimbusSeeds = stormJobPropertiesBean.getNimbusseeds();
		if (stormJobPropertiesBean.getNimbusthriftport() != null)
			numbusThriftPort = Integer.parseInt(stormJobPropertiesBean.getNimbusthriftport());

		topologyJarUrl = Paths.get(workFlowsUrl, STORM_JAR_NAME).toString();

		JSONArray okstr = JSON.parseArray(nimbusSeeds);
		List<String> nimusList = new ArrayList<>();
		for (Object ok : okstr) {
			nimusList.add(ok.toString());
		}
		storm_conf = Utils.readStormConfig();
		storm_conf.put(Config.NIMBUS_SEEDS, nimusList);
		storm_conf.put(Config.NIMBUS_THRIFT_PORT, numbusThriftPort);
	}

	@Async("stormExecutor")
	public void submitToNimbus() throws InterruptedException, AlreadyAliveException, TException {
		StringBuffer commandStr = new StringBuffer("");
		commandStr.append(SUBMIT_PREFIX).append(" ").append(topologyJarUrl).append(" ").append(STORM_MAIN_CLASS_NAME)
				.append(" ").append(topologyName);
		CmdExecutor cmdExecutor = new CmdExecutor();
		Date startTime = jobRunInfoDAO.insertNewStart(workFlowID);

		TaskInfo execTaskInfo = new TaskInfo();
		execTaskInfo.setId(Integer.parseInt(workFlowID));
		execTaskInfo.setExecState(DBConstant.TASK_INFO_EXEC_STATE_EXECING);
		execTaskInfo.setHangStatus(DBConstant.TASK_INFO_HANG_STATUS_RUNNING);
		taskInfoMapper.updateByPrimaryKey(execTaskInfo);
		boolean execStat = false;
		try {
			execStat = cmdExecutor.execCommand(commandStr.toString(), null);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			execStat = false;
		}
		LOG.debug("Topology name:{}, status: {}", topologyName, getTopologyStat());
		if (execStat) {
			/*
			 * lock the saving status.
			 */
			execTaskInfo.setStartTime(startTime);
			execTaskInfo.setExecState(DBConstant.TASK_INFO_EXEC_STATE_EXECING);
			taskInfoMapper.updateByPrimaryKey(execTaskInfo);

			TaskMetric taskMetric = new TaskMetric();
			taskMetric.setTaskId(Integer.parseInt(workFlowID));
			taskMetric.setTopologyId(this.getTopologyID());
			taskMetric.setExecStat(1);
			taskMetricMapper.updateByPrimaryKey(taskMetric);
		} else {
			execTaskInfo.setExecState(DBConstant.TASK_INFO_EXEC_STATE_EXEC_FAILED);
			execTaskInfo.setHangStatus(DBConstant.TASK_INFO_HANG_STATUS_WAIT);
			taskInfoMapper.updateByPrimaryKey(execTaskInfo);
			jobRunInfoDAO.updateFailedEnd(workFlowID, startTime);
		}

	}

	public void submitToNimbus(String topologyName) throws InterruptedException, AlreadyAliveException, TException {
		this.topologyName = topologyName;
		this.submitToNimbus();
	}

	/**
	 * Deactivates the topology's spouts.
	 * 
	 * @param topologyName
	 * @throws NotAliveException
	 * @throws AuthorizationException
	 * @throws TException
	 */
	public void deactivateTopology() throws NotAliveException, AuthorizationException, TException {
		nimbus = NimbusClient.getConfiguredClient(storm_conf);
		nimbus.getClient().deactivate(topologyName);
		LOG.debug("Topology name:{}, status: {}", topologyName, getTopologyStat());
		int uptimeSecs = getTopologyUpTimeSecs();
		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(workFlowID);
		Date startTime = taskInfo.getStartTime();
		jobRunInfoDAO.updateStreamEnd(workFlowID, startTime, uptimeSecs);

		TaskInfo execTaskInfo = new TaskInfo();
		execTaskInfo.setId(Integer.parseInt(workFlowID));
		execTaskInfo.setExecState(DBConstant.TASK_INFO_EXEC_STATE_SAVE_SUCCESS);
		execTaskInfo.setHangStatus(DBConstant.TASK_INFO_HANG_STATUS_WAIT);
		taskInfoMapper.updateByPrimaryKey(execTaskInfo);
	}

	/**
	 * Deactivates the specified topology's spouts.
	 * 
	 * @param topologyName
	 * @throws NotAliveException
	 * @throws AuthorizationException
	 * @throws TException
	 */
	public void deactivateTopology(String topologyName) throws NotAliveException, AuthorizationException, TException {
		this.topologyName = topologyName;
		this.deactivateTopology();
	}

	/**
	 * Activates the topology's spouts.
	 * 
	 * @throws NotAliveException
	 * @throws AuthorizationException
	 * @throws TException
	 */
	public void activateTopology() throws NotAliveException, AuthorizationException, TException {
		nimbus = NimbusClient.getConfiguredClient(storm_conf);
		nimbus.getClient().activate(topologyName);
		LOG.debug("Topology name:{}, status: {}", topologyName, getTopologyStat());
	}

	/**
	 * Activates the specified topology's spouts.
	 * 
	 * @param topologyName
	 * @throws NotAliveException
	 * @throws AuthorizationException
	 * @throws TException
	 */
	public void activateTopology(String topologyName) throws NotAliveException, AuthorizationException, TException {
		this.topologyName = topologyName;
		this.activateTopology();
	}

	/**
	 * Kill this topology.
	 * 
	 * @return
	 * @throws TException
	 * @throws AuthorizationException
	 * @throws NotAliveException
	 * @throws InterruptedException
	 */
	@Async("stormExecutor")
	public void killTopology() throws NotAliveException, AuthorizationException, TException, InterruptedException {
		nimbus = NimbusClient.getConfiguredClient(storm_conf);
		Thread.sleep(10000);
		nimbus.getClient().killTopology(topologyName);
		LOG.debug("Topology name:{}, status: {}", topologyName, getTopologyStat());
	}

	/**
	 * Kill topology by using the given topology name.
	 * 
	 * @param topologyName
	 * @return
	 * @throws TException
	 * @throws AuthorizationException
	 * @throws NotAliveException
	 * @throws InterruptedException
	 */
	public void killTopology(String topologyName)
			throws NotAliveException, AuthorizationException, TException, InterruptedException {
		this.topologyName = topologyName;
		this.killTopology();
	}

	/**
	 * Get the status of the specified topology
	 * 
	 * @return
	 * @throws NotAliveException
	 * @throws AuthorizationException
	 * @throws TException
	 */
	public String getTopologyStat() throws NotAliveException, AuthorizationException, TException {
		Client client = NimbusClient.getConfiguredClient(storm_conf).getClient();
		ClusterSummary clusterSummary = client.getClusterInfo();
		List<TopologySummary> topologySummarys = clusterSummary.get_topologies();
		String status = "None";
		for (TopologySummary topologySummary : topologySummarys) {
			if (topologyName.equals(topologySummary.get_name())) {
				status = topologySummary.get_status();
			}
		}
		return status;
	}

	public String getTopologyID() throws AuthorizationException, TException {
		Client client = NimbusClient.getConfiguredClient(storm_conf).getClient();
		ClusterSummary clusterSummary = client.getClusterInfo();
		List<TopologySummary> topologySummarys = clusterSummary.get_topologies();
		String topologyID = null;
		for (TopologySummary topologySummary : topologySummarys) {
			if (topologyName.equals(topologySummary.get_name())) {
				topologyID = topologySummary.get_id();
			}
		}
		return topologyID;
	}

	public int getTopologyUpTimeSecs() throws AuthorizationException, TException {
		int upTimeSecs = 0;
		Client client = NimbusClient.getConfiguredClient(storm_conf).getClient();
		ClusterSummary clusterSummary = client.getClusterInfo();
		List<TopologySummary> topologySummarys = clusterSummary.get_topologies();

		for (TopologySummary topologySummary : topologySummarys) {
			if (topologyName.equals(topologySummary.get_name())) {
				upTimeSecs = topologySummary.get_uptime_secs();
			}
		}
		return upTimeSecs;
	}

	public String getTopologyStat(String topologyName) throws NotAliveException, AuthorizationException, TException {
		this.topologyName = topologyName;
		return this.getTopologyStat();
	}

	public String getNimbusSeeds() {
		return nimbusSeeds;
	}

	public void setNimbusSeeds(String nimbusSeeds) {
		StromJobManager.nimbusSeeds = nimbusSeeds;
	}

	public String getTopologyName() {
		return topologyName;
	}

	public void setTopologyName(String topologyName) {
		this.workFlowID = topologyName;
		this.topologyName = topologyName + "storm";
	}
}
