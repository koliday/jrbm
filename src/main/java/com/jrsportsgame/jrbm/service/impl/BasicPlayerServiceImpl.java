package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.mapper.BasicplayerMapper;
import com.jrsportsgame.jrbm.model.Basicplayer;
import com.jrsportsgame.jrbm.model.BasicplayerExample;
import com.jrsportsgame.jrbm.service.intf.BasicPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicPlayerServiceImpl implements BasicPlayerService {
    @Autowired
    private BasicplayerMapper basicplayerMapper;
    @Override
    public Basicplayer getBasicPlayerByBpid(Integer bpid) {
        BasicplayerExample basicplayerExample=new BasicplayerExample();
        basicplayerExample.createCriteria().andBpidEqualTo(bpid);
        List<Basicplayer> basicplayers = basicplayerMapper.selectByExample(basicplayerExample);
        if(!basicplayers.isEmpty())
            return basicplayers.get(0);
        return null;
    }
}
