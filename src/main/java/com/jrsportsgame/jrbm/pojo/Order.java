package com.jrsportsgame.jrbm.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {
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
