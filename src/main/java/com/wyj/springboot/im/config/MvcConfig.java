package com.wyj.springboot.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import com.wyj.springboot.im.authorize.AnalyzeInterceptor;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月29日
 */


@Configuration
//@EnableWebMvc
@ComponentScan(basePackages={"com.wyj.springboot.im.controller"})
//@PropertySource(value = "classpath:application.properties",  ignoreResourceNotFound = true,encoding = "UTF-8") 
public class MvcConfig extends WebMvcConfigurerAdapter{
	
	//这种方式的注入解决了 interceptor中注入bean失败的问题
	@Bean
	public AnalyzeInterceptor analyzeInterceptor() {
		return new AnalyzeInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(analyzeInterceptor())
				.addPathPatterns("/**")
//				.excludePathPatterns("/static/**")
				;
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/static/").addResourceLocations("classpath:/static/");
//		super.addResourceHandlers(registry);
//	}

}
