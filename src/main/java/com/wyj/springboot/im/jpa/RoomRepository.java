package com.wyj.springboot.im.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wyj.springboot.im.entity.Room;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月24日
 */

public interface RoomRepository extends JpaRepository<Room, Long>{
	
}
