package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.model.Teaminfo;

import java.util.ArrayList;
import java.util.List;

public interface ServerService {
    List<Teaminfo> getTeamInfoList(Integer uid);
    Integer createNewTeam(Integer uid,String teamName,Integer sid);
}
