package com.wyj.springboot.im.service;

import com.wyj.springboot.im.entity.User;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月28日
 */

public interface UserService{
	boolean isExist(String name);
	
	User getUser(String name);
}
