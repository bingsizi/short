package com.project.base.security.org.service;

import java.util.List;
import com.project.base.security.org.entity.Organization;
import com.project.base.security.org.support.OrgTreeGridVO;
import com.project.framework.controller.vo.TreeVo;

/**  
 * 组织机构业务方法
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月12日 上午8:48:39
 */
public interface OrganizationService{
	
	/**
	 * 保存部门方法
	 */
	public void save(Organization org);
	/**
	 * 根据id删除
	 * @param id
	 * @author zhangpeiran 2016年5月12日 上午9:44:22
	 */
	public void deleteById(Long id);
	/**
	 * 重写findId
	 */
	public Organization find(Long id);
	/**
	 * 根据id获得子treegrid数据
	 * @param id
	 * @param compId
	 * @return
	 * @author gql 2015-9-22 上午11:29:51
	 */
	public List<OrgTreeGridVO> orgTreeGridList(Long id);
	/**
	 * 一次性全部获取部门树结构的方法,如果id有值以当前id为七点
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月12日 上午9:09:27
	 */
	public List<TreeVo> orgTreeList(Long id);
	/**
	 * 查询所有子部门，不包含本部门
	 * @param departId
	 * @return
	 * @author gql 2015-10-13 下午3:20:11
	 */
	public List<Organization> findAllChilds(Long id);
	/**
	 * 获得根节点
	 * @return
	 * @author zhangpeiran 2016年5月12日 上午8:57:21
	 */
	public List<Organization> findRoot();
	/**
	 * 根据id获得子节点
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月12日 上午8:59:13
	 */
	public List<Organization> findChilds(Long id);
	/**
	 * 判断这个id节点下是否含有子节点.
	 * @param id
	 * @return
	 * @author zhangpeiran 2016年5月12日 上午9:02:11
	 */
	public boolean isLeafNode(Long id);
}
