package com.project.base.security.shiro.exception;

import org.apache.shiro.authc.AccountException;

/**  
 * 菜单没有发现异常
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月10日 上午10:04:14
 */
public class MenuNotFoundException extends AccountException{

	/**
	 * serialVersionUID long
	 * created by zhangpeiran 2016年5月10日 上午10:13:43
	 */
	private static final long serialVersionUID = 1L;

	public MenuNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MenuNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MenuNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MenuNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
