package com.project.framework.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.base.user.entity.User;
import com.project.framework.common.Constants;


public class WebContext {
	
	private static ServletContext servletContext;

	public static HttpServletRequest getRequest() {
		try{
			ServletRequestAttributes sr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			return sr.getRequest();
		}catch(Exception ex){
			return null;
		}
	}
	public static HttpSession getSession(){
		HttpServletRequest request = getRequest();
		if(null != request){
			return getRequest().getSession();
		}
		return null;
	}
	/**
	 * 获取系统上下文
	 * @return
	 * @author gql 2014-11-20 上午8:59:49
	 */
	public static ServletContext getServletContext(){
		return servletContext;
	}
	public static void setServletContext(ServletContext servletContext){
		WebContext.servletContext=servletContext;
	}
	/**
	 * 获取项目路径
	 * @return F:\Program Files\Apache Software Foundation\tomcat-7.0.47\wtpwebapps\BGWeb\
	 * @author gql 2014-11-20 上午8:59:09
	 */
	public static String getPhysicalPath(){
		String path = servletContext.getRealPath("/");
		return path;
	}
	/**
	 * 获取网站访问路径
	 * @return http://localhost:8081/BGWeb
	 * @author gql 2014-11-20 上午9:29:19
	 */
	public static String getWebPath(){
		return getRequest().getScheme() + "://" + getRequest().getServerName()+ ":" + getRequest().getServerPort() + getRequest().getContextPath();
	}
	/**
	 * 获取登陆用户
	 * @return
	 * @author gql 2015-9-18 下午4:37:39
	 */
	public static User getLoginUser(){
		HttpServletRequest request = getRequest();
		if(null == request){
			return null;
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constants.LOGIN_USER);
		return user;
	}
	/**
	 * 设置登陆用户
	 * @param user
	 * @author gql 2015-9-18 下午4:38:50
	 */
	public static void setLoginUser(User user){
		HttpSession session = getRequest().getSession();
		session.setAttribute(Constants.LOGIN_USER,user);
	}
}