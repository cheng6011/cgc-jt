package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.anno.CaCheAnnotation;
import com.jt.anno.CaCheAnnotation.CACHE_TYPE;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@Controller
@RequestMapping("/item/")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	/**
	 * 关于框架编码的说明:
	 * 1.使用旧的ssm框架时,如果返回数据为String,则将数据听过@ResponseBody返回时,采用ISO-8859-1编码格式
	 * 所以返回数据必定乱码
	 * 2,如果返回数据为对象时(pojo/vo),采用UTF-8格式编码
	 * 当springBoot时,返回的数据都是UTF-8
	 * 
	 * 低版本出现处理post乱码问题:produces="text/html;charset=utf-8"
	 * 
	 * @param itemCatId
	 * @return item/cat/queryItemName
	 */
	@RequestMapping(value="cat/queryItemName")
	@ResponseBody
	public String findItemCatNameById(Long itemCatId) {
		return itemCatService.findItemCatNameById(itemCatId);
	}
	
	//http://localhost:8091/item/cat/list
	@RequestMapping("cat/list")
	@ResponseBody
	@CaCheAnnotation(index=0,cacheType=CACHE_TYPE.FIND)
	public List<EasyUITree> findItemCatList(@RequestParam(value="id",defaultValue="0")Long parentId) {
		//减少代码的耦合性
		//return itemCatService.findItemCatCacheList(parentId);
		return itemCatService.findItemCatList(parentId);
	}
	
}
