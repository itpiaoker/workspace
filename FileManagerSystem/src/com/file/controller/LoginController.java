package com.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
@Controller
public class LoginController {
	@RequestMapping("login.html")
    public String login(HttpServletRequest request){
		System.out.println("login============");
		//String resultPageURL = InternalResourceViewResolver.FORWARD_URL_PREFIX + "/";  
        String username = request.getParameter("username");  
        String password = request.getParameter("password");
        System.out.println("".equals(username));
        System.out.println("".equals(password));
		return "login"; 
		
		
		
	}
}
