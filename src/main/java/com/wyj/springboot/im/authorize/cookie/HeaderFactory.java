package com.wyj.springboot.im.authorize.cookie;

import javax.servlet.http.Cookie;

import com.wyj.springboot.im.config.Constants;

/**
 * 
 * @author wuyingjie
 * @date 2017年7月28日
 */

public class HeaderFactory {
	
	public static final String HEADER_KEY_USER_TOKEN = "zjh_header_user_token";
	public static final String COOKIE_KEY_USER_NAME = "_un";
	public static final String COOKIE_KEY_USER_ICON = "_icon";
	
	public static Header getUserHeader(UserHeaderContainer container) {
		return new Header(HEADER_KEY_USER_TOKEN, UserHeaderContainer.generatorKey(container));
	}

	public static class Header {
		public String key;
		public String value;

		public Header() {}

		public Header(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}
}
