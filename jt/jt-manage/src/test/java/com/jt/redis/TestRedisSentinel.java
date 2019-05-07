package com.jt.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestRedisSentinel {
	
	@Test
	public void test() {
		String hostAndPort = new HostAndPort("192.168.88.134", 26379).toString();
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.88.134:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis resource = pool.getResource();
		resource.set("1812", "哈哈哈哈哈哈哈");
		System.out.println(resource.get("1812"));
		pool.close();
	}
}
