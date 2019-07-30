package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.dto.ProductDTO;
import com.jrsportsgame.jrbm.entity.ProductEntity;

import java.util.List;

public interface MallService {
    List<ProductDTO> getProductList();
}
