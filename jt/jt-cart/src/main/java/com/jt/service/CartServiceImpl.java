package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartList(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		List<Cart> cartList = cartMapper.selectList(queryWrapper);
		return cartList;
	}

	@Override
	@Transactional
	public void updateCartNum(Cart cart) {
		Cart cartDB = new Cart();
		cartDB.setNum(cart.getNum()).setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId())
					.eq("item_id", cart.getItemId());
		cartMapper.update(cartDB, updateWrapper);
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional
	public void saveCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("item_id", cart.getItemId())
					.eq("user_id", cart.getUserId());
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if(cartDB==null) {
			cart.setCreated(new Date())
				.setUpdated(cart.getUpdated());
			cartMapper.insert(cart);
		} else {
			int num = cart.getNum()+cartDB.getNum();
			cartDB.setNum(num);
			cartDB.setUpdated(new Date());
			cartMapper.updateById(cartDB);
		}
		
	}

	@Override
	@Transactional
	public void deleteCart(Long itemId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("item_id", itemId);
		cartMapper.delete(queryWrapper);
	}
	
}
