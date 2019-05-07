package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@Configuration
//@ImportResource({"classpath:/spring/application-redis.xml"})
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	
	//redis集群的引入
	@Value("${redis.nodes}")
	private String nodes;
	
	@Bean
	public JedisCluster jedisCluster() {
		//redis.clients.jedis.JedisCluster.JedisCluster(Set<HostAndPort> nodes)
		Set<HostAndPort> set = new HashSet<>();
		String[] split = nodes.split(",");
		for (String s : split) {
			String[] args = s.split(":");
			int port = Integer.parseInt(args[1]);
			HostAndPort hostAndPort = new HostAndPort(args[0], port);
			set.add(hostAndPort);
		}
		return new JedisCluster(set);
	}
	
	/*
	 * @Value("${redis.host}") private String host;
	 * 
	 * @Value("${redis.port}") private Integer port;
	 */
	/*
	 * @Value("${redis.shards}") private String redisShards;
	 */
	/*
	 * @Value("${redis.hostAndpost}") private String hp;
	 * 
	 * @Value("${redis.masterName}") private String masterName;
	 * 
	 * @Bean public JedisSentinelPool jedisSentinelPool() { Set<String> sentinels =
	 * new HashSet<>(); sentinels.add(hp); return new JedisSentinelPool(masterName,
	 * sentinels); }
	 */
	
	//@Bean//将返回值对象交给spring容器管理
	/*
	 * public Jedis jedis() { return new Jedis(host, port); }
	 */
	
	/*
	 * @Bean public ShardedJedis shardedJedis() { List<JedisShardInfo> shards = new
	 * ArrayList<JedisShardInfo>(); String[] nodes = redisShards.split(","); for
	 * (String node : nodes) { String[] host_port = node.split(":"); JedisShardInfo
	 * jedisShardInfo = new JedisShardInfo(host_port[0], host_port[1]);
	 * shards.add(jedisShardInfo); } return new ShardedJedis(shards); }
	 */
}
