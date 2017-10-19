package com.wyj.springboot.im;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImApplication {

	private static Logger logger = LoggerFactory.getLogger(ImApplication.class);
	
	public static void main(String[] args) throws IOException {
		
		Properties pro = new Properties();
//		InputStream isRedis = ImApplication.class.getClassLoader().getResourceAsStream("redis.properties");
		InputStream isSys = ImApplication.class.getClassLoader().getResourceAsStream("application.properties");
		pro.load(isSys);
//		pro.load(isRedis);
		
		logger.info("properties:"+pro.keySet().toString());
		
		SpringApplication app = new SpringApplication(ImApplication.class);
		app.setDefaultProperties(pro);
		
		app.run(args);
	}
}
