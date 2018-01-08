package cn.com.carenet.scheduler.dao;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.com.carenet.common.web.entity.TaskInfo;
import cn.com.carenet.scheduler.bean.TransposeInfos;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.entity.TransposeInfo;

/**
 * Title: Description:
 *
 * @author lianxy
 * @date 2017/7/3
 */
@Component("transposeDAO")
public class TransposeDAO {

	final private static String updateSQL = "update TASK_DETAILS set TYPE_NAME=?,TASK_REPEAT=?,BIG_BEAN=?,QUARTZ_TIME=?,QUARTZ_CRON=? where TASK_ID=?";
	final private static String insertSQL = "insert into TASK_DETAILS (TYPE_NAME,TASK_REPEAT,BIG_BEAN,QUARTZ_TIME,QUARTZ_CRON,TASK_ID) values(?,?,?,?,?,?);";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private WorkFlowDAO workFlowDAO;

	public TransposeInfo selectInfos(String workFlowID) {
		String sql = "select * from task_info where id = ?";
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<TaskInfo> list = jdbcTemplate.query(sql, new Object[] { workFlowID },
				new BeanPropertyRowMapper(TaskInfo.class));
		TaskInfo taskInfo = list.get(0);
		String infos = taskInfo.getTaskInfo();
		Integer id = taskInfo.getId();
		TransposeInfo transposeInfo = new TransposeInfo();
		transposeInfo.setId(id + "");
		transposeInfo.setTransposeInfo(infos);
		return transposeInfo;
	}

	public String updateInfos(TransposeInfos transposeInfos) {
		String transposeInfoStr = JSON.toJSONString(transposeInfos);
		if (workFlowDAO == null) {
			jdbcTemplate.update(insertSQL, WebModuleNameConstant.transpose, transposeInfos.isRepeat(), transposeInfoStr,
					transposeInfos.getTimestampExpression(), transposeInfos.getCronExpression(),
					transposeInfos.getWorkFlowID());
		} else {
			jdbcTemplate.update(updateSQL, WebModuleNameConstant.transpose, transposeInfos.isRepeat(), transposeInfoStr,
					transposeInfos.getTimestampExpression(), transposeInfos.getCronExpression(),
					transposeInfos.getWorkFlowID());
		}
		return null;
	}

	public String deleteInfos(String workFlowID) {
		TransposeInfo selectInfos = selectInfos(workFlowID);
		String id = selectInfos.getId();
		String transposeInfo = selectInfos.getTransposeInfo();
		Object[] params = new Object[] { id, transposeInfo, new Date() };
		int[] types = new int[] { Types.NUMERIC, Types.VARCHAR, Types.TIMESTAMP };

		StringBuffer transposeInfoSqlSaved = new StringBuffer();
		transposeInfoSqlSaved.append("insert into transpose_info (");
		transposeInfoSqlSaved.append("id, transpose_info, exec_state, delete_flag, create_time, start_time, end_time");
		transposeInfoSqlSaved.append(") values (");
		transposeInfoSqlSaved.append("");
		transposeInfoSqlSaved.append(") ");
		jdbcTemplate.update(transposeInfoSqlSaved.toString(), params, types);
		// jdbcTemplate.update(sqlSaving, workFlowID);
		return "";
	}

}
