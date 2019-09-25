package com.jrsportsgame.jrbm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BagEntity {
    private Long bagId;
    private Long teamId;
    private Long productId;
    private String productName;
    private String productImg;
    private String productDesc;
    private Integer productQuantity;
}
