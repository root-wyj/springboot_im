package com.wyj.springboot.im.authorize;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.authorize.cache.keymodel.UserCacheKey;
import com.wyj.springboot.im.authorize.cookie.HeaderFactory;
import com.wyj.springboot.im.authorize.cookie.UserHeaderContainer;
import com.wyj.springboot.im.config.BeanIocConfig;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.tools.StringUtil;


/**
 * 
 * @author wuyingjie
 * @date 2017年6月13日
 */

public class AnalyzeInterceptor implements HandlerInterceptor{

	Logger logger = LoggerFactory.getLogger(AnalyzeInterceptor.class);
	
	@Resource(name=BeanIocConfig.USER_CACHE)
	RedisCacheManager<UserCacheKey, User> userCache;
	
	private UserContext userContext;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		Object o = request.getAttribute("backAuth");
//		int backAuth = StringUtil.toInteger(String.valueOf(o), 0);
//		System.out.println("get backAuth value->"+backAuth);
		
		String userSessionCookie = request.getHeader(HeaderFactory.HEADER_KEY_USER_TOKEN);
		
		if (!StringUtil.isEmpty(userSessionCookie)) {
			UserHeaderContainer container = null;
			if ((container = UserHeaderContainer.resolveUserCookie(userSessionCookie)) != null) {
				User user = userCache.get(new UserCacheKey(container.getUserId(), container.getUuid()));
				if (user != null) {
					setUser(user, container.getUuid());
				}
				//如果超过3分钟更新
//				updateUserCookie(container, response);
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
			userContext.close();
		}
	}
	
	private void setUser(User user, String uuid){
		userContext = new UserContext(user, uuid);
	}
	
//	private void updateUserCookie(UserHeaderContainer container, HttpServletResponse response) {
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
//		response.addCookie(HeaderFactory.getUserHeader(new UserHeaderContainer(container.getUuid(), container.getUser(), now)));
//	}

}
