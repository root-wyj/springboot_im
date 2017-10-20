package com.wyj.springboot.im.authorize;

import java.net.URLDecoder;

import org.springframework.stereotype.Component;

import com.wyj.springboot.im.entity.User;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月29日
 */

//@Component(value="userContext")
public class UserContext{
	
	private ThreadContext<User> threadContext = new ThreadContext<>();
	
	public User get() {
		return threadContext.get();
	}
	
	public void set(User u) {
		threadContext.save(u);
	}
	
	public User remove() {
		return threadContext.remove();
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
