package com.wyj.springboot.im.entity.common;

import com.alibaba.fastjson.JSONObject;

public class ResponseBean {
	
	public static ResponseBean crtSuccessBean() {
		return new ResponseBean(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMsg());
	}

	public static ResponseBean crtParameterErrorBean() {
		return new ResponseBean(RespCode.PARAMETER_ERROR.getCode(),
				RespCode.PARAMETER_ERROR.getMsg(), null);
	}
	
	public static ResponseBean crtFailureBean(String msg) {
		return new ResponseBean(RespCode.FAILURE.getCode(), msg, null);
	}
	
	public static ResponseBean crtFailureBean() {
		return new ResponseBean(RespCode.FAILURE.getCode(), RespCode.FAILURE.getMsg(), null);
	}
	
	public static ResponseBean crtServerErrorBean() {
		return new ResponseBean(RespCode.SERVER_ERROR.getCode(),
				RespCode.SERVER_ERROR.getMsg(), null);
	}
	
	public static ResponseBean crtResponseBean(String code, String message, Object body){
		return new ResponseBean(code, message, body);
	}
	
	public static ResponseBean crtResponseBean(String code, String message) {
		return new ResponseBean(code, message, null);
	}
	
	public static String crtSuccessResult() {
		return crtSuccessBean().toString();
	}
	
	public static String crtParameterErrorResult() {
		return crtParameterErrorBean().toString();
	}
	
	public static String crtFailureResult(String msg) {
		return crtFailureBean(msg).toString();
	}
	
	private String code;
	private String msg;
	private Object content;
	
	private ResponseBean(String code, String message, Object content) {
		this.code = code;
		this.msg = message;
		this.content = content;
	}
	
	private ResponseBean(String code, String message) {
		this.code = code;
		this.msg = message;
	}
	
	public void setRespCode(RespCode respCode) {
		this.code = respCode.getCode();
		this.msg = respCode.getMsg();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return msg;
	}

	public void setMessage(String message) {
		this.msg = message;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		JSONObject body = new JSONObject();
		body.put(DataKeys.CODE, code);
		body.put(DataKeys.MSG, msg);
		if (content != null) {
			body.put(DataKeys.CONTENT, this.getContent());
		}
		return body.toString();
	}
	
}
