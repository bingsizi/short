package com.project.framework.util;

import java.util.ResourceBundle;

/**
 * ResourceBundle的帮助类
 * @author Nice
 * 
 */
public final class ResBundleUtil {
  /**
   * prevent from initializing.
   */
  private ResBundleUtil() {
  }

  public static String DEFAULT_BUNDLE = "application";
  
  /**
   * 返回定义在ResourceBundle中的 int 值
   * @return
   */
  public static int getInt(ResourceBundle rb, String key) {
    return Integer.parseInt(rb.getString(key));
  }

  /**
   * 返回定义在ResourceBundle中的String值
   * @param defaultValue 缺省值，如果出现任何异常，都返回该值
   * 
   */
  public static String getString(ResourceBundle rb, String key) {
    return rb.getString(key);
  }

  /**
   * 返回定义在ResourceBundle中的boolean值
   * @return
   */
  public static boolean getBoolean(ResourceBundle rb, String key) {
    return Boolean.valueOf(rb.getString(key).trim());
  }
}
