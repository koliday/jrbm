package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.entity.UserPlayerEntity;

public interface UserPlayerService {
    UserPlayerEntity getUserPlayerByUpid(Long upid);
}
