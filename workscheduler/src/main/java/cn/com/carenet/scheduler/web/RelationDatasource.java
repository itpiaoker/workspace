package cn.com.carenet.scheduler.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import cn.com.carenet.common.web.entity.DatasourceInfos;
import cn.com.carenet.scheduler.bean.DataSourceProp;
import cn.com.carenet.scheduler.constant.WebModuleNameConstant;
import cn.com.carenet.scheduler.entity.SysDatasource;
import cn.com.carenet.scheduler.mapper.SysDatasourceMapper;
import cn.com.carenet.scheduler.utils.ConnectionTypeUtil;

@Repository
public class RelationDatasource {
	@Autowired
	private SysDatasourceMapper sysDatasourceMapper;
	
	public String getInput(Map<String, DataSourceProp> dataSourceMap) {
		List<DatasourceInfos> inputs = new ArrayList<>();
		for (Entry<String, DataSourceProp> dataSourceEntry : dataSourceMap.entrySet()) {
			DataSourceProp dataSourceProp = dataSourceEntry.getValue();
			int putType = dataSourceProp.getPutType();
			if (putType == DataSourceProp.TYPE_INPUT) {
				String dataBaseName = dataSourceProp.getDataBaseName();
				String tableName = dataSourceProp.getTableName();
				String datasourceId = dataSourceProp.getDataSourceId();
				DatasourceInfos datasourceInfos = new DatasourceInfos();
				datasourceInfos.setDatabaseName(dataBaseName);
				datasourceInfos.setTableName(tableName);
				datasourceInfos.setSysDatasourceID(Integer.parseInt(datasourceId));
				inputs.add(datasourceInfos);
			}
		}
		return JSON.toJSONString(inputs);
	}
	
	public String getOutput(Map<String, DataSourceProp> dataSourceMap) {
		List<DatasourceInfos> outputs = new ArrayList<>();
		for (Entry<String, DataSourceProp> dataSourceEntry : dataSourceMap.entrySet()) {
			DataSourceProp dataSourceProp = dataSourceEntry.getValue();
			int putType = dataSourceProp.getPutType();
			if (putType == DataSourceProp.TYPE_OUTPUT) {
				String dataBaseName = dataSourceProp.getDataBaseName();
				String tableName = dataSourceProp.getTableName();
				DatasourceInfos datasourceInfos = new DatasourceInfos();
				datasourceInfos.setDatabaseName(dataBaseName);
				datasourceInfos.setTableName(tableName);
				
				String sourceName = dataSourceProp.getSourceName();
				SysDatasource sysDatasource = new SysDatasource();
				switch (sourceName) {
				case WebModuleNameConstant.kafka:
					sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_KAFKA);
					sysDatasource.setIp(dataSourceProp.getIp());
					sysDatasource.setPort(dataSourceProp.getPort());
					sysDatasource.setUrl(dataSourceProp.getZkServers());
					sysDatasource.setDatabaseName(dataSourceProp.getKafkaTopics());
					sysDatasource.setSeparator(dataSourceProp.getDatasouceDelimiter());
					break;
				case WebModuleNameConstant.hdfs:
					sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_HDFS);
					sysDatasource.setIp(dataSourceProp.getIp());
					sysDatasource.setPort(dataSourceProp.getPort());
					sysDatasource.setDatabaseName(dataSourceProp.getDataBaseName());
					sysDatasource.setUrl(
							ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_HDFS, dataSourceProp.getIp(),
									dataSourceProp.getPort(), dataSourceProp.getDataBaseName()));
					sysDatasource.setSeparator(dataSourceProp.getDatasouceDelimiter());
					break;
				case WebModuleNameConstant.mysql:
					sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_MYSQL);
					sysDatasource.setIp(dataSourceProp.getIp());
					sysDatasource.setPort(dataSourceProp.getPort());
					sysDatasource.setDatabaseName(dataSourceProp.getDataBaseName());
					sysDatasource.setUserName(dataSourceProp.getUserName());
					sysDatasource.setPassword(dataSourceProp.getPassword());
					sysDatasource.setUrl(
							ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_MYSQL, dataSourceProp.getIp(),
									dataSourceProp.getPort(), dataSourceProp.getDataBaseName()));
					sysDatasource.setDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_MYSQL));
					break;
				case WebModuleNameConstant.oracle:
					sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_ORACLE);
					sysDatasource.setIp(dataSourceProp.getIp());
					sysDatasource.setPort(dataSourceProp.getPort());
					sysDatasource.setDatabaseName(dataSourceProp.getDataBaseName());
					sysDatasource.setUserName(dataSourceProp.getUserName());
					sysDatasource.setPassword(dataSourceProp.getPassword());
					sysDatasource.setUrl(
							ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_ORACLE, dataSourceProp.getIp(),
									dataSourceProp.getPort(), dataSourceProp.getDataBaseName()));
					sysDatasource.setDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_ORACLE));
					break;
				case WebModuleNameConstant.greenPlum:
					sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_GP);
					sysDatasource.setIp(dataSourceProp.getIp());
					sysDatasource.setPort(dataSourceProp.getPort());
					sysDatasource.setDatabaseName(dataSourceProp.getDataBaseName());
					sysDatasource.setUserName(dataSourceProp.getUserName());
					sysDatasource.setPassword(dataSourceProp.getPassword());
					sysDatasource.setUrl(
							ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_GP, dataSourceProp.getIp(),
									dataSourceProp.getPort(), dataSourceProp.getDataBaseName()));
					sysDatasource.setDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_GP));
					break;
				case WebModuleNameConstant.hive:
					sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_HIVE);
					sysDatasource.setIp(dataSourceProp.getIp());
					sysDatasource.setPort(dataSourceProp.getPort());
					sysDatasource.setDatabaseName(dataSourceProp.getDataBaseName());
					sysDatasource.setUserName(dataSourceProp.getUserName());
					sysDatasource.setPassword(dataSourceProp.getPassword());
					sysDatasource.setUrl(
							ConnectionTypeUtil.getUrl(ConnectionTypeUtil.DBTYPE_HIVE, dataSourceProp.getIp(),
									dataSourceProp.getPort(), dataSourceProp.getDataBaseName()));
					sysDatasource.setDriver(ConnectionTypeUtil.getDriver(ConnectionTypeUtil.DBTYPE_HIVE));
					break;
				case WebModuleNameConstant.hBase:
					sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_HBASE);
					sysDatasource.setIp(dataSourceProp.getIp());
					sysDatasource.setPort(dataSourceProp.getPort());
					sysDatasource.setDatabaseName(dataSourceProp.getDataBaseName());
					sysDatasource.setUrl(dataSourceProp.getUrls());
					break;
				case WebModuleNameConstant.elasticSearch:
					sysDatasource.setDatabaseType(ConnectionTypeUtil.DBTYPE_ELASTICSEARCH);
					sysDatasource.setIp(dataSourceProp.getIp());
					sysDatasource.setPort(dataSourceProp.getPort());
					sysDatasource.setDatabaseName(dataSourceProp.getDataBaseName());
					sysDatasource.setUrl(
							String.format("%s:%d", dataSourceProp.getIp(), dataSourceProp.getPort()));
					break;
				}
				SysDatasource resultSysDatasource = sysDatasourceMapper.selectOne(sysDatasource);
				
				Integer datasourceId = resultSysDatasource.getId();
				datasourceInfos.setSysDatasourceID(datasourceId);
				outputs.add(datasourceInfos);
			}
		}
		return JSON.toJSONString(outputs);
	}
}
