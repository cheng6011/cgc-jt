package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class SentinelService {

	@Autowired(required=false)
	private JedisSentinelPool pool;
	
	public void set(String key,String value) {
		Jedis jedis = pool.getResource();
		jedis.set(key, value);
		jedis.close();
	}
	
	public String get(String key) {
		Jedis redis = pool.getResource();
		String result = redis.get(key);
		redis.close();
		return result;
	}
	
	public void del(String key) {
		Jedis redis = pool.getResource();
		redis.del(key);
		redis.close();
	}
}
