package com.jt.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 该工具类解决对象转化中的try-cache问题
 * 1.对象转JSON toJSON
 * 2.JSON转对象  toObject
 * @author tedu
 *
 */
public class  ObjectMapperUtil {
	//定义成员变量时不允许修改//是否有线程安全问题???  没有线程问题
	private static final ObjectMapper mapper =  new ObjectMapper();
	public static String toJSON(Object obj) {
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	
	public static <T>T toObject(String json,Class<T> cls) {
		T t = null;
		try {
			t = mapper.readValue(json, cls);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}
}
