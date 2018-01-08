package cn.com.carenet.components.hive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.com.carenet.components.hive.beans.HiveOperationsBean;
import cn.com.carenet.components.hive.utils.HiveUtilJdbc;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.utils.CmdExecutor;

public class HiveRelationalDataBase {
	final private static Logger LOG = LoggerFactory.getLogger(HiveRelationalDataBase.class);

	private String commandEnvironmentPath;

	private HiveOperationsBean operationsBean;

	public void hiveToMysql() throws Exception {
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
				StringBuffer commandStrb = new StringBuffer("");
				// get the location of the hive table from meta data
				HiveConf conf = new HiveConf();
				conf.set(HiveConf.ConfVars.METASTOREURIS.varname, operationsBean.getMetaStoreURI());
				HiveMetaStoreClient hiveMetaStoreClient = new HiveMetaStoreClient(conf);
				// get the meta data to create a new mysql table;
				List<FieldSchema> hiveSchemas = hiveMetaStoreClient.getSchema(operationsBean.getDbName(),
						operationsBean.getHiveTableName());
				Map<String, String> metaFields = new HashMap<>();
				for (int i = 0; i < hiveSchemas.size(); i++) {
					metaFields.put(hiveSchemas.get(i).getName(), schemaTypeTransfer(hiveSchemas.get(i).getType()));
				}
				HiveUtilJdbc jdbcUtil;
				JdbcTemplate rJdbcTemplate;
				CmdExecutor cmdExecutor = new CmdExecutor();
				switch (dataSourceOptions.getSourceName()) {
				case "mysql":
					jdbcUtil = new HiveUtilJdbc(HiveUtilJdbc.mysql,
							String.format("%s:%d", dataSourceOptions.getIp(), dataSourceOptions.getPort()),
							dataSourceOptions.getDataBaseName(), dataSourceOptions.getUserName(),
							dataSourceOptions.getPassword());
					rJdbcTemplate = jdbcUtil.getJdbcTemplate();
					if (!HiveUtilJdbc.isTableExist(rJdbcTemplate, dataSourceOptions.getTableName()))
						HiveUtilJdbc.createTable(rJdbcTemplate, dataSourceOptions.getTableName(), metaFields);

					commandStrb.append("sqoop export --connect jdbc:mysql://").append(dataSourceOptions.getIp())
							.append(":").append(dataSourceOptions.getPort()).append("/")
							.append(dataSourceOptions.getDataBaseName()).append(" --username ")
							.append(dataSourceOptions.getUserName()).append(" --password ")
							.append(dataSourceOptions.getPassword()).append(" --table ")
							.append(dataSourceOptions.getTableName()).append(" --hcatalog-database ")
							.append(operationsBean.getDbName()).append(" --hcatalog-table ")
							.append(operationsBean.getHiveTableName());
					cmdExecutor.execCommand(commandStrb.toString(), null);
					break;
				case "oracle":
					jdbcUtil = new HiveUtilJdbc(HiveUtilJdbc.oracle,
							String.format("%s:%d", dataSourceOptions.getIp(), dataSourceOptions.getPort()),
							dataSourceOptions.getDataBaseName(), dataSourceOptions.getUserName(),
							dataSourceOptions.getPassword());
					rJdbcTemplate = jdbcUtil.getJdbcTemplate();
					if (!HiveUtilJdbc.isTableExist(rJdbcTemplate, dataSourceOptions.getTableName()))
						HiveUtilJdbc.createTable(rJdbcTemplate, dataSourceOptions.getTableName(), metaFields);
					commandStrb.append("sqoop export --connect jdbc:oracle:thin:@").append(dataSourceOptions.getIp())
							.append(":").append(dataSourceOptions.getPort()).append(":")
							.append(dataSourceOptions.getDataBaseName()).append(" --username ")
							.append(dataSourceOptions.getUserName()).append(" --password ")
							.append(dataSourceOptions.getPassword()).append(" --table ")
							.append(dataSourceOptions.getTableName()).append(" --hcatalog-database ")
							.append(operationsBean.getDbName()).append(" --hcatalog-table ")
							.append(operationsBean.getHiveTableName());
					cmdExecutor.execCommand(commandStrb.toString(), null);
					break;
				}
			}
		}
	}

	public void mysqlToHive() {
		try {
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
				if (dataSourceOptions.getPutType() == 0) {
					StringBuffer commandStrb = new StringBuffer("");
					CmdExecutor cmdExecutor = new CmdExecutor();
					switch (dataSourceOptions.getSourceName()) {
					case "mysql":
						commandStrb.append("sqoop import --hive-import --connect jdbc:mysql://")
								.append(dataSourceOptions.getIp()).append(":").append(dataSourceOptions.getPort())
								.append("/").append(dataSourceOptions.getDataBaseName()).append(" --username ")
								.append(dataSourceOptions.getUserName()).append(" --password ")
								.append(dataSourceOptions.getPassword()).append(" --table ")
								.append(dataSourceOptions.getTableName()).append(" --hive-table ")
								.append(dataSourceOptions.getTableName());
						cmdExecutor.execCommand(commandStrb.toString(), null);
						break;
					case "oracle":
						commandStrb.append("sqoop import --hive-import --connect jdbc:oracle:thin:@")
								.append(dataSourceOptions.getIp()).append(":").append(dataSourceOptions.getPort())
								.append(":").append(dataSourceOptions.getDataBaseName()).append(" --username ")
								.append(dataSourceOptions.getUserName()).append(" --password ")
								.append(dataSourceOptions.getPassword()).append(" --table ")
								.append(dataSourceOptions.getTableName()).append(" --hive-table ")
								.append(dataSourceOptions.getTableName());
						cmdExecutor.execCommand(commandStrb.toString(), null);
						break;
					}

				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	protected String schemaTypeTransfer(String type) {
		switch (type.toLowerCase()) {
		case "string":
			type = "text";
			break;

		}
		return type;
	}

	public String getCommandEnvironmentPath() {
		return commandEnvironmentPath;
	}

	public void setCommandEnvironmentPath(String commandEnvironmentPath) {
		this.commandEnvironmentPath = commandEnvironmentPath;
	}

	public HiveOperationsBean getOperationsBean() {
		return operationsBean;
	}

	public void setOperationsBean(HiveOperationsBean operationsBean) {
		this.operationsBean = operationsBean;
	}
}
