package com.project.base.security.menu.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.project.base.security.menu.support.MenuType;
import com.project.framework.common.Constants;
import com.project.framework.entity.IdEntity;

/**
 * 资源表
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月9日 上午9:50:30
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends IdEntity implements Serializable{
	
	
	/**
	 * serialVersionUID long
	 * created by zhangpeiran 2016年5月16日 上午11:17:10
	 */
	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	private String name;// 资源名称
	@Enumerated(EnumType.ORDINAL)
	private MenuType type;// 资源类型
	private int seq;// 顺序
	private Long parentId;// 父编号,例如:如0/1/2/表示其祖先是2、1、0；其中根节点父Id为0
	private String parentIds;// 父编号列表
	private String permission;// 权限字符串
	private String available = Constants.YES;// 是否可用
	private String icon; // 资源图标
	private String indexUrl; // 菜单起始url
	private String className;// 可访问的类名.全路径
	private String methodName;// 可访问类中的方法.默认为空.表示全部可以访问
	private String description;// 描述
	
	/**非持久化 字段**/
	private String parentName;//上级菜单名称
	
	@Transient
	public String getParentName() {
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MenuType getType() {
		return type;
	}

	public void setType(MenuType type) {
		this.type = type;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
