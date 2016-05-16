package com.project.framework.controller.vo;

import java.util.ArrayList;
import java.util.List;

/**  
 * easyUi前台用的treeVo
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月11日 下午1:54:44
 */
public class TreeVo {
	/** 扩展字段 **/
	private String id;// id
	private String text;// 显示的内容
	private String state = "open";// 默认为展开状态 open为展开,closed为关闭
	private String iconCls;// 节点图标
	private boolean checked = false;//是否选中 
	private Object attributes;	// 自定义属性
	private List<TreeVo> children = new ArrayList<TreeVo>();//子部门集
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the iconCls
	 */
	public String getIconCls() {
		return iconCls;
	}
	/**
	 * @param iconCls the iconCls to set
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * @return the children
	 */
	public List<TreeVo> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}
	public Object getAttributes() {
		return attributes;
	}
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

}
