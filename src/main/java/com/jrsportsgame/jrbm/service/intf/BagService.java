package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.entity.BagEntity;

import java.util.List;

public interface BagService {
    List<BagEntity> getBagListByTeamId(Long teamId);
}
