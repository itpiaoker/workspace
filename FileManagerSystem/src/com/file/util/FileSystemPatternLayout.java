package com.file.util;

import org.apache.log4j.PatternLayout;

/**
 * 解决log4j发送邮件出现乱码的问题。
 * @author scott
 *
 */
public class FileSystemPatternLayout extends PatternLayout {
	private String tContentType = "text/plain";

	public void setContentType(String tContentType) {
		this.tContentType = tContentType;
	}

	public String getContentType() {
		return this.tContentType;
	}
}