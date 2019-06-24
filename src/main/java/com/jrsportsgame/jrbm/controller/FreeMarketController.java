package com.jrsportsgame.jrbm.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jrsportsgame.jrbm.dto.FreeMarketPlayerDTO;
import com.jrsportsgame.jrbm.service.intf.FreeMarketService;
import com.jrsportsgame.jrbm.util.DataTablesPageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FreeMarketController {
    @Autowired
    private FreeMarketService freeMarketService;
    @GetMapping("/freemarket")
    public String loadFreeMarket(){
        return "freemarket";
    }

    @ResponseBody
    @PostMapping(value = "/freemarketpage",produces = "application/json;charset=UTF-8")
    public String loadFreeMarketPage(HttpServletRequest request){
        //获取datatables通过ajax传入的三个参数
        Integer draw=Integer.valueOf(request.getParameter("draw"));
        Integer start=Integer.valueOf(request.getParameter("start"));
        Integer length=Integer.valueOf(request.getParameter("length"));
        //将start和length计算成pagenum和pagesize
        Integer pagenum=start/length+1;
        Integer pagesize=length;
        //通过pagehelper进行分页
        PageInfo<FreeMarketPlayerDTO> pageInfo = freeMarketService.getFreeMarketPlayerList(pagenum,pagesize);

        DataTablesPageUtil<FreeMarketPlayerDTO> dataTablesForFreeMarket=new DataTablesPageUtil<>();
        dataTablesForFreeMarket.setDraw(draw);

        dataTablesForFreeMarket.setRecordsTotal(freeMarketService.getFreeMarketCount());
        dataTablesForFreeMarket.setRecordsFiltered(dataTablesForFreeMarket.getRecordsTotal());
        dataTablesForFreeMarket.setData(pageInfo.getList());

        return JSON.toJSONString(dataTablesForFreeMarket);
    }
}
