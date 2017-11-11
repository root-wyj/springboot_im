package com.wyj.springboot.im.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
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
	
	@Autowired
	RedisCacheManager<String, User> userCache;
	@Autowired
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
	
	@GetMapping("/redis/set")
	public String putInRedis(@RequestParam(name="key") String key, @RequestParam("value") String value) {
		return redisService.set(key, value)+"";
	}
	
	@GetMapping("/redis/get")
	public String getInRedis(@RequestParam(name="key") String key) {
		return redisService.get(key);
	}
	
	@GetMapping("/redis/expire")
	public String putInRedis(@RequestParam(name="key") String key, @RequestParam("expire") long expire) {
		return redisService.expire(key, expire)+"";
	}
	
	@GetMapping("/redis/del")
	public String delInRedis(@RequestParam(name="key")String key) {
		redisService.del(key);
		return "success";
	}
	
	@GetMapping("/redis/delu")
	public String redisDelUser(@RequestParam(name="key") String key) {
		redisTemplate.delete(key);
		return "success";
	}
	
	@Autowired
	RedisTemplate<String, User> redisTemplate;
	@RequestMapping("/redis/user/get")
	public String redisGetUser(@RequestParam(name="key")String key) {
		return JSON.toJSONString(redisTemplate.opsForValue().get(key));
	}
}
