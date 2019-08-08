package com.jrsportsgame.jrbm.util;

import com.jrsportsgame.jrbm.pojo.Order;
import com.jrsportsgame.jrbm.pojo.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 * 订单工具类
 * 生成订单编号
 * 计算订单价格
 */

public class OrderUtil {
    @Autowired
    private static IdWorker idWorker;
    /*
     * 生成订单编号
     */
    public static Long getNewOrderId(){
        return idWorker.nextId();
    }
    /*
     * 计算订单价格
     */
    public static Integer[] calucateCost(Order order){
        Integer[] cost=new Integer[2];
        int costCoin=0;
        int costDiamond=0;
        List<OrderDetail> orderDetailList = order.getOrderDetails();
        for(OrderDetail orderDetail:orderDetailList){
            if(orderDetail.getCurrency()==0){
                costCoin+=orderDetail.getPriceCoin();
            }else{
                costDiamond+=orderDetail.getPriceDiamond();
            }
        }
        cost[0]=costCoin;
        cost[1]=costDiamond;
        return cost;
    }
}
