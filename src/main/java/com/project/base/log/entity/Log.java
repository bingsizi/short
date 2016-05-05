package com.project.base.log.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.project.framework.entity.IdEntity;

/**
 * 日志类
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月3日 上午10:42:41
 */
@Entity
@Table(name = "log")
public class Log extends IdEntity {
	private Date createTime;
	private String content;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
