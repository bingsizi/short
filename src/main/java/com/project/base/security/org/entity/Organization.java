package com.project.base.security.org.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.project.framework.common.Constants;
import com.project.framework.entity.IdEntity;

/**
 * 组织机构
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月9日 上午9:40:55
 */
@Entity
@Table(name = "sys_organization")
public class Organization extends IdEntity {
	private String name;// 组织机构名称
	private int seq;// 显示顺序
	private Long parentId;// 父机构id
	private String parentIds;// 父编号列表 例如:如0/1/2/表示其祖先是2、1、0；其中根节点父Id为0
	private String available = Constants.YES;// 是否可用
	private Date createTime;// 创建时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
