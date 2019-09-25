package com.jrsportsgame.jrbm.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付后返回的结果实体
 */
@Getter
@Setter
public class OrderResult {
    //购买结果，0-未知原因失败，1-购买成功，2-余额不足
    private Integer orderResult;
    //订单ID，若购买不成功，则为null
    private Long orderId;

    public OrderResult(Integer orderResult, Long orderId) {
        this.orderResult = orderResult;
        this.orderId = orderId;
    }
}
