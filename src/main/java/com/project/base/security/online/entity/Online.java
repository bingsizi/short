package com.project.base.security.online.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project.framework.json.JsonDateTimeSerializer;

/**
 * 在线用户
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月16日 上午8:55:30
 */
public class Online {
	
	private long userId;// 用户ID
	private Serializable sessionId;//sessionID
	private String username;// 用户名称
	private String realName;// 真实姓名
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date startTimestamp;//启动时间
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date lastAccessTime;//最后访问时间

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(Date startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Serializable getSessionId() {
		return sessionId;
	}

	public void setSessionId(Serializable sessionId) {
		this.sessionId = sessionId;
	}
}
