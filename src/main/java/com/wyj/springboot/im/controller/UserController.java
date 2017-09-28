package com.wyj.springboot.im.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(value="login")
	public String login(
			@RequestParam("name")String name,
			@RequestParam("password")String password) {
		
		return ""+userService.isExist(name, password);
	}
	
}
