package com.jrsportsgame.jrbm.mapper;

import com.jrsportsgame.jrbm.pojo.Order;
import com.jrsportsgame.jrbm.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper{
    Long createOrder(@Param("order") Order order);

    Long createOrderDetail(@Param("orderDetailList")List<OrderDetail> orderDetailList);

    Long payOrder(Order order);
}
