package com.wyj.springboot.im.authorize;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.exception.ZJHRuntimeException;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月29日
 */

//@Component(value="userContext")
public class UserContext{
	
static final ThreadLocal<Map<String, Object>> current = new ThreadLocal<>();
	
	private static final String USER_KEY = "user";
	private static final String UUID_KEY = "uuid";
	
	public UserContext(User user, String uuid) {
		Map<String, Object> map = new HashMap<>();
		map.put(USER_KEY, user);
		map.put(UUID_KEY, uuid);
		current.set(map);
	}
	
	public static User getCurrentUser() {
		User info = null;
		Map<String, Object> map = current.get();
		if (map != null) {
			info = (User) map.get(USER_KEY);
			if (info.getId() <= 0) {
				info = null;
			}
		}
		
		return info;
	}
	
	public static String getCurrentUUID() {
		String uuid = "";
		Map<String, Object> map = current.get();
		if (map != null) {
			uuid = String.valueOf(map.get(UUID_KEY));
		}
		return uuid;
	}
	
	public static long getCurrentUserId() {
		User info = getCurrentUser();
		long userId = 0;
		if (info != null) {
			userId = info.getId();
		}
		return userId;
	}
	
	public static long getRequiredCurrentUserId() {
		long userId = getCurrentUserId();

		if (userId > 0) {
			return userId;
		} else {
			throw new ZJHRuntimeException("没有权限");
		}
	}
	
	public void close() {
		current.remove();
	}
	
	
	
	public static void main(String[] args) throws Exception{
		String str = URLDecoder.decode("%AC%ED%00%05t%00%09", "utf-8");
		int[] aa = {0xAC, 0xED, 0x00, 0x05, 0x00, 0x09};
		StringBuilder sb = new StringBuilder();
		for (int a : aa) {
			char c = (char)a;
			System.out.println(c);
			sb.append(c);
		}
		System.out.println(sb.toString());
		System.out.println(str);
	}
}
