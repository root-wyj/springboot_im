package com.wyj.springboot.im.tools;

/**
 * 字符通用工具类
 * 
 * @author http://www.micmiu.com/lang/java/java-check-chinese/;
 */
public class CharUtils {

	/*
	public static void main(String[] args) {
		String[] strArr = new String[] { "www.micmiu.com",
				"!@#$%^&amp;*()_+{}[]|\"'?/:;&lt;&gt;,.", "！￥……（）——：；“”‘’《》，。？、", "不要啊",
				"やめて", "韩佳人", "한가인" };
		for (String str : strArr) {
			System.out.println("===========&gt; 测试字符串：" + str);
			System.out.println("正则判断：" + CharUtils.isChineseByREG(str) + " -- "
					+ CharUtils.isChineseByName(str));
			System.out.println("Unicode判断结果 ：" + CharUtils.isChinese(str));
			System.out.println("详细判断列表：");
			char[] ch = str.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				char c = ch[i];
				System.out.println(c + " --&gt; " + (CharUtils.isChinese(c) ? "是" : "否"));
			}
		}
	}
	*/
	
	// 根据Unicode编码完美的判断中文汉字和符号
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}
	
	public static int getChineseNum(String strName) {
		if (null == strName || 0 == strName.length()) {
			return 0;
		}
		
		int num = 0;
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				num++;
			}
		}
		return num;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
//	public static boolean isChineseByREG(String str) {
//		if (str == null) {
//			return false;
//		}
//		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
//		return pattern.matcher(str.trim()).find();
//	}

	// 只能判断部分CJK字符（CJK统一汉字
//	public static boolean isChineseByName(String str) {
//		if (str == null) {
//			return false;
//		}
//		// 大小写不同：\\p 表示包含，\\P 表示不包含 
//		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
//		String reg = "\\p{InCJK Unified Ideographs}&amp;&amp;\\P{Cn}";
//		Pattern pattern = Pattern.compile(reg);
//		return pattern.matcher(str.trim()).find();
//	}
}
