package com.jrsportsgame.jrbm.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class OrderDetail {
    private Long orderDetailId;
    private Long orderId;
    private Integer productId;
    private String productName;
    private String productImg;
    private Integer priceCoin;
    private Integer priceDiamond;
    private Integer currency;
    private String priceStr;
    private String productDesc;
    private Integer amount;

}
