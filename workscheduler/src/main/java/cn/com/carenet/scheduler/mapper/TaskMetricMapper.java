package cn.com.carenet.scheduler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.carenet.scheduler.entity.TaskMetric;
import cn.com.carenet.scheduler.mapper.base.BaseMapper;

public interface TaskMetricMapper extends BaseMapper<TaskMetric> {
	@Select({ "<script>", "select * from TASK_METRIC", "where 1=1",
			"<when test=\"kafkaTopic != null and kafkaTopic != ''\">", "and KAFKA_TOPIC=#{kafkaTopic}", "</when>",
			"<when test=\"execStat != null and execStat != ''\">", "and EXEC_STAT=#{execStat}", "</when>",
			"<when test=\"typeName != null and typeName != ''\">", "and TYPE_NAME=#{typeName}", "</when>",
			"<when test=\"topologyId != null and topologyId != ''\">", "and TOPOLOGY_ID=#{topologyId}", "</when>",
			"<when test=\"optionMap != null and optionMap != ''\">", "and OPTION_MAP=#{optionMap}", "</when>",
			"<when test=\"extra != null and extra != ''\">", "and EXTRA=#{extra}", "</when>", "</script>" })
	@Override
	public TaskMetric selectOne(TaskMetric record);

	@Select({ "<script>", "select * from TASK_METRIC", "where 1=1",
			"<when test=\"kafkaTopic != null and kafkaTopic != ''\">", "and KAFKA_TOPIC=#{kafkaTopic}", "</when>",
			"<when test=\"execStat != null and execStat != ''\">", "and EXEC_STAT=#{execStat}", "</when>",
			"<when test=\"typeName != null and typeName != ''\">", "and TYPE_NAME=#{typeName}", "</when>",
			"<when test=\"topologyId != null and topologyId != ''\">", "and TOPOLOGY_ID=#{topologyId}", "</when>",
			"<when test=\"optionMap != null and optionMap != ''\">", "and OPTION_MAP=#{optionMap}", "</when>",
			"<when test=\"extra != null and extra != ''\">", "and EXTRA=#{extra}", "</when>", "</script>" })
	@Override
	public List<TaskMetric> select(TaskMetric record);

	@Select("select * from TASK_METRIC")
	@Override
	public List<TaskMetric> selectAll();

	@Select({ "<script>", "select count(*) from TASK_METRIC", "where 1=1",
			"<when test=\"kafkaTopic != null and kafkaTopic != ''\">", "and KAFKA_TOPIC=#{kafkaTopic}", "</when>",
			"<when test=\"execStat != null and execStat != ''\">", "and EXEC_STAT=#{execStat}", "</when>",
			"<when test=\"typeName != null and typeName != ''\">", "and TYPE_NAME=#{typeName}", "</when>",
			"<when test=\"topologyId != null and topologyId != ''\">", "and TOPOLOGY_ID=#{topologyId}", "</when>",
			"<when test=\"optionMap != null and optionMap != ''\">", "and OPTION_MAP=#{optionMap}", "</when>",
			"<when test=\"extra != null and extra != ''\">", "and EXTRA=#{extra}", "</when>", "</script>" })
	@Override
	public int selectCount(TaskMetric record);

	@Select("select * from TASK_METRIC where TASK_ID = #{key}")
	@Override
	public TaskMetric selectByPrimaryKey(Object key);
	
	@Select("select * from TASK_METRIC where TASK_ID = #{key}")
	public List<TaskMetric> selectManyByPrimaryKey(Object key);

	@Insert("insert into TASK_METRIC(TASK_ID,KAFKA_TOPIC,EXEC_STAT,TYPE_NAME,TOPOLOGY_ID,OPTION_MAP) "
			+ "values(#{taskId},#{kafkaTopic},#{execStat},#{typeName},#{topologyId},#{optionMap})")
	@Override
	public int insert(TaskMetric record);

	@Override
	public int insertSelective(TaskMetric record);

	/**
	 * update by using task ID
	 */
	@Update({ "<script>", "update TASK_METRIC set EXTRA = null",
			"<when test=\"kafkaTopic != null\">", ", KAFKA_TOPIC=#{kafkaTopic}", "</when>",
			"<when test=\"execStat != null\">", ", EXEC_STAT=#{execStat}", "</when>",
			"<when test=\"typeName != null\">", ", TYPE_NAME=#{typeName}", "</when>",
			"<when test=\"topologyId != null\">", ", TOPOLOGY_ID=#{topologyId}", "</when>",
			"<when test=\"optionMap != null\">", ", OPTION_MAP=#{optionMap}", "</when>",
			"where TASK_ID=#{taskId}", "</script>" })
	@Override
	public int updateByPrimaryKey(TaskMetric record);

	@Override
	public int updateByPrimaryKeySelective(TaskMetric record);

	/**
	 * delete metric message by using task ID
	 */
	@Delete("delete from TASK_METRIC where TASK_ID=#{key}")
	@Override
	public int deleteByPrimaryKey(Object key);

	@Delete({ "<script>", "delete from TASK_METRIC", "where 1=1",
			"<when test=\"kafkaTopic != null and kafkaTopic != ''\">", "and KAFKA_TOPIC=#{kafkaTopic}", "</when>",
			"<when test=\"execStat != null and execStat != ''\">", "and EXEC_STAT=#{execStat}", "</when>",
			"<when test=\"typeName != null and typeName != ''\">", "and TYPE_NAME=#{typeName}", "</when>",
			"<when test=\"topologyId != null and topologyId != ''\">", "and TOPOLOGY_ID=#{topologyId}", "</when>",
			"<when test=\"optionMap != null and optionMap != ''\">", "and OPTION_MAP=#{optionMap}", "</when>",
			"<when test=\"extra != null and extra != ''\">", "and EXTRA=#{extra}", "</when>", "</script>" })
	@Override
	public int delete(TaskMetric record);

}
