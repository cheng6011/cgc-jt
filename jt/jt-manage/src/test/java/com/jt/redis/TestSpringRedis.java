package com.jt.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringRedis {

	@Autowired
	private ShardedJedis jedis;
	
	@Test
	public void testSet() {
		jedis.set("aa", "springRedis测试成功");
		System.out.println(jedis.get("aa"));
	}
	
	@Test
	public void test() {
		jedis.set("bb", "5646+4654654654564654565465");
		System.out.println(jedis.get("bb"));
	}
}
