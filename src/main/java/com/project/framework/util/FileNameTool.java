package com.project.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 操作文件名工具类
 */
public class FileNameTool {
	/**
	 * 获取文件扩展名,带.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		int temp = fileName.lastIndexOf(".");
		if (temp == -1)
			return null;
		return fileName.substring(temp, fileName.length());
	}
	/**
	 * 返回文件扩展名 不带.
	 * @param fileName
	 * @return
	 */
	public static String readExtName(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}
	/**
	 * 验证扩展名是否为图片类型的,支持.jpg,jpeg,png;
	 * 
	 * @param extendstion
	 * @return
	 */
	public static boolean checkImageExtenstion(String extendstion) {
		//String reg = "[.](JPEG|jpeg|JPG|jpg|GIF|gif|BMP|bmp|PNG|png)$";
		String reg = "[.](JPEG|jpeg|JPG|jpg|PNG|png)$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(extendstion);
		return matcher.find();
	}
	/**
	 * 验证扩展名类型是否为 xls
	 * @param extendstion
	 * @return
	 */
	public static boolean checkXLSExtenstion(String extendstion){
		String reg = "[.](xls)$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(extendstion);
		return matcher.find();
	}
	public static void main(String[] args) {
		String aa = "aaaa.jpg";
		System.out.println(FileNameTool.getExtension(aa));
	}
}
