package com.wyj.springboot.im.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.jpa.UserRepository;
import com.wyj.springboot.im.service.UserService;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月28日
 */

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	public boolean isExist(String name, String password) {
		return null!=userRepository.findUser(name, password);
	}
	
	public User getUser(String name) {
		return userRepository.findByName(name);
	}
	
//	public boolean addUser(String name, String password) {
		
//	}
}
