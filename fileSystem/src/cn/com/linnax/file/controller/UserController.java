package cn.com.linnax.file.controller;
//package com.file.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.file.model.PageBean;
//import com.file.service.FileService;
//
//@Controller
//@RequestMapping("/user")
//public class UserController {
//	@Autowired
//	private FileService fileService;
//	@RequestMapping("/login")
//	public ModelAndView queryUserByUsername(HttpServletRequest req, Model model,PageBean pageBean) {
//		System.out.println("queryUserByUsername==========");
//		ModelAndView view =new ModelAndView();
//        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
//        String error = null;
//        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
//            error = "用户名/密码错误";
//        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
//            error = "用户名/密码错误";
//        } else if(exceptionClassName != null) {
//            error = "其他错误：" + exceptionClassName;
//        }
//        view.setViewName("index");
//        view.addObject("error", error);
//        return view;
//	}
//}
