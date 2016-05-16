package com.project.base.security.org.controller;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.base.security.org.entity.Organization;
import com.project.base.security.org.support.OrgTreeGridVO;
import com.project.framework.controller.BaseController;
import com.project.framework.util.DateUtils;

/**  
 * 组织机构
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月12日 上午9:24:36
 */
@Controller
@RequestMapping("/security/org")
public class OrganizationController extends BaseController{
	/**
	 * 跳转页面
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index() {
		return "security/org/index";
	}
	/**
	 * 返回树结构的组织结构
	 * @param id
	 * @return
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public List<OrgTreeGridVO> list(Long id){
		return serviceManager.organizationService.orgTreeGridList(id);
	}
	
	/**
	 * 增加组织结构
	 * @param dept
	 * @return
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Object save(Organization org){
		try{
			if(org==null)
				return getErrorMsg("不能增加空组织机构");
			if(StringUtils.isEmpty(org.getName()))
				return getErrorMsg("名称不能为空");
			//增加部门
			org.setCreateTime(DateUtils.getSystemTime());
			serviceManager.organizationService.save(org);
			return getSuccessMsg("增加组织结构成功,名称："+org.getName());
		}catch(Exception e){
			e.printStackTrace();
			return getErrorMsg(e.getMessage());
		}
	}
	/**
	 * 修改组织机构
	 * @param org
	 * @return
	 * @author zhangpeiran 2016年5月12日 上午9:34:15
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public Object updateDepartment(Organization org){
		try{
			if(org==null)
				return getErrorMsg("不能修改空组织机构");
			if(org.getId()==null)
				return getErrorMsg("ID不能为空");
			if(StringUtils.isEmpty(org.getName()))
				return getErrorMsg("部门名称不能为空");
			Organization oldOrg = serviceManager.organizationService.find(org.getId());
			if(null == oldOrg){
				return getErrorMsg("组织机构不存在"); 
			}
			oldOrg.setName(org.getName());
			oldOrg.setSeq(org.getSeq());
			serviceManager.organizationService.save(oldOrg);
			return getSuccessMsg("修改部门成功,部门名称："+org.getName());
		}catch(Exception e){
			e.printStackTrace();
			return getErrorMsg(e.getMessage());
		}
	}
	/**
	 * 根据id删除一个组织机构
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(Long id){
		try{
			//验证ID是否为空
			if(id==null)
				return getErrorMsg("ID不能为空");
			Organization org = serviceManager.organizationService.find(id);
			//验证是否存在
			if(org==null){
				return getErrorMsg("要删除的数据不存在");
			}
			//验证是否有子
			boolean flag = serviceManager.organizationService.isLeafNode(id);
			if(flag){
				return getErrorMsg("请先删除数据");
			}
			serviceManager.organizationService.deleteById(id);
			return getSuccessMsg("删除部门["+org.getName()+"]成功!");
		}catch(Exception e){
			return getErrorMsg("系统错误:"+e.getMessage());
		}
	}
}
