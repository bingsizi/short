package com.project.base.security.menu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import com.project.base.security.menu.dao.MenuDao;
import com.project.base.security.menu.entity.Menu;
import com.project.base.security.menu.entity.RoleMenu;
import com.project.base.security.menu.service.MenuService;
import com.project.base.security.menu.service.RoleMenuService;
import com.project.base.security.menu.support.MenuSeqComparator;
import com.project.framework.controller.Page;
import com.project.framework.controller.vo.TreeVo;

/**
 * 资源业务层
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月9日 上午11:03:36
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Resource
	private RoleMenuService roleMenuService;
	@Resource
	private MenuDao menuDao;

	/**
	 * 获得根据id查询
	 */
	@Override
	public Menu find(Long id) {
		return menuDao.findById(id);
	}

	/**
	 * 保存菜单
	 */
	@Override
	public void save(Menu menu) {
		if (menu.getParentId() != null) {
			Menu pareMenu = find(menu.getParentId());
			if (pareMenu != null) {
				String parentIds = (StringUtils.isNoneBlank(pareMenu.getParentIds()) ? pareMenu.getParentIds() : "")
						+ pareMenu.getId() + "/";
				menu.setParentIds(parentIds);
			}
		}
		menuDao.save(menu);
	}


	@Override
	public void deleteById(Long id) {
		menuDao.deleteById(id);
	}
	
	/**
	 * 判断menu是否使用过
	 * 
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:52:06
	 */
	@Override
	public boolean isUsed(Long id) {
		return roleMenuService.isMenuUsed(id);
	}

	/**
	 * 根据id,获得所有子
	 * 
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:44:20
	 */
	@Override
	public List<Menu> findChilds(Long id) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("parentId", id);
		return menuDao.executeQuery("from Menu where parentId=:parentId order by seq", paramMap);
	}

	/**
	 * 获得全部根菜单
	 * 
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:41:46
	 */
	@Override
	public List<Menu> findRootMenus() {
		String queryString = "from Menu where parentId is null or parentId='' order by seq";
		return menuDao.executeQuery(queryString, new HashMap<String, Object>());
	}

	/**
	 * 分页查询菜单
	 * 
	 * @param page
	 * @param name
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:26:20
	 */
	@Override
	public Page<Menu> findByPage(Page<Menu> page, String name) {
		Map<String, Object> paramMap = new HashMap<>();
		String hql = "from Menu where 1=1 ";
		if (StringUtils.isNoneBlank(name)) {
			hql += " and name = :name";
			paramMap.put("name", name);
		}
		return menuDao.findPage(page, hql, paramMap);
	}

	/**
	 * 保存角色菜单
	 * 
	 * @param roleId
	 * @param menuIds
	 * @author zhangpeiran 2016年5月10日 上午8:57:22
	 */
	@Override
	public void saveRoleMenu(Long roleId, Long[] menuIds) {
		// 首先删除角色原有菜单配置
		roleMenuService.deleteByRole(roleId);
		// 保存新的菜单项
		for (Long menuId : menuIds) {
			RoleMenu rm = new RoleMenu();
			rm.setRoleId(roleId);
			rm.setMenuId(menuId);
			roleMenuService.save(rm);
		}
	}

	/**
	 * 根据ids获得资源
	 * 
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午11:16:59
	 */
	@Override
	public List<Menu> findByIds(Long... menuIds) {
		return menuDao.executeQuery(Restrictions.in("id", menuIds));
	}

	/**
	 * 根据roleIds获得拥有的menuIds
	 * 
	 * @param userId
	 * @return
	 * @author zhangpeiran 2016年5月9日 上午10:35:50
	 */
	@Override
	public Long[] findMenuIds(Long... roleIds) {
		List<RoleMenu> list = roleMenuService.findByRole(roleIds);
		Long[] menuIds = new Long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			menuIds[i] = list.get(i).getMenuId();
		}
		// 排重
		if (menuIds.length > 1) {
			Set<Long> set = new TreeSet<>();
			for (Long id : menuIds) {
				set.add(id);
			}
			Long[] ids = new Long[set.size()];
			int j = 0;
			for (Long id : set) {
				ids[j++] = id;
			}
			return ids;
		}
		return menuIds;
	}

	/**
	 * 获得全部菜单
	 * 
	 * @return
	 * @author zhangpeiran 2016年5月9日 下午4:18:24
	 */
	@Override
	public int countNum() {
		return menuDao.countResult(menuDao.createCriteria(null));
	}

	/**
	 * 获得所有菜单,以属性结构显示
	 */
	@Override
	public List<TreeVo> findTreeMenus() {
		List<TreeVo> treeList = new ArrayList<TreeVo>();
		List<Menu> rootMenuList = findRootMenus();
		if (rootMenuList.size() > 0) {
			for (Menu menu : rootMenuList) {
				TreeVo tv = new TreeVo();
				tv.setId(menu.getId() + "");
				tv.setText("[" + menu.getType() + "]" + menu.getName());
				tv.setIconCls(menu.getIcon());
				setChildrenList(menu, tv);
				treeList.add(tv);
			}
		}
		return treeList;
	}

	/**
	 * 设置子菜单List
	 * 
	 * @param menu
	 * @param tv
	 * @return
	 */
	private Menu setChildrenList(Menu menu, TreeVo tv) {
		List<Menu> childrenList = findChilds(menu.getId());
		if (childrenList.size() > 0) {
			List<TreeVo> treeList = new ArrayList<TreeVo>();
			for (Menu tempMenu : childrenList) {
				TreeVo treevo = new TreeVo();
				treevo.setId(tempMenu.getId() + "");
				treevo.setText("[" + menu.getType() + "]" + tempMenu.getName());
				treevo.setIconCls(tempMenu.getIcon());
				treeList.add(treevo);
				setChildrenList(tempMenu, treevo);
			}
			tv.setChildren(treeList);
		}
		return menu;
	}

	/******************************************************************** 根据菜单List生成菜单html **************************************************/
	/**
	 * 根据menuList获得菜单html
	 * 
	 * @param menuList
	 * @return
	 * @author zhangpeiran 2016年5月10日 上午10:20:49
	 */
	@Override
	public String getMenuHtml(List<Menu> menuList) {
		StringBuffer html = new StringBuffer();
		html.append("<div class=\"easyui-accordion\" data-options=\"fit:true,border:false\">");
		// 遍历顶级菜单
		List<Menu> cloneMenuList = new ArrayList<>();
		cloneMenuList.addAll(menuList);
		List<Menu> rootMenuList = getRootMenu(menuList);
		int i = 0;
		for (Menu menu : rootMenuList) {
			html.append("<div title=\"" + menu.getName() + "\" style=\"padding:10px;\"");
			if (i == 0) {
				html.append("  data-options=\"selected:true\"");
			}
			html.append(">");
			// 遍历子菜单
			List<Menu> subMenuList = getSubMenu(cloneMenuList, menu);
			for (Menu subMenu : subMenuList) {
				String name = (subMenu.getName() == null) ? "" : subMenu.getName();
				String icon = (subMenu.getIcon() == null) ? "" : subMenu.getIcon();
				String url = (subMenu.getIndexUrl() == null) ? "" : subMenu.getIndexUrl();
				html.append("<a href=\"javascript:void(0);\" type=\"menuItem\" name=\"" + name + "\" icon=\"" + icon
						+ "\" url=\"" + url + "\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'"
						+ subMenu.getIcon() + "'\">" + subMenu.getName() + "</a>");
				html.append("<br>");
			}
			i++;
			html.append("</div>");
		}
		html.append("</div>");
		return html.toString();
	}

	/**
	 * 得到所有顶级菜单
	 * 
	 * @param menuList
	 * @return
	 * @author zhangpeiran 2016年5月10日 上午10:34:00
	 */
	private List<Menu> getRootMenu(List<Menu> menuList) {
		List<Menu> rootMenuList = new ArrayList<>();
		// 得到所有顶级菜单
		for (Menu menu : menuList) {
			if (menu.getParentId() == null) {
				rootMenuList.add(menu);
			}
		}
		// 排序
		Collections.sort(rootMenuList, new MenuSeqComparator());
		return rootMenuList;
	}

	/**
	 * 根据上级菜单,得到子菜单
	 * 
	 * @param menuList
	 *            全部菜单集合
	 * @param menu
	 *            上级菜单
	 * @return
	 * @author zhangpeiran 2016年5月10日 上午10:35:36
	 */
	private List<Menu> getSubMenu(List<Menu> menuList, Menu menu) {
		Long parentId = menu.getId();
		List<Menu> subMenuList = new ArrayList<>();
		// 得到所有子菜单
		for (Menu m : menuList) {
			if (m.getParentId() == parentId) {
				subMenuList.add(m);
			}
		}
		// 排序
		Collections.sort(subMenuList, new MenuSeqComparator());
		return subMenuList;
	}
}
