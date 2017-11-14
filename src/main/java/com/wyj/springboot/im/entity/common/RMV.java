package com.wyj.springboot.im.entity.common;

public class RMV {
	// 公用接口
	public static final String COMMON_VALIDICON 				= "/common/validicon";
	public static final String COMMON_NATIONS 					= "/common/nations";
	public static final String COMMOON_TRADES					= "/common/trades";
	public static final String COMMON_VALIDATE_GETICONTOKEN 	= "/common/validate/geticontoken";
	public static final String COMMON_VALIDATE_GETPHONETOKEN 	= "/common/validate/getphonetoken";
	public static final String COMMON_VALIDATE_GETEMAILTOKEN 	= "/common/validate/getemailtoken";
		
	// 身份认证
	public static final String IDENTITY_CHECKAUTH 	= "/identity/checkauth";
	public static final String IDENTITY_UIDCARD 	= "/identity/uidcard";
	public static final String IDENTITY_UPASSPORT 	= "/identity/upassport";
	public static final String IDENTITY_UCOMPANY 	= "/identity/ucompany";
	public static final String IDENTITY_UPIC 		= "/identity/upic";
	public static final String IDENTITY_VIDNUMBER 	= "/identity/vidnumber";
	
	// 身份审核
	public static final String IDENTITY_REVIEW_QUERY 	= "/identity/review/query";
	public static final String IDENTITY_REVIEW_COMMIT 	= "/identity/review/commit";
	public static final String IDENTITY_REVIEW_MESSAGE 	= "/identity/review/message";
	
	// 用户授权
	public static final String USER_REGISTE_PHONE 		= "/user/register/phone";
	public static final String USER_REGISTE_EMAIL 		= "/user/register/email";
	public static final String USER_LOGIN_PHONE_CODE 	= "/user/login/phone";
	public static final String USER_LOGIN_UN_PWD 		= "/user/login/user_shixi";
	public static final String USER_REGISTE_CHECK_PHONE = "/user/register/phone/check";
	public static final String USER_REGISTE_CHECK_EMAIL = "/user/register/email/check";
	public static final String USER_SEND_PHONE_V_CODE 	= "/user/{path}/phone/sendcode";
	public static final String USER_MODIFY_PASSWORD		= "/user/password/phone/modifyPassword";
	public static final String USER_LOGOUT 				= "/user/logout";
	public static final String USER_THIRD_LOGIN_AUTHOR	= "/user/{provider}/author";
	public static final String USER_THIRD_LOGIN_BIND	= "/user/author/thirdbind";
	
	// 视频数据
	public static final String VIDEO_VINFO 			= "/video/vinfo";
	public static final String VIDEO_VLIST 			= "/video/vlist";
	public static final String VIDEO_USERVINFO 		= "/video/uservinfo";
	public static final String VIDEO_USERVLIST 		= "/video/uservlist";
	public static final String VIDEO_RELATIVE 		= "/video/relative";
	public static final String VIDEO_RECOMMEND 		= "/video/recommend";
	public static final String VIDEO_EDITINFO 		= "/video/editinfo";
	public static final String VIDEO_DELVIDEO 		= "/video/delvideo";
	public static final String VIDEO_DELREASON 		= "/video/delreason";
	public static final String VIDEO_VIDEOPRICE		= "/video/videoprice";
	public static final String VIDEO_SEARCH			= "/video/search";
	public static final String VIDEO_SEARCHHOT		= "/video/searchHot";
	public static final String VIDEO_SEARCHKEY		= "/video/searchKey";
	public static final String VIDEO_SEARCHADVICE	= "/video/searchAdvice";
	public static final String VIDEO_PLAYPROGRESS	= "/video/playprogress";
	public static final String VIDEO_VOTE			= "/video/vote";
	public static final String VIDEO_SHARETO		= "/video/shareto";
	public static final String VIDEO_MAINLIST		= "/video/mainlist";
	
	// 视频上传
	public static final String UPLOAD_UEDITOR 			= "/upload/ueditor";
	public static final String UPLOAD_UEDITORIMAGE 		= "/upload/ueditorimage";
	public static final String UPLOAD_USTART 			= "/upload/ustart";
	public static final String UPLOAD_UFILE 			= "/upload/ufile";
	public static final String UPLOAD_UINFO 			= "/upload/uinfo";
	public static final String UPLOAD_UCOMMIT 			= "/upload/ucommit";
	public static final String UPLOAD_UDELETE 			= "/upload/udelete";
	public static final String UPLOAD_UPROGRESS 		= "/upload/uprogress";
	public static final String UPLOAD_ULIST 			= "/upload/ulist";
	public static final String UPLOAD_VINFO 			= "/upload/vinfo";
	public static final String UPLOAD_VLIST 			= "/upload/vlist";
	public static final String UPLOAD_USORT 			= "/upload/usort";
	public static final String UPLOAD_USTATUS 			= "/upload/ustatus";
	public static final String UPLOAD_GETINFO 			= "/upload/getinfo";
	public static final String UPLOAD_VTAGS 			= "/upload/vtags";
	public static final String UPLOAD_VCATS 			= "/upload/vcats";
	public static final String UPLOAD_GETUPID 			= "/upload/getupid";
	public static final String UPLOAD_UREFRESH 			= "/upload/urefresh";
	public static final String UPLOAD_UHEARTBEAT 		= "/upload/uheartbeat";
	
