package com.jt.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.Service.ItemService;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private HttpClientService httpClient;
	
	@Override
	public Item findItemById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemById/"+itemId;
		String result = httpClient.doGet(url);
		return ObjectMapperUtil.toObject(result, Item.class);
	}
	
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById/"+itemId;
		String result = httpClient.doGet(url);
		return ObjectMapperUtil.toObject(result, ItemDesc.class);
	}

}
