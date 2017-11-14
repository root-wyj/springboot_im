package com.wyj.springboot.im.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.wyj.springboot.im.authorize.UserContext;
import com.wyj.springboot.im.authorize.cache.LongRedisCacheManager;
import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.authorize.cookie.CookieFactory;
import com.wyj.springboot.im.authorize.cookie.UserCookieContainer;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.service.IRedisService;
import com.wyj.springboot.im.service.UserService;
import com.wyj.springboot.im.config.BeanIocConfig;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月28日
 */

@RestController
@RequestMapping(produces="application/json;charset=utf-8")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Resource(name=BeanIocConfig.USER_CACHE)
	RedisCacheManager<String, User> userCache;
	@Resource(name=BeanIocConfig.LOGIN_TIMES_CACHE)
	LongRedisCacheManager<User> loginTimeCache;
	
	@Resource
	IRedisService redisService;
	
	@Autowired
	UserContext userContext;
	
	@RequestMapping(value="login")
	public String login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("name")String name,
			@RequestParam("password")String password) {
		
		if (!userService.isExist(name, password)) {
			return "name or pswd error!";
		}
		
		User userInfo = userService.getUser(name);
		// 更新cookie
		String uuid = UUID.randomUUID().toString();
		response.addCookie(
				CookieFactory.getUserCookie(new UserCookieContainer(uuid, userInfo, System.currentTimeMillis())));
		// 添加到cache中
		userCache.set(uuid, userInfo);
		loginTimeCache.increment(userInfo);

		response.addCookie(CookieFactory.getUsernameCookie(userInfo.getName()));

		return JSON.toJSONString(userInfo);
	}
	
	@GetMapping("/userInfo")
	public String userInfo(HttpServletRequest request, HttpServletResponse response) {
		return JSON.toJSONString(userContext.get());
	}
	
}
