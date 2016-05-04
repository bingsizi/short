package com.project.framework.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.project.base.user.service.UserService;

@Service
public class ServiceManager {
	@Resource
	public UserService userService;
}
