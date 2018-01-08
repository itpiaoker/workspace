package cn.com.carenet.scheduler.bean.storm;

public class TopologyEnvProp {

	final private static int DEFAULT_TICK_TUPLE_FREQ = 0;
	final private static int DEFAULT_WORKER_NUM = 1;
	final private static int DEFAULT_PARALLELISM_NUM = 0;
	final private static int DEFAULT_ACKER_NUM = 0;

	private String sourceName;
	private boolean debug = false;
	private int ackerNum = DEFAULT_ACKER_NUM;
	private int workerNum = DEFAULT_WORKER_NUM;
	private int maxParallelismNum = DEFAULT_PARALLELISM_NUM;
	private String topologyName;
	private int tickTupleFreqSecsForAll = DEFAULT_TICK_TUPLE_FREQ;
	/*
	 * components record the last elements' informations including id(preOpID),
	 * which data source comes from(sourcePrimary/streamID), groupMethod(in
	 * storm)
	 */
	private int transferBufferSize;
	private int receiverBufferSize;
	private int excutorReceiveBufferSize;
	private int excutorSendBufferSize;
	private int heartBeatFrequencySecs;
	private int sleepSpoutWaitStrategyTimeMs;
	private int zookeeperPort;

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public int getAckerNum() {
		return ackerNum;
	}

	public void setAckerNum(int ackerNum) {
		this.ackerNum = ackerNum;
	}

	public int getWorkerNum() {
		return workerNum;
	}

	public void setWorkerNum(int workerNum) {
		this.workerNum = workerNum;
	}

	public int getMaxParallelismNum() {
		return maxParallelismNum;
	}

	public void setMaxParallelismNum(int maxParallelismNum) {
		this.maxParallelismNum = maxParallelismNum;
	}

	public String getTopologyName() {
		return topologyName;
	}

	public void setTopologyName(String topologyName) {
		this.topologyName = topologyName;
	}

	public int getTickTupleFreqSecsForAll() {
		return tickTupleFreqSecsForAll;
	}

	public void setTickTupleFreqSecsForAll(int tickTupleFreqSecsForAll) {
		this.tickTupleFreqSecsForAll = tickTupleFreqSecsForAll;
	}

	public int getTransferBufferSize() {
		return transferBufferSize;
	}

	public void setTransferBufferSize(int transferBufferSize) {
		this.transferBufferSize = transferBufferSize;
	}

	public int getReceiverBufferSize() {
		return receiverBufferSize;
	}

	public void setReceiverBufferSize(int receiverBufferSize) {
		this.receiverBufferSize = receiverBufferSize;
	}

	public int getExcutorReceiveBufferSize() {
		return excutorReceiveBufferSize;
	}

	public void setExcutorReceiveBufferSize(int excutorReceiveBufferSize) {
		this.excutorReceiveBufferSize = excutorReceiveBufferSize;
	}

	public int getExcutorSendBufferSize() {
		return excutorSendBufferSize;
	}

	public void setExcutorSendBufferSize(int excutorSendBufferSize) {
		this.excutorSendBufferSize = excutorSendBufferSize;
	}

	public int getHeartBeatFrequencySecs() {
		return heartBeatFrequencySecs;
	}

	public void setHeartBeatFrequencySecs(int heartBeatFrequencySecs) {
		this.heartBeatFrequencySecs = heartBeatFrequencySecs;
	}

	public int getSleepSpoutWaitStrategyTimeMs() {
		return sleepSpoutWaitStrategyTimeMs;
	}

	public void setSleepSpoutWaitStrategyTimeMs(int sleepSpoutWaitStrategyTimeMs) {
		this.sleepSpoutWaitStrategyTimeMs = sleepSpoutWaitStrategyTimeMs;
	}

	public int getZookeeperPort() {
		return zookeeperPort;
	}

	public void setZookeeperPort(int zookeeperPort) {
		this.zookeeperPort = zookeeperPort;
	}

}
