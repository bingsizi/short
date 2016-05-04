package com.project.framework.controller;

/**
 * 消息
 */
public class Msg {
	private boolean success;
	private String message; // 消息内容
	private Object obj;
	/**
	 * 是否是成功消息；true：是；false：不是
	 * @return true：是；false：不是
	 */
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 得到错误消息内容
	 * 
	 * @return 错误消息内容
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置错误消息内容
	 * 
	 * @param message
	 *            错误消息内容
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public Msg() {
		super();
	}

	public Msg(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}