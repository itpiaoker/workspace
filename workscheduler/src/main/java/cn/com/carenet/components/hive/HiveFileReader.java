package cn.com.carenet.components.hive;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.com.carenet.components.hive.beans.HiveOperationsBean;
import cn.com.carenet.components.hive.utils.HiveUtilHiveJdbc;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.SingleMetaData;
import cn.com.carenet.scheduler.utils.ConnectionTypeUtil;

public class HiveFileReader {
	private JdbcTemplate jdbcTemplate;
	private HiveOperationsBean operationsBean;

	public void prepareDatabase() {
		HiveUtilHiveJdbc hiveJdbcUtils = new HiveUtilHiveJdbc();
		jdbcTemplate = hiveJdbcUtils.prepareHiveJdbc(operationsBean.getHiveJdbcUrl(), operationsBean.getDbName());

		Map<String, DataSourceProp> dataSourceInfos = operationsBean.getDataSourceInfos();
		for (Entry<String, DataSourceProp> dataSourceInfo : dataSourceInfos.entrySet()) {
			DataSourceProp dataSourceOptions = dataSourceInfo.getValue();
			if (dataSourceOptions.getPutType() == 0) {
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

				List<SingleMetaData> dataSourceMetaInfos = dataSourceOptions.getMetaDatas();
				String tableName = dataSourceOptions.getTableName();

				StringBuffer sqlb = new StringBuffer("");
				sqlb.append("create table ").append(tableName).append("(");
				String[] cache = new String[dataSourceMetaInfos.size()];
				for (SingleMetaData metadata : dataSourceMetaInfos) {
					switch (metadata.getFieldType()) {
					case SingleMetaData.TYPE_DATE:
						cache[metadata.getColNum()] = metadata.getFieldName() + " date";
						break;
					case SingleMetaData.TYPE_DOUBLE:
						cache[metadata.getColNum()] = metadata.getFieldName() + " double";
						break;
					case SingleMetaData.TYPE_INTEGER:
						cache[metadata.getColNum()] = metadata.getFieldName() + " int";
						break;
					case SingleMetaData.TYPE_STRING:
						cache[metadata.getColNum()] = metadata.getFieldName() + " string";
						break;
					}
				}
				for (int i = 0; i < dataSourceMetaInfos.size(); i++) {
					if (i < dataSourceMetaInfos.size() - 1) {
						sqlb.append(cache[i]).append(",");
					} else {
						sqlb.append(cache[i]).append(") ROW FORMAT DELEMITED").append(" FIELDS TERMINATED BY \'")
								.append(delimiter).append("\'").append(" STORED AS TEXTFILE");
					}
				}
				String sql = sqlb.toString();
				sqlb = null;
				jdbcTemplate.execute(sql);
			}

		}
	}

	public void localToHive() throws Exception {
		Map<String, DataSourceProp> dataSourceInfos = operationsBean.getDataSourceInfos();
		for (Entry<String, DataSourceProp> dataSourceInfo : dataSourceInfos.entrySet()) {
			DataSourceProp dataSourceOptions = dataSourceInfo.getValue();
			if (dataSourceOptions.getPutType() == 0) {
				String url = dataSourceOptions.getPath();
				StringBuffer sqlb = new StringBuffer("");
				sqlb.append("load data local inpath \'").append(url).append("\' into table ")
						.append(operationsBean.getHiveTableName());
				jdbcTemplate.execute(sqlb.toString());
			}
		}
	}

	public void hdfsToHive() {
		Map<String, DataSourceProp> dataSourceInfos = operationsBean.getDataSourceInfos();
		for (Entry<String, DataSourceProp> dataSourceInfo : dataSourceInfos.entrySet()) {
			DataSourceProp dataSourceOptions = dataSourceInfo.getValue();
			if (dataSourceOptions.getPutType() == 0) {
				String hdfsUrl = ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_HDFS, dataSourceOptions.getIp(),
						dataSourceOptions.getPort(), dataSourceOptions.getDataBaseName());
				StringBuffer sqlb = new StringBuffer("");
				sqlb.append("load data inpath \'").append(hdfsUrl).append("\' into table ")
						.append(operationsBean.getHiveTableName());
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
