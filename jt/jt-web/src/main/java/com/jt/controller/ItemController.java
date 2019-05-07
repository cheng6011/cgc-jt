package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.Service.ItemService;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.pojo.ItemDesc;

@Controller
@RequestMapping("/item/")
public class ItemController {
	
	@Autowired 
	private ItemService itemService;
	
	@RequestMapping("{itemId}")
	public String findItemById(@PathVariable Long itemId,Model model) {
		Item item= itemService.findItemById(itemId);
		ItemDesc itemDesc= itemService.findItemDescById(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
	
}
