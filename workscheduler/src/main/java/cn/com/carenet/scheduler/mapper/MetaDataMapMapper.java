package cn.com.carenet.scheduler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.com.carenet.scheduler.entity.MetaDataMap;
import cn.com.carenet.scheduler.mapper.base.BaseMapper;

public interface MetaDataMapMapper extends BaseMapper<MetaDataMap> {

	@Select({ "<script>", "select * from META_DATA_MAP", "where 1=1",
			"<when test=\"tableName != null and tableName != ''\">", "and TABLE_NAME=#{tableName}", "</when>",
			"<when test=\"columnName != null and columnName != ''\">", "and COLUMN_NAME=#{columnName}", "</when>",
			"<when test=\"ordernum != null and ordernum != ''\">", "and ORDERNUM=#{ordernum}", "</when>",
			"<when test=\"dataSourceId != null and dataSourceId != ''\">", "and DATA_SOURCE_ID=#{dataSourceId}",
			"</when>", "</script>" })
	@Override
	public MetaDataMap selectOne(MetaDataMap record);

	@Select({ "<script>", "select * from META_DATA_MAP", "where 1=1",
			"<when test=\"tableName != null and tableName != ''\">", "and TABLE_NAME=#{tableName}", "</when>",
			"<when test=\"columnName != null and columnName != ''\">", "and COLUMN_NAME=#{columnName}", "</when>",
			"<when test=\"ordernum != null and ordernum != ''\">", "and ORDERNUM=#{ordernum}", "</when>",
			"<when test=\"dataSourceId != null and dataSourceId != ''\">", "and DATA_SOURCE_ID=#{dataSourceId}",
			"</when>", "</script>" })
	@Override
	public List<MetaDataMap> select(MetaDataMap record);

	@Select("select * from META_DATA_MAP")
	@Override
	public List<MetaDataMap> selectAll();

	@Select({ "<script>", "select count(*) from META_DATA_MAP", "where 1=1",
			"<when test=\"tableName != null and tableName != ''\">", "and TABLE_NAME=#{tableName}", "</when>",
			"<when test=\"columnName != null and columnName != ''\">", "and COLUMN_NAME=#{columnName}", "</when>",
			"<when test=\"ordernum != null and ordernum != ''\">", "and ORDERNUM=#{ordernum}", "</when>",
			"<when test=\"dataSourceId != null and dataSourceId != ''\">", "and DATA_SOURCE_ID=#{dataSourceId}",
			"</when>", "</script>" })
	@Override
	public int selectCount(MetaDataMap record);

	@Select("select * from META_DATA_MAP where ID = #{key}")
	@Override
	public MetaDataMap selectByPrimaryKey(Object key);

	@Override
	public boolean existsWithPrimaryKey(Object key);

	@Insert("insert into META_DATA_MAP(TABLE_NAME,COLUMN_NAME,COLUMN_DEFAULT,NULLABLE,COLUMN_TYPE,"
			+ "COLUMN_LENGTH,IS_KEY,DESCRIPTION,DATA_SOURCE_ID,ORDERNUM,DATE_FORMAT) "
			+ "values(#{tableName},#{columnName},#{columnDefault},#{nullable},#{columnType},"
			+ "#{columnLength},#{isKey},#{description},#{dataSourceId},#{ordernum},#{dateFormat})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Override
	public int insert(MetaDataMap record);

	@Override
	public int insertSelective(MetaDataMap record);

	@Override
	public int updateByPrimaryKey(MetaDataMap record);

	@Override
	public int updateByPrimaryKeySelective(MetaDataMap record);

	@Delete({ "<script>", "delete from META_DATA_MAP", "where 1=1",
			"<when test=\"tableName != null and tableName != ''\">", "and TABLE_NAME=#{tableName}", "</when>",
			"<when test=\"columnName != null and columnName != ''\">", "and COLUMN_NAME=#{columnName}", "</when>",
			"<when test=\"ordernum != null and ordernum != ''\">", "and ORDERNUM=#{ordernum}", "</when>",
			"<when test=\"dataSourceId != null and dataSourceId != ''\">", "and DATA_SOURCE_ID=#{dataSourceId}",
			"</when>", "</script>" })
	@Override
	public int delete(MetaDataMap record);

	@Delete("delete from META_DATA_MAP where ID=#{key}")
	@Override
	public int deleteByPrimaryKey(Object key);

	@Select("select * from META_DATA_MAP where DATA_SOURCE_ID = #{dataSourceId}")
	public List<MetaDataMap> selectByDataSourceId(String dataSourceId);

	@Insert({ "<script>", "insert into META_DATA_MAP",
			"(TABLE_NAME,COLUMN_NAME,COLUMN_DEFAULT,NULLABLE,COLUMN_TYPE,"
					+ "COLUMN_LENGTH,IS_KEY,DESCRIPTION,DATA_SOURCE_ID,ORDERNUM,DATE_FORMAT)",
			"values <foreach collection=\"list\" item=\"item\" separator=\",\">",
			"(#{item.tableName,jdbcType=VARCHAR},#{item.columnName},#{item.columnDefault},"
					+ "#{item.nullable},#{item.columnType},#{item.columnLength},#{item.isKey},"
					+ "#{item.description},#{item.dataSourceId},#{item.ordernum},#{item.dateFormat})",
			"</foreach>", "</script>" })
	public void insertBatch(@Param("list") List<MetaDataMap> record);
}
