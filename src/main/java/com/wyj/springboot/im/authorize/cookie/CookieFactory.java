package com.wyj.springboot.im.authorize.cookie;

import javax.servlet.http.Cookie;

import com.wyj.springboot.im.config.Constants;

/**
 * 
 * @author wuyingjie
 * @date 2017年7月28日
 */

public class CookieFactory {
	
	public static final String COOKIE_KEY_USER_SESSION = "ayou_session_user_id";
	public static final String COOKIE_KEY_VALIDATE_CODE = "ayou_session_validate_code";
	public static final String COOKIE_KEY_NATIVE_ID = "_native";
	public static final String COOKIE_KEY_USER_NAME = "_un";
	public static final String COOKIE_KEY_USER_ICON = "_icon";
	
	public static final String COOKIE_KEY_BACK_USER = "_u_back";
	public static final String COOKIE_KEY_BACK_USERNAME = "_u_back_un";
	
	public static final int COOKIE_AGE_END_WITH_SESSION = -1;
	
	public static Cookie getUserCookie(UserCookieContainer container) {
		Cookie cookie = new Cookie(COOKIE_KEY_USER_SESSION, UserCookieContainer.generatorKey(container));
		cookie.setMaxAge(Constants.COOKIE_USER_EXPIRED);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie getUsernameCookie(String nickname) {
		Cookie cookie = new Cookie(COOKIE_KEY_USER_NAME, nickname);
		cookie.setMaxAge(COOKIE_AGE_END_WITH_SESSION);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie getUserIconCookie(String icon) {
		Cookie cookie = new Cookie(COOKIE_KEY_USER_ICON, icon);
		cookie.setMaxAge(COOKIE_AGE_END_WITH_SESSION);
		cookie.setPath("/");
		return cookie;
	}
}
