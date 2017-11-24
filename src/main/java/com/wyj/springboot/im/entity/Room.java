package com.wyj.springboot.im.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月24日
 */

@Entity(name="room")
public class Room {
	@Id
	@GeneratedValue
	@Column(name="room_id")
	private Long roomId;
	
	@Column(name="play_info")
	private String info;
	
	@Column(name="room_status")
	private Integer status=0;
	
	@Column(name="play_counts")
	private Integer playCounts=0;
	
	@Column(name="room_owner")
	private Long roomOwner;
	
	@Column(name="operate_type")
	private Integer operateType;
	
	@Column(name="operate_user")
	private Long operateUser;

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPlayCounts() {
		return playCounts;
	}

	public void setPlayCounts(Integer playCounts) {
		this.playCounts = playCounts;
	}

	public Long getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(Long roomOwner) {
		this.roomOwner = roomOwner;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Long getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(Long operateUser) {
		this.operateUser = operateUser;
	}
	
	
}
