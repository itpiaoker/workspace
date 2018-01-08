package cn.com.linnax.file.realm;
//package com.file.realm;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.cache.Cache;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//
//import com.file.model.AuthUserinfo;
//import com.file.service.UserService;
//
///**
// * 
// * @Description: 
// * @Author: w-lianxy
// * @Revision: 122149
// * @Date: 2015-9-16 下午2:28:03
// * <pre>
// * @Modification History:
// * [Date]         [Author]        [Description]
// * 
// * </pre>
// */
//public class UserRealm extends AuthorizingRealm {
//
//    private UserService userService;
//
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
////    	System.out.println("doGetAuthorizationInfo===============");
////        String username = (String)principals.getPrimaryPrincipal();
////
////        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
////        authorizationInfo.setRoles(userService.findRoles(username));
////        authorizationInfo.setStringPermissions(userService.findmenus(username));
////
////        return authorizationInfo;
//    	
//		/* 这里编写授权代码 */
//		Set<String> roleNames = new HashSet<String>();
//	    Set<String> permissions = new HashSet<String>();
//	    roleNames.add("admin");
//	    roleNames.add("zhangsan");
//	    permissions.add("user.do?myjsp");
//	    permissions.add("login.do?main");
//	    permissions.add("login.do?logout");
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
//	    info.setStringPermissions(permissions);
//		return info;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
////    	System.out.println("doGetAuthenticationInfo===============");
////        String username = (String)token.getPrincipal();
////
////        AuthUserinfo user = userService.findByUsername(username);
////
////        if(user == null) {
////            throw new UnknownAccountException();//没找到帐号
////        }
////
//////        if(Boolean.TRUE.equals(user.getLocked())) {
//////            throw new LockedAccountException(); //帐号锁定
//////        }
////
////        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
////        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
////                user.getLoginname(), //用户名
////                user.getLoginpsw(), //密码
////                ByteSource.Util.bytes(user.getLoginname()+user.getLoginpsw()),//salt=username+salt
////                getName()  //realm name
////        );
////        return authenticationInfo;
//    	
//    	
//		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//		System.out.println(token.getUsername());
//		AuthUserinfo user = userService.findByUsername(token.getUsername());
//		System.out.println(user);
//		if (user != null) {
//			return new SimpleAuthenticationInfo(user.getLoginname(), user.getLoginpsw(), getName());
//		}else{
//			throw new AuthenticationException();
//		}
//    }
//    
//	/**
//	 * 更新用户授权信息缓存.
//	 */
//	public void clearCachedAuthorizationInfo(String principal) {
//		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
//		clearCachedAuthorizationInfo(principals);
//	}
//
//	/**
//	 * 清除所有用户授权信息缓存.
//	 */
//	public void clearAllCachedAuthorizationInfo() {
//		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
//		if (cache != null) {
//			for (Object key : cache.keys()) {
//				cache.remove(key);
//			}
//		}
//	}
//
//}
