package com.project.base.security.user.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.framework.common.Constants;
import com.project.framework.entity.IdEntity;
import com.project.framework.json.JsonDateSerializer;

/**
 * user用户类
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月3日 上午10:42:41
 */
@Entity
@Table(name="sys_user")
public class User extends IdEntity implements Serializable{
	
	/**
	 * serialVersionUID long
	 * created by zhangpeiran 2016年5月10日 上午11:27:54
	 */
	private static final long serialVersionUID = 7826302322272727318L;
	
	/**必要属性**/
	private String username;//用户名
	private String password;//密码
	private String locked = Constants.NO;//是否锁定
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date createTime;//创建时间
	/** 其他属性**/
	private String realName;//真实姓名
	private Long orgId;//所属机构Id
	
	/** 临时属性 **/
	private String orgName;//所属机构名称
	
	@Transient
	public String getOrgName() {
		return orgName;
	}
	
	/** get and set **/

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

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

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}
}
