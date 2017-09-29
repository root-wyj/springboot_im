package com.wyj.springboot.im.authorize.cookie;

import javax.servlet.http.Cookie;


/**
 * 
 * @author wuyingjie
 * @date 2017年7月28日
 */

public class EmptyCookieFactory {
	
	private static int AGE_DELETED = 0;
	
	public static Cookie getEmptyUserCookie() {
		Cookie cookie = new Cookie(CookieFactory.COOKIE_KEY_USER_SESSION, "");
		cookie.setMaxAge(AGE_DELETED);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie getEmptyValidIconCookie() {
		Cookie cookie = new Cookie(CookieFactory.COOKIE_KEY_VALIDATE_CODE, "");
		cookie.setMaxAge(AGE_DELETED);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie getEmptyNativeIdCookie() {
		Cookie cookie = new Cookie(CookieFactory.COOKIE_KEY_NATIVE_ID, "");
		cookie.setMaxAge(AGE_DELETED);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie getEmptyUsernameCookie() {
		Cookie cookie = new Cookie(CookieFactory.COOKIE_KEY_USER_NAME, "");
		cookie.setMaxAge(AGE_DELETED);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie getEmptyUserIconCookie() {
		Cookie cookie = new Cookie(CookieFactory.COOKIE_KEY_USER_ICON, "");
		cookie.setMaxAge(AGE_DELETED);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie getEmptyBackUserCookie() {
		Cookie cookie = new Cookie(CookieFactory.COOKIE_KEY_BACK_USER, "");
		cookie.setMaxAge(AGE_DELETED);
		cookie.setPath("/");
		return cookie;
	}
	
	public static Cookie getEmptyBackUsernameCookie() {
		Cookie cookie = new Cookie(CookieFactory.COOKIE_KEY_BACK_USERNAME, "");
		cookie.setMaxAge(AGE_DELETED);
		cookie.setPath("/");
		return cookie;
	}
}
