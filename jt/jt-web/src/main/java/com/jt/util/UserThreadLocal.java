package com.jt.util;
/**
 * 该对象保存的是用户信息
 * @author tedu
 *
 */

import com.jt.pojo.User;

public class UserThreadLocal {
	//JVM直接创建
	private static ThreadLocal<User> thread = new ThreadLocal<>();
	
	public static void set(User user) {
		thread.set(user);
	}
	
	public static User get() {
		return thread.get();
	}
	
	//使用threadLocal是必须注意内存泄漏
	public static void remove() {
		thread.remove();
	}
}
