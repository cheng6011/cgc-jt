package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

/**
 *	核心:获取用户的访问数据/路径 request/response
 * @author tedu
 *
 */
@Component
public class UserItercepter implements HandlerInterceptor{

	@Autowired
	private JedisCluster jedisCluster;
	
	//完成用户效验,如果没有登录,则跳转用户登录页面,如果用户已经登录则放行
	//boolean:      true:放行           false:拦截,重定向
	/**
	 * 用户拦截实现步骤:
	 * 1.首先获取用户的cookie数据
	 * 2.判断用户是否已经登录
	 * 		如果用户没有登录,则重定向用户登录页面
	 * 		如果用户已经登录,则判断redis中是有数据
	 * 						有:表示用户之前登录成功给与放行
	 * 						没有:表示用户登录失败,之后重定向到登录页面.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		if(!StringUtils.isEmpty(token)) {
			//判断redis中是否有数据
			String userJSON = jedisCluster.get(token);
			if(!StringUtils.isEmpty(userJSON)) {
				//程序执行到这里表示用户已经登录
					//把当前用户数据发送到Controller层
				User user = ObjectMapperUtil.toObject(userJSON, User.class);
					//request.setAttribute("JT_TICKET", user);
				UserThreadLocal.set(user);
				return true;
			}
		}
		response.sendRedirect("/user/login.html");
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//删除线程
		UserThreadLocal.remove();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
