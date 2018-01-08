package cn.com.linnax.file.service.impl;
//package com.file.service.impl;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Set;
//
//import com.file.dao.AuthUserinfoDAO;
//import com.file.model.AuthUserinfo;
//import com.file.service.PasswordHelper;
//import com.file.service.UserService;
//
///**
// * <p>User: Zhang Kaitao
// * <p>Date: 14-1-28
// * <p>Version: 1.0
// */
//public class UserServiceImpl implements UserService {
//
//    public UserServiceImpl() {
//		super();
//	}
//
//	private AuthUserinfoDAO userDao;
//
//    public void setUserDao(AuthUserinfoDAO userDao) {
//        this.userDao = userDao;
//    }
//
//    public AuthUserinfoDAO getUserDao() {
//		return userDao;
//	}
//
//	private PasswordHelper passwordHelper;
//
//    public void setPasswordHelper(PasswordHelper passwordHelper) {
//        this.passwordHelper = passwordHelper;
//    }
//
//	@Override
//	public AuthUserinfo createUser(AuthUserinfo user) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void changePassword(Long userId, String newPassword) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void correlationRoles(Long userId, Long... roleIds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void uncorrelationRoles(Long userId, Long... roleIds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public AuthUserinfo findByUsername(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Set<String> findRoles(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Set<String> findUrls(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Set<String> findmenus(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<AuthUserinfo> findAllUsers() {
//		List<AuthUserinfo> list=null;
//		try {
//			list=userDao.selectByExample(null);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//
//
//
//}
