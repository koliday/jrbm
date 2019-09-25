package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.entity.BagEntity;
import com.jrsportsgame.jrbm.mapper.BagMapper;
import com.jrsportsgame.jrbm.service.intf.BagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BagServiceImpl implements BagService {
    @Autowired
    private BagMapper bagMapper;
    @Override
    public List<BagEntity> getBagListByTeamId(Long teamId) {
        return bagMapper.getBagListByTeamId(teamId);
    }
}
