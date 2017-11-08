package com.wyj.springboot.im.authorize;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wyj.springboot.im.authorize.cookie.CookieFactory;
import com.wyj.springboot.im.authorize.cookie.EmptyCookieFactory;
import com.wyj.springboot.im.authorize.cookie.UserCookieContainer;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.tools.StringUtil;


/**
 * 
 * @author wuyingjie
 * @date 2017年6月13日
 */

public class AnalyzeInterceptor implements HandlerInterceptor{

	Logger logger = LoggerFactory.getLogger(AnalyzeInterceptor.class);
	
	@Autowired
	private UserContext userContext
			= new UserContext()
			;
		
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object o = request.getAttribute("backAuth");
		int backAuth = StringUtil.toInteger(String.valueOf(o), 0);
		System.out.println("get backAuth value->"+backAuth);
		
		Cookie[] cookies = request.getCookies();
		Long userId = null;
		String userSessionCookie = null;
		String usernameCookie = null;
		
		if (cookies != null && cookies.length > 0) {
			for(Cookie c : cookies) {
				if (c.getName().equals(CookieFactory.COOKIE_KEY_USER_SESSION)) {
					userSessionCookie = c.getValue();
				} else if (c.getName().equals(CookieFactory.COOKIE_KEY_USER_NAME)) {
					usernameCookie = c.getValue();
				}
			}
		}
		
		if (!StringUtil.isEmpty(userSessionCookie)) {
			UserCookieContainer container = null;
			if ((container = UserCookieContainer.resolveUserCookie(userSessionCookie)) != null) {
				setUser(container.getUser(), container.getUuid());
				userId = container.getUser().getId();

				//如果超过3分钟更新
//				updateUserCookie(container, response);

				if (StringUtil.isEmpty(usernameCookie)) {
//					UserInfo userInfo = userService.getUserInfoById(userId);
//					response.addCookie(CookieFactory.getUsernameCookie(userInfo.getNickname()));
//					response.addCookie(CookieFactory.getUserIconCookie(userInfo.getIcon()));
				}

			} else {
				response.addCookie(EmptyCookieFactory.getEmptyUserCookie());
			}
		}
		
		
		if (userId == null) {
			userContext.remove();
			
			if (!StringUtil.isEmpty(usernameCookie)) {
				response.addCookie(EmptyCookieFactory.getEmptyUsernameCookie());
			}
		}
		
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (userContext != null) {
			userContext.remove();
		}
	}
	
	private void setUser(User user, String uuid){
		userContext.set(user);
	}
	
//	private void updateUserCookie(UserCookieContainer container, HttpServletResponse response) {
//		
//		if (container == null || StringUtil.isEmpty(container.getUuid()) || container.getUser() == null || container.getUser().getUserId() <= 0) {
//			return ;
//		}
//		
//		long now = System.currentTimeMillis();
//		if (now - container.getTimestamp() <= Constants.USER_CACHE_DB_REFRESH) {
//			return ;
//		}
//		
//		response.addCookie(CookieFactory.getUserCookie(new UserCookieContainer(container.getUuid(), container.getUser(), now)));
//	}

}
