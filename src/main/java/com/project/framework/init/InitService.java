package com.project.framework.init;


import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import com.project.base.security.menu.entity.Menu;
import com.project.base.security.menu.support.MenuType;
import com.project.base.security.org.entity.Organization;
import com.project.base.security.role.entity.Role;
import com.project.base.security.user.entity.User;
import com.project.framework.common.Constants;
import com.project.framework.service.ServiceManager;


/**
 * 初始化完成之后执行
 * @author Nice
 */
@Service
public class InitService implements ApplicationListener<ContextRefreshedEvent> {
	
	private final Logger log = LoggerFactory.getLogger(InitService.class);
	
	@Resource
	private ServiceManager serviceManagerManager;
	

	/**
	 * 当初始化完成后，将触发事件，调用以下代码。SpringMVC所以与Spring初始化完成后都会调用，所以增加判断
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null){
			if(Constants.INIT_SQL_DATA){
				log.info("初始化sql数据...................................................");
				//初始化菜单,系统管理
				Menu systemTop = new Menu();
				systemTop.setName("系统设置");
				systemTop.setSeq(1);
				systemTop.setType(MenuType.菜单);
				serviceManagerManager.menuService.save(systemTop);
				/******系统菜单的子菜单*******************/
				String systemTopParentIds ="/"+systemTop.getId()+"/";
				
				Menu userManager = new Menu();
				userManager.setName("用户管理");
				userManager.setParentId(systemTop.getId());
				userManager.setParentIds(systemTopParentIds);
				userManager.setType(MenuType.菜单);
				userManager.setSeq(1);
				userManager.setIcon("icon-user");
				userManager.setPermission("/security/user");
				userManager.setIndexUrl("/security/user/index");
				serviceManagerManager.menuService.save(userManager);
				
				Menu orgManager = new Menu();
				orgManager.setName("组织机构管理");
				orgManager.setParentId(systemTop.getId());
				orgManager.setParentIds(systemTopParentIds);
				orgManager.setType(MenuType.菜单);
				orgManager.setSeq(2);
				orgManager.setIcon("icon-org");
				orgManager.setPermission("/security/org");
				orgManager.setIndexUrl("/security/org/index");
				serviceManagerManager.menuService.save(orgManager);
				
				Menu roleManager = new Menu();
				roleManager.setName("角色管理");
				roleManager.setParentId(systemTop.getId());
				roleManager.setParentIds(systemTopParentIds);
				roleManager.setType(MenuType.菜单);
				roleManager.setSeq(3);
				roleManager.setIcon("icon-role");
				roleManager.setPermission("/security/role");
				roleManager.setIndexUrl("/security/role/index");
				serviceManagerManager.menuService.save(roleManager);
				
				
				Menu menuManager = new Menu();
				menuManager.setName("菜单管理");
				menuManager.setParentId(systemTop.getId());
				menuManager.setParentIds(systemTopParentIds);
				menuManager.setType(MenuType.菜单);
				menuManager.setSeq(4);
				menuManager.setIcon("icon-menu");
				menuManager.setPermission("/security/menu");
				menuManager.setIndexUrl("/security/menu/index");
				serviceManagerManager.menuService.save(menuManager);
				
				Menu onUserManager = new Menu();
				onUserManager.setName("在线用户管理");
				onUserManager.setParentId(systemTop.getId());
				onUserManager.setParentIds(systemTopParentIds);
				onUserManager.setType(MenuType.菜单);
				onUserManager.setSeq(5);
				onUserManager.setIcon("icon-online");
				onUserManager.setPermission("/security/online");
				menuManager.setIndexUrl("/security/online/index");
				serviceManagerManager.menuService.save(onUserManager);
				
				//初始化角色
				Role role = new Role();
				role.setName("admin");
				role.setDescription("系统管理员");
				serviceManagerManager.roleService.save(role);
				
				
				//初始化组织机构
				Organization org = new Organization();
				org.setName("最上级部门");
				serviceManagerManager.organizationService.save(org);
				
				//初始化用户
				User user = new User();
				user.setUsername("admin");
				user.setPassword("manager");
				user.setOrgId(org.getId());
				serviceManagerManager.userSerivce.save(user);
				
				//初始化用户角色
				serviceManagerManager.roleService.saveUserRole(user.getId(),role.getId());
				
				//初始化角色菜单
				Long[] menuIds = new Long[6];
				menuIds[0] = systemTop.getId();
				menuIds[1] = userManager.getId();
				menuIds[2] = orgManager.getId();
				menuIds[3] = roleManager.getId();
				menuIds[4] = menuManager.getId();
				menuIds[5] = onUserManager.getId();
				serviceManagerManager.menuService.saveRoleMenu(role.getId(),menuIds);
				
			}
		}
	}
}
