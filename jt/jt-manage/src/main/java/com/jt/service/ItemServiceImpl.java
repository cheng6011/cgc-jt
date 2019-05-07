package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIList;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUIList findItemByPage(Integer page, Integer rows) {
		int total = itemMapper.selectCount(null);
		int start = (page-1)*rows;
		List<Item> list = itemMapper.findItemListByPage(start,rows);
		return new EasyUIList(total, list);
	}

	@Override
	@Transactional
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		itemDesc.setItemId(item.getId())
			.setCreated(new Date())
			.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	@Transactional
	public void updateStatus(Long[] ids, int status) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
		updateWrapper.in("id", Arrays.asList(ids));
		itemMapper.update(item, updateWrapper);
	}

	@Override
	@Transactional
	public void deleteItems(Long[] ids) {
		itemMapper.deleteBatchIds(Arrays.asList(ids));
		itemDescMapper.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	@Transactional
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setCreated(new Date());
		itemMapper.updateById(item);
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateById(itemDesc);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}

	@Override
	public Item findItemById(Long itemId) {
		return itemMapper.selectById(itemId);
	}
	

}
