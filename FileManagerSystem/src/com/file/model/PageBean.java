package com.file.model;

public class PageBean {
	private int begin;
	private int end;
	private int currentPage = 1;
	private int totalPage;
	private int totalNumber;
	private int pageSize;
	//pageBean is change in session
	private boolean IsChangePageBean;
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isIsChangePageBean() {
		return IsChangePageBean;
	}
	public void setIsChangePageBean(boolean isChangePageBean) {
		IsChangePageBean = isChangePageBean;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageBean [begin=");
		builder.append(begin);
		builder.append(", end=");
		builder.append(end);
		builder.append(", currentPage=");
		builder.append(currentPage);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append(", totalNumber=");
		builder.append(totalNumber);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", IsChangePageBean=");
		builder.append(IsChangePageBean);
		builder.append("]");
		return builder.toString();
	}


	
}
