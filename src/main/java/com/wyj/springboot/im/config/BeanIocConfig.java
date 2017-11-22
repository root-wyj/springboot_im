package com.wyj.springboot.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.wyj.springboot.im.authorize.cache.KeySerilizable;
import com.wyj.springboot.im.authorize.cache.LongRedisCacheManager;
import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.authorize.cache.keymodel.UserCacheKey;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.socketnio.NettySocketServer;


/**
 * 
 * @author wuyingjie
 * @date 2017年9月29日
 */

@Configuration
public class BeanIocConfig {
	
	/**
	 * 用于扫描socket事件监听的注解的类
	 * @param socketServer
	 * @return
	 */
	@Bean  
    public SpringAnnotationScanner springAnnotationScanner(NettySocketServer socketServer) { 
        return new SpringAnnotationScanner(socketServer.getServer());  
    } 


	public static final String LOGIN_TIMES_CACHE = "loginTimesCache";
	
	@Bean(name=LOGIN_TIMES_CACHE)
	public LongRedisCacheManager<User> loginTimeCM() {
		return new LongRedisCacheManager<User>(RedisCacheManager.DEFAULT_TIMEOUT, new KeySerilizable<User>() {
			@Override
			public String serilizableKey(User key) {
				return LOGIN_TIMES_CACHE+"_"+key.getId();
			}
		});
	}
	
	public static final String USER_CACHE = "userCache";
	
	@Bean(name=USER_CACHE)
	public RedisCacheManager<UserCacheKey, User> userCM() {
		return new RedisCacheManager<UserCacheKey, User>(Constants.HEADER_USER_TOKEN_EXPIRED, new KeySerilizable<UserCacheKey>() {
			@Override
			public String serilizableKey(UserCacheKey key) {
				return USER_CACHE+"_"+key.getUserId()+"_"+key.getUuid();
			}
		});
	}
	
}
