package com.jrsportsgame.jrbm.controller;

import com.jrsportsgame.jrbm.dto.ProductDTO;
import com.jrsportsgame.jrbm.entity.ProductEntity;
import com.jrsportsgame.jrbm.service.intf.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MallController {
    @Autowired
    private MallService mallService;

    @GetMapping("/mall")
    public String getMallPage(Model model){
        List<ProductDTO> productList = mallService.getProductList();
        model.addAttribute("productList", productList);
        return "mall";
    }
}
