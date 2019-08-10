package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.entity.BasicPlayerEntity;
import com.jrsportsgame.jrbm.mapper.BasicPlayerMapper;
import com.jrsportsgame.jrbm.service.intf.BasicPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BasicPlayerServiceImpl implements BasicPlayerService {
    @Autowired
    private BasicPlayerMapper basicplayerMapper;
    @Override
    public BasicPlayerEntity getBasicPlayerByBpid(Integer bpid) {
        return basicplayerMapper.getBasicPlayerByBpId(bpid);
    }
}
