package cn.com.carenet.components.greenplum;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.com.carenet.components.greenplum.bean.PublicDataSourceInfo;
import cn.com.carenet.components.greenplum.input.GPloadRunner;
import cn.com.carenet.components.greenplum.utils.GreenPlumSQL;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.SingleMetaData;
import cn.com.carenet.scheduler.utils.ConnectionTypeUtil;

public class GreenPlumControl {
	private GreenPlumSQL greenPlumSQL;
	private GPloadRunner GPloadRunner = new GPloadRunner();

	public static String url;
	public static String database;
	public static String user;
	public static String passwd;

	public static String ssh_ip;
	public static String ssh_user;
	public static String ssh_pwd;

	public GreenPlumControl(String url, String database, String user, String passwd) {
		GreenPlumControl.url = url;
		GreenPlumControl.database = database;
		GreenPlumControl.user = user;
		GreenPlumControl.passwd = passwd;
		greenPlumSQL = new GreenPlumSQL(url, database, user, passwd);
	}

	public void hdfsInput(DataSourceProp dataSourceOptionsBean) {
		createExteranlTable(dataSourceOptionsBean);
	}

	public boolean localInput(DataSourceProp dataSourceOptionsBean) {
		GreenPlumControl.ssh_ip = dataSourceOptionsBean.getIp();
		GreenPlumControl.ssh_user = dataSourceOptionsBean.getUserName();
		GreenPlumControl.ssh_pwd = dataSourceOptionsBean.getPassword();

		String[] args = new String[] { "--input=" + dataSourceOptionsBean.getPath(),
				"--table=" + dataSourceOptionsBean.getTableName(), "--url=" + url + "/" + database, "--user=" + user,
				"--delimiter=" + dataSourceOptionsBean.getDatasouceDelimiter() };
		boolean execStat = GPloadRunner.localInsert(args);
		return execStat;
	}

	public void localOutput(DataSourceProp  dataSourceOptionsBean, String tablequery) {
		String[] tqs = StringUtils.splitPreserveAllTokens(tablequery, ";");
		for (String tq : tqs) {
			if (tq != "" || !tq.equals(""))
				outputFile(tq, dataSourceOptionsBean.getPath(), dataSourceOptionsBean.getDatasouceDelimiter());
		}
	}

	private void outputFile(String tablequery, String path, String delimter) {
		StringBuffer sql = new StringBuffer();

		sql.append("COPY ");
		if (tablequery.toLowerCase().indexOf("select") == -1) {
			sql.append(tablequery);
		} else {
			sql.append(" ( " + tablequery + " ) ");
		}
		sql.append(" TO ");
		sql.append("'" + path + "'");
		sql.append(" DELIMITER '" + delimter + "'");

		greenPlumSQL.runSQL(sql.toString());
	}

	private boolean createExteranlTable(DataSourceProp dataSourceOptionsBean) {

		Map<Integer, PublicDataSourceInfo> hashInfos = new HashMap<>();
		for (SingleMetaData operateOptionsDataSourceInfo : dataSourceOptionsBean.getMetaDatas()) {
			PublicDataSourceInfo public_data_source_info = new PublicDataSourceInfo();
			public_data_source_info.setCol_Num(operateOptionsDataSourceInfo.getColNum());
			public_data_source_info.setField_name(operateOptionsDataSourceInfo.getFieldName());
			String fieldTypeName = operateOptionsDataSourceInfo.getFieldType();
			switch (fieldTypeName) {
			case SingleMetaData.TYPE_INTEGER:
				fieldTypeName = "int";
				break;
			case SingleMetaData.TYPE_DATE:
				fieldTypeName = "date";
				break;
			default:
				fieldTypeName = "text";
				break;
			}
			public_data_source_info.setField_type(fieldTypeName);
			hashInfos.put(operateOptionsDataSourceInfo.getColNum(), public_data_source_info);
		}

		StringBuffer sql = new StringBuffer();
		sql.append("CREATE EXTERNAL TABLE " + dataSourceOptionsBean.getTableName() + "");
		sql.append("(");
		for (int i = 0; i < hashInfos.size(); i++) {
			PublicDataSourceInfo publicDataSourceInfo = hashInfos.get(i);
			sql.append(publicDataSourceInfo.getField_name() + "\t");
			sql.append(publicDataSourceInfo.getField_type());
			if (i < hashInfos.size() - 1)
				sql.append(",");
		}

		sql.append(")");
		sql.append(
				"LOCATION ('" + ConnectionTypeUtil
						.getUrl(ConnectionTypeUtil.DBTYPE_HDFS, dataSourceOptionsBean.getIp(),
								dataSourceOptionsBean.getPort(), dataSourceOptionsBean.getDataBaseName())
						.replaceAll("hdfs", "gphdfs") + "')");
		sql.append("FORMAT 'TEXT' (DELIMITER '" + dataSourceOptionsBean.getDatasouceDelimiter() + "');");
		boolean execStat = greenPlumSQL.runSQL(sql.toString());
		return execStat;
	}

}
