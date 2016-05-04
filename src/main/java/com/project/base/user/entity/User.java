package com.project.base.user.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.project.framework.entity.IdEntity;

/**
 * user用户类
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月3日 上午10:42:41
 */
@Entity
@Table(name="user")
public class User extends IdEntity{
	
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
