package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.ShardedJedis;


@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;
	
	//@Autowired
	private ShardedJedis jedis;
	
	@Override
	public String findItemCatNameById(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat.getName();
	}
	@Override
	public List<EasyUITree> findItemCatList(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> selectList = itemCatMapper.selectList(queryWrapper);
		List<EasyUITree> easyUITrees = new ArrayList<>();
		for (ItemCat itemCat : selectList) {
			EasyUITree tree = new EasyUITree();
			tree.setId(itemCat.getId());
			tree.setText(itemCat.getName());
			String state = itemCat.getIsParent() ? "closed" : "open";
			tree.setState(state);
			easyUITrees.add(tree);
		}
		return easyUITrees;
	}
	@Override
	public List<EasyUITree> findItemCatCacheList(Long parentId) {
		List<EasyUITree> list = new ArrayList<>();
		
		String key = "ITEM_CAT_"+parentId;
		String result = jedis.get(key);
		if(StringUtils.isEmpty(result)) {
			list = findItemCatList(parentId);
			String json = ObjectMapperUtil.toJSON(list);
			jedis.set(key, json);
		} else {
			list = ObjectMapperUtil.toObject(result, list.getClass());
		}
		return list;
	}
	

}
