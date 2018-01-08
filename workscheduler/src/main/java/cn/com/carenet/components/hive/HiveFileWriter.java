package cn.com.carenet.components.hive;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.com.carenet.components.hive.beans.HiveOperationsBean;
import cn.com.carenet.components.hive.utils.HiveUtilHiveJdbc;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.utils.ConnectionTypeUtil;

public class HiveFileWriter {
	final private static Logger LOG = LoggerFactory.getLogger(HiveFileWriter.class);
	private JdbcTemplate jdbcTemplate;
	private HiveOperationsBean operationsBean;

	public void prepareDatabase() {
		HiveUtilHiveJdbc hiveJdbcUtils = new HiveUtilHiveJdbc();
		jdbcTemplate = hiveJdbcUtils.prepareHiveJdbc(operationsBean.getHiveJdbcUrl(), operationsBean.getDbName());
	}

	public void hiveToLocal() {
		Map<String, DataSourceProp> dataSourceInfos = operationsBean.getDataSourceInfos();
		for (Entry<String, DataSourceProp> dataSourceInfo : dataSourceInfos.entrySet()) {
			DataSourceProp dataSourceOptions = dataSourceInfo.getValue();
			String delimiter = dataSourceOptions.getDatasouceDelimiter();
			switch (delimiter) {
			case "\t":
				delimiter = "\\t";
				break;
			case "\n":
				delimiter = "\\n";
				break;
			case "\r":
				delimiter = "\\r";
				break;
			}
			if (dataSourceOptions.getPutType() == 1) {
				String url = dataSourceOptions.getPath();
				StringBuffer sqlb = new StringBuffer("");
				sqlb.append("insert overwrite local directory \'").append(url)
						.append("\' ROW FORMAT DELIMITED FIELDS TERMINATED by \'").append(delimiter)
						.append("\' select * from ").append(operationsBean.getHiveTableName());
				LOG.info(sqlb.toString());
				jdbcTemplate.execute(sqlb.toString());
			}
		}
	}

	public void hiveToHdfs() {
		Map<String, DataSourceProp> dataSourceInfos = operationsBean.getDataSourceInfos();
		for (Entry<String, DataSourceProp> dataSourceInfo : dataSourceInfos.entrySet()) {
			DataSourceProp dataSourceOptions = dataSourceInfo.getValue();
			String delimiter = dataSourceOptions.getDatasouceDelimiter();
			switch (delimiter) {
			case "\t":
				delimiter = "\\t";
				break;
			case "\n":
				delimiter = "\\n";
				break;
			case "\r":
				delimiter = "\\r";
				break;
			}
			if (dataSourceOptions.getPutType() == 1) {
				String hdfsUrl = ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_HDFS, dataSourceOptions.getIp(),
						dataSourceOptions.getPort(), dataSourceOptions.getDataBaseName());
				StringBuffer sqlb = new StringBuffer("");
				sqlb.append("insert overwrite directory \'").append(hdfsUrl)
						.append("\' ROW FORMAT DELIMITED FIELDS TERMINATED by \'").append(delimiter)
						.append("\' select * from ").append(operationsBean.getHiveTableName());
				jdbcTemplate.execute(sqlb.toString());
			}

		}
	}

	public HiveOperationsBean getOperationsBean() {
		return operationsBean;
	}

	public void setOperationsBean(HiveOperationsBean operationsBean) {
		this.operationsBean = operationsBean;
	}
}
