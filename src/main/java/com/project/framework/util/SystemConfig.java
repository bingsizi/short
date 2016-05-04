package com.project.framework.util;

import java.util.ResourceBundle;

/**
 * 读取系统配置文件信息
 * @author Administrator
 */
public class SystemConfig {
	
	private static final ResourceBundle bundle = ResourceBundle.getBundle("sysconfig");
	
	public static String getValue(String key) {
		try{
			return bundle.getString(key);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
