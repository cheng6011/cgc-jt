package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.anno.CaCheAnnotation;
import com.jt.anno.CaCheAnnotation.CACHE_TYPE;
import com.jt.service.SentinelService;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Component //交给spring管理
@Aspect    //切面
public class CacheAspect {

	@Autowired
	private JedisCluster shards;

	@Around("@annotation(caCheAnno)")
	public Object around(ProceedingJoinPoint joinPoint,CaCheAnnotation caCheAnno) {
		String key = getKey(joinPoint,caCheAnno);
		Object value = getValue(joinPoint,caCheAnno,key);
		return value;
	}

	private Object getValue(ProceedingJoinPoint joinPoint, CaCheAnnotation caCheAnno, String key) {
		CACHE_TYPE cacheType = caCheAnno.cacheType();
		Object value = null;
		switch (cacheType) {
		case FIND:
			value = findCaChe(joinPoint,caCheAnno,key); 
			break;

		case UPDATE:
			value = updateCaChe(joinPoint,caCheAnno,key);
			break;
		}
		return value;
	}

	private String getKey(ProceedingJoinPoint joinPoint, CaCheAnnotation caCheAnno) {
		//获取包名
		String typeName = joinPoint.getSignature().getDeclaringTypeName();
		//获取方法名
		String methorName = joinPoint.getSignature().getName();
		//获取参数
		MethodSignature method = (MethodSignature) joinPoint.getSignature();
		String[] parameterNames = method.getParameterNames();
		//获取参数的下标
		String param = parameterNames[caCheAnno.index()];
		Object args = joinPoint.getArgs()[caCheAnno.index()];
		//合拼成包名.类名.方法名.下标
		String key = typeName+"."+methorName+"."+param+"."+args;
		//System.err.println(key);
		return key;
	}

	private Object updateCaChe(ProceedingJoinPoint joinPoint, CaCheAnnotation caCheAnno, String key) {
		try {
			shards.del(key);
			joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object findCaChe(ProceedingJoinPoint joinPoint, CaCheAnnotation caCheAnno, String key) {
		String value = shards.get(key);
		Object obj = null;
		try {
			if(StringUtils.isEmpty(value)) {
				obj = joinPoint.proceed();
				String json = ObjectMapperUtil.toJSON(obj);
				shards.set(key, json);
				System.out.println("数据库查询!!!");
			} else {
				String val = shards.get(key);
				MethodSignature method = (MethodSignature) joinPoint.getSignature();
				Class<?> returnType = method.getReturnType();
				obj = ObjectMapperUtil.toObject(val, returnType);
				System.out.println("redis缓存查找!!!");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return new RuntimeException();
		}
		return obj;
	}


}
