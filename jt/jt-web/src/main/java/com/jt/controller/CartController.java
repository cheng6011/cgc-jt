package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.CartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(timeout=3000,check=false)
	private CartService cartService;
	
	//http://www.jt.com/cart/show.html
	@RequestMapping("/show")
	public String show(Model model) {
		//获取当前用户信息
		Long userId = UserThreadLocal.get().getId();;  //暂时写死
		//1.先获取购物车信息
		List<Cart> cartList = cartService.findCartList(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	// http://www.jt.com/service/cart/update/num/562379/8
	//如果使用restful风格有多个参数,并且和pojo属性一样用pojo对象接收
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(Cart cart) {
		try {
			Long userId = UserThreadLocal.get().getId();
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//商品加入购物车
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart) {
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/cart.html";//跳转到购物车页面
	}
	
	//删除购物车的商品
	//http://www.jt.com/cart/delete/562379.html
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId) {
		
		cartService.deleteCart(itemId);
		return "redirect:/cart/cart.html";
	}
}
