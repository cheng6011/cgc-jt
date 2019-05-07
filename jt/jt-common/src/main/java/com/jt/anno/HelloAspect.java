package com.jt.anno;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class HelloAspect {
	/**
	 * 切入点表达式的写法
	 * within(包名.类名) 粗粒度
	 * execution(返回值类型  包名.类名.方法名(参数))
	 */
	//1.定义前台通知
	@Before("within(com.jt.controller.HelloController)")
	public void before(JoinPoint joinPoint) {
		System.out.println("前置开始!!!");
	}
	
	@Around("execution(* com.jt.controller.HelloController.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) {
		try {
			System.out.println("环绕通知开始!!!");
			joinPoint.proceed();
			System.out.println("环绕通知结束!!!");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@AfterReturning("execution(* com.jt.controller.HelloController.*(..))")
	public void afterReturning(JoinPoint joinPoint) {
		System.out.println("后置开始!!!");
	}
	
	@After("execution(* com.jt.controller.HelloController.*(..))")
	public void after(JoinPoint joinPoint) {
		System.out.println("最终开始!!!");
	}
	
	@AfterThrowing(value="execution(* com.jt.controller.HelloController.*(..))",throwing="thr")
	public void afterThrowing(JoinPoint joinPoint,Throwable thr) {
		
		System.out.println(thr.getMessage());
	}
}
