package cn.com.carenet.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.esotericsoftware.minlog.Log;

import cn.com.carenet.components.ftp.FtpUtil;
import cn.com.carenet.components.greenplum.GreenPlumManager;
import cn.com.carenet.components.hive.HiveFileReader;
import cn.com.carenet.components.hive.HiveFileWriter;
import cn.com.carenet.components.hive.HiveRelationalDataBase;
import cn.com.carenet.components.hive.beans.HiveOperationsBean;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.bean.EtlAllBean;
import cn.com.carenet.scheduler.bean.OperateComponent;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.dao.JobRunInfoDAO;
import cn.com.carenet.scheduler.entity.TaskDetails;
import cn.com.carenet.scheduler.utils.ConnectionTypeUtil;

@Service
public class ExtractTransformLoadJob {

	@Autowired
	private JobRunInfoDAO jobRunInfoDAO;
	@Autowired
	private GreenPlumManager greenPlumManager;

	public boolean execETL(String workFlowID) throws Exception {
		TaskDetails taskDetails = jobRunInfoDAO.selectTasksDetails(workFlowID);
		if (taskDetails != null) {
			String bigBean = taskDetails.getBigBean();
			JSONObject jobj = JSON.parseObject(bigBean);
			EtlAllBean etlAllBean = jobj.toJavaObject(EtlAllBean.class);

			Map<String, List<String>> inputDatasources = new HashMap<>();
			Map<String, List<String>> outputDatasources = new HashMap<>();
			Map<String, DataSourceProp> datasources = etlAllBean.getDataSources();
			// Map<String, DataSchema> dataSchemas =
			// etlAllBean.getDataSchemas();
			for (Entry<String, DataSourceProp> datasourceEntry : datasources.entrySet()) {
				String id = datasourceEntry.getKey();
				DataSourceProp dataSourceOptions = datasourceEntry.getValue();
				String sourceName = dataSourceOptions.getSourceName();
				int sourceType = dataSourceOptions.getPutType();
				if (sourceType == 0) {
					if (inputDatasources.isEmpty()) {
						List<String> ids = new ArrayList<>();
						ids.add(id);
						inputDatasources.put(sourceName, ids);
					} else {
						if (!inputDatasources.containsKey(sourceName)) {
							List<String> ids = new ArrayList<>();
							ids.add(id);
							inputDatasources.put(sourceName, ids);
						} else {
							List<String> ids = inputDatasources.remove(sourceName);
							ids.add(id);
							inputDatasources.put(sourceName, ids);
						}
					}
				} else {
					if (outputDatasources.isEmpty()) {
						List<String> ids = new ArrayList<>();
						ids.add(id);
						outputDatasources.put(sourceName, ids);
					} else {
						if (!outputDatasources.containsKey(sourceName)) {
							List<String> ids = new ArrayList<>();
							ids.add(id);
							outputDatasources.put(sourceName, ids);
						} else {
							List<String> ids = outputDatasources.remove(sourceName);
							ids.add(id);
							outputDatasources.put(sourceName, ids);
						}
					}
				}
			}

			for (Entry<String, List<String>> inputEntry : inputDatasources.entrySet()) {
				String sourceName = inputEntry.getKey();
				List<String> fileIds = inputEntry.getValue();
				switch (sourceName) {
				case WebModuleNameConstant.localFile:
					return localFileETL(workFlowID, fileIds, datasources, inputDatasources, outputDatasources);
				case WebModuleNameConstant.hdfs:
					return hdfsETL(workFlowID, fileIds, datasources, inputDatasources, outputDatasources);
				case WebModuleNameConstant.hive:
					return hiveETL(workFlowID, fileIds, datasources, inputDatasources, outputDatasources);
				case WebModuleNameConstant.greenPlum:
					return greenPlumManager.execGreenPlumJob(workFlowID);
				case WebModuleNameConstant.ftp:
					return ftpETL(workFlowID, fileIds, datasources, inputDatasources, outputDatasources);
				case WebModuleNameConstant.mysql:
					return mysqlETL(workFlowID, fileIds, datasources, inputDatasources, outputDatasources);
				}
			}
		}
		return false;
	}

