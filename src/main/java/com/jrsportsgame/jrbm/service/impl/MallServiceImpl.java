package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.dto.ProductDTO;
import com.jrsportsgame.jrbm.entity.ProductEntity;
import com.jrsportsgame.jrbm.mapper.MallMapper;
import com.jrsportsgame.jrbm.service.intf.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MallServiceImpl implements MallService {
    @Autowired
    private MallMapper mallMapper;
    @Override
    public List<ProductDTO> getProductList() {
        List<ProductEntity> productEntityList = mallMapper.getAllProduct();
        List<ProductDTO> productDTOList=new ArrayList<>();
        for(ProductEntity productEntity:productEntityList){
            ProductDTO productDTO=new ProductDTO();
            productDTO.setProductEntity(productEntity);
            String priceStr="";
            Integer priceCoin=productEntity.getPriceCoin();
            Integer priceDiamond=productEntity.getPriceDiamond();
            if(priceCoin!=null){
                priceStr+=productEntity.getPriceCoin()+" JR币";
            }
            if(priceDiamond!=null){
                if(priceCoin!=null)
                    priceStr+="/";
                priceStr+=priceDiamond+" JR钻";
            }
            productDTO.setPriceStr(priceStr);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }
}
