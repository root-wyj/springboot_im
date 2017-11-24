package com.wyj.springboot.im.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月28日
 */
@Entity(name="user")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;

	public User() {}
	
	public User(Builder builder) {
		this.id = builder.id;
		this.username = builder.username;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public static class Builder{
		Long id;
		String username;
		
		public Builder() {}
		
		public Builder setId(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
		
	}
}
