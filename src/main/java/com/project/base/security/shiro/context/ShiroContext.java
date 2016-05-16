package com.project.base.security.shiro.context;

import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.project.base.security.menu.entity.Menu;
import com.project.base.security.user.entity.User;
import com.project.framework.common.Constants;

/**
 * 上下文
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月9日 下午3:57:19
 */
public class ShiroContext {
	/**
	 * 获得shiro的session
	 * 
	 * @return
	 * @author zhangpeiran 2016年5月9日 下午4:01:02
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 设置当前登陆用户
	 * 
	 * @param user
	 * @author zhangpeiran 2016年5月9日 下午3:59:12
	 */
	public static void setCurrentUser(User user) {
		getSession().setAttribute(Constants.USER_IN_SESSION, user);
	}

	/**
	 * 获得当前登陆用户
	 * 
	 * @return
	 * @author zhangpeiran 2016年5月9日 下午4:00:05
	 */
	public static User getCurrentUser() {
		return (User) getSession().getAttribute(Constants.USER_IN_SESSION);
	}

	/**
	 * 设置当前登陆用户menu
	 * 
	 * @param menuList
	 * @author zhangpeiran 2016年5月10日 上午10:18:22
	 */
	public static void setMenus(List<Menu> menuList) {
		getSession().setAttribute(Constants.MENU_IN_SESSION, menuList);
	}

	/**
	 * 获得当前登陆用户menu
	 * @return
	 * @author zhangpeiran 2016年5月10日 上午10:19:08
	 */
	@SuppressWarnings("unchecked")
	public static List<Menu> getMenus() {
		return (List<Menu>) getSession().getAttribute(Constants.MENU_IN_SESSION);
	}
}