	private boolean localFileETL(String workFlowID, List<String> fileIds, Map<String, DataSourceProp> datasources,
			Map<String, List<String>> inputDatasources, Map<String, List<String>> outputDatasources) {
		try {
			if (outputDatasources.containsKey(WebModuleNameConstant.hdfs)) {
				return false;
			} else if (outputDatasources.containsKey(WebModuleNameConstant.hive)) {
				HiveFileReader hfr = new HiveFileReader();
				HiveOperationsBean hiveOperationsBean = new HiveOperationsBean();
				String id = outputDatasources.get(WebModuleNameConstant.hive).get(0);
				DataSourceProp dsb = datasources.get(id);
				String thrftUrl = dsb.getMetaStoreURI();
				int index0 = thrftUrl.indexOf("//");
				int index1 = thrftUrl.indexOf(":");
				String ip;
				if (index0 > 0) {
					ip = thrftUrl.substring(index0, index1);
				} else {
					ip = thrftUrl.substring(0, index1);
				}
				hiveOperationsBean.setDataSourceInfos(datasources);
				hiveOperationsBean.setDbName(dsb.getDataBaseName());
				hiveOperationsBean.setHiveJdbcUrl(String.format("%s:%d", ip, 10000));
				hiveOperationsBean.setHiveTableName(dsb.getTableName());
				hiveOperationsBean.setMetaStoreURI(dsb.getMetaStoreURI());
				hfr.setOperationsBean(hiveOperationsBean);
				// create hive table;
				hfr.prepareDatabase();
				hfr.localToHive();
			} else if (outputDatasources.containsKey(WebModuleNameConstant.greenPlum)) {
				greenPlumManager.execGreenPlumJob(workFlowID);
			} else if (outputDatasources.containsKey(WebModuleNameConstant.ftp)) {
				String id = outputDatasources.get(WebModuleNameConstant.ftp).get(0);
				DataSourceProp dsb = datasources.get(id);
				FtpUtil ftpUtil = new FtpUtil();
				ftpUtil.prepare(dsb.getIp(), dsb.getPort(), dsb.getUserName(), dsb.getPassword(), dsb.getEncoding());
				for (String fileId : fileIds) {
					boolean isPreOp = false;
					for (OperateComponent component : dsb.getComponents()) {
						if (fileId == component.getPreOpID()) {
							isPreOp = true;
						}
					}
					if (isPreOp) {
						DataSourceProp dataSourceOptionsBean = datasources.get(fileId);
						String localFile = dataSourceOptionsBean.getPath();
						ftpUtil.localToFtp(localFile, dsb.getPath());
					}
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean mysqlETL(String workFlowID, List<String> fileIds, Map<String, DataSourceProp> datasources,
			Map<String, List<String>> inputDatasources, Map<String, List<String>> outputDatasources) {
		try {
			if (outputDatasources.containsKey(WebModuleNameConstant.localFile)) {
				return false;
			} else if (outputDatasources.containsKey(WebModuleNameConstant.hive)) {
				HiveRelationalDataBase hrd = new HiveRelationalDataBase();
				HiveOperationsBean hiveOperationsBean = new HiveOperationsBean();
				String id = outputDatasources.get(WebModuleNameConstant.hive).get(0);
				DataSourceProp dsb = datasources.get(id);
				String thrftUrl = dsb.getMetaStoreURI();
				int index0 = thrftUrl.indexOf("//");
				int index1 = thrftUrl.indexOf(":");
				String ip;
				if (index0 > 0) {
					ip = thrftUrl.substring(index0, index1);
				} else {
					ip = thrftUrl.substring(0, index1);
				}
				hiveOperationsBean.setDataSourceInfos(datasources);
				hiveOperationsBean.setDbName(dsb.getDataBaseName());
				hiveOperationsBean.setHiveJdbcUrl(String.format("%s:%d", ip, 10000));
				hiveOperationsBean.setHiveTableName(dsb.getTableName());
				hiveOperationsBean.setMetaStoreURI(dsb.getMetaStoreURI());
				hrd.setOperationsBean(hiveOperationsBean);
				// create hive table;
				hrd.mysqlToHive();
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean ftpETL(String workFlowID, List<String> fileIds, Map<String, DataSourceProp> datasources,
			Map<String, List<String>> inputDatasources, Map<String, List<String>> outputDatasources) {
		try {
			if (outputDatasources.containsKey(WebModuleNameConstant.hdfs)) {
				for (String fileId : fileIds) {
					boolean isPreOp = false;
					DataSourceProp dataSourceOptions = datasources.get(fileId);
					FtpUtil ftpUtil = new FtpUtil();
					ftpUtil.prepare(dataSourceOptions.getIp(), dataSourceOptions.getPort(),
							dataSourceOptions.getUserName(), dataSourceOptions.getPassword(),
							dataSourceOptions.getEncoding());
					List<String> oIds = outputDatasources.get(WebModuleNameConstant.hdfs);
					for (String oId : oIds) {
						for (OperateComponent component : datasources.get(oId).getComponents()) {
							if (fileId == component.getPreOpID()) {
								isPreOp = true;
							}
						}
						if (isPreOp) {
							DataSourceProp dataSourceOptionsHDFS = datasources.get(fileId);
							String hdfsUrl = ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_HDFS,
									dataSourceOptionsHDFS.getIp(), dataSourceOptionsHDFS.getPort(),
									dataSourceOptionsHDFS.getDataBaseName());
							ftpUtil.ftpToHdfs(dataSourceOptions.getUrls(), hdfsUrl);
						}
					}
				}
			} else if (outputDatasources.containsKey(WebModuleNameConstant.localFile)) {
				for (String fileId : fileIds) {
					boolean isPreOp = false;
					DataSourceProp dataSourceOptions = datasources.get(fileId);
					FtpUtil ftpUtil = new FtpUtil();
					ftpUtil.prepare(dataSourceOptions.getIp(), dataSourceOptions.getPort(),
							dataSourceOptions.getUserName(), dataSourceOptions.getPassword(),
							dataSourceOptions.getEncoding());
					List<String> oIds = outputDatasources.get(WebModuleNameConstant.localFile);
					for (String oId : oIds) {
						for (OperateComponent component : datasources.get(oId).getComponents()) {
							if (fileId == component.getPreOpID()) {
								isPreOp = true;
							}
						}
						if (isPreOp) {
							DataSourceProp dataSourceOptionsLocal = datasources.get(fileId);
							ftpUtil.ftpToLocal(dataSourceOptions.getPath(), dataSourceOptionsLocal.getPath());
						}
					}
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean hdfsETL(String workFlowID, List<String> fileIds, Map<String, DataSourceProp> datasources,
			Map<String, List<String>> inputDatasources, Map<String, List<String>> outputDatasources) {
		try {
			if (outputDatasources.containsKey(WebModuleNameConstant.greenPlum)) {
				greenPlumManager.execGreenPlumJob(workFlowID);
			} else if (outputDatasources.containsKey(WebModuleNameConstant.hive)) {
				HiveFileReader hfr = new HiveFileReader();
				HiveOperationsBean hiveOperationsBean = new HiveOperationsBean();
				String id = outputDatasources.get(WebModuleNameConstant.hive).get(0);
				DataSourceProp dsb = datasources.get(id);
				String thrftUrl = dsb.getMetaStoreURI();
				int index0 = thrftUrl.indexOf("//");
				int index1 = thrftUrl.indexOf(":");
				String ip;
				if (index0 > 0) {
					ip = thrftUrl.substring(index0, index1);
				} else {
					ip = thrftUrl.substring(0, index1);
				}
				hiveOperationsBean.setDataSourceInfos(datasources);
				hiveOperationsBean.setDbName(dsb.getDataBaseName());
				hiveOperationsBean.setHiveJdbcUrl(String.format("%s:%d", ip, 10000));
				hiveOperationsBean.setHiveTableName(dsb.getTableName());
				hiveOperationsBean.setMetaStoreURI(dsb.getMetaStoreURI());
				hfr.setOperationsBean(hiveOperationsBean);
				// create hive table;
				hfr.prepareDatabase();
				hfr.hdfsToHive();
			} else if (outputDatasources.containsKey(WebModuleNameConstant.ftp)) {
				String id = outputDatasources.get(WebModuleNameConstant.ftp).get(0);
				DataSourceProp dsb = datasources.get(id);
				FtpUtil ftpUtil = new FtpUtil();
				ftpUtil.prepare(dsb.getIp(), dsb.getPort(), dsb.getUserName(), dsb.getPassword(), dsb.getEncoding());
				for (String fileId : fileIds) {
					boolean isPreOp = false;
					for (OperateComponent component : dsb.getComponents()) {
						if (fileId == component.getPreOpID()) {
							isPreOp = true;
						}
					}
					if (isPreOp) {
						DataSourceProp dataSourceOptionsBean = datasources.get(fileId);
						String databaseName = dataSourceOptionsBean.getDataBaseName();
						if (databaseName.startsWith("/") || databaseName.startsWith("\\")) {
							databaseName = databaseName.substring(1);
						}
						String hdfsPath = ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_HDFS,
								dataSourceOptionsBean.getIp(), dataSourceOptionsBean.getPort(), databaseName);
						ftpUtil.hdfsToFtp(hdfsPath, dsb.getPath());
					}
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean hiveETL(String workFlowID, List<String> fileIds, Map<String, DataSourceProp> datasources,
			Map<String, List<String>> inputDatasources, Map<String, List<String>> outputDatasources) {
		try {
			if (outputDatasources.containsKey(WebModuleNameConstant.hdfs)) {
				HiveFileWriter hfw = new HiveFileWriter();
				HiveOperationsBean hiveOperationsBean = new HiveOperationsBean();
				for (String id : fileIds) {
					DataSourceProp dsb = datasources.get(id);
					String thrftUrl = dsb.getMetaStoreURI();
					int index0 = thrftUrl.indexOf("//");
					int index1 = thrftUrl.indexOf(":");
					String ip;
					if (index0 > 0) {
						ip = thrftUrl.substring(index0, index1);
					} else {
						ip = thrftUrl.substring(0, index1);
					}
					hiveOperationsBean.setDataSourceInfos(datasources);
					hiveOperationsBean.setDbName(dsb.getDataBaseName());
					hiveOperationsBean.setHiveJdbcUrl(String.format("%s:%d", ip, 10000));
					hiveOperationsBean.setHiveTableName(dsb.getTableName());
					hiveOperationsBean.setMetaStoreURI(dsb.getMetaStoreURI());
					hfw.setOperationsBean(hiveOperationsBean);
					hfw.hiveToHdfs();
				}

			} else if (outputDatasources.containsKey(WebModuleNameConstant.localFile)) {
				HiveFileWriter hfw = new HiveFileWriter();
				HiveOperationsBean hiveOperationsBean = new HiveOperationsBean();
				for (String id : fileIds) {
					DataSourceProp dsb = datasources.get(id);
					String thrftUrl = dsb.getMetaStoreURI();
					int index0 = thrftUrl.indexOf("//");
					int index1 = thrftUrl.indexOf(":");
					String ip;
					if (index0 > 0) {
						ip = thrftUrl.substring(index0, index1);
					} else {
						ip = thrftUrl.substring(0, index1);
					}
					hiveOperationsBean.setDataSourceInfos(datasources);
					hiveOperationsBean.setDbName(dsb.getDataBaseName());
					hiveOperationsBean.setHiveJdbcUrl(String.format("%s:%d", ip, 10000));
					hiveOperationsBean.setHiveTableName(dsb.getTableName());
					hiveOperationsBean.setMetaStoreURI(dsb.getMetaStoreURI());
					hfw.setOperationsBean(hiveOperationsBean);
					hfw.hiveToLocal();
					;
				}
			} else if (outputDatasources.containsKey(WebModuleNameConstant.mysql)) {
				HiveRelationalDataBase hrd = new HiveRelationalDataBase();
				HiveOperationsBean hiveOperationsBean = new HiveOperationsBean();
				for (String id : fileIds) {
					DataSourceProp dsb = datasources.get(id);
					String thrftUrl = dsb.getMetaStoreURI();
					int index0 = thrftUrl.indexOf("//");
					int index1 = thrftUrl.indexOf(":");
					String ip;
					if (index0 > 0) {
						ip = thrftUrl.substring(index0, index1);
					} else {
						ip = thrftUrl.substring(0, index1);
					}
					hiveOperationsBean.setDataSourceInfos(datasources);
					hiveOperationsBean.setDbName(dsb.getDataBaseName());
					hiveOperationsBean.setHiveJdbcUrl(String.format("%s:%d", ip, 10000));
					hiveOperationsBean.setHiveTableName(dsb.getTableName());
					hiveOperationsBean.setMetaStoreURI(dsb.getMetaStoreURI());
					hrd.setOperationsBean(hiveOperationsBean);
					hrd.hiveToMysql();
				}
			} else {
				return false;
			}
			return true;
		} catch (Exception e) {
			Log.error(e.getMessage());
			return false;
		}
	}
}
