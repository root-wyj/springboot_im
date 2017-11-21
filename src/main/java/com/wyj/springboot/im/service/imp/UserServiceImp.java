package com.wyj.springboot.im.service.imp;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.authorize.cache.keymodel.UserCacheKey;
import com.wyj.springboot.im.config.BeanIocConfig;
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
	
	@Resource(name=BeanIocConfig.USER_CACHE)
	RedisCacheManager<UserCacheKey, User> userCache;
	
	public boolean isExist(String name) {
		return null!=userRepository.findByName(name);
	}
	
	public boolean isExist(long id) {
		return userRepository.exists(id);
	}
	
	public User getUser(long id) {
		return userRepository.findOne(id);
	}
	
	public User getUser(String name) {
		return userRepository.findByName(name);
	}
	
}
