package com.wyj.springboot.im;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.wyj.springboot.im.socketnio.NettySocketServer;

@SpringBootApplication
public class ImApplication {

	private static Logger logger = LoggerFactory.getLogger(ImApplication.class);
	
	@Value("${nss.server.host}")
	private String host;
	@Value("${nss.server.port}")
	private Integer port;
	
	public static void main(String[] args) throws IOException {
		
		Properties pro = new Properties();
//		InputStream isRedis = ImApplication.class.getClassLoader().getResourceAsStream("redis.properties");
//		InputStream isSys = ImApplication.class.getClassLoader().getResourceAsStream("application.properties");
//		pro.load(isSys);
//		pro.load(isRedis);
		
		logger.info("properties:"+pro.keySet().toString());
		
		SpringApplication app = new SpringApplication(ImApplication.class);
//		app.setDefaultProperties(pro);
		
		
		
		app.run(args);
	}
	
	
	@Bean  
    public SpringAnnotationScanner springAnnotationScanner(NettySocketServer socketServer) { 
		logger.info("host:"+host+", port:"+port);
        return new SpringAnnotationScanner(socketServer.getServer());  
    } 

//	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean reg = new ServletRegistrationBean(dispatcherServlet);
		reg.getUrlMappings().clear();
		reg.addUrlMappings("/api/**");
		return reg;
	}
}
