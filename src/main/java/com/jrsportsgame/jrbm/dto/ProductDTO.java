package com.jrsportsgame.jrbm.dto;

import com.jrsportsgame.jrbm.entity.ProductEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private ProductEntity productEntity;
    private String priceStr;
}
