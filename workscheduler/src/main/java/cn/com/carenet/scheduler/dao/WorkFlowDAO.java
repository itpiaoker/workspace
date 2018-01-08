package cn.com.carenet.scheduler.dao;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.entity.TaskMetric;
import cn.com.carenet.scheduler.entity.TaskTimeInfos;

@Repository
public class WorkFlowDAO {
	final private static Logger LOG = LoggerFactory.getLogger(WorkFlowDAO.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean updateExecState(String workFlowId, String state) {
		String sql = "update task_info set exec_state = ? where id = ?";
		try {
			jdbcTemplate.update(sql, state, workFlowId);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean updateExecState(String workFlowId, Integer state, Integer saveStat) {
		String sql = "update task_info set exec_state = ?,hang_status = ? where id = ?";
		try {
			jdbcTemplate.update(sql, state, saveStat, workFlowId);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean updateExecState(String workFlowId, Integer state, Date startTime) {
		String sql = "update task_info set exec_state = ?,start_time = ? where id = ?";
		try {
			jdbcTemplate.update(sql, state, startTime, workFlowId);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean updateExecState(String workFlowId, String state, String saveStat, Date startTime) {
		String sql = "update task_info set exec_state = ?,hang_status = ?,start_time = ? where id = ?";
		try {
			jdbcTemplate.update(sql, state, saveStat, startTime, workFlowId);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean delete(String workFlowId, Integer deleteFlag) {
		String sql = "update task_info set delete_flag = ? where id = ?";
		Object[] params = new Object[] { deleteFlag, workFlowId };
		int[] types = new int[] { Types.NUMERIC, Types.VARCHAR };
		try {
			jdbcTemplate.update(sql, params, types);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public TaskDetails selectTasksDetails(String workFlowID) {
		String sql = "select * from TASK_DETAILS where TASK_ID = ?";
		List<TaskDetails> list = jdbcTemplate.query(sql, new Object[] { workFlowID },
				new BeanPropertyRowMapper<TaskDetails>(TaskDetails.class));
		if (list.isEmpty()) {
			return null;
		}
		TaskDetails taskDetails = list.get(0);
		return taskDetails;
	}

	public boolean dropOldTimeInfo(String workFlowID) {
		String sql = "select * from TASK_TIME_INFOS where TASK_ID = ? and DELETE_FLAG = 0";
		String dropSQL = "update TASK_TIME_INFOS set DELETE_FLAG=1 where TASK_ID = ?";
		List<TaskTimeInfos> list = jdbcTemplate.query(sql, new Object[] { workFlowID },
				new BeanPropertyRowMapper<TaskTimeInfos>(TaskTimeInfos.class));
		try {
			if (!list.isEmpty()) {
				jdbcTemplate.update(dropSQL, workFlowID);
			}
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean addMetric(String topicName, String workFlowID, String typeName) {
		String sql = "insert into TASK_METRIC(TASK_ID,KAFKA_TOPIC,TYPE_NAME,EXEC_STAT) values(?,?,?,0)";
		try {
			jdbcTemplate.update(sql, workFlowID, topicName, typeName);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean addMetric(String topicName, String workFlowID, String typeName, String optionMap) {
		String sql = "insert into TASK_METRIC(TASK_ID,KAFKA_TOPIC,TYPE_NAME,OPTION_MAP,EXEC_STAT) values(?,?,?,?,0)";
		try {
			jdbcTemplate.update(sql, workFlowID, topicName, typeName, optionMap);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean updateMetricRun(String workFlowID) {
		String sql = "update TASK_METRIC set EXEC_STAT=1 where TASK_ID=?";
		try {
			jdbcTemplate.update(sql, workFlowID);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean updateMetricRun(String workFlowID, String topologyId) {
		String sql = "update TASK_METRIC set TOPOLOGY_ID=?,EXEC_STAT=1 where TASK_ID=?";
		try {
			jdbcTemplate.update(sql, topologyId, workFlowID);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean updateMetricStop(String workFlowID) {
		String sql = "update TASK_METRIC set EXEC_STAT=0 where TASK_ID=?";
		try {
			jdbcTemplate.update(sql, workFlowID);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public boolean dropMetric(String workFlowID) {
		String sql = "delete from TASK_METRIC where TASK_ID=?";
		try {
			jdbcTemplate.update(sql, workFlowID);
			return true;
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public TaskMetric selectTasksMetric(String workFlowID) {
		String sql = "select * from TASK_METRIC where TASK_ID = ?";
		List<TaskMetric> list = jdbcTemplate.query(sql, new Object[] { workFlowID },
				new BeanPropertyRowMapper<TaskMetric>(TaskMetric.class));
		if (list.isEmpty()) {
			return null;
		}
		TaskMetric tasksMetric = list.get(0);
		return tasksMetric;
	}

}
