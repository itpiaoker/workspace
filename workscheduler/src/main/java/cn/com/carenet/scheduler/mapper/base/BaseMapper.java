package cn.com.carenet.scheduler.mapper.base;

import java.util.List;

public interface BaseMapper<T> {

	T selectOne(T record);

	List<T> select(T record);

	List<T> selectAll();

	int selectCount(T record);

	T selectByPrimaryKey(Object key);

	boolean existsWithPrimaryKey(Object key);

	int insert(T record);

	int insertSelective(T record);

	int updateByPrimaryKey(T record);

	int updateByPrimaryKeySelective(T record);

	int delete(T record);

	int deleteByPrimaryKey(Object key);
}
