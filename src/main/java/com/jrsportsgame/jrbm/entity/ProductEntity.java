package com.jrsportsgame.jrbm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEntity {
    private Integer productId;
    private String productName;
    private String productImg;
    private Integer priceCoin;
    private Integer priceDiamond;
    private String productDesc;
    private Long createTime;
    private Integer productStatus;
}
