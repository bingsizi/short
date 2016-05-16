package com.project.base.security.menu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import com.project.base.security.menu.dao.RoleMenuDao;
import com.project.base.security.menu.entity.RoleMenu;
import com.project.base.security.menu.service.RoleMenuService;

/**  
 * 角色资源业务层
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月9日 上午11:03:01
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService{
	
	@Resource
	private RoleMenuDao roleMenuDao;
	/**
	 * 判断menu是否使用过
	 * @param menuId
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午9:49:37
	 */
	@Override
	public boolean isMenuUsed(Long menuId){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("menuId",menuId);
		long count = roleMenuDao.countResult("from RoleMenu where menuId = :menuId",paramMap);
		return (count>0)?true:false;
	}
	@Override
	public void deleteByRole(Long roleId) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("roleId", roleId);
		roleMenuDao.executeUpdate("delete from RoleMenu where roleId=:roleId", paramMap);
	}
	@Override
	public void save(RoleMenu rm) {
		roleMenuDao.save(rm);
	}
	@Override
	public List<RoleMenu> findByRole(Long...roleIds) {
		return roleMenuDao.executeQuery(Restrictions.in("roleId",roleIds));
	}
}
