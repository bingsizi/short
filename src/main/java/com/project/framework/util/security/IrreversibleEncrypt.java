package com.project.framework.util.security;

/**
 * <p>
 * 不可逆加密算法，采用DES加密算法和MD5加密相结合，试用于密码等不可逆加密内容。
 * </p>
 * <p>
 * version:1.0
 * </p>
 */
public class IrreversibleEncrypt {
	/**
	 * DES密钥
	 */
	private static final String DES_STR_KEY = "&d8%k6BY";
	private static DESUtil desUtil = new DESUtil(DES_STR_KEY);

	/**
	 * 将给定字符串加密
	 * 
	 * @param mingStr
	 *            加密前字符串
	 * @return 加密后字符串
	 */
	public static String encrypt(String mingStr) {
		return MD5Util.MD5Encode(desUtil.encryptStr(mingStr));
	}
	public static void main(String[] args) {
		DESUtil desUtil = new DESUtil(DES_STR_KEY);
		System.out.println(desUtil.encryptStr("222222"));
		System.out.println(desUtil.decryptStr(desUtil.encryptStr("111111")));
		System.out.println(IrreversibleEncrypt.encrypt("xx1111"));
	}
}