	// 视频审核
	public static final String UPLOAD_REVIEW_QUERY 		= "/upload/review/query";
	public static final String UPLOAD_REVIEW_COMMIT 	= "/upload/review/commit";
	public static final String UPLOAD_REVIEW_CLIP 		= "/upload/review/clip";
	public static final String UPLOAD_REVIEW_UFILE 		= "/upload/review/ufile";
	public static final String UPLOAD_REVIEW_USTATUS 	= "/upload/review/ustatus";
	public static final String UPLOAD_REVIEW_EDITINFO 	= "/upload/review/editinfo";
	public static final String UPLOAD_REVIEW_MESSAGE 	= "/upload/review/message";
	
	// 用户充值
	public static final String RECHARGE_NEWTRADE 			= "/recharge/newTrade";
	public static final String RECHARGE_PAYTRADE 			= "/recharge/payTrade";
	public static final String RECHARGE_CODEPAY 			= "/recharge/codePay";
	public static final String RECHARGE_QUERYTRADE 			= "/recharge/querytrade";
	public static final String RECHARGE_ALIPAYRETURN 		= "/recharge/alipayReturn";
	public static final String RECHARGE_ALIPAYNOTIFY 		= "/recharge/alipayNotify";
	public static final String RECHARGE_WEIXINNOTIFY 		= "/recharge/weixinNotify";
	public static final String RECHARGE_RECORDS 			= "/recharge/records";
	public static final String RECHAGE_RECORD_DETAIL		= "/recharge/recordDetail";
	public static final String RECHARGE_QUERYTRADESTATUS 	= "/recharge/queryTradeStatus";
	public static final String RECHARGE_CONFIRMTRADE 		= "/recharge/confirmTrade";
	
	// 用户中心
	public static final String MEMBER_ASSETS 				= "/member/assets";
	public static final String USER_INFO 					= "/user/userinfo";
	public static final String USER_SERCURITY				= "/user/accountsecurity";
	public static final String USER_EMAIL_MODIFY			= "/user/email/modify";
	public static final String USER_PHONE_MODIFY			= "/user/phone/modify";
	public static final String USER_THIRD_BIND				= "/user/{provider}/bind";
	public static final String USER_THIRD_UNBIND			= "/user/{provider}/unbind";
	public static final String USER_UPLOAD_PROTOCOL_STATUS	= "/protocol/uploadstatus";
	public static final String USER_DETAIL_INFO				= "/user/detailInfo";
	public static final String USER_UPDATE_DETAIL_INFO		= "/user/update/detailInfo";
	public static final String USER_COMPANY_INFO			= "/user/companyInfo";
	public static final String USER_UPDATE_COMPANY_INFO		= "/user/update/companyInfo";
	
	// 交易购买
	public static final String TRADE_RECORDS 				= "/trade/records";
	public static final String TRADE_DETAIL 				= "/trade/detail";
	public static final String TRADE_INCOME					= "/trade/income";
	public static final String TRADE_VIDEOORDER 			= "/trade/videoorder";
	public static final String TRADE_PAYORDER 				= "/trade/payorder";
	public static final String TRADE_PURCHASERS 			= "/trade/purchasers";
	public static final String TRADE_REWARDDETAIL 			= "/trade/rewarddetail";
	public static final String TRADE_PAYREWARD 				= "/trade/payreward";
	public static final String TRADE_REWARDERS 				= "/trade/rewarders";
	
	// 优惠券
	public static final String COUPON_ADD 					= "/coupon/add";
	public static final String COUPON_RECORDS 				= "/coupon/records";
	public static final String COUPON_BY_VIDEO				= "/coupon/byvideo";
	
	//优惠券后台
	public static final String MANAGE_COUPON_ADD			= "/managecoupon/add";
	public static final String MANAGE_COUPON_GET			= "/managecoupon/get";
	public static final String MANAGE_COUPON_SEND			= "/managecoupon/send";
	public static final String MANAGE_COUPON_SEND_ALL		= "/managecoupon/sendall";
	
	//后台用户与权限
	public static final String MANAGER_LOGIN 				= "/manager/login";
	public static final String MANAGER_LOGINSTATUS			= "/manager/loginStatus";
	public static final String MANAGER_ADDUSER				= "/manager/addUser";
	public static final String MANAGER_LOGOUT				= "/manager/logout";
	public static final String MANAGER_MODIFY_PSWD			= "/manager/modifyPassword";
	public static final String MANAGER_MODIFY_AUTHORITY		= "/manager/modifyAuthority";
	public static final String MANAGER_BACKUSERINFO			= "/manager/getBackInfo";
	public static final String MANAGER_BACKUSERLIST			= "/manager/getBackUserList";
	
	
	// 编号生成器
	public static final String GENERATOR_ORDERNUMBER		= "/generator/ordernumber";
}
