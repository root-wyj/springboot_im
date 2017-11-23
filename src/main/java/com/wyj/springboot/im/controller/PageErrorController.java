package com.wyj.springboot.im.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyj.springboot.im.entity.common.ResponseBean;

/**
 * 以下是系统的默认处理方式，我们这里实现ErrorController来自己处理这种错误
 * {
  "timestamp": 1511402039129,
  "status": 404,
  "error": "Not Found",
  "message": "/user/test",
  "path": "/user/test"
	}
 * 
 * @author wuyingjie
 * @date 2017年11月23日
 */

@RestController
public class PageErrorController implements ErrorController{

	private static final String ERROR_PATH = "/error";
	
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
	
	
	@RequestMapping(ERROR_PATH)
	public String handleError() {
		return ResponseBean.crtFailureResult("");
	}

}
