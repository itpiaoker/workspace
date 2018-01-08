package cn.com.carenet.scheduler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.mapper.base.BaseMapper;

public interface TaskDetailsMapper extends BaseMapper<TaskDetails> {

	@Select({ "<script>", "select * from TASK_DETAILS", "where 1=1", "<when test=\"taskId != null and taskId != ''\">",
			"and TASK_ID=#{taskId}", "</when>", "<when test=\"typeName != null and typeName != ''\">",
			"and TYPE_NAME=#{typeName}", "</when>", "<when test=\"taskRepeat != null and taskRepeat != ''\">",
			"and TASK_REPEAT=#{taskRepeat}", "</when>", "<when test=\"quartzTime != null and quartzTime != ''\">",
			"and QUARTZ_TIME=#{quartzTime}", "</when>", "<when test=\"quartzCron != null and quartzCron != ''\">",
			"and QUARTZ_CRON=#{quartzCron}", "</when>", "</script>" })
	@Override
	public TaskDetails selectOne(TaskDetails record);

	@Select({ "<script>", "select * from TASK_DETAILS", "where 1=1", "<when test=\"taskId != null and taskId != ''\">",
			"and TASK_ID=#{taskId}", "</when>", "<when test=\"typeName != null and typeName != ''\">",
			"and TYPE_NAME=#{typeName}", "</when>", "<when test=\"taskRepeat != null and taskRepeat != ''\">",
			"and TASK_REPEAT=#{taskRepeat}", "</when>", "<when test=\"quartzTime != null and quartzTime != ''\">",
			"and QUARTZ_TIME=#{quartzTime}", "</when>", "<when test=\"quartzCron != null and quartzCron != ''\">",
			"and QUARTZ_CRON=#{quartzCron}", "</when>", "</script>" })
	@Override
	public List<TaskDetails> select(TaskDetails record);

	@Select("select * from TASK_DETAILS")
	@Override
	public List<TaskDetails> selectAll();

	@Select({ "<script>", "select count(*) from TASK_DETAILS", "where 1=1",
			"<when test=\"taskId != null and taskId != ''\">", "and TASK_ID=#{taskId}", "</when>",
			"<when test=\"typeName != null and typeName != ''\">", "and TYPE_NAME=#{typeName}", "</when>",
			"<when test=\"taskRepeat != null and taskRepeat != ''\">", "and TASK_REPEAT=#{taskRepeat}", "</when>",
			"<when test=\"quartzTime != null and quartzTime != ''\">", "and QUARTZ_TIME=#{quartzTime}", "</when>",
			"<when test=\"quartzCron != null and quartzCron != ''\">", "and QUARTZ_CRON=#{quartzCron}", "</when>",
			"</script>" })
	@Override
	public int selectCount(TaskDetails record);

	@Select("select * from TASK_DETAILS where TASK_ID = #{key}")
	@Override
	public TaskDetails selectByPrimaryKey(Object key);

	@Override
	public boolean existsWithPrimaryKey(Object key);

	@Insert("insert into TASK_DETAILS(TASK_ID,TYPE_NAME,TASK_REPEAT,BIG_BEAN,DATA_SOURCES_INFOS,"
			+ "OPERATES_INFOS,FIELDS_INFOS,SQLS_INFOS,QUARTZ_TIME,QUARTZ_CRON) "
			+ "values(#{taskId},#{typeName},#{taskRepeat},#{bigBean},#{dataSourcesInfos},"
			+ "#{operatesInfos},#{fieldsInfos},#{sqlsInfos},#{quartzTime},#{quartzCron})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Override
	public int insert(TaskDetails record);

	@Override
	public int insertSelective(TaskDetails record);

	@Update({ "<script>", "update TASK_DETAILS set TASK_ID=#{taskId}",
			"<when test=\"typeName != null\">", ", TYPE_NAME=#{typeName}", "</when>",
			"<when test=\"taskRepeat != null\">", ", TASK_REPEAT=#{taskRepeat}", "</when>",
			"<when test=\"bigBean != null and bigBean != ''\">", ", BIG_BEAN=#{bigBean}", "</when>",
			"<when test=\"dataSourcesInfos != null and dataSourcesInfos != ''\">",
			", DATA_SOURCES_INFOS=#{dataSourcesInfos}", "</when>",
			"<when test=\"operatesInfos != null and operatesInfos != ''\">", ", OPERATES_INFOS=#{operatesInfos}",
			"</when>", "<when test=\"fieldsInfos != null and fieldsInfos != ''\">", ", FIELDS_INFOS=#{fieldsInfos}",
			"</when>", "<when test=\"sqlsInfos != null and sqlsInfos != ''\">", ", SQLS_INFOS=#{sqlsInfos}", "</when>",
			"<when test=\"quartzTime != null and quartzTime != ''\">", ", QUARTZ_TIME=#{quartzTime}", "</when>",
			"<when test=\"quartzCron != null and quartzCron != ''\">", ", QUARTZ_CRON=#{quartzCron}", "</when>",
			"where TASK_ID=#{taskId}", "</script>" })
	@Override
	public int updateByPrimaryKey(TaskDetails record);

	@Override
	public int updateByPrimaryKeySelective(TaskDetails record);

	@Delete({ "<script>", "delete from TASK_DETAILS", "where 1=1", "<when test=\"taskId != null and taskId != ''\">",
			"and TASK_ID=#{taskId}", "</when>", "<when test=\"typeName != null and typeName != ''\">",
			"and TYPE_NAME=#{typeName}", "</when>", "<when test=\"taskRepeat != null and taskRepeat != ''\">",
			"and TASK_REPEAT=#{taskRepeat}", "</when>", "<when test=\"quartzTime != null and quartzTime != ''\">",
			"and QUARTZ_TIME=#{quartzTime}", "</when>", "<when test=\"quartzCron != null and quartzCron != ''\">",
			"and QUARTZ_CRON=#{quartzCron}", "</when>", "</script>" })
	@Override
	public int delete(TaskDetails record);

	@Delete("delete from TASK_DETAILS where TASK_ID=#{key}")
	@Override
	public int deleteByPrimaryKey(Object key);
}
