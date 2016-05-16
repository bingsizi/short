package com.project.base.security.menu.service;

import java.util.List;

import com.project.base.security.menu.entity.RoleMenu;

/**  
 * 角色资源接口
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月9日 上午11:03:01
 */
public interface RoleMenuService{
	/**
	 * 保存
	 * @param rm
	 * @author zhangpeiran 2016年5月16日 上午10:48:56
	 */
	public void save(RoleMenu rm);
	/**
	 * 判断menu是否使用过
	 * @param menuId
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:49:37
	 */
	public boolean isMenuUsed(Long menuId);
	/**
	 * 根据角色id,删除菜单
	 * @param roleId
	 * @author zhangpeiran 2016年5月16日 上午10:45:42
	 */
	public void deleteByRole(Long roleId);
	/**
	 * 根据角色返回菜单
	 * @param roleIds
	 * @return
	 * @author zhangpeiran 2016年5月16日 上午10:53:08
	 */
	public List<RoleMenu> findByRole(Long...roleIds);
}
