package com.project.base.security.menu.controller;

import javax.servlet.ServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.base.security.menu.entity.Menu;
import com.project.framework.controller.BaseController;
import com.project.framework.controller.Page;

/**  
 * 菜单管理
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月11日 上午9:57:59
 */
@RequestMapping("/security/menu")
@Controller
public class MenuController extends BaseController{
	/**
	 * 跳转页面
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index() {
		return "security/menu/index";
	}
	/**
	 * 得到所有的菜单
	 * @return
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Object menuList(ServletRequest request){
		String name = request.getParameter("name");
		Page<Menu> page = buildPage(request);
		return serviceManager.menuService.findByPage(page, name);
	}
	
	/**
	 * 增加一个菜单
	 * @return
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Object save(Menu menu){
	    try{
	    	//验证菜单是否为空
	    	if(menu==null){
	    		return getErrorMsg("菜单信息为空");
	    	}
	    	//验证菜单名称是否为空
	    	if(StringUtils.isEmpty(menu.getName())){
	    		return getErrorMsg("菜单名称不能为空");
	    	}
	    	//增加菜单
	    	serviceManager.menuService.save(menu);
	    	return getSuccessMsg("增加菜单成功,菜单名称为:"+menu.getName());
	    }catch(Exception e){
	    	e.printStackTrace();
	    	return getErrorMsg(e.getMessage());
	    }
	}
	/**
	 * 修改菜单
	 * @return
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public Object update(Menu menu){
	    try{
	    	//验证菜单是否为空
	    	if(menu==null){
	    		return getErrorMsg("菜单信息为空");
	    	}
	    	//验证菜单名称是否为空
	    	if(StringUtils.isEmpty(menu.getName())){
	    		return getErrorMsg("菜单名称不能为空");
	    	}
	    	Menu oldMenu = serviceManager.menuService.find(menu.getId());
	    	if(oldMenu==null)
	    		return getErrorMsg("未找到要修改的菜单");
	    	oldMenu.setIndexUrl(menu.getIndexUrl());
	    	oldMenu.setClassName(menu.getClassName());
	    	oldMenu.setParentId(menu.getParentId());
	    	oldMenu.setDescription(menu.getDescription());
	    	oldMenu.setSeq(menu.getSeq());
	    	oldMenu.setName(menu.getName());
	    	oldMenu.setPermission(menu.getPermission());
	    	oldMenu.setMethodName(menu.getMethodName());
	        oldMenu.setType(menu.getType());
	    	//增加菜单
	    	serviceManager.menuService.save(oldMenu);
	    	return getSuccessMsg("修改菜单成功,菜单名称为:"+menu.getName());
	    }catch(Exception e){
	    	e.printStackTrace();
	    	return getErrorMsg(e.getMessage());
	    }
	}
	/**
	 * 删除一个菜单
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(Long id){
		try{
			//验证要删除的菜单ID是否为空
			if(id==null)
				return getErrorMsg("请选择要删除的菜单");
			Menu menu = serviceManager.menuService.find(id);
			if(menu==null)
				return getErrorMsg("要删除的菜单不存在");
			//验证菜单是否为空
			boolean flag = serviceManager.menuService.isUsed(id);
			if(flag){
				return getErrorMsg("菜单已被角色绑定，请先删除");
			}
			serviceManager.menuService.deleteById(id);
			return getSuccessMsg("删除菜单["+menu.getName()+"]成功");
		}catch(Exception e){
			e.printStackTrace();
			return getErrorMsg(e.getMessage());
		}
	}
	/**
	 * 获得根菜单
	 * @return
	 * @author zhangpeiran 2016年5月11日 上午10:34:19
	 */
	@RequestMapping(value="menuTree")
	@ResponseBody
	public Object menuTree(){
		return serviceManager.menuService.findTreeMenus();
	}
}
