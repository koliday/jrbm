package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.dto.MyTeamPlayerDTO;
import com.jrsportsgame.jrbm.model.Teaminfo;

import java.util.List;

public interface MyTeamService {
    List<MyTeamPlayerDTO> getMyTeamPlayerList(Integer tid);
    Teaminfo getMyTeamInfo(Integer tid);
}
