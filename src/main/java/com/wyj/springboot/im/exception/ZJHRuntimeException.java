package com.wyj.springboot.im.exception;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月22日
 */

public class ZJHRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ZJHRuntimeException() {
		super();
	}
	
	public ZJHRuntimeException(String message) {
		super(message);
	}
	
	public ZJHRuntimeException(Throwable throwable) {
		super(throwable);
	}
	
	public ZJHRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
