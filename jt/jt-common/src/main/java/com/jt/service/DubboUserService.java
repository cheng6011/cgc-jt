package com.jt.service;

import com.jt.pojo.User;

public interface DubboUserService {

	void saveUser(User user);
	//dubbo接口实现单点登录
	String findUserByUP(User user);
	
	void deleteRedis(String token);

}
