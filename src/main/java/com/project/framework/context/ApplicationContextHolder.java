package com.project.framework.context;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 */
public class ApplicationContextHolder implements ApplicationContextAware {

	private final static Logger logger = Logger.getLogger(ApplicationContextHolder.class);
	// spring 上下文
	private static ApplicationContext ctx = null;

	/**
	 * 读取bean，必须在spring上下文中，否则返回null,读取后强制类型转换
	 * @param beanName 一般首字母都是小写
	 * @return
	 */
	public static Object getBean(String beanName) {
		if (ctx == null)
			return null;
		try {
			return ctx.getBean(beanName);
		} catch (Exception localException) {
			logger.error(localException);
		}
		return null;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		ctx = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	public <T> Map<String, T> getBeanByType(Class<T> type) {
		return ctx.getBeansOfType(type);
	}

	/**
	 * 查询所有的bean
	 * @param type
	 * @return
	 * @author gql 2011-12-22 下午4:51:55
	 */
	public static Map<?,?> getBeansByType(Class<?> type){
		return ctx.getBeansOfType(type);
	}

}
