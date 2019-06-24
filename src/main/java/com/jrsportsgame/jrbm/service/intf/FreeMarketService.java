package com.jrsportsgame.jrbm.service.intf;

import com.github.pagehelper.PageInfo;
import com.jrsportsgame.jrbm.dto.FreeMarketPlayerDTO;
import com.jrsportsgame.jrbm.model.Freemarket;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FreeMarketService {

    PageInfo<FreeMarketPlayerDTO> getFreeMarketPlayerList(Integer pagenum,Integer pagesize);
    List<Freemarket> getAllFreeMarketPlayerList();
    Integer getFreeMarketCount();
}
