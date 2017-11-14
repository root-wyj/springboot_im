package com.wyj.springboot.im.entity.common;

public class Constants {
	// 上传文件路径
	public static final String UTF8 = "utf-8";
	
	/**
	 * user cookie 的过期时间 单位 s <br>
	 * 两个地方需要用到，一是cookie，二是cache，客户端和服务器端的数据要同步。cache是在配置文件中修改的。
	 * 还有一个就是方式loginstate数据有误，检测数据有效性时，也会用到
	 */
	public static final int COOKIE_USER_EXPIRED = 20*60;
		
	/**
	 * 图片验证码的 最大存活时间 单位 s
	 */
	public static final int COOKIE_VALID_ICON_MAX_LIVE = 5*60;
	
	/**
	 * 图片验证码的 最长不使用导致过期的时间 单位s
	 */
	public static final int COOKIE_VALID_ICON_IDLE_EXPIRED = 3*60;
	
	/**
	 * user cache 用户登录状态 的最小刷新间隔 单位 ms
	 */
	public static final int USER_CACHE_DB_REFRESH = 1*60*1000;
	
	/**
	 * 邮件验证码过期时间 单位s
	 */
	public static final int EMAIL_CODE_EXPIRED = 5*60;
	
	/**
	 * 图片验证码的过期时间(新) 单位s
	 */
	public static final int ICON_CODE_EXPIRED = 90;
	
	/**
	 * 验证过程中返回的token的过期时间 单位s
	 */
	public static final int VALIDATE_TOKEN_EXPIRED = 5*60;
	
	/**
	 * 每天每个用户最多输入密码错误次数
	 */
	public static final int DIALY_USER_LOGIN_PSWD_ERROR = 3;
	
	/**
	 * 发送邮箱验证码的内容
	 * 请使用String.format(Constants.EMAIL_MSG_SEND_CODE, email|nickname:String, forWhat:String, code:String); 生成信息 
	 */
	public static final String EMAIL_MSG_SEND_CODE = "Dear, %1$s<br><br>%2$s<span style='font-weight:bold;font-size:18px;color:#3897e4;'>%3$s</span> 请在"+EMAIL_CODE_EXPIRED/60+"分钟内输入验证码进行下一步操作。<br><br>如果您未做过更改并认为有"
			+ "人未经授权访问了您的账户，请尽快前往 <span style='color:#3897e4;'>http://ayou.docin.com/myself/safe</span> 修改您的安全信息。<br><br>此致<br><br>哎呦支持";
	
}
