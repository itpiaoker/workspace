package cn.com.carenet.components.greenplum;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.com.carenet.components.greenplum.utils.GreenPlumSQL;
import cn.com.carenet.scheduler.bean.CommandSQLAllBean;
import cn.com.carenet.scheduler.bean.DataBaseSQLBean;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.dao.JobRunInfoDAO;
import cn.com.carenet.scheduler.entity.TaskDetails;

@Service
public class GreenPlumManager {
	final private static Logger LOG = LoggerFactory.getLogger(GreenPlumManager.class);
	@Autowired
	private JobRunInfoDAO jobRunInfoDAO;

	public boolean execGreenPlumSQLJob(String workFlowID) {
		try {
			TaskDetails taskDetails = jobRunInfoDAO.selectTasksDetails(workFlowID);
			String allBean = taskDetails.getBigBean();
			CommandSQLAllBean commandSQLAllBean = JSON.parseObject(allBean, CommandSQLAllBean.class);
			DataBaseSQLBean gpDataBaseSQLBean = commandSQLAllBean.getDataBaseSQLBean();
			Map<String, DataSourceProp> dataSourceOptionsBeanMap = commandSQLAllBean.getDataSources();
			String database = gpDataBaseSQLBean.getDataBaseName();
			String user = gpDataBaseSQLBean.getUserName();
			String passwd = gpDataBaseSQLBean.getPassword();
			String fullUrl = gpDataBaseSQLBean.getUrls();
			String sqls = gpDataBaseSQLBean.getScript();
			LOG.debug("{},{},{}", sqls, database, fullUrl);

			GreenPlumControl gpControl = new GreenPlumControl(fullUrl, database, user, passwd);
			GreenPlumSQL greenPlumSQL = new GreenPlumSQL(fullUrl, database, user, passwd);
			boolean outputMark = false;
			for (Entry<String, DataSourceProp> dataSourceOptionsBeanEntry : dataSourceOptionsBeanMap.entrySet()) {
				DataSourceProp dataSourceOptionsBean = dataSourceOptionsBeanEntry.getValue();
				String sourceName = dataSourceOptionsBean.getSourceName();
				int sourceType = dataSourceOptionsBean.getPutType();
				String tableName = dataSourceOptionsBean.getTableName();

				if (sourceType == 0) {
					switch (sourceName) {
					case WebModuleNameConstant.localFile:
						gpControl.localInput(dataSourceOptionsBean);
						break;
					case WebModuleNameConstant.hdfs:
						gpControl.hdfsInput(dataSourceOptionsBean);
						break;
					}
				} else if (sourceType == 1) {
					switch (sourceName) {
					case WebModuleNameConstant.localFile:
						outputMark = true;
						if (sqls != null) {
							gpControl.localOutput(dataSourceOptionsBean, sqls);
						} else {
							gpControl.localOutput(dataSourceOptionsBean, tableName);
						}
						break;
					}
				}
			}
			if (!outputMark && sqls != null) {
				greenPlumSQL.runSQLs(sqls);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean execGreenPlumJob(String workFlowID) {
		try {
			TaskDetails taskDetails = jobRunInfoDAO.selectTasksDetails(workFlowID);
			String allBean = taskDetails.getBigBean();
			CommandSQLAllBean commandSQLAllBean = JSON.parseObject(allBean, CommandSQLAllBean.class);
			DataBaseSQLBean gpDataBaseSQLBean = commandSQLAllBean.getDataBaseSQLBean();
			Map<String, DataSourceProp> dataSourceOptionsBeanMap = commandSQLAllBean.getDataSources();
			String database = gpDataBaseSQLBean.getDataBaseName();
			String user = gpDataBaseSQLBean.getUserName();
			String passwd = gpDataBaseSQLBean.getPassword();
			String fullUrl = gpDataBaseSQLBean.getUrls();
			String sqls = gpDataBaseSQLBean.getScript();
			LOG.debug("{},{},{}", sqls, database, fullUrl);

			GreenPlumControl gpControl = new GreenPlumControl(fullUrl, database, user, passwd);
			for (Entry<String, DataSourceProp> dataSourceOptionsBeanEntry : dataSourceOptionsBeanMap.entrySet()) {
				DataSourceProp dataSourceOptionsBean = dataSourceOptionsBeanEntry.getValue();
				String sourceName = dataSourceOptionsBean.getSourceName();
				int sourceType = dataSourceOptionsBean.getPutType();
				String tableName = dataSourceOptionsBean.getTableName();

				if (sourceType == 0) {
					switch (sourceName) {
					case WebModuleNameConstant.localFile:
						gpControl.localInput(dataSourceOptionsBean);
						break;
					case WebModuleNameConstant.hdfs:
						gpControl.hdfsInput(dataSourceOptionsBean);
						break;
					}
				} else if (sourceType == 1) {
					switch (sourceName) {
					case WebModuleNameConstant.localFile:
						gpControl.localOutput(dataSourceOptionsBean, tableName);
						break;
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
		return true;
	}
}
