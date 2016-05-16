package com.project.base.security.user.controller;

import javax.servlet.ServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.base.security.user.entity.User;
import com.project.framework.common.Constants;
import com.project.framework.controller.BaseController;
import com.project.framework.controller.Page;

/**  
 * 用户管理
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月12日 上午10:38:58
 */
@RequestMapping("/security/user")
@Controller
public class UserController extends BaseController{
	/**
	 * 跳转页面
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index() {
		return "security/user/index";
	}
	/**
	 * 返回用户
	 * @param request
	 * @param orgId
	 * @param username
	 * @param realName
	 * @return
	 * @author zhangpeiran 2016年5月12日 上午11:10:22
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(ServletRequest request,Long orgId,String username,String realName){
		Page<User> page = buildPage(request);
		page.addDescOrder("createTime");
		return serviceManager.userSerivce.findPage(page,orgId, username, realName);
	}
	/**
	 * 返回全部组织机构
	 * @return
	 * @author zhangpeiran 2016年5月12日 上午11:11:37
	 */
	@RequestMapping(value = "orgTree")
	@ResponseBody
	public Object orgTree(){
		return serviceManager.organizationService.orgTreeList(null);
	}
	/**
	 * 返回全部角色
	 * @return
	 * @author zhangpeiran 2016年5月12日 下午1:36:49
	 */
	@RequestMapping(value = "roleList")
	@ResponseBody
	public Object roleList(){
		return serviceManager.roleService.findAll();
	}
	/**
	 * 返回当前用户拥有的角色
	 * @return
	 * @author zhangpeiran 2016年5月12日 下午2:04:37
	 */
	@RequestMapping(value = "roleIds")
	@ResponseBody
	public Object roleIds(Long id){
		return serviceManager.roleService.findRoleIds(id);
	}
	/**
	 * 保存user
	 * @param user
	 * @return
	 * @author zhangpeiran 2016年5月12日 下午12:00:13
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(ServletRequest request){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String realName = request.getParameter("realName");
		String orgId = request.getParameter("orgId");
		String[] roleIds = request.getParameterValues("roleIds");
		
		if(StringUtils.isEmpty(username)){
			return getErrorMsg("用户名不能为空");
		}
		if(StringUtils.isEmpty(password)){
			return getErrorMsg("密码不能为空");
		}
		if(StringUtils.isEmpty(orgId)){
			return getErrorMsg("组织机构不能为空");
		}
		if(roleIds.length<=0){
			return getErrorMsg("角色不能为空");
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRealName(realName);
		user.setOrgId(Long.valueOf(orgId));
		
		Long[] roleLongIds = new Long[roleIds.length];
		int i=0;
		for(String id:roleIds){
			roleLongIds[i++] = Long.valueOf(id);
		}
		serviceManager.userSerivce.saveUser(user,roleLongIds);
		return getSuccessMsg("增加用户成功");
	}
	/**
	 * 修改user
	 * @param user
	 * @return
	 * @author zhangpeiran 2016年5月12日 下午12:00:13
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(ServletRequest request){
		String id = request.getParameter("id");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String realName = request.getParameter("realName");
		String orgId = request.getParameter("orgId");
		String[] roleIds = request.getParameterValues("roleIds");
		
		if(StringUtils.isEmpty(id)){
			return getErrorMsg("ID不能为空");
		}
		if(StringUtils.isEmpty(username)){
			return getErrorMsg("用户名不能为空");
		}
		if(StringUtils.isEmpty(password)){
			return getErrorMsg("密码不能为空");
		}
		if(StringUtils.isEmpty(orgId)){
			return getErrorMsg("组织机构不能为空");
		}
		if(roleIds.length<=0){
			return getErrorMsg("角色不能为空");
		}
		
		User user = serviceManager.userSerivce.find(Long.valueOf(id));
		if(user==null){
			return getErrorMsg("未发现要修改的用户");
		}
		
		user.setUsername(username);
		user.setPassword(password);
		user.setRealName(realName);
		user.setOrgId(Long.valueOf(orgId));
		
		Long[] roleLongIds = new Long[roleIds.length];
		int i=0;
		for(String roleId:roleIds){
			roleLongIds[i++] = Long.valueOf(roleId);
		}
		serviceManager.userSerivce.saveUser(user,roleLongIds);
		return getSuccessMsg("修改用户成功");
	}
	/**
	 * 锁定解锁用户
	 * @param lock
	 * @return
	 * @author zhangpeiran 2016年5月12日 下午2:46:24
	 */
	@RequestMapping(value = "locked")
	@ResponseBody
	public Object locked(Long id,String lock){
		if(StringUtils.isEmpty(lock)||id==null){
			return getErrorMsg("无效操作");
		}
		if(lock.equals(Constants.YES)){
			serviceManager.userSerivce.unLock(id);
			return getSuccessMsg("");
		}else if(lock.equals(Constants.NO)){
			serviceManager.userSerivce.lock(id);
			return getSuccessMsg("");
		}else{
			return getErrorMsg("无效标识");
		}
	}
}
