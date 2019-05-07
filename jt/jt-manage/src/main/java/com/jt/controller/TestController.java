package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.ShardedJedis;

@RestController
public class TestController {

	//@Autowired
	private ShardedJedis shards;
	
	@RequestMapping("getMsg")
	public String getMsg() {
		return "我的端口是8093";
	}
	
	@RequestMapping("/test/shards")
	public String shards() {
		shards.set("1813", "哈哈哈哈哈");
		return shards.get("1813");
	}
}
