package com.wyj.springboot.im.sockethandler.card;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月27日
 */

public class CardDesc {
	public static List<Integer> randomCard(int size) {
		List<Integer> result = new ArrayList<>();
		for (int i=0; i<size; i++) {
			result.add((int)(Math.random()*54)+1);
		}
		return result;
	}
	
	/**
	 * compare cards
	 * @param a
	 * @param b
	 * @return a > b return 1, a == b return 0, a < b return -1;
	 */
	public static int compareCard(Integer a, Integer b) {
		return a<b?-1:a>b?1:0;
	}
}
