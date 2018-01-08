package cn.com.linnax.file.model;

public class PageBean {
	private int begin=0;
	private int page=1;
	private int rows=10;

	public int getBegin() {
		return (page-1)*rows;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageBean [begin=");
		builder.append(begin);
		builder.append(", page=");
		builder.append(page);
		builder.append(", rows=");
		builder.append(rows);
		builder.append("]");
		return builder.toString();
	}


}
