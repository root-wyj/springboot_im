package com.wyj.springboot.im.controller;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.wyj.springboot.im.entity.common.ResponseBean;
import com.wyj.springboot.im.service.IRedisService;
import com.wyj.springboot.im.service.UserService;
import com.wyj.springboot.im.tools.StringUtil;
import com.wyj.springboot.im.tools.XXTEA;
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
	
	@Autowired
	Environment env;
	
	@PostMapping(value="login")
	public String login(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> model) {
		if (model == null) {
			return ResponseBean.crtParameterErrorResult();
		}
		
		String username = model.get("username");
		String password = model.get("password");
		
		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			return ResponseBean.crtParameterErrorResult();
		}
		
		if (!userService.isExist(username)) {
			return ResponseBean.crtFailureResult("该用户不存在");
		}
		
		User user = userService.getUser(username);
		if (user.getPassword().equals(password)) {
			ResponseBean bean = ResponseBean.crtSuccessBean();
			bean.setCode(code);
			return JSON.toJSONString(user);
		} else {
			return ResponseBean.crtFailureResult("密码错误");
		}
		
		
		
	}
	
//	@PostMapping
//	public String registe(HttpServletRequest request, HttpServletResponse response,
//			)
	
	@GetMapping("/userInfo")
	public String userInfo(HttpServletRequest request, HttpServletResponse response) {
		return JSON.toJSONString(userContext.get());
	}
	
	private void loginSuccess(HttpServletResponse response, User user) {
		// 更新cookie
		String uuid = UUID.randomUUID().toString();
		response.addCookie(
				CookieFactory.getUserCookie(new UserCookieContainer(uuid, user, System.currentTimeMillis())));
		// 添加到cache中
		userCache.set(uuid, user);
		loginTimeCache.increment(user);

		response.addCookie(CookieFactory.getUsernameCookie(user.getName()));
	}
	
	public static void main(String[] args) {
		String originStr = System.currentTimeMillis()+":"+"2"
//					+":"+UUID.randomUUID().toString()
					;
		
		String encrytStr = XXTEA.encrypt(originStr);
		
		String base64Str = Base64.getEncoder().encodeToString(originStr.getBytes());
		System.out.println("originStr:"+originStr
					+"\n encrytStr:"+encrytStr
					+"\n base64Str:"+base64Str);
	}
	
}
