package com.project.base.security.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.framework.controller.BaseController;


/**  
 * 在线管理
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月16日 上午8:50:00
 */
@Controller
@RequestMapping("/security/online")
public class OnlineController extends BaseController{
	/**
	 * 跳转页面
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index() {
		return "security/online/index";
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(){
		return serviceManager.onlineService.findAllSession();
	}
}
