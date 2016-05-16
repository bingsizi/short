package com.project.base.security.menu.service;

import java.util.List;
import com.project.base.security.menu.entity.Menu;
import com.project.framework.controller.Page;
import com.project.framework.controller.vo.TreeVo;

/**  
 * 资源接口
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月9日 上午11:03:36
 */
public interface MenuService{
	
	public Menu find(Long id);
	/**
	 * 保存菜单
	 */
	public void save(Menu menu);
	/**
	 * 根据id删除
	 * @param Id
	 * @author zhangpeiran 2016年5月16日 上午11:05:31
	 */
	public void deleteById(Long id);
	
	/**
	 * 判断menu是否使用过
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:52:06
	 */
	public boolean isUsed(Long id);
	
	/**
	 * 根据id,获得所有子
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:44:20
	 */
	public List<Menu> findChilds(Long id);
	
	/**
	 * 获得全部根菜单
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:41:46
	 */
	public List<Menu> findRootMenus();
	
	/**
	 * 分页查询菜单
	 * @param page
	 * @param name
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:26:20
	 */
	public Page<Menu> findByPage(Page<Menu> page,String name);
	
	/**
	 * 保存角色菜单
	 * @param roleId
	 * @param menuIds
	 * @author zhangpeiran 2016年5月10日 上午8:57:22
	 */
	public void saveRoleMenu(Long roleId,Long[] menuIds);
	 /**
	  * 根据ids获得资源
	  * @return
	  * @author zhangpeiran 2016年5月9日 上午11:16:59
	  */
     public List<Menu> findByIds(Long...menuIds);
     
	  /**
	   * 根据roleIds获得拥有的menuIds
	   * @param userId
	   * @return
	   * @author zhangpeiran 2016年5月9日 上午10:35:50
	   */
	  public Long[] findMenuIds(Long...roleIds);
     /**
      * 获得全部菜单
      * @return
      * @author zhangpeiran 2016年5月9日 下午4:18:24
      */
     public int countNum();
 	/**
 	 * 获得所有菜单,以属性结构显示
 	 */
 	public List<TreeVo> findTreeMenus() ;
 	
    /********************************************************************根据菜单List生成菜单html**************************************************/
 	/**
 	 * 根据menuList获得菜单html
 	 * @param menuList
 	 * @return
 	 * @author zhangpeiran 2016年5月10日 上午10:20:49
 	 */
 	public String getMenuHtml(List<Menu> menuList);
}
