package com.wyj.springboot.im.authorize.cookie;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.config.Constants;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.tools.StringUtil;
import com.wyj.springboot.im.tools.XXTEA;

/**
 * 
 * @author wuyingjie
 * @date 2017年7月28日
 */
@Component
public class UserCookieContainer{
	private String uuid;
	private User user;
	private long timestamp;
	
	public UserCookieContainer(String uuid, User user, long timestamp) {
		this.uuid = uuid;
		this.user = user;
		this.timestamp = timestamp;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public User getUser() {
		return user;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	
	static Logger logger = LoggerFactory.getLogger(UserCookieContainer.class);
	
	
	@Autowired
	RedisCacheManager<String, User> userCache;
	
	static RedisCacheManager<String, User> sUserCache;
	
	public UserCookieContainer() {}
	
	@PostConstruct
	private void init() {
		sUserCache = userCache;
	}
	
	public static String generatorKey(UserCookieContainer c) {
		if (c == null || StringUtil.isEmpty(c.uuid) || c.user == null || c.user.getId() <= 0) {
			return "";
		}
		
		long timestamp = System.currentTimeMillis();
		String encrytStr = "";
		try{
			encrytStr = XXTEA.encrypt(timestamp+":"+c.user.getId()+":"+c.uuid);
		} catch(Exception e) {
			logger.error("加密生成UserCookieContainer的时候出错，error:"+e.getMessage()+". UserCookieContainer:{}"+JSON.toJSONString(c), e);
		}
		
		return timestamp+":"+encrytStr;
	}
	
	public static UserCookieContainer resolveUserCookie(String key) {

		UserCookieContainer result = null;
		if (StringUtil.isEmpty(key)) return null;
		
		String[] values = key.split(":");
		try {
			if (Long.valueOf(values[0])+Constants.COOKIE_USER_EXPIRED*1000 < System.currentTimeMillis()) {
				logger.info("cookie过期");
				return null;
			}
			
			
			String[] desValues = XXTEA.decrypt(values[1]).split(":");
			//验证时间戳是否一致，是否明文或密文的时间戳被篡改
			if (!desValues[0].equals(values[0])) {
				logger.warn("cookie 被篡改");
				return null;
			}
			String userId = desValues[1];
			
			//如果有这条数据 这时候已经更新了服务器方面的 cache失效时间
			User user = sUserCache.get(desValues[2].toString());
			//检查是否有该用户
			if (user == null) {
				logger.warn("从 cookie 中解析出来的 userid={} 在数据库中找不到", userId);
				return null;
			}
			result = new UserCookieContainer(desValues[2], user, StringUtil.toLong(values[0], 0L));
		} catch (Exception e) {
			logger.error("解析cookie出错，"+e.getMessage(), e);
			return null;
		}
		return result;
	
	}
}
