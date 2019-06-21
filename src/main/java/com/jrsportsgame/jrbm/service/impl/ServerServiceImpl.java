package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.mapper.TeaminfoMapper;
import com.jrsportsgame.jrbm.model.Teaminfo;
import com.jrsportsgame.jrbm.model.TeaminfoExample;
import com.jrsportsgame.jrbm.service.intf.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {
    @Autowired
    private TeaminfoMapper teaminfoMapper;
    @Override
    public List<Teaminfo> getTeamInfoList(Integer uid) {
        TeaminfoExample teaminfoExample = new TeaminfoExample();
        teaminfoExample.createCriteria().andUidEqualTo(uid);
        List<Teaminfo> teaminfoList = teaminfoMapper.selectByExample(teaminfoExample);
        return teaminfoList;
    }

    /*
    *  创建一支新球队
     */
    @Override
    public Integer createNewTeam(Integer uid, String teamName, Integer sid) {
        Teaminfo teaminfo=new Teaminfo();
        teaminfo.setUid(uid);
        teaminfo.setTname(teamName);
        teaminfo.setSid(sid);
        return teaminfoMapper.insertSelective(teaminfo);
    }
}
