package cn.com.carenet.components.greenplum.utils;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class BeanConnect {

	private static AbstractApplicationContext conn=new ClassPathXmlApplicationContext("spring.xml");
	public static AbstractApplicationContext getcontext(){
		
		return conn;
	}
	
}
