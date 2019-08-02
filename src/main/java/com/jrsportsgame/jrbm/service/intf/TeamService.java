package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.dto.TeamInfoDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerListDTO;
import com.jrsportsgame.jrbm.model.Teaminfo;

import java.util.List;

public interface TeamService {
    //根据tid获得球队球员总列表
    TeamPlayerListDTO getTeamPlayerList(Integer tid);
    //根据tid获得球队基本信息
    TeamInfoDTO getTeamInfo(Integer tid);
    //根据tid获得球队球员信息列表
    List<TeamPlayerDTO> getTeamPlayerDTOList(Integer tid);
    //换人
    Integer exchangeTeamPlayer(Integer tid,Integer exchangeFromUpid,Integer exchangeToUpid);
    //解雇
    Integer fireTeamPlayer(Integer tid,Integer upid);
    //通过upid获取球员信息
    TeamPlayerDTO getTeamPlayerDTOByUpid(Integer upid);


}
