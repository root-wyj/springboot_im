package com.wyj.springboot.im.tools;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptUtils {
	
	private static final String DES_KEY_STR = "DocIn_sHIxi_5&(*%@94^";
	
	private static final String DES_ALGORITHM = "DES/ECB/PKCS5Padding";
	
	public static String desEncrypt(String data)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException {
		
		SecureRandom sr = new SecureRandom();
        SecretKeyFactory keyFactory;
        DESKeySpec dks = new DESKeySpec(DES_KEY_STR.getBytes());
        keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
		byte[] encryptByte = cipher.doFinal(data.getBytes("UTF-8"));
		String result = Base64.getEncoder().encodeToString(encryptByte);
		return result;
	}
	
	public static String desDecrypt(String data)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException {
		SecureRandom sr = new SecureRandom();    
        SecretKeyFactory keyFactory;  
        DESKeySpec dks = new DESKeySpec(DES_KEY_STR.getBytes());  
        keyFactory = SecretKeyFactory.getInstance("DES");  
        SecretKey secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
		byte[] decryptByte = cipher.doFinal(Base64.getDecoder().decode(data));
		return new String(decryptByte, "UTF-8");
	}
	
	public static void main(String[] args) throws Exception{
		String data = "xiixhaha";
		String encryptStr = desEncrypt(data);
		String decryptStr = desDecrypt(encryptStr);
		System.out.println(String.format("data:%s, encryptStr:%s, decryptStr:%s", data, encryptStr, decryptStr));
	}
	
	
	
}
