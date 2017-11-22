package com.wyj.springboot.im.authorize.cookie;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.authorize.cache.keymodel.UserCacheKey;
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
public class UserHeaderContainer {
	private long userId;
	private long timestamp;
	private String uuid;
	
	public UserHeaderContainer(long userId, String uuid, long timestamp) {
		this.userId = userId;
		this.uuid = uuid;
		this.timestamp = timestamp;
	}

	public long getUserId() {
		return userId;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	
	static Logger logger = LoggerFactory.getLogger(UserHeaderContainer.class);
	
	
	@Autowired
	RedisCacheManager<UserCacheKey, User> userCache;
	
	static RedisCacheManager<UserCacheKey, User> sUserCache;
	
	public UserHeaderContainer() {}
	
	@PostConstruct
	private void init() {
		sUserCache = userCache;
	}
	
	public static String generatorKey(UserHeaderContainer c) {
		if (c == null || c.userId <= 0) {
			return "";
		}

		if (c.timestamp <= 0) {
			c.timestamp = System.currentTimeMillis();
		}
		String encrytStr = "";
		try{
			encrytStr = XXTEA.encrypt(c.timestamp+":"+c.userId+":"+c.uuid);
		} catch(Exception e) {
			logger.error("加密生成UserCookieContainer的时候出错，error:"+e.getMessage()+". UserHeaderContainer:{}"+JSON.toJSONString(c), e);
		}
		
		return c.timestamp+":"+encrytStr;
	}
	
	public static UserHeaderContainer resolveUserCookie(String key) {

		UserHeaderContainer result = null;
		if (StringUtil.isEmpty(key)) return null;
		
		String[] values = key.split(":");
		try {
			if (Long.valueOf(values[0])+Constants.HEADER_USER_TOKEN_EXPIRED*1000 < System.currentTimeMillis()) {
				logger.info("user token过期");
				return null;
			}
			
			
			String[] desValues = XXTEA.decrypt(values[1]).split(":");
			//验证时间戳是否一致，是否明文或密文的时间戳被篡改
			if (!desValues[0].equals(values[0])) {
				logger.warn("token 被篡改");
				return null;
			}
			String userId = desValues[1];
			
			//如果有这条数据 这时候已经更新了服务器方面的 cache失效时间
//			User user = sUserCache.get(desValues[2].toString());
			//检查是否有该用户
//			if (user == null) {
//				logger.warn("从 cookie 中解析出来的 userid={} 在数据库中找不到", userId);
//				return null;
//			}
			result = new UserHeaderContainer();
			result.userId = Long.valueOf(userId);
			result.uuid = desValues[2];
		} catch (Exception e) {
			logger.error("解析cookie出错，"+e.getMessage(), e);
			return null;
		}
		return result;
	
	}
}
