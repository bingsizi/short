package com.project.base;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.base.security.shiro.exception.MenuNotFoundException;
import com.project.framework.controller.BaseController;

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
	
	/**
	 * 跳转到登陆页
	 * @return
	 * @author zhangpeiran 2016年5月9日 下午2:25:41
	 */
	@RequestMapping("toLogin")
	public String toLogin(){
		return "login";
	}
    /**
     * 登陆
     * @param username
     * @param password
     * @param remember
     * @return
     * @author zhangpeiran 2016年5月10日 下午2:52:39
     */
	@RequestMapping("login")
	@ResponseBody
	public Object login(@RequestParam(value = "username", required = true) String username,
			            @RequestParam(value = "password", required = true) String password,
			            @RequestParam(value = "remember", required = false,defaultValue = "false") Boolean remember) {
		Subject subject = SecurityUtils.getSubject();  
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		if((boolean)remember){
			token.setRememberMe(true);  
		}
		try {  
            subject.login(token);  
        } catch (UnknownAccountException e) {  
            return getErrorMsg("用户名或密码错误");
        } catch (IncorrectCredentialsException e) {  
            return getErrorMsg("用户名或密码错误");
        } catch (LockedAccountException e) {  
        	return getErrorMsg("用户被锁定");
        } catch (MenuNotFoundException e){
        	return getErrorMsg("用户没有菜单,无法登陆");
        } 
		return getSuccessMsg("登陆成功");
	}
	/**
	 * 跳转到首页
	 * @return
	 * @author zhangpeiran 2016年5月10日 下午2:52:31
	 */
	@RequestMapping("main")
	public String main(){
		return "main";
	}
	/**
	 * 注销用户
	 * @param request
	 * @return
	 * @author zhangpeiran 2016年5月10日 下午2:52:26
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();  
		subject.logout();
		return "login";
	}
	/**
	 * 跳转到授权失败页面
	 * @return
	 * @author zhangpeiran 2016年5月10日 下午2:53:03
	 */
	@RequestMapping("unauthor")
	public String unauthor(){
		return "unauthor";
	}
}
