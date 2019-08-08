package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.dto.ProductDTO;
import com.jrsportsgame.jrbm.entity.ProductEntity;
import com.jrsportsgame.jrbm.pojo.Cart;

import java.util.List;

public interface MallService {
    List<ProductDTO> getProductList();

    List<Cart> updateCart(Cart cart);

    List<Cart> getCart(Long teamId);
}
