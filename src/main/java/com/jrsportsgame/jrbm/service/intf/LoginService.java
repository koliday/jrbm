package com.jrsportsgame.jrbm.service.intf;

import com.jrsportsgame.jrbm.entity.WXUserEntity;

public interface LoginService {
    Long loginByUsername(String username,String password);
    Long loginByWeixin(WXUserEntity wxUser);
}
