package cn.com.carenet.scheduler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.carenet.scheduler.entity.SysDatasource;
import cn.com.carenet.scheduler.mapper.base.BaseMapper;

public interface SysDatasourceMapper extends BaseMapper<SysDatasource> {

	@Select({ "<script>", "select * from sys_datasource", "where del_flag=0",
			"<when test=\"databaseName != null and databaseName != ''\">", "and database_name=#{databaseName}","</when>",
			"<when test=\"url != null and url != ''\">", "and url=#{url}","</when>",
			"<when test=\"port != null and port != ''\">", "and port=#{port}","</when>","</script>"})
	@Override
	public SysDatasource selectOne(SysDatasource record);

	@Select({ "<script>", "select * from sys_datasource", "where del_flag=0",
		"<when test=\"databaseName != null and databaseName != ''\">", "and database_name=#{databaseName}","</when>",
		"<when test=\"url != null and url != ''\">", "and url=#{url}","</when>",
		"<when test=\"port != null and port != ''\">", "and port=#{port}","</when>","</script>"})
	@Override
	public List<SysDatasource> select(SysDatasource record);

	@Select("select * from sys_datasource")
	@Override
	public List<SysDatasource> selectAll();

	@Select({ "<script>", "select count(*) from sys_datasource", "where 1=1",
			"<when test=\"databaseName != null and databaseName != ''\">", "and database_name=#{databaseName}","</when>",
			"<when test=\"url != null and url != ''\">", "and url=#{url}","</when>",
			"<when test=\"port != null and port != ''\">", "and port=#{port}" ,"</when>","</script>"})
	@Override
	public int selectCount(SysDatasource record);

	@Select("select * from sys_datasource where id = #{key}")
	@Override
	public SysDatasource selectByPrimaryKey(Object key);

	@Select("select 1 from sys_datasource where id =#{key}")
	@Override
	public boolean existsWithPrimaryKey(Object key);

	@Insert("insert into sys_datasource(database_type,database_name,user_name,password,port,ip,url,driver,datasource_name,disabled,del_flag) values(#{databaseType},#{databaseName},#{userName},#{password},#{port},#{ip},#{url},#{driver},#{datasourceName},0,0)")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Override
	public int insert(SysDatasource record);

	@Override
	public int insertSelective(SysDatasource record);

	@Update({ "<script>", "update sys_datasource", "set del_flag=0",
			"<when test=\"databaseName != null and databaseName != ''\">", ", database_name=#{databaseName}","</when>",
			"<when test=\"databaseType != null and databaseType != ''\">", ", database_type=#{databaseType}","</when>",
			"<when test=\"userName != null and userName != ''\">", ", user_name=#{userName}","</when>",
			"<when test=\"password != null and password != ''\">", ", password=#{password}","</when>",
			"<when test=\"url != null and url != ''\">", ", url=#{url}", "</when>",
			"<when test=\"ip != null and ip != ''\">",", port=#{ip}","</when>",
			"<when test=\"port != null and port != ''\">", ", port=#{port}","</when>",
			"<when test=\"separator != null and separator != ''\">", ", separator=#{separator}","</when>",
			"where id=#{id}" ,"</script>"})
	@Override
	public int updateByPrimaryKey(SysDatasource record);

	@Override
	public int updateByPrimaryKeySelective(SysDatasource record);

	@Update("update sys_datasource set del_flag=1 where database_name=#{databaseName} and url=#{url} and port=#{port}")
	@Override
	public int delete(SysDatasource record);

	@Update("update sys_datasource set del_flag=1 where id=#{key}")
	@Override
	public int deleteByPrimaryKey(Object key);

	@Delete("delete from sys_datasource where database_name=#{databaseName} and url=#{url} and port=#{port}")
	public int drop(SysDatasource record);

	@Delete("delete from sys_datasource where id=#{id}")
	public int dropByPrimaryKey(SysDatasource record);
}
