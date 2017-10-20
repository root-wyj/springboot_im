package com.wyj.springboot.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wyj.springboot.im.authorize.UserContext;
import com.wyj.springboot.im.authorize.cache.KeySerilizable;
import com.wyj.springboot.im.authorize.cache.LongRedisCacheManager;
import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.entity.User;


/**
 * 
 * @author wuyingjie
 * @date 2017年9月29日
 */

@Configuration
public class BeanIocConfig {
	
	@Bean
	public UserContext userContext() {
		return new UserContext();
	}
	
	@Bean(name="loginTimeCache")
	public LongRedisCacheManager<User> loginTimeCM() {
		return new LongRedisCacheManager<User>(RedisCacheManager.DEFAULT_TIMEOUT, new KeySerilizable<User>() {
			@Override
			public String serilizableKey(User key) {
				return key.getClass().getName()+"_"+key.getId()+"_loginTimeCache";
			}
		});
	}
	
	@Bean(name="userCache")
	public RedisCacheManager<User, User> userCM() {
		return new RedisCacheManager<User, User>(RedisCacheManager.DEFAULT_TIMEOUT, new KeySerilizable<User>() {
			@Override
			public String serilizableKey(User key) {
				return key.getClass().getName()+"_"+key.getId()+"_loginTimeCache";
			}
		});
	}
	
}
