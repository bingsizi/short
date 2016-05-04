package com.project.base.user.service;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import com.project.base.user.entity.User;
import com.project.framework.controller.Page;
import com.project.framework.service.BaseServiceImpl;
import com.project.framework.util.StringUtil;
import com.project.framework.util.security.MD5Util;

/**  
 * 用户业务层
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月3日 下午4:19:23
 */
@Service
public class UserService extends BaseServiceImpl<User,Long>{
	
	/**
	 * 验证用户登陆
	 * @param username
	 * @param password
	 * @return
	 * @author zhangpeiran 2016年5月4日 下午1:51:19
	 */
	public User login(String username,String password){
		return baseDao.executeUniqueQuery(Restrictions.eq("username",username),Restrictions.eq("password",MD5Util.MD5Encode(password)));
	}
	/**
	 * 检查是否含有用户
	 * @return
	 * @author zhangpeiran 2016年5月4日 下午1:56:26
	 */
	public boolean isHasUser(){
		long count = countResult("from User");
		return count>0?true:false;
	}
	/**
	 * 根据条件分页查询用户
	 * @param page
	 * @param username
	 * @return
	 * @author zhangpeiran 2016年5月4日 下午4:57:57
	 */
	public Page<User> findByPage(Page<User> page,String username){
		if(StringUtil.isNullOrEmpty(username)){
			return super.pageQuery(page);
		}
		else{
			return super.pageQuery(page,Restrictions.like("username", username, MatchMode.ANYWHERE));
		}
	}
}
