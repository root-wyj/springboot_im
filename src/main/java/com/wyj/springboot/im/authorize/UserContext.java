package com.wyj.springboot.im.authorize;

import org.springframework.stereotype.Component;

import com.wyj.springboot.im.entity.User;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月29日
 */

@Component(value="userContext")
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
}
