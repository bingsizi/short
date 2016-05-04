package com.project.framework.util.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * ����DES����
 */
public class TripleDES {
	
	public static final String __DATA_KEY  = "H"+"A"+"H"+"A"+"Q"+"U"+"S"+"H"+"I"+"B"+"A"+"#"+"T"+"M"+"D"+"#"+"N"+"N"+"D"+"#"+"D"+"M"+"@"+"@"+"C"+"A"+"N"+"I"+"M"+"A"+"D"+"E"+"#"+"@";
	public static final String __PARAM_KEY = "T"+"M"+"D"+"#"+"N"+"N"+"D"+"#"+"D"+"M"+"@"+"Q"+"U"+"S"+"H"+"I"+"B"+"A"+"#"+"H"+"A"+"H"+"A"+"@"+"C"+"A"+"N"+"I"+"M"+"A"+"D"+"E"+"#"+"@";
	public static final String __TOKEN_KEY = "C"+"A"+"N"+"I"+"M"+"A"+"D"+"E"+"#"+"@"+"Q"+"U"+"S"+"H"+"I"+"B"+"A"+"#"+"H"+"A"+"H"+"A"+"@"+"T"+"M"+"D"+"#"+"N"+"N"+"D"+"#"+"D"+"M"+"@";
	
	// The length of Encryptionstring should be 24 bytes and not be a weak key
	private String EncryptionString;
	// The initialization vector should be 8 bytes
	private final byte[] EncryptionIV = "TT1301!@".getBytes();
	private final static String DESede = "DESede/CBC/PKCS5Padding";

	public TripleDES() {}

	/**
	 * Saving key for encryption and decryption
	 * @param EncryptionString  String
	 */
	public TripleDES(String EncryptionString) {
		this.EncryptionString = EncryptionString;
	}

	/**
	 * Encrypt a byte array
	 * @param SourceData byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public byte[] encryptionByteData(byte[] SourceData) throws Exception {
		byte[] retByte = null;
		// Create SecretKey object
		byte[] EncryptionByte = EncryptionString.getBytes();
		DESedeKeySpec dks = new DESedeKeySpec(EncryptionByte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Create IvParameterSpec object with initialization vector
		IvParameterSpec spec = new IvParameterSpec(EncryptionIV);
		// Create Cipter object
		Cipher cipher = Cipher.getInstance(DESede);
		// Initialize Cipher object
		cipher.init(Cipher.ENCRYPT_MODE, securekey, spec);
		// Encrypting data
		retByte = cipher.doFinal(SourceData);
		return retByte;
	}

	/**
	 * Decrypt a byte array
	 * @param SourceData  byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public byte[] decryptionByteData(byte[] SourceData) throws Exception {
		byte[] retByte = null;

		// Create SecretKey object
		byte[] EncryptionByte = EncryptionString.getBytes();
		DESedeKeySpec dks = new DESedeKeySpec(EncryptionByte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Create IvParameterSpec object with initialization vector
		IvParameterSpec spec = new IvParameterSpec(EncryptionIV);

		// Create Cipter object
		Cipher cipher = Cipher.getInstance(DESede);

		// Initialize Cipher object
		cipher.init(Cipher.DECRYPT_MODE, securekey, spec);

		// Decrypting data
		retByte = cipher.doFinal(SourceData);
		return retByte;
	}

	/**
	 * Encrypt a string
	 * @param SourceData String
	 * @throws Exception
	 * @return String
	 */
	public String encryptionStringData(String SourceData) throws Exception {
		String retStr = null;
		byte[] retByte = null;

		// Transform SourceData to byte array
		byte[] sorData = SourceData.getBytes("UTF-8");

		// Encrypte data
		retByte = encryptionByteData(sorData);

		// Encode encryption data
//		BASE64Encoder be = new BASE64Encoder();
//		retStr = be.encode(retByte);

		return Base64.encode(retByte);
	}

	/**
	 * Decrypt a string
	 * @param SourceData String
	 * @throws Exception
	 * @return String
	 */
	public String decryptionStringData(String SourceData) throws Exception {
		String retStr = null;
		byte[] retByte = null;

		// Decode encryption data
//		BASE64Decoder bd = new BASE64Decoder();
//		byte[] sorData = bd.decodeBuffer(SourceData);
		byte[] sorData = Base64.decode(SourceData);

		// Decrypting data
		retByte = decryptionByteData(sorData);
		retStr = new String(retByte,"UTF-8");

		return retStr;
	}
}
