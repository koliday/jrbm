package com.jrsportsgame.jrbm.entity;

import com.jrsportsgame.jrbm.pojo.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderEntity {
    private Long orderId;
    private Long orderNo;
    private Long userId;
    private Long teamId;
    private Integer payCoin;
    private Integer payDiamond;
    private List<OrderDetail> orderDetails;
    private Date createTime;
    private Integer orderStatus;
}
