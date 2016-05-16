package com.project.base.security.role.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.project.framework.entity.IdEntity;

/**
 * 用户角色关系试题
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月9日 上午9:48:49
 */
@Entity
@Table(name = "sys_user_role")
public class UserRole extends IdEntity {
	
	private Long userId;
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
