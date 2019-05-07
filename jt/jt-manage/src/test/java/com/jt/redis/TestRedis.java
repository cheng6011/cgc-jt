package com.jt.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class TestRedis {

	//如果需要从容器中获取数据时,才使用spring的测试方法
	//1.实现字符串操作
	@Test
	public void testString() throws InterruptedException {
		Jedis jedis = new Jedis("192.168.88.134",6379);
		jedis.set("1812", "tomcat猫!!");
		
		System.out.println(jedis.get("1812"));
		jedis.del("aa");
		//设定超时时间
		jedis.expire("1812", 30);
		Thread.sleep(4000);
		System.out.println("key还能存活多久:"+jedis.ttl("1812"));
	}
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.88.134",6379);
		jedis.lpush("list", "1","2","3","4","5");
		System.out.println(jedis.rpop("list"));
	}
	@Test
	public void testTX() {
		Jedis jedis = new Jedis("192.168.88.134",6379);
		Transaction multi = jedis.multi();
		try {
			multi.set("qq", "123131");
			multi.set("ww", "12121212");
			int a = 1/0;
			multi.exec();
		} catch (Exception e) {
			e.printStackTrace();
			multi.discard();
		}
		
	}
}
