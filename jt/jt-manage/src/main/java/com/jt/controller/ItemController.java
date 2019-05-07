package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIList;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//http://localhost:8091/item/query/1/20
	@RequestMapping("query")
	public EasyUIList findItemByPage(Integer page,Integer rows) {
		EasyUIList list = itemService.findItemByPage(page,rows);
		 return list;
	}
	@RequestMapping("save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//http://localhost:8091/item/instock
	@RequestMapping("instock")
	public SysResult instock(Long[] ids) {
		int status = 2;
		try {
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	@RequestMapping("reshelf")
	public SysResult reshelf(Long[] ids) {
		int status = 1;
		try {
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//http://localhost:8091/item/delete
	
	@RequestMapping("delete") 
	public SysResult deleteItem(Long[] ids) {
		try {
			itemService.deleteItems(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//http://localhost:8091/item/update
	@RequestMapping("update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.updateItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//http://localhost:8091/item/query/item/desc/14743919
	@RequestMapping("query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			System.out.println(itemDesc);
			return SysResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//
	
}
