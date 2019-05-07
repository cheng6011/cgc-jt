package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@RequestMapping("test/addUser")
	@ResponseBody
	public String addUser() {
		return "方法执行成功!!!";
	}
	
	@RequestMapping("test/findUser")
	@ResponseBody
	public String findUser() {
		//int a = 1/0;
		return "查询成功!!!";
	}
}
