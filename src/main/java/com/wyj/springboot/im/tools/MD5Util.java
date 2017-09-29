package com.wyj.springboot.im.tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

public class MD5Util {
	// 进行32为加密
	public static String MD5_32(String plainText) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	// md5 16位加密
	public static String MD5_16(String plainText) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString().substring(8, 24);
	}

	public static String byteArrayToString(String text) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] b = messageDigest.digest(text.getBytes());
			StringBuffer resultSb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				resultSb.append(byteToNumString(b[i]));// 使用本函数则返回加密结果的10进制数字字串，即全数字形式
			}
			return resultSb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "0";
		}

	}

	private static String byteToNumString(byte b) {
		int _b = b;
		if (_b < 0) {
			_b = 256 + _b;
		}
		return String.valueOf(_b);
	}

	public static Integer gethash(String key) {
		BigInteger big = new BigInteger(MD5Util.byteArrayToString(key));
		BigInteger area = new BigInteger("999999");
		BigInteger[] array = big.divideAndRemainder(area);
		return array[1].intValue();
	}
	//crc32
	public static Long getCRC32(String key){
		CRC32 crc32 = new CRC32();
		crc32.update(key.getBytes());
		return crc32.getValue();
	}
}
