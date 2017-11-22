package com.wyj.springboot.im.exception;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月22日
 */

public class ZJHException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ZJHException() {
		super();
	}
	
	public ZJHException(String message) {
		super(message);
	}
	
	public ZJHException(Throwable throwable) {
		super(throwable);
	}
	
	public ZJHException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
