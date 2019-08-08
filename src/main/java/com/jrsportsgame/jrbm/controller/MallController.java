package com.jrsportsgame.jrbm.controller;

import com.jrsportsgame.jrbm.dto.ProductDTO;
import com.jrsportsgame.jrbm.dto.TeamInfoDTO;
import com.jrsportsgame.jrbm.entity.ProductEntity;
import com.jrsportsgame.jrbm.pojo.Cart;
import com.jrsportsgame.jrbm.pojo.Order;
import com.jrsportsgame.jrbm.pojo.OrderDetail;
import com.jrsportsgame.jrbm.service.intf.MallService;
import com.jrsportsgame.jrbm.service.intf.OrderService;
import com.jrsportsgame.jrbm.service.intf.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MallController {
    private static Logger logger= LoggerFactory.getLogger(MallController.class);

    @Autowired
    private MallService mallService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/mall")
    public String getMallPage(Model model,HttpSession session){
        Integer teamId=(Integer)session.getAttribute("tid");
        List<ProductDTO> productList = mallService.getProductList();
        TeamInfoDTO teamInfo = teamService.getTeamInfo(teamId);
        model.addAttribute("teamInfo",teamInfo);
        model.addAttribute("productList", productList);
        return "mall";
    }

    @PostMapping("/updateCart")
    public ResponseEntity<List<Cart>> updateCart(@RequestBody Cart cart,HttpSession session){
        Long userId=(Long)session.getAttribute("uid");
        Long teamId=(Long)session.getAttribute("tid");
        cart.setUserId(userId);
        cart.setTeamId(teamId);
        List<Cart> cartList = mallService.updateCart(cart);
        if(cartList==null||cartList.isEmpty()){
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(cartList);
    }

    @PostMapping("/getCart")
    public ResponseEntity<List<Cart>> getCart(HttpSession session){
        Long teamId=(Long)session.getAttribute("tid");
        List<Cart> cartList = mallService.getCart(teamId);
        if(cartList==null||cartList.isEmpty()){
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(cartList);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Void> createOrder(@RequestBody List<OrderDetail> orderDetails, HttpSession session){
        Long userId=(Long)session.getAttribute("uid");
        Long teamId=(Long)session.getAttribute("tid");
        Order order=new Order();
        order.setUserId(userId);
        order.setTeamId(teamId);
        order.setOrderDetails(orderDetails);
        orderService.createOrder(order);

    }
}
