package com.project.framework.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 判断是否为空或是空字符串,如果为null或为“”返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		if (null == str || "".equalsIgnoreCase(str.trim()) || "null".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 * @param size
	 * @return
	 */
	public synchronized static String splitString(String str, int size) {
		if (StringUtil.isNullOrEmpty(str)) {
			return "";
		}
		if (str.length() > size) {
			return str.substring(0, size) + "...";
		}
		return str;
	}

	/**
	 * 从ISO8859-1转换为utf-8
	 * 
	 * @param str
	 * @return
	 */
	public synchronized static String convert2UTF8FromIOS8859_1(String str) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		try {
			return new String(str.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 从utf-8转换为ISO8859-1
	 * 
	 * @param str
	 * @return
	 */
	public synchronized static String convert2ISO8859_1FromUTF8(String str) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		try {
			return new String(str.getBytes("UTF-8"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 判断字符串是否是正整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPositiveInteger(String str) {
		if(isNullOrEmpty(str)){
			return false;
		}
		return str.matches("[0-9]*");
	}
	/**
	 * 得到一个字符串,在另一个字符串中重复出现的次数,区分大小写
	 * 
	 * @param str
	 *            原字符串
	 * @param temp
	 *            计算重复出现的字符串
	 * @return
	 */
	public static int stringRepeatNum(String str, String temp) {
		Pattern p = Pattern.compile(temp);
		Matcher m = p.matcher(str);
		int i = 0;
		while (m.find()) {
			i++;
		}
		return i;
	}
    /**
     * 根据values值依次替换字符串中的temp选项,区分大小写
     * @param str 原字符串
     * @param temp 被替换的参数
     * @param values 替换值
     * @return 返回替换后的字符串
     */
	public static String replaceStringByValues(String str,String temp,String...values){
		if(values==null)
			return str;
		Pattern p = Pattern.compile(temp);
		Matcher m = p.matcher(str);
		int i = 0;
		while (m.find()) {
			if(StringUtil.isNullOrEmpty(values[i]))
				str = str.replaceFirst(temp,"");
			else{
				str = str.replaceFirst(temp,values[i]);
				i++;
				if(i>=values.length)
					break;	
			}
		}
		return str;
	}
	/**
	 * 判断一个字符串数组里是否包含某个字符串
	 * @param temps
	 * @param str
	 * @return 包含返回true,不包含返回false
	 */
	public static boolean isContainsStr(String [] temps,String str){
		if(StringUtil.isNullOrEmpty(str))
			return false;
		if(temps==null)
			return false;
		for (String string : temps) {
			if(str.equals(string))
				return true;
		}
		return false;
	}
	/**
	 * 得到UUID
	 * @return
	 * @author gql 2013-7-5 下午4:17:54
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	/**
	 * 验证是否是数字
	 * @param str
	 * @return
	 * @author gql 2013-7-3 下午6:43:52
	 */
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	public static int parseInt(String str){
		if(isNum(str)){
			return Integer.parseInt(str);
		}else{
			return 0;
		}
	}
	
	public static double parseDouble(String str){
		if(isNum(str)){
			return Double.parseDouble(str);
		}else{
			return 0;
		}
	}
	
	/**
	 * 字符串转字符串集合
	 * @param src
	 * @param splitStr
	 * @return
	 * @author gql 2014-12-19 下午4:07:14
	 */
	public static List<String> string2List(String src,String splitStr){
		if(StringUtil.isNullOrEmpty(src) || StringUtil.isNullOrEmpty(splitStr)){
			return null;
		}
		String[] s = src.split(splitStr);
		List<String> list = new ArrayList<String>();
		for (String string : s) {
			if(StringUtil.isNullOrEmpty(string))
				continue;
			list.add(string);
		}
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println(string2List("1,2,3,4,5 ", ","));
	}
}
