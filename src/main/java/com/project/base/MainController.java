package com.project.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.base.user.entity.User;
import com.project.framework.common.Constants;
import com.project.framework.controller.BaseController;
import com.project.framework.filter.WebContext;

/**
 * 首页
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月3日 上午10:55:25
 */
@Controller
public class MainController extends BaseController {

	@RequestMapping("login")
	@ResponseBody
	public Object login(@RequestParam(value = "username", required = true) String username,
			            @RequestParam(value = "password", required = true) String password) {
		 User user = serviceManager.userService.login(username, password);
		 if(user!=null){
			 WebContext.setLoginUser(user);
			 return getSuccessMsg("登陆成功");
		 }else{
			 return getErrorMsg("用户名或密码错误");
		 }
	}
	@RequestMapping("main")
	public String main(){
		return "main";
	}
	@RequestMapping("exitSystem")
	public String exitSystem(HttpServletRequest request){
		request.getSession().removeAttribute(Constants.LOGIN_USER);
		return "login";
	}
}
