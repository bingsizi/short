package com.project.framework.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.base.security.menu.service.MenuService;
import com.project.base.security.online.service.OnlineService;
import com.project.base.security.org.service.OrganizationService;
import com.project.base.security.role.service.RoleService;
import com.project.base.security.user.service.UserSerivce;

@Service
public class ServiceManager {
	@Resource
	public MenuService menuService;
	@Resource
	public OnlineService onlineService;
	@Resource
	public UserSerivce userSerivce;
	@Resource
	public RoleService roleService;
	@Resource
	public OrganizationService organizationService;
}
