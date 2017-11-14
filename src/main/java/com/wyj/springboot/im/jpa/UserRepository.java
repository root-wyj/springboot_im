package com.wyj.springboot.im.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wyj.springboot.im.entity.User;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月28日
 */

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByName(String name);

	String findPasswordByName(String name);
	
	@Query("from User u where u.name=:name and u.password=:password")
	User findUser(@Param("name")String name, @Param("password")String password);
}
