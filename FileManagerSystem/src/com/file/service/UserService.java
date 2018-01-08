//package com.file.service;
//
//
//import java.util.List;
//import java.util.Set;
//
//import com.file.model.AuthUserinfo;
//
///**
// * 
// * @Description: 
// * @Author: w-lianxy
// * @Revision: 122149
// * @Date: 2015-9-16 上午11:19:54
// * <pre>
// * @Modification History:
// * [Date]         [Author]        [Description]
// * 
// * </pre>
// */
//public interface UserService {
//
//    /**
//     * 创建用户
//     * @param user
//     */
//    public AuthUserinfo createUser(AuthUserinfo user);
//
//    /**
//     * 修改密码
//     * @param userId
//     * @param newPassword
//     */
//    public void changePassword(Long userId, String newPassword);
//
//    /**
//     * 添加用户-角色关系
//     * @param userId
//     * @param roleIds
//     */
//    public void correlationRoles(Long userId, Long... roleIds);
//
//
//    /**
//     * 移除用户-角色关系
//     * @param userId
//     * @param roleIds
//     */
//    public void uncorrelationRoles(Long userId, Long... roleIds);
//
//    /**
//     * 根据用户名查找用户
//     * @param username
//     * @return
//     */
//    public AuthUserinfo findByUsername(String username);
//    
//    /**
//     * 查找所有用户
//     * @param username
//     * @return
//     */
//    public List<AuthUserinfo> findAllUsers();
//
//    /**
//     * 根据用户名查找其角色
//     * @param username
//     * @return
//     */
//    public Set<String> findRoles(String username);
//    
//    /**
//     * 根据用户名查找其url
//     * @param username
//     * @return
//     */
//    public Set<String> findUrls(String username);
//    
//    /**
//     * 根据用户名查找其menu
//     * @param username
//     * @return
//     */
//    public Set<String> findmenus(String username);
//
////    /**
////     * 根据用户名查找其权限
////     * @param username
////     * @return
////     */
////    public Set<String> findPermissions(String username);
//
//}
