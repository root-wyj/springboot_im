package com.wyj.springboot.im.sockethandler.room;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月27日
 */

public class RoomMessage {
	public static enum TYPE {
		GAME_START("start"), YAZHU("yazhu"), GIVE_UP("giveup"), END("end");
		
		private String str;
		
		private TYPE(String str) {
			this.str = str;
		}
		
		public String getStr() {
			return str;
		}
	
	}
}
