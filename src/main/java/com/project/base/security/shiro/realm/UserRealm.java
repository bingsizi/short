package com.project.base.security.shiro.realm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.project.base.security.menu.entity.Menu;
import com.project.base.security.menu.support.MenuType;
import com.project.base.security.shiro.context.ShiroContext;
import com.project.base.security.shiro.exception.MenuNotFoundException;
import com.project.base.security.user.entity.User;
import com.project.framework.common.Constants;
import com.project.framework.service.ServiceManager;

/**
 * 授权验证用户
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月9日 上午11:33:58
 */
public class UserRealm extends AuthorizingRealm {

	@Resource
	private ServiceManager serviceManager;

	@Override
	public String getName() {
		return "userRealm";
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Set<String> roleSet = serviceManager.userSerivce.findRoleNameSet(username);
		Set<String> permissionSet = serviceManager.userSerivce.findPermissions(username);
		authorizationInfo.setRoles(roleSet);
		/**测试数据,规则同WildcardPermission只不过把':'改成了'/'.用法参考WildcardPermission授权法**/
		//permissionSet.add("/user/save,update");
		//permissionSet.add("/role/*/view");
		authorizationInfo.setStringPermissions(permissionSet);
		return authorizationInfo;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		User user = serviceManager.userSerivce.findByUsername(username);

		/******* 验证用户 ******/
		if (user == null) {
			throw new UnknownAccountException("没有找到账户");
		} else if (!user.getPassword().equals(password)) {
			throw new IncorrectCredentialsException("密码错误");
		} else if (Constants.YES.equals(user.getLocked())) {
			throw new LockedAccountException("账户被锁定");
		}
		/****** 验证有没有权限 *******/
		List<Menu> menuList = serviceManager.userSerivce.findMenus(user.getId());
		if (menuList.isEmpty()) {
			throw new MenuNotFoundException("用户没有任何菜单和可用权限");
		}

		// 设置登陆用户
		ShiroContext.setCurrentUser(user);
		// 获得用户拥有的全部菜单,并生成html
		List<Menu> menuHtmlList = new ArrayList<>();
		for(Menu menu:menuList){
			if(menu.getType()==MenuType.菜单){
				menuHtmlList.add(menu);
			}
		}
		String html = serviceManager.menuService.getMenuHtml(menuHtmlList);
		ShiroContext.getSession().setAttribute("menuHtml",html);
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现；
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
