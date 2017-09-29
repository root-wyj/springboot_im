package com.wyj.springboot.im.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.wyj.springboot.im.authorize.RedisCache;
import com.wyj.springboot.im.authorize.cookie.CookieFactory;
import com.wyj.springboot.im.authorize.cookie.UserCookieContainer;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.service.UserService;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月28日
 */

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RedisCache redisCache;
	
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
		redisCache.save(uuid, userInfo);

		response.addCookie(CookieFactory.getUsernameCookie(userInfo.getName()));

		return JSON.toJSONString(userInfo);
	}
	
}
