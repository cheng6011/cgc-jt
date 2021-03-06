package com.jt.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;


//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;
	//当定时任务执行时,执行job具体操作
	//需要修改超时的订单的状态
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Order order = new Order();
		order.setStatus(6).setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -15);
		Date dateOut = calendar.getTime();
		updateWrapper.eq("status", 1).lt("created", dateOut);
		orderMapper.update(order, updateWrapper);
		System.out.println("定时任务开始!!!");
	}
	
}
