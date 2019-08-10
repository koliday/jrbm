package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.dto.TeamInfoDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerListDTO;

import java.util.List;

public interface TeamService {
    //根据tid获得球队球员总列表
    TeamPlayerListDTO getTeamPlayerList(Long tid);
    //根据tid获得球队基本信息
    TeamInfoDTO getTeamInfo(Long tid);
    //根据tid获得球队球员信息列表
    List<TeamPlayerDTO> getTeamPlayerDTOList(Long tid);
//    //换人
//    Integer exchangeTeamPlayer(Long tid,Long exchangeFromUpid,Long exchangeToUpid);
//    //解雇
//    Integer fireTeamPlayer(Long tid,Long upid);
//    //通过upid获取球员信息
//    TeamPlayerDTO getTeamPlayerDTOByUpid(Long upid);


}
