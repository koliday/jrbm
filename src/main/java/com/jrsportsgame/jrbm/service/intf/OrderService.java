package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.pojo.Order;

public interface OrderService {
    Long createOrder(Order order);
    Integer payOrder(Order order);
}
