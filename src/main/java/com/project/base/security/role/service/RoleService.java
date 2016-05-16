package com.project.base.security.role.service;

import java.util.List;

import com.project.base.security.role.entity.Role;

/**  
 * 角色业务接口
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月16日 上午11:23:16
 */
public interface RoleService {
	 /**
	  * 保存角色
	  * @param role
	  * @author zhangpeiran 2016年5月16日 下午12:02:16
	  */
	 public void save(Role role);
	 /**
	  * 根据id返回一个角色
	  * @param id
	  * @return
	  * @author zhangpeiran 2016年5月16日 下午12:03:02
	  */
	 public Role find(Long id);
	 /**
	  * 保存用户角色
	  * @param userId
	  * @param roleIds
	  * @author zhangpeiran 2016年5月16日 上午11:25:43
	  */
     public void saveUserRole(Long userId,Long...roleIds);
     
     /**
      * 根据userId返回拥有的角色ids
      * @param userId
      * @return
      * @author zhangpeiran 2016年5月16日 上午11:33:37
      */
     public Long[] findRoleIds(Long userId);
     /**
      * 根据ids返回list
      * @param ids
      * @return
      * @author zhangpeiran 2016年5月16日 上午11:34:48
      */
     public List<Role> findByIds(Long...ids);
     /**
      * 返回全部角色
      * @return
      * @author zhangpeiran 2016年5月16日 上午11:41:09
      */
     public List<Role> findAll();
     /**
      * 删除角色
      * @param role
      * @author zhangpeiran 2016年5月16日 上午11:52:05
      */
     public void delete(Role role);
     /**
      * 判断menu是否使用过
      * @param id
      * @return
      * @author zhangpeiran 2016年5月16日 上午11:54:17
      */
	 public boolean isUsed(Long id);
}
