package cn.com.linnax.file.util;

import org.apache.log4j.HTMLLayout;

public class Loger4jHtmlLayout extends HTMLLayout {
    @Override
    public String getContentType() {
        return "text/html;charset=utf-8"; 
    }
}
