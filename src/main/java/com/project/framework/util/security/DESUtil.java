package com.project.framework.util.security;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <p>DES加密/解密工具类</p>
 * <p>从blog.csdn.net/shibenjie/article/details/5365355链接地址中复制。</p>
 * <p>version:1.0</p>
 */
public class DESUtil {
	private static byte[] iv = {1,2,3,4,5,6,7,8};
	private Key key;

	public DESUtil(){}
	public DESUtil(String strKey) {
		setKey(strKey);
	}
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * 根据密钥字符串参数生成KEY
	 * 
	 * @param strKey
	 *            密钥字符串
	 */
	public void setKey(String strKey) {
//		try {
		key = new SecretKeySpec(strKey.getBytes(), "DES");
//			KeyGenerator generator = KeyGenerator.getInstance("DES");
//			generator.init(new SecureRandom(strKey.getBytes()));
//			this.key = generator.generateKey();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
	}

	public String encryptStr(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = this.encryptByte(byteMing);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以 String 密文输入 ,String 明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public String decryptStr(String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = this.decryptByte(byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以 byte[] 明文输入 ,byte[] 密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private byte[] encryptByte(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
	        cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");  
	        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
//			cipher = Cipher.getInstance("DES");
//			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private byte[] decryptByte(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
//			cipher = Cipher.getInstance("DES");
//			cipher.init(Cipher.DECRYPT_MODE, key);
			IvParameterSpec zeroIv = new IvParameterSpec(iv);  
//	      IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);  
	        cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");  
	        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}
}