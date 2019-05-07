package com.jt.controller;

import java.lang.ProcessBuilder.Redirect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	@Reference(timeout=3000,check=false)
	private DubboUserService dubboUserService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@RequestMapping("/{modeleName}")
	public String modeleName(@PathVariable String modeleName) {
		return modeleName;
	}
	

	
	//注册中心
	//http://www.jt.com/service/user/doRegister
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			dubboUserService.saveUser(user);
		} catch (Exception e) {
			return SysResult.fail();
		}
		return SysResult.ok();
	}
	
	//单点登录中心
	//http://www.jt.com/service/user/doLogin?r=0.34739674328362513
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		try {
			//如果用户名和密码错误,token为null
			String token = dubboUserService.findUserByUP(user);
			//下行代表表示用户名和密码正确
			if(!StringUtils.isEmpty(token)) {
				//创建一个cookie对象
				Cookie cookie = new Cookie("JT_TICKET", token);
				//cookie保存的最大时间7天
				cookie.setMaxAge(7*24*60*60);
				cookie.setPath("/");//cookie的权限 jt项目中都可以访问这个cookie
				//把cookie存入浏览器中
				response.addCookie(cookie);
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		return SysResult.fail();
	}
	
	//http://www.jt.com/user/logout.html
	@RequestMapping("logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//1.获取token值
		String token = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		//2.删除redis数据
		jedisCluster.del(token);
		
		//3.删除cookie
		Cookie cookie = new Cookie("JT_TICKET", "");
		//0:立即删除    -1:关闭会话时删除
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/index.html";
	}
	
}
