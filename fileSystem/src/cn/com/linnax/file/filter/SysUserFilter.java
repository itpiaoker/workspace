package cn.com.linnax.file.filter;
//package com.file.filter;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.web.filter.PathMatchingFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.file.model.AuthUserinfo;
//import com.file.service.UserService;
//import com.file.util.Constants;
//
///**
// * <p>User: Zhang Kaitao
// * <p>Date: 14-2-15
// * <p>Version: 1.0
// */
//public class SysUserFilter extends PathMatchingFilter {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//    	System.out.println("SysUserFilter============");
//        String username = (String)SecurityUtils.getSubject().getPrincipal();
//        request.setAttribute(Constants.CURRENT_USER, userService.findByUsername(username));
//        return true;
//    }
//}
