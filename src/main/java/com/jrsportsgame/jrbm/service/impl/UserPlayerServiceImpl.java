package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.entity.UserPlayerEntity;
import com.jrsportsgame.jrbm.mapper.UserPlayerMapper;
import com.jrsportsgame.jrbm.service.intf.UserPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPlayerServiceImpl implements UserPlayerService {
    @Autowired
    private UserPlayerMapper userPlayerMapper;

    @Override
    public UserPlayerEntity getUserPlayerByUpid(Long upId) {
        return userPlayerMapper.getUserPlayerByUpid(upId);
    }

}
