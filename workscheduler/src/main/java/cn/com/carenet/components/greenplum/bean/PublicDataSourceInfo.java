package cn.com.carenet.components.greenplum.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PublicDataSourceInfo {
	private String field_name;
	private String field_type;
	private int col_Num;

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getField_type() {
		return field_type;
	}

	public void setField_type(String field_type) {
		this.field_type = field_type;
	}

	public int getCol_Num() {
		return col_Num;
	}

	public void setCol_Num(int col_Num) {
		this.col_Num = col_Num;
	}

}
