package cn.com.carenet.scheduler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.carenet.common.web.entity.TaskInfo;
import cn.com.carenet.scheduler.mapper.base.BaseMapper;

/**
 * 
 * @author Sherard Lee
 * @since 26/Aug/2017
 */
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {
	@Select({ "<script>", "select * from task_info", "where 1=1", "<when test=\"taskName != null and taskName != ''\">",
			"and task_name=#{taskName}", "</when>", "<when test=\"execState != null and execState != ''\">",
			"and exec_state=#{execState}", "</when>", "<when test=\"taskType != null and taskType != ''\">",
			"and task_type=#{taskType}", "</when>", "<when test=\"hangStatus != null and hangStatus != ''\">",
			"and hang_status=#{hangStatus}", "</when>", "</script>" })
	@Override
	public TaskInfo selectOne(TaskInfo record);

	@Select({ "<script>", "select * from task_info", "where 1=1", "<when test=\"taskName != null and taskName != ''\">",
			"and task_name=#{taskName}", "</when>", "<when test=\"execState != null and execState != ''\">",
			"and exec_state=#{execState}", "</when>", "<when test=\"taskType != null and taskType != ''\">",
			"and task_type=#{taskType}", "</when>", "<when test=\"hangStatus != null and hangStatus != ''\">",
			"and hang_status=#{hangStatus}", "</when>", "</script>" })
	@Override
	public List<TaskInfo> select(TaskInfo record);

	@Select("select * from task_info")
	@Override
	public List<TaskInfo> selectAll();

	@Select({ "<script>", "select count(*) from task_info", "where 1=1",
			"<when test=\"taskName != null and taskName != ''\">", "and task_name=#{taskName}", "</when>",
			"<when test=\"execState != null and execState != ''\">", "and exec_state=#{execState}", "</when>",
			"<when test=\"taskType != null and taskType != ''\">", "and task_type=#{taskType}", "</when>",
			"<when test=\"hangStatus != null and hangStatus != ''\">", "and hang_status=#{hangStatus}", "</when>",
			"</script>" })
	@Override
	public int selectCount(TaskInfo record);

	@Select("select * from task_info where id = #{key}")
	@Override
	public TaskInfo selectByPrimaryKey(Object key);

	@Override
	public int insert(TaskInfo record);

	@Override
	public int insertSelective(TaskInfo record);

	@Update({ "<script>", "update task_info set delete_flag = 0",
			"<when test=\"execState != null and execState != ''\">", ", exec_state=#{execState}", "</when>",
			"<when test=\"hangStatus != null and hangStatus != ''\">", ", hang_status=#{hangStatus}", "</when>",
			"<when test=\"startTime != null\">", ", start_time=#{startTime}", "</when>",
			"where id=#{id} and delete_flag = 0", "</script>" })
	@Override
	public int updateByPrimaryKey(TaskInfo record);

	@Override
	public int updateByPrimaryKeySelective(TaskInfo record);

	@Update("update task_info set delete_flag = 1 where id=#{key}")
	@Override
	public int deleteByPrimaryKey(Object key);

	@Delete({ "<script>", "delete from task_info", "where 1=1", "<when test=\"taskName != null and taskName != ''\">",
			"and task_name=#{taskName}", "</when>", "<when test=\"execState != null and execState != ''\">",
			"and exec_state=#{execState}", "</when>", "<when test=\"taskType != null and taskType != ''\">",
			"and task_type=#{taskType}", "</when>", "<when test=\"hangStatus != null and hangStatus != ''\">",
			"and hang_status=#{hangStatus}", "</when>", "</script>" })
	public int drop(TaskInfo record);

	@Delete("delete from task_info where ID=#{key}")
	public int dropByPrimaryKey(Object key);

}
