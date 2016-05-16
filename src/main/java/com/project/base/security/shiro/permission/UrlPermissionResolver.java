package com.project.base.security.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

/**  
 * 实现url权限执行
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月10日 下午4:43:00
 */
public class UrlPermissionResolver implements PermissionResolver {

    @Override
    public Permission resolvePermission(String permissionString) {
        return new URLPermission(permissionString);
    }
}
