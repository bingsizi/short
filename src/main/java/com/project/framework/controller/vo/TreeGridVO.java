package com.project.framework.controller.vo;

/**  
 * easyui 树形grid数据
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月12日 上午8:52:11
 */
public class TreeGridVO {

	/** 扩展字段 **/
	private String id;// id
	private String _parentId;//父节点ID
	private String text;// 显示的内容
	private String state = "open";// 默认为展开状态 open为展开,closed为关闭
	private String iconCls;// 节点图标

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String get_parentId() {
		return _parentId;
	}

	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}

}
