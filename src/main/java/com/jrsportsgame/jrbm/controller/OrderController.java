package com.jrsportsgame.jrbm.controller;

import com.alibaba.fastjson.JSON;
import com.jrsportsgame.jrbm.pojo.Order;
import com.jrsportsgame.jrbm.pojo.OrderDetail;
import com.jrsportsgame.jrbm.pojo.OrderResult;
import com.jrsportsgame.jrbm.service.intf.OrderService;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderResult> createOrder(@RequestBody List<OrderDetail> orderDetails, HttpSession session){
        Long userId=(Long)session.getAttribute("uid");
        Long teamId=(Long)session.getAttribute("tid");
        Order order=new Order();
        order.setUserId(userId);
        order.setTeamId(teamId);
        order.setOrderDetails(orderDetails);
        Long orderId = orderService.createOrder(order);
        Integer payResult = orderService.payOrder(order);
        OrderResult orderResult=new OrderResult(payResult,orderId);
        return new ResponseEntity<>(orderResult, HttpStatus.OK);
    }

    @PostMapping("/testOrder")
    public ResponseEntity<String> testOrder(@RequestBody List<OrderDetail> orderDetails, HttpSession session){
        //Long userId=(Long)session.getAttribute("uid");
        //Long teamId=(Long)session.getAttribute("tid");
        Order order=new Order();
        order.setUserId(1L);
        order.setTeamId(1L);
        order.setOrderDetails(orderDetails);
        Long orderId = orderService.createOrder(order);
        Integer payResult = orderService.payOrder(order);
        return ResponseEntity.ok(orderId+" "+payResult);
    }
}
