package com.wyj.springboot.im.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wyj.springboot.im.entity.common.ResponseBean;
import com.wyj.springboot.im.exception.ZJHRuntimeException;

/**
 * 该类主要针对系统中（猜测->确切的说应该是Controller中）抛出异常时的处理情况
 * 
 * 但是并不能处理404这种错误
 * 
 * @author wuyingjie
 * @date 2017年11月23日
 */

@RestControllerAdvice
public class ExceptionHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public String handle(Exception e) {
		
		logger.error("统一异常处理ExceptionHandler捕获到异常, msg:"+e.getMessage(), e);
		
		if (e instanceof ZJHRuntimeException) {
			return ResponseBean.crtFailureResult(e.getMessage());
		} else {
			return ResponseBean.crtFailureResult("未知错误");
		}
		
	}

}
