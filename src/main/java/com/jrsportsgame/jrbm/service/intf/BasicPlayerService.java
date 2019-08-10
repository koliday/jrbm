package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.entity.BasicPlayerEntity;



public interface BasicPlayerService {
    BasicPlayerEntity getBasicPlayerByBpid(Integer bpid);
}
