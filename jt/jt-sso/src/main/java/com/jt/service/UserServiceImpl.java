package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public Boolean findCheckUser(String param, Integer type) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		String column = null;
		switch (type) {
		case 1:
			column = "username";
			break;

		case 2:
			column = "phone";
			break;
			
		case 3:
			column = "email";
			break;
		}
		queryWrapper.eq(column, param);
		int count = userMapper.selectCount(queryWrapper);
		Boolean flag = count==0 ? false : true;
		return flag;
	}
	
	
	
}
