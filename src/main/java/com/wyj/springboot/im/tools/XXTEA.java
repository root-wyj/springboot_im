package com.wyj.springboot.im.tools;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
 
/**
 * XXTEA 加密算法
 * @author wuyingjie
 * @date 2017年8月30日
 */
public class XXTEA {
	//该算法加密后位数解析：首先key 在PaddingRight之后 一定会是一个长度为MIN_LENGTH=32的字符串（虽然后面是用\0填充的）
	//	之后转化为32长度的字节数组，使每个字符都用一个字节来表示，toLongArray会合并字节数组 规则是每8个合成一个，最后得到一个
	//	长度为4的long数组.
	//	同样的，data 被加密的数据被要求的最短长度也是32，那么最后得到的long数组长度就是4，通过TEAEncrypt方法知道，返回加密的结果
	//	的长度就和 data最后得到的long 数组的长度有关。通过日志和ToHexString方法知道 返回的data[i]最长有64位（这也说明该算法能保证操作之后的数据在64位内）
	//	，表示为16进制字符串 就是64/4=16长度的字符串。long 数组有4个数，所以一般的长度就是64.而且当被加密的数据超过32位之后，每8位的长度 加密后增加16位的长度
	//	最后的长度就是(a=data.length) 当a>32 a/8+(a%8==0?0:1))*16  大约就是 a*2
	
	private static final String DES_KEY_STR = "DocIn_sHIxi_5&(*%@94^";
	
	public static void main(String[] args) {
		String[] strs = {"1497951465445:11000:aac99960-d7c7-475f-a4f2-776be0390c3b",
				"1497951381509:10019:6dc7b433-13a5-458c-8157-919f3152a6a9",
				"1503643841294:1:0b20528e-d912-48a4-982f-21c431ba66de"};
		long timestart = System.currentTimeMillis();
		for (int i=0; i<strs.length; i++) {
			String encrypt = Encrypt(strs[i], DES_KEY_STR);
			String decrypt = Decrypt(encrypt, DES_KEY_STR);
			System.out.println("str:"+strs[i]+", encrypt:"+encrypt+", decrypt:"+decrypt+", encrypt.length:"+encrypt.length());
			
		}
		long timeend = System.currentTimeMillis();
		System.out.println("总用时："+(timeend - timestart)+", 平均用时："+(timeend - timestart)/strs.length);
		
	}
	
	public static String encrypt(String data) {
		return Encrypt(data, DES_KEY_STR);
	}
	
	public static String decrypt(String data) {
		return Decrypt(data, DES_KEY_STR);
	}
	
    private static String Encrypt(String data, String key) {
        return ToHexString(TEAEncrypt(
                ToLongArray(PadRight(data, MIN_LENGTH).getBytes(
                        Charset.forName("UTF8"))),
                ToLongArray(PadRight(key, MIN_LENGTH).getBytes(
                        Charset.forName("UTF8")))));
    }
 
    private static String Decrypt(String data, String key) {
        if (data == null || data.length() < MIN_LENGTH) {
            return data;
        }
        byte[] code = ToByteArray(TEADecrypt(
                ToLongArray(data),
                ToLongArray(PadRight(key, MIN_LENGTH).getBytes(
                        Charset.forName("UTF8")))));
        return new String(code, Charset.forName("UTF8"));
    }
 
    private static long[] TEAEncrypt(long[] data, long[] key) {
        int n = data.length;
        if (n < 1) {
            return data;
        }
 
        long z = data[data.length - 1], y = data[0], sum = 0, e, p, q;
        q = 6 + 52 / n;
        while (q-- > 0) {
            sum += DELTA;
            e = (sum >> 2) & 3;
            for (p = 0; p < n - 1; p++) {
                y = data[(int) (p + 1)];
                z = data[(int) p] += (z >> 5 ^ y << 2) + (y >> 3 ^ z << 4)
                        ^ (sum ^ y) + (key[(int) (p & 3 ^ e)] ^ z);
            }
            y = data[0];
            z = data[n - 1] += (z >> 5 ^ y << 2) + (y >> 3 ^ z << 4)
                    ^ (sum ^ y) + (key[(int) (p & 3 ^ e)] ^ z);
        }
 
        return data;
    }
 
    private static long[] TEADecrypt(long[] data, long[] key) {
        int n = data.length;
        if (n < 1) {
            return data;
        }
 
        long z = data[data.length - 1], y = data[0], sum = 0, e, p, q;
        q = 6 + 52 / n;
        sum = q * DELTA;
        while (sum != 0) {
            e = (sum >> 2) & 3;
            for (p = n - 1; p > 0; p--) {
                z = data[(int) (p - 1)];
                y = data[(int) p] -= (z >> 5 ^ y << 2) + (y >> 3 ^ z << 4)
                        ^ (sum ^ y) + (key[(int) (p & 3 ^ e)] ^ z);
            }
            z = data[n - 1];
            y = data[0] -= (z >> 5 ^ y << 2) + (y >> 3 ^ z << 4) ^ (sum ^ y)
                    + (key[(int) (p & 3 ^ e)] ^ z);
            sum -= DELTA;
        }
 
        return data;
    }
 
    
    
    private static long[] ToLongArray(byte[] data) {
        int n = (data.length % 8 == 0 ? 0 : 1) + data.length / 8;
        long[] result = new long[n];
 
        for (int i = 0; i < n - 1; i++) {
            result[i] = bytes2long(data, i * 8);
        }
 
        byte[] buffer = new byte[8];
        for (int i = 0, j = (n - 1) * 8; j < data.length; i++, j++) {
            buffer[i] = data[j];
        }
        result[n - 1] = bytes2long(buffer, 0);
 
        return result;
    }
 
    private static byte[] ToByteArray(long[] data) {
        List<Byte> result = new ArrayList<Byte>();
 
        for (int i = 0; i < data.length; i++) {
            byte[] bs = long2bytes(data[i]);
            for (int j = 0; j < 8; j++) {
                result.add(bs[j]);
            }
        }
 
        while (result.get(result.size() - 1) == SPECIAL_CHAR) {
            result.remove(result.size() - 1);
        }
 
        byte[] ret = new byte[result.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = result.get(i);
        }
        return ret;
    }
 
    public static byte[] long2bytes(long num) {
        ByteBuffer buffer = ByteBuffer.allocate(8).order(
                ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(num);
        return buffer.array();
    }
 
    public static long bytes2long(byte[] b, int index) {
        ByteBuffer buffer = ByteBuffer.allocate(8).order(
                ByteOrder.LITTLE_ENDIAN);
        buffer.put(b, index, 8);
        return buffer.getLong(0);
    }
 
    private static String ToHexString(long[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(PadLeft(Long.toHexString(data[i]), 16));
//            System.out.println("data["+i+"] getBytes.length:"+Long.toBinaryString(data[i]).length());
        }
        return sb.toString();
    }
    
    private static long[] ToLongArray(String data) {
        int len = data.length() / 16;
        long[] result = new long[len];
        for (int i = 0; i < len; i++) {
            result[i] = new BigInteger(data.substring(i * 16, i * 16 + 16), 16)
                    .longValue();
        }
        return result;
    }
 
    private static String PadRight(String source, int length) {
        while (source.length() < length) {
            source += SPECIAL_CHAR;
        }
        return source;
    }
 
    private static String PadLeft(String source, int length) {
        while (source.length() < length) {
            source = '0' + source;
        }
        return source;
    }
 
    private static long DELTA = 2654435769L;
    private static int MIN_LENGTH = 32;
    private static char SPECIAL_CHAR = '\0';
}
