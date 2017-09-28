package com.wyj.springboot.im.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月28日
 */
@Configuration
public class DataSourceConfig {
	
	//profiles and properties
	@Autowired
	private Environment env;
	
	//destory-method=close 是当数据库连接不使用的时候，就把该连接重新放到数据池中，方便下次使用
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc1.driver"));
		dataSource.setUrl(env.getProperty("jdbc1.url"));
		dataSource.setUsername(env.getProperty("jdbc1.username"));
		dataSource.setPassword(env.getProperty("jdbc1.password"));
		dataSource.setInitialSize(env.getProperty("jdbc1.initialSize", Integer.class));
		dataSource.setMaxActive(env.getProperty("jdbc1.maxActive", Integer.class));
		dataSource.setMaxWait(env.getProperty("jdbc1.maxWait", Long.class));
		dataSource.setValidationQuery(env.getProperty("jdbc1.validationQuery"));
		dataSource.setTestOnBorrow(env.getProperty("jdbc1.testOnBorrow", Boolean.class));
		dataSource.setTestWhileIdle(env.getProperty("jdbc1.testWhileIdle", Boolean.class));
		dataSource.setTimeBetweenEvictionRunsMillis(env.getProperty("jdbc1.timeBetweenEvictionRunsMillis", Long.class));
		return dataSource;
	}
	
}
