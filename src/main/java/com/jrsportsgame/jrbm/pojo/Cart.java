package com.jrsportsgame.jrbm.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
    private Long userId;
    private Long teamId;
    private Long productId;
    private String productName;
    private String productImg;
    private Integer priceCoin;
    private Integer priceDiamond;
    private String priceStr;
    private String productDesc;
    private Integer amount;
}
