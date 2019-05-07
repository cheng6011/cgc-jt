package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private UserService userService;
	
	/**
	 * 前台通过jsonp形式实现跨域请求
	 */
	//http://sso.jt.com/user/check/{param}/{type}
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(@PathVariable String param,@PathVariable Integer type,String callback) {
		Boolean flag = userService.findCheckUser(param,type);
		return new JSONPObject(callback, SysResult.ok(flag));
	}
	
	//http://sso.jt.com/user/query/b64369f366ff435842649069c4bb67de?callback=jsonp1555903652937&_=1555903653078
	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(@PathVariable String token,String callback) {
		String userJSON = jedisCluster.get(token);
		return new JSONPObject(callback, SysResult.ok(userJSON));
	}
}
