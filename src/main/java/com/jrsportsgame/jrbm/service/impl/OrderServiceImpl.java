package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.mapper.OrderMapper;
import com.jrsportsgame.jrbm.pojo.Order;
import com.jrsportsgame.jrbm.service.intf.OrderService;
import com.jrsportsgame.jrbm.util.OrderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public Long createOrder(Order order) {
        //完善订单信息
        //生成订单编号
        order.setOrderNo(OrderUtil.getNewOrderId());
        //计算价格
        Integer[] cost = OrderUtil.calucateCost(order);
        order.setPayCoin(cost[0]);
        order.setPayDiamond(cost[1]);
        //设置订单时间和状态
        order.setCreateTime(new Date());
        order.setOrderStatus(0);
        //插入订单
        orderMapper.createOrder(order);

        //获得回写的订单Id
        Long orderId=order.getOrderId();
        //将订单编号加入订单详细
        order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrderId(orderId));
        orderMapper.createOrderDetail(order.getOrderDetails());
        return orderId;
    }

    public Long payOrder(){

    }
}
