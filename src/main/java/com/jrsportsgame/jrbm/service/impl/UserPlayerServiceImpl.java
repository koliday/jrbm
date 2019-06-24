package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.mapper.UserplayersMapper;
import com.jrsportsgame.jrbm.model.Userplayers;
import com.jrsportsgame.jrbm.model.UserplayersExample;
import com.jrsportsgame.jrbm.service.intf.UserPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPlayerServiceImpl implements UserPlayerService {
    @Autowired
    private UserplayersMapper userplayersMapper;
    @Override
    public Userplayers getUserPlayerByUpid(Integer upid) {
        UserplayersExample userplayersExample=new UserplayersExample();
        userplayersExample.createCriteria().andUpidEqualTo(upid);
        List<Userplayers> userplayers = userplayersMapper.selectByExample(userplayersExample);
        if(!userplayers.isEmpty())
            return userplayers.get(0);
        return null;
    }
}
