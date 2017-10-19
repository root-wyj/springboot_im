package com.wyj.springboot.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wyj.springboot.im.authorize.UserContext;


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
	
}
