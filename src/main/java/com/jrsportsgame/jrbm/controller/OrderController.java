package com.jrsportsgame.jrbm.controller;

import com.jrsportsgame.jrbm.service.intf.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;


}
