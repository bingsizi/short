package com.project.base.security.user.service;

import java.util.List;
import java.util.Set;
import com.project.base.security.menu.entity.Menu;
import com.project.base.security.role.entity.Role;
import com.project.base.security.user.entity.User;
import com.project.framework.controller.Page;

/**
 * 用户业务接口
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月9日 上午10:17:15
 */
public interface UserSerivce{
	
	/**
	 * 保存用户
	 * @param user
	 * @author zhangpeiran 2016年5月16日 下午1:53:22
	 */
	public void save(User user);
	
	/**
	 * 锁定用户
	 * @param id
	 * @author zhangpeiran 2016年5月12日 下午2:49:22
	 */
	public void lock(Long id);
	/**
	 * 解锁用户
	 * @param id
	 * @author zhangpeiran 2016年5月12日 下午2:50:47
	 */
	public void unLock(Long id);
	
	/**
	 * 保存用户
	 * @param user
	 * @param roleIds
	 * @author zhangpeiran 2016年5月12日 下午1:52:37
	 */
	public void saveUser(User user,Long[] roleIds);
	/**
	 * 根据id返回
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月16日 上午11:21:16
	 */
	public User find(Long id);
	/**
	 * 分页查询user
	 * @param orgId
	 * @param username
	 * @param realName
	 * @return
	 * @author zhangpeiran 2016年5月12日 上午11:08:10
	 */
	public Page<User> findPage(Page<User> page,Long orgId,String username,String realName);

	/**
	 * 根据用户名得到用户
	 * 
	 * @param username
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午10:19:40
	 */
	public User findByUsername(String username);

	/**
	 * 根据用户名获得角色标识集合
	 * 
	 * @param username
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午10:27:13
	 */
	public Set<String> findRoleNameSet(String username);

	/**
	 * 根据用户id返回角色list
	 * 
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午10:59:16
	 */
	public List<Role> findRoles(Long id);

	/**
	 * 根据用户名得到资源权限标识集合
	 * 
	 * @param username
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午11:00:36
	 */
	public Set<String> findPermissions(String username);

	/**
	 * 根据用户id,返回拥有的所有菜单和权限
	 * 
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午11:01:17
	 */
	public List<Menu> findMenus(Long id);
}
