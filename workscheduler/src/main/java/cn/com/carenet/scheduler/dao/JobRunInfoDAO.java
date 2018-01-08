package cn.com.carenet.scheduler.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.entity.TaskTimeInfos;

@Repository
public class JobRunInfoDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	public TaskTimeInfos selectTaskTimeInfos(String workFlowID) {
		String sql = "select * from  TASK_TIME_INFOS where TASK_ID = ?";
		List<TaskTimeInfos> list = jdbcTemplate.query(sql, new Object[] { workFlowID },
				new BeanPropertyRowMapper<TaskTimeInfos>(TaskTimeInfos.class));
		TaskTimeInfos taskTimeInfos = list.get(0);
		return taskTimeInfos;
	}

	public TaskTimeInfos selectTaskTimeInfos(String workFlowID, Date startTime) {
		String sql = "select * from  TASK_TIME_INFOS where TASK_ID = ? and START_TIME=?";
		List<TaskTimeInfos> list = jdbcTemplate.query(sql, new Object[] { workFlowID, startTime },
				new BeanPropertyRowMapper<TaskTimeInfos>(TaskTimeInfos.class));
		TaskTimeInfos taskTimeInfos = list.get(0);
		return taskTimeInfos;
	}

	public Date insertNewStart(String workFlowID) {
		String sql = "insert into TASK_TIME_INFOS (START_TIME,TASK_ID,TASK_STATUS,RUNNING_TIME) values(?,?,2,0)";
		Date startTime = new Date();
		jdbcTemplate.update(sql, startTime, workFlowID);
		return startTime;
	}

	public void updateSuccessEnd(String workFlowID, Date startTime) {
		Date thisTime = new Date();
		long value = thisTime.getTime() - startTime.getTime();
		String sql = "update TASK_TIME_INFOS as t set END_TIME=?,TASK_STATUS=1,RUNNING_TIME = ? where TASK_ID=? and t.START_TIME=?";
		jdbcTemplate.update(sql, thisTime, value, workFlowID, startTime);
	}

	public void updateStreamEnd(String workFlowID, Date startTime, int uptimeSecs) {
		Date thisTime = new Date();
		long value = uptimeSecs * 1000;
		String sql = "update TASK_TIME_INFOS as t set END_TIME=?,TASK_STATUS=0,RUNNING_TIME = ? where TASK_ID=? and t.START_TIME=?";
		jdbcTemplate.update(sql, thisTime, value, workFlowID, startTime);
	}

	public void updateFailedEnd(String workFlowID, Date startTime) {
		Date thisTime = new Date();
		long value = thisTime.getTime() - startTime.getTime();
		String sql = "update TASK_TIME_INFOS as t set END_TIME=?,TASK_STATUS=-1,RUNNING_TIME = ? where TASK_ID=? and t.START_TIME=?";
		jdbcTemplate.update(sql, thisTime, value, workFlowID, startTime);
	}
}
