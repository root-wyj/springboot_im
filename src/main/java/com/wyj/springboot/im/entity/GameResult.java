package com.wyj.springboot.im.entity;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月28日
 */

public class GameResult {
	private String username;
	private int cost;
	private Integer card;
	
	public GameResult(String username, int cost, Integer card) {
		this.username = username;
		this.cost = cost;
		this.card = card;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}

	public Integer getCard() {
		return card;
	}

	public void setCard(Integer card) {
		this.card = card;
	}
	
	
	
}
