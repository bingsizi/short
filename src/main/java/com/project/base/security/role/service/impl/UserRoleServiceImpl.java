package com.project.base.security.role.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.base.security.role.dao.UserRoleDao;
import com.project.base.security.role.entity.UserRole;
import com.project.base.security.role.service.UserRoleService;

/**  
 * 用户角色业务层
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月9日 上午10:30:30
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService{
	
	@Resource
	private UserRoleDao userRoleDao;
	/**
	 * 判断角色有没有被使用
	 * @param roleId
	 * @return
	 * @author zhangpeiran 2016年5月11日 下午2:22:18
	 */
	@Override
	public boolean isRoleUsed(Long roleId){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("roleId",roleId);
		long count = userRoleDao.countResult("from UserRole where roleId = :roleId", paramMap);
		return (count>0)?true:false;
	}
	@Override
	public List<UserRole> findByUser(Long userId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		return userRoleDao.executeQuery("from UserRole where userId = :userId ", paramMap);
	}
	@Override
	public void deleteByUser(Long userId) {
		//先删除用户角色关系
		Map<String,Object> map = new HashMap<>();
		map.put("userId",userId);
		userRoleDao.executeUpdate("delete from UserRole where userId = :userId",map);
	}
	@Override
	public void save(UserRole ur) {
		userRoleDao.save(ur);
	}
}
