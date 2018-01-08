package cn.com.carenet.scheduler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.carenet.common.web.entity.TaskDatasource;
import cn.com.carenet.scheduler.mapper.base.BaseMapper;

public interface TaskDatasourceMapper extends BaseMapper<TaskDatasource> {
	@Select({ "<script>", "select * from TASK_DATASOURCE", "where 1=1", "<when test=\"id != null\">", "and ID=#{id}",
			"</when>", "<when test=\"taskId != null\">", "and TASK_ID=#{taskId}", "</when>", "</script>" })
	@Override
	public TaskDatasource selectOne(TaskDatasource record);

	@Select({ "<script>", "select * from TASK_DATASOURCE", "where 1=1", "<when test=\"id != null\">", "and ID=#{id}",
			"</when>", "<when test=\"taskId != null\">", "and TASK_ID=#{taskId}", "</when>", "</script>" })
	@Override
	public List<TaskDatasource> select(TaskDatasource record);

	@Select("select * from TASK_DATASOURCE")
	@Override
	public List<TaskDatasource> selectAll();

	@Select({ "<script>", "select count(*) from TASK_DATASOURCE", "where 1=1", "<when test=\"id != null\">",
			"and ID=#{id}", "</when>", "<when test=\"taskId != null\">", "and TASK_ID=#{taskId}", "</when>",
			"</script>" })
	@Override
	public int selectCount(TaskDatasource record);

	@Select("select * from TASK_DATASOURCE where TASK_ID = #{key}")
	public TaskDatasource selectByTaskID(Object key);

	@Override
	public boolean existsWithPrimaryKey(Object key);

	@Insert("insert into TASK_DATASOURCE(TASK_ID,SYS_INPUT_ID,SYS_OUTPUT_ID) "
			+ "values(#{taskId},#{sysInputId},#{sysOutputId})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Override
	public int insert(TaskDatasource record);

	@Update({ "<script>", "update TASK_DATASOURCE", "set TASK_ID=#{taskId}", "<when test=\"sysInputId != null\">",
			"and SYS_INPUT_ID=#{sysInputId}", "</when>", "<when test=\"sysInputId != null\">",
			"and SYS_OUTPUT_ID=#{sysOutputId}", "</when>", "where ID=#{id}", "<when test=\"taskId != null\">",
			"and TASK_ID=#{taskId}", "</when>", "</script>" })
	@Override
	public int updateByPrimaryKey(TaskDatasource record);

	@Override
	public int updateByPrimaryKeySelective(TaskDatasource record);

	@Override
	public int delete(TaskDatasource record);

	@Delete("delete from TASK_DATASOURCE where ID=#{key}")
	@Override
	public int deleteByPrimaryKey(Object key);

	@Select("select * from TASK_DATASOURCE where TASK_ID = #{taskId}")
	public List<TaskDatasource> selectByTaskId(String taskId);
}
