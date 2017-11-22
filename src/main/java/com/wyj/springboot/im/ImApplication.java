package com.wyj.springboot.im;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImApplication {

	private static Logger logger = LoggerFactory.getLogger(ImApplication.class);
	
	public static void main(String[] args) throws IOException {
		SpringApplication app = new SpringApplication(ImApplication.class);
		app.run(args);
		logger.info("springboot 程序启动成功！！");
	}

}
