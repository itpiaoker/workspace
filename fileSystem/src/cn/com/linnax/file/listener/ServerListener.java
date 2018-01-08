package cn.com.linnax.file.listener;
//package com.file.listener;
//
//import java.util.Date;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
//import org.apache.log4j.Logger;
//
//import com.file.util.InitIndexData;
//
//
//public class ServerListener implements  ServletContextListener {
//
//	private static Logger log=Logger.getLogger(ServerListener.class);
//
//    public void contextInitialized(ServletContextEvent event) {
////        restartNotifcation();
//    }
//
//    public void contextDestroyed(ServletContextEvent event) {
////    	stoptNotifcation();
//    }
//    
//    public void restartNotifcation() {
//        try {
//            String indexPath=System.getProperty("catalina.home")+"/index";
//            InitIndexData.init(indexPath);
//            throw new Exception("aliyun tomcat restart at " + new Date());
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//    
//    public void stoptNotifcation() {
//        try {
//            throw new Exception("aliyun tomcat stop at " + new Date());
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//}
