package com.project.base.security.role.service;

import java.util.List;

import com.project.base.security.role.entity.UserRole;

/**  
 * 用户角色接口
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月16日 上午11:46:13
 */
public interface UserRoleService {
	/**
	 * 判断角色有没有被使用
	 * @param roleId
	 * @return
	 * @author zhangpeiran 2016年5月16日 上午11:49:21
	 */
	public boolean isRoleUsed(Long roleId);
	/**
	 * 根据userId返回用户角色
	 * @param userId
	 * @return
	 * @author zhangpeiran 2016年5月16日 上午11:55:52
	 */
	public List<UserRole> findByUser(Long userId);
	/**
	 * 根据userId删除角色
	 * @param userId
	 * @author zhangpeiran 2016年5月16日 上午11:58:06
	 */
	public void deleteByUser(Long userId);
	/**
	 * 保存用户角色
	 * @param ur
	 * @author zhangpeiran 2016年5月16日 上午11:59:28
	 */
	public void save(UserRole ur);
}
