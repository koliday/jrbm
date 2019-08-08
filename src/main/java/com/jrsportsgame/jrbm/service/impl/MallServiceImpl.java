package com.jrsportsgame.jrbm.service.impl;

import com.alibaba.fastjson.JSON;
import com.jrsportsgame.jrbm.dto.ProductDTO;
import com.jrsportsgame.jrbm.entity.ProductEntity;
import com.jrsportsgame.jrbm.mapper.MallMapper;
import com.jrsportsgame.jrbm.pojo.Cart;
import com.jrsportsgame.jrbm.service.intf.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MallServiceImpl implements MallService {
    @Autowired
    private MallMapper mallMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:cart:";

    @Override
    public List<ProductDTO> getProductList() {
        List<ProductEntity> productEntityList = mallMapper.getAllProduct();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (ProductEntity productEntity : productEntityList) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductEntity(productEntity);
            String priceStr = "";
            Integer priceCoin = productEntity.getPriceCoin();
            Integer priceDiamond = productEntity.getPriceDiamond();
            if (priceCoin != null) {
                priceStr += productEntity.getPriceCoin() + " JR币";
            }
            if (priceDiamond != null) {
                if (priceCoin != null)
                    priceStr += "/";
                priceStr += priceDiamond + " JR钻";
            }
            productDTO.setPriceStr(priceStr);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    /*
     * 更新服务端购物车数据
     * 若已有数据，则修改数量，若没有数据则创建
     */

    @Override
    public List<Cart> updateCart(Cart cart) {
        //查询购物车记录
        BoundHashOperations<String, Object, Object> cartHashOperations = redisTemplate.boundHashOps(KEY_PREFIX + cart.getTeamId());
        //判断当前商品是否在购物车
        String key=cart.getProductId().toString();
        if(cartHashOperations.hasKey(key)){
            //在，更新数量
            String cartJson=cartHashOperations.get(key).toString();
            Cart existedCart=JSON.parseObject(cartJson,Cart.class);
            existedCart.setAmount(existedCart.getAmount()+cart.getAmount());
            //若更新后的数量等于0，则删除该项
            if(existedCart.getAmount()==0)
                cartHashOperations.delete(key);
            else
                cartHashOperations.put(key,JSON.toJSONString(existedCart));
        }else{
            //不在，新增商品到购物车
            cartHashOperations.put(key,JSON.toJSONString(cart));
        }
        //返回最新的购物车记录
        return getCart(cart.getTeamId());
    }

    @Override
    public List<Cart> getCart(Long teamId) {
        //判断是否有购物车记录
        if(!redisTemplate.hasKey(KEY_PREFIX+teamId))
            return null;
        //获取购物车信息
        BoundHashOperations<String, Object, Object> cartHashOperations = redisTemplate.boundHashOps(KEY_PREFIX + teamId);
        List<Object> cartJsonList = cartHashOperations.values();
        //判断购物车是否为空
        if(cartJsonList==null||cartJsonList.isEmpty())
            return null;
        return cartJsonList.stream().map(cartJson->JSON.parseObject(cartJson.toString(),Cart.class)).collect(Collectors.toList());
    }
}
