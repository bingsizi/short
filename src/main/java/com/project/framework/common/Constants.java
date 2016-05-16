package com.project.framework.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.project.framework.util.ResBundleUtil;

/**
 * 基础常量类
 * @author Nice
 */
public final class Constants {
	
	//私有化构造
	private Constants(){
	}
	
	/**
	 * 资源文件.
	 */
	public static final String KEY = "application";
	
	public static final String USER_IN_SESSION = "userInSession";
	public static final String MENU_IN_SESSION = "menuInSession";

	/**
	 * 资源绑定对象
	 */
	public static final ResourceBundle RES = ResourceBundle.getBundle(KEY);
	
	/**
	 *  字符串表示的true，本项目中涉及true的调用皆使用此变量
	 */
	public static final String YES = "Y";
	
	/**
	 *  字符串表示的false，本项目中涉及false的调用皆使用此变量
	 */
	public static final String NO = "N";
	
	/**
	 * true or false map<br>
	 * 本项目中涉及true or false的调用皆使用此变量
	 */
	public static final Map<String, String> TF_MAP = new LinkedHashMap<String, String>();

	static {
		TF_MAP.put(YES, "是");
		TF_MAP.put(NO, "否");
	}
	
	public static final String DESC = "desc";
	
	public static final String ASC = "asc";

	/**
	 * 缺省的分页容量
	 */
	public static final int DEF_PAGE_SIZE = ResBundleUtil.getInt(RES, "pagesize");
	public static final Boolean INIT_SQL_DATA = ResBundleUtil.getBoolean(RES,"initSqlData");
	
	public static final String EQ = "EQ";
	public static final String LIKE = "LIKE";
	public static final String GT = "GT";
	public static final String LT = "LT";
	public static final String GTE = "GTE";
	public static final String LTE = "LTE";
	
	
	
	public static Map<String, String> OPT_MAPS = null;
	
	static{
		OPT_MAPS = new LinkedHashMap<String, String>();
		OPT_MAPS.put(EQ, " = ");
		OPT_MAPS.put(LIKE, " like ");
		OPT_MAPS.put(GT, " > ");
		OPT_MAPS.put(LT, " < ");
		OPT_MAPS.put(GTE, " >= ");
		OPT_MAPS.put(LTE, " <= ");
	}

}
