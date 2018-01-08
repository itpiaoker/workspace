//package com.file.service;
//
//import com.file.model.AuthRole;
//
///**
// * <p>User: Zhang Kaitao
// * <p>Date: 14-1-28
// * <p>Version: 1.0
// */
//public interface RoleService {
//
//
//    public AuthRole createRole(AuthRole role);
//    public void deleteRole(Long roleId);
//
//    /**
//     * 添加角色-url之间关系
//     * @param roleId
//     * @param permissionIds
//     */
//    public void correlationUrls(Long roleId, Long... urls);
//
//    /**
//     * 移除角色-url之间关系
//     * @param roleId
//     * @param permissionIds
//     */
//    public void uncorrelationUrls(Long roleId, Long... urls);
//    
//    /**
//     * 添加角色-菜单之间关系
//     * @param roleId
//     * @param permissionIds
//     */
//    public void correlationMenus(Long roleId, Long... menus);
//    
//    /**
//     * 移除角色-菜单之间关系
//     * @param roleId
//     * @param permissionIds
//     */
//    public void uncorrelationMenus(Long roleId, Long... menus);
//
//}
