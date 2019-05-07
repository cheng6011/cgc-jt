package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

//jt-sso服务的提供者,实现指定的接口
@Service
public class DubboUserServiceImpl implements DubboUserService {

	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void saveUser(User user) {
		//加密算法md5,加密密码
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		//把加密的密码封装到user对象
		user.setPassword(md5Pass);
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		//System.out.println(user);
		//对数据表进行插入处理
		userMapper.insert(user);
	}

	/**
	 * 步骤:
	 * 1.根据用户名和密码查询数据库
	 * 2.生成密钥token串 md5
	 * 3.把用户对象转化为json
	 * 4.将数据保存到redis
	 */
	@Override
	public String findUserByUP(User user) {
		String md5pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", user.getUsername())
					.eq("password", md5pass);
		User userDB = userMapper.selectOne(queryWrapper);
		//3.判断用户是否正确
		if(userDB==null) {
			return null;
		}
		//4.程序执行到这里,表示用户名和密码正确
		String token = "JT_TICKET"+System.currentTimeMillis()+user.getUsername();
		token = DigestUtils.md5DigestAsHex(token.getBytes());
		//必须进行脱敏处理
		userDB.setPassword("*******");
		String userJSON = ObjectMapperUtil.toJSON(userDB);
		//把数据存入redis
		jedisCluster.setex(token, 7*24*3600, userJSON);
		return token;
	}

	@Override
	public void deleteRedis(String token) {
		System.out.println();
		jedisCluster.del(token);
	}
	
}
