package cn.com.carenet.scheduler.bean;

import java.util.List;

import cn.com.carenet.scheduler.bean.spark.DataSource;
import cn.com.carenet.scheduler.bean.spark.FieldType;
import cn.com.carenet.scheduler.bean.spark.OperateType;


public class CommMapReduceBean {
	private List<DataSource> DataSources;
	private List<OperateType> operates;
	private List<FieldType> fields;
	
	public CommMapReduceBean(){
		
	}

	public List<DataSource> getDataSources() {
		return DataSources;
	}

	public void setDataSources(List<DataSource> dataSources) {
		DataSources = dataSources;
	}

	public List<OperateType> getOperates() {
		return operates;
	}

	public void setOperates(List<OperateType> operates) {
		this.operates = operates;
	}

	public List<FieldType> getFields() {
		return fields;
	}

	public void setFields(List<FieldType> fields) {
		this.fields = fields;
	}
}