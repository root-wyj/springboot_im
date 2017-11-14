package com.wyj.springboot.im.entity.common;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月14日
 */

public enum RespCode {

	SUCCESS("0", "ok"),
	//也包括 通常业务逻辑处理出错
	FAILURE("-1", "error"),
	
	//10-99 公共错误
	PARAMETER_ERROR("10", "参数错误"),
	SERVER_ERROR("11", "服务器出现错误"),
	
	//100-999 http 请求出现错误
	//100-199
	
	//1000-9999 socket 请求出现错误
	
	
	;
	
	private String code;
	private String msg;
	
	private RespCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}
	
}
