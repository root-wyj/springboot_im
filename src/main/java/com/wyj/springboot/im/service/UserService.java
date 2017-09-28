package com.wyj.springboot.im.service;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月28日
 */

public interface UserService{
	boolean isExist(String name, String password);
}
