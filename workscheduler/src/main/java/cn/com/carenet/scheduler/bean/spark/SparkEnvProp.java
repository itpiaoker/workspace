package cn.com.carenet.scheduler.bean.spark;

public class SparkEnvProp {
	private String workFlowID;
	private Integer executorCores;
	private Integer executorMemory;
	private Integer numExecutors;
	/**
	 * org.apache.spark.serializer.KryoSerializer
	 * org.apache.spark.serializer.JavaSerializer
	 */
	private String sparkSerializer;
	private Integer sparkDefaultParallelism;
	private Integer sparkReduceMaxSizeInFlight;
	private Integer sparkShuffleFileBuffer;
	private Double sparkShuffleMemoryFraction;
	private Double sparkStorageMemoryFraction;
	/**
	 * org.apache.spark.sql.api.java.StructField
	 */
	private String registerKryoClasses;

	public Integer getExecutorCores() {
		return executorCores;
	}

	public void setExecutorCores(Integer executorCores) {
		this.executorCores = executorCores;
	}

	public Integer getExecutorMemory() {
		return executorMemory;
	}

	public void setExecutorMemory(Integer executorMemory) {
		this.executorMemory = executorMemory;
	}

	public Integer getNumExecutors() {
		return numExecutors;
	}

	public void setNumExecutors(Integer numExecutors) {
		this.numExecutors = numExecutors;
	}

	public Integer getSparkDefaultParallelism() {
		return sparkDefaultParallelism;
	}

	public void setSparkDefaultParallelism(Integer sparkDefaultParallelism) {
		this.sparkDefaultParallelism = sparkDefaultParallelism;
	}

	public Integer getSparkReduceMaxSizeInFlight() {
		return sparkReduceMaxSizeInFlight;
	}

	public void setSparkReduceMaxSizeInFlight(Integer sparkReduceMaxSizeInFlight) {
		this.sparkReduceMaxSizeInFlight = sparkReduceMaxSizeInFlight;
	}

	public Integer getSparkShuffleFileBuffer() {
		return sparkShuffleFileBuffer;
	}

	public void setSparkShuffleFileBuffer(Integer sparkShuffleFileBuffer) {
		this.sparkShuffleFileBuffer = sparkShuffleFileBuffer;
	}

	public Double getSparkShuffleMemoryFraction() {
		return sparkShuffleMemoryFraction;
	}

	public void setSparkShuffleMemoryFraction(Double sparkShuffleMemoryFraction) {
		this.sparkShuffleMemoryFraction = sparkShuffleMemoryFraction;
	}

	public Double getSparkStorageMemoryFraction() {
		return sparkStorageMemoryFraction;
	}

	public void setSparkStorageMemoryFraction(Double sparkStorageMemoryFraction) {
		this.sparkStorageMemoryFraction = sparkStorageMemoryFraction;
	}

	public String getWorkFlowID() {
		return workFlowID;
	}

	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}

	public String getSparkSerializer() {
		return sparkSerializer;
	}

	public void setSparkSerializer(String sparkSerializer) {
		this.sparkSerializer = sparkSerializer;
	}

	public String getRegisterKryoClasses() {
		return registerKryoClasses;
	}

	public void setRegisterKryoClasses(String registerKryoClasses) {
		this.registerKryoClasses = registerKryoClasses;
	}
}
