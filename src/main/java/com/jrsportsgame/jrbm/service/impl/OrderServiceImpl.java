package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.entity.TeamInfoEntity;
import com.jrsportsgame.jrbm.mapper.OrderMapper;
import com.jrsportsgame.jrbm.mapper.TeamInfoMapper;
import com.jrsportsgame.jrbm.pojo.Order;
import com.jrsportsgame.jrbm.service.intf.OrderService;
import com.jrsportsgame.jrbm.util.OrderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TeamInfoMapper teamInfoMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:cart:";
    /*
     * 创建订单
     */
    @Override
    @Transactional
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
        log.info("创建订单:"+order.toString());
        //将订单编号加入订单详细
        order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrderId(orderId));
        orderMapper.createOrderDetail(order.getOrderDetails());
        log.info("创建订单明细："+order.getOrderDetails().toString());
        return orderId;
    }

    /*
     * 支付订单
     */
    @Transactional
    @Override
    public Integer payOrder(Order order){
        Long teamId=order.getTeamId();
        TeamInfoEntity teamInfo = teamInfoMapper.getTeamInfoByTeamId(teamId);
        if(teamInfo.getTeamCoin()<order.getPayCoin() || teamInfo.getTeamDiamond()<order.getPayDiamond())
            return 2;
        Integer result=orderMapper.payOrder(order);
        if(result==1){
            //更新订单状态
            orderMapper.updateOrderStatus(order);
            //删除购物车缓存
            redisTemplate.delete(KEY_PREFIX + order.getTeamId());
        }
        return result;
    }

}
