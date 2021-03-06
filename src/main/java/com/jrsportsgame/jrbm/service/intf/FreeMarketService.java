package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.dto.FreeMarketPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerDTO;

import java.util.List;

public interface FreeMarketService {

    //PageInfo<FreeMarketPlayerDTO> getFreeMarketPlayerList(Integer pagenum,Integer pagesize);

    //将球员推入自由市场
    void createFreeMarketPlayer(TeamPlayerDTO teamPlayerDTO,Integer source);
    //读取自由市场所有球员
    List<FreeMarketPlayerDTO> getAllFreeMarketPlayerDTOList();
    //读取自由市场球员分页
    List<FreeMarketPlayerDTO> getFreeMarketPlayerDTOPageList(Integer start,Integer length);
    //移除指定过期球员
    void removeExpiredFreeMarketPlayer(String fpid);
    //获得当前自由球员数量
    Integer getFreeMarketPlayerCount();

}
