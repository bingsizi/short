package com.project.base.security.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.project.base.security.menu.entity.Menu;
import com.project.base.security.menu.service.MenuService;
import com.project.base.security.role.entity.Role;
import com.project.base.security.role.service.RoleService;
import com.project.base.security.user.dao.UserDao;
import com.project.base.security.user.entity.User;
import com.project.base.security.user.service.UserSerivce;
import com.project.framework.common.Constants;
import com.project.framework.controller.Page;
import com.project.framework.util.DateUtils;

/**
 * 用户业务层
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月9日 上午10:17:15
 */
@Service
public class UserSerivceImpl implements UserSerivce{
	
	@Resource
	private RoleService roleService;
	@Resource
	private MenuService menuService;
	@Resource
	private UserDao userDao;
	
	@Override
	public void save(User user) {
		userDao.save(user);
	}
	/**
	 * 锁定用户
	 * @param id
	 * @author zhangpeiran 2016年5月12日 下午2:49:22
	 */
	@Override
	public void lock(Long id){
		Map<String,Object> map = new HashMap<>();
		map.put("id",id);
		map.put("locked",Constants.YES);
		userDao.executeUpdate("update User set locked=:locked where id=:id", map);
	}
	/**
	 * 解锁用户
	 * @param id
	 * @author zhangpeiran 2016年5月12日 下午2:50:47
	 */
	@Override
	public void unLock(Long id){
		Map<String,Object> map = new HashMap<>();
		map.put("id",id);
		map.put("locked",Constants.NO);
		userDao.executeUpdate("update User set locked=:locked where id=:id", map);
	}
	
	/**
	 * 保存用户
	 * @param user
	 * @param roleIds
	 * @author zhangpeiran 2016年5月12日 下午1:52:37
	 */
	@Override
	public void saveUser(User user,Long[] roleIds){
		//先保存user
		if(user.getCreateTime()==null){
			user.setCreateTime(DateUtils.getSystemTime());
		}
		userDao.save(user);
		//再保存用户角色
		roleService.saveUserRole(user.getId(), roleIds);
	}
	
	@Override
	public User find(Long id) {
		return userDao.findById(id);
	}
	
	/**
	 * 分页查询user
	 * @param orgId
	 * @param username
	 * @param realName
	 * @return
	 * @author zhangpeiran 2016年5月12日 上午11:08:10
	 */
	@Override
	public Page<User> findPage(Page<User> page,Long orgId,String username,String realName){
	    String hql = "select u,o.name from User u,Organization o where u.orgId = o.id ";
	    Map<String,Object> paramMap = new HashMap<>();
	    if(orgId!=null){
	    	hql+=" and o.id =:orgId";
	    	paramMap.put("orgId", orgId);
	    }
	    if(StringUtils.isNoneEmpty(username)){
	    	hql+=" and u.username like :username";
	    	paramMap.put("username","%"+username+"%");
	    }
	    if(StringUtils.isNoneEmpty(realName)){
	    	hql+=" and u.realName like :realName";
	    	paramMap.put("realName","%"+realName+"%");
	    }
	    Page<User> returnPage = userDao.findPage(page, hql, paramMap);
	    //进行二次封装
	    if(returnPage.getTotal()>0){
	    	List<User> userList = new ArrayList<>();
	    	for(Object obj:returnPage.getRows()){
	    		Object[] objs = (Object[])obj;
	    		User user = (User)objs[0];
	    		String orgName = objs[1].toString();
	    		if(StringUtils.isNotEmpty(orgName)){
	    			user.setOrgName(orgName);
	    		}
	    		userList.add(user);
	    	}
	    	returnPage.setRows(userList);
	    }
	    return returnPage;
	}

	/**
	 * 根据用户名得到用户
	 * 
	 * @param username
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午10:19:40
	 */
	@Override
	public User findByUsername(String username) {
		String hql = "from User where username = :username";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		List<User> list = userDao.executeQuery(hql, paramMap);
		return (list.size() > 0) ? list.get(0) : null;
	}

	/**
	 * 根据用户名获得角色标识集合
	 * 
	 * @param username
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午10:27:13
	 */
	@Override
	public Set<String> findRoleNameSet(String username) {
		Set<String> roleSet = new HashSet<>();
		User user = findByUsername(username);
		if (user != null) {
			Long[] roleIds = roleService.findRoleIds(user.getId());
			if (roleIds.length > 0) {
				List<Role> roleList = roleService.findByIds(roleIds);
				for (Role role : roleList) {
					roleSet.add(role.getName());
				}
			}
		}
		return roleSet;
	}

	/**
	 * 根据用户id返回角色list
	 * 
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午10:59:16
	 */
	@Override
	public List<Role> findRoles(Long id) {
		User user = find(id);
		if (user != null) {
			Long[] roleIds = roleService.findRoleIds(user.getId());
			if (roleIds.length > 0) {
				return roleService.findByIds(roleIds);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * 根据用户名得到资源权限标识集合
	 * 
	 * @param username
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午11:00:36
	 */
	@Override
	public Set<String> findPermissions(String username) {
		Set<String> permissionSet = new HashSet<>();
		User user = findByUsername(username);
		if (user != null) {
			List<Menu> list = findMenus(user.getId());
			for (Menu menu : list) {
				String permission = menu.getPermission();
				if (StringUtils.isNotBlank(permission)) {
					permissionSet.add(permission);
				}
			}
			return permissionSet;
		}
		return permissionSet;
	}

	/**
	 * 根据用户id,返回拥有的所有菜单和权限
	 * 
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午11:01:17
	 */
	@Override
	public List<Menu> findMenus(Long id) {
		User user = find(id);
		if (user != null) {
			Long[] roleIds = roleService.findRoleIds(user.getId());
			if (roleIds.length > 0) {
				Long[] menuIds = menuService.findMenuIds(roleIds);
				if (menuIds.length > 0) {
					return menuService.findByIds(menuIds);
				}
			}
		}
		return Collections.emptyList();
	}
}
