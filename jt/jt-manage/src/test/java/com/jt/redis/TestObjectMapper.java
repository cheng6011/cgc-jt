package com.jt.redis;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemCat;

public class TestObjectMapper {

	//测试1:将java对象转化为json串
	@Test
	public void testToJSON() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ItemCat itemCat = new ItemCat();
		itemCat.setId(1000L).setName("测试").setParentId(2000);
		//将对象转化为json串,必须调用对象的get/set方法
		String json = mapper.writeValueAsString(itemCat);
		System.out.println(json);
		//将json转化为对象
		ItemCat item = mapper.readValue(json, ItemCat.class);
		System.out.println(item);
	}
	
	//转化为list集合数据
	@Test
	public void testList() throws IOException {
		ArrayList<ItemCat> catList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			ItemCat itemCat = new ItemCat();
			itemCat.setId(100L+i).setName("测试"+i).setParentId(1000+i);
			catList.add(itemCat);
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(catList);
		System.err.println(json);
		
		ArrayList list = mapper.readValue(json, catList.getClass());
		System.err.println(list);
	}
}
