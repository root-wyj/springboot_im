package com.wyj.springboot.im.authorize;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月29日
 */

@SuppressWarnings("unchecked")
public class ThreadContext<T> {
	static final ThreadLocal<Map<String, Object>> curr = new ThreadLocal<>();
	
	private String getKeyPrefix() {
		return this.getClass().getName()+"_";
	}
	
	protected T get() {
		return get("");
	}
	
	protected T get(String key) {
		Map<String, Object> currMap = curr.get();
		if (currMap == null || currMap.isEmpty()) {
			return null;
		} else {
			Object o = currMap.get(getKeyPrefix()+key);
			return o==null?null:(T)o;
		}
	}
	
	protected final void save(T value) {
		save("", value);
	}
	
	protected final void save(String key, T value) {
		Map<String, Object> currMap = curr.get();
		if (currMap == null) {
			currMap = new HashMap<>();
		}
		
		currMap.put(getKeyPrefix()+key, value);
		curr.set(currMap);
	}
	
	protected final T remove() {
		return remove("");
	}
	
	protected final T remove(String key) {
		T result = null;
		Map<String, Object> currMap = curr.get();
		if (currMap != null) {
			Object o = currMap.remove(getKeyPrefix()+key);
			if (o != null) {
				result = (T)o;
				curr.set(currMap);
			}
			if (currMap.isEmpty()) {
				curr.remove();
			}
		}
		return result;
	}
	
	
	private volatile static int count = 0;
	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				ThreadContext<Integer> threadContext = new ThreadContext<Integer>();
				int i = (int)(Math.random()*10+1);
				String threadName = Thread.currentThread().getName();
				
				threadContext.save(i);
				System.out.println(threadName+" save "+i);
				
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int value = threadContext.get();
				System.out.println(threadName+" get "+value+", current i:"+i);
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				threadContext.remove();
				System.out.println(threadName+" remove "+value+", current i:"+i);
				
				count ++;
			}
		};
		
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
		
		while(count < 3) {
			
		}
		System.out.println("main thread end");
	}
	
}
