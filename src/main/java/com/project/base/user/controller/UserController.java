package com.project.base.user.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.base.user.entity.User;
import com.project.framework.controller.BaseController;
import com.project.framework.controller.Page;

/**  
 * 用户管理
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月4日 下午4:50:58
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	 @RequestMapping("/index")
     public String index(){
    	 return "user/list";
     }
	 @RequestMapping("/list")
	 @ResponseBody
	 public Object list(HttpServletRequest request){
		String username = request.getParameter("username");
		Page<User> page = buildPage(request);
		return serviceManager.userService.findByPage(page, username);
	 }
}
