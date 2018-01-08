//package com.file.test;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.file.model.AuthUserinfo;
//import com.file.service.UserService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"spring-mybatis.xml","/spring-beans.xml","/applicationContext.xml"})
//public class UserServiceImplTest {
//	@Autowired
//	UserService userService;
//	@Test
//	public void queryAllUsers(){
//		List<AuthUserinfo> list=userService.findAllUsers();
//		System.out.println(list.size());
//	}
//}
