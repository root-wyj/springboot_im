package com.wyj.springboot.im.authorize.cache;


/**
 * 如果缓存的是验证码这类的，那么用RedisCacheManager 和 LongRedisCacheManager就足够了
 * 
 * 如果是缓存的数据库，一般的使用@Cache 系列Annotation就可以了
 * 
 * 但是如果，数据库的数据结构和系统中的数据结构不一致的话，可能会需要单独写一个两个格式之间转换的 也就是这个类了
 * 
 * @author wuyingjie
 * @date 2017年11月10日
 */

public class UserCacheManager {
/*
	private static Logger logger = LoggerFactory.getLogger(UserRedisCacheManager.class);
	
	public UserRedisCacheManager(long timeout, KeySerilizable<AUUserCacheKeyModel> keySerilizable) {
		super(timeout, keySerilizable);
	}
	
	@Autowired
	UserWebStateService userWebStateService;
	
	@Override
	public void set(AUUserCacheKeyModel key, AUUser value) {
		
		if (key == null || StringUtil.isEmpty(key.getUuid()) || value==null
				|| value.getUserId() != key.getUserId()) return ;
		updateDBState(key);
		super.set(key, value);
	}

	@Override
	public AUUser get(AUUserCacheKeyModel key) {
		AUUser u = super.getAndUpdateTTL(key);
		if (u == null) {
			u = getUserFromDAO(key.getUserId(), key.getUuid());
			if (u != null) {
				super.set(key, u);
			}
		}
		
		if (u != null) {
			updateDBState(key);
		}
		
		return u;
	}
	
	@Override
	public void del(AUUserCacheKeyModel key) {
		userWebStateService.deleteWebState(key.getUserId(), serilizableKey(key));
		super.del(key);
	}
	
	private void updateDBState(AUUserCacheKeyModel key) {
		userWebStateService.updateWebState(key.getUserId(), serilizableKey(key));
	}
	
	@Autowired
	UserService userService;
	private AUUser getUserFromDAO(long userId, String uuid) {
		logger.debug("访问数据库获取user, userId:{}, uuid:{}", userId, uuid);
		AUUserWebLoginState webLoginState = userWebStateService.getWebState(userId);
		if (webLoginState == null) {
			logger.debug("数据库中不存在这条数据");
			return null;
		}
		Integer timestamp = webLoginState.getLoginInfo().get(uuid);
		if (timestamp != null
				&& timestamp + Constants.COOKIE_USER_EXPIRED > System.currentTimeMillis()/1000	//保证 万一有某条记录程序没有清除，防止下次检测时 还是登录状态
				) {	
			logger.debug("成功从数据库中获取user userid[{}]", userId);
			return userService.getAUUserSimple(userId);
		} else {
			logger.debug("数据库中不存在这条数据, timestamp:{}", timestamp);
			return null;
		}
	}
 */
}
