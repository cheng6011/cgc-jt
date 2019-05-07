package com.jt.redis;
//测试redis分片技术

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestShardRedis {
	
	//@Autowired
	private ShardedJedis shard;

	@Test
	public void test01() {
		//准备list集合封装多台redis
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("192.168.88.134",6379));
		shards.add(new JedisShardInfo("192.168.88.134",6380));
		shards.add(new JedisShardInfo("192.168.88.134",6381));
		ShardedJedis jedis = new ShardedJedis(shards);
		jedis.set("1812", "分片机制成功!!!");
		System.out.println(jedis.get("1812"));
	}
	
	@Test
	public void test02() {
		shard.set("1813", "哈哈哈哈哈哈哈哈哈哈");
		System.out.println(shard.get("1813"));
	}
}
