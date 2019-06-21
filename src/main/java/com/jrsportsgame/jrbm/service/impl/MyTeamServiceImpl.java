package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.dto.MyTeamPlayerDTO;
import com.jrsportsgame.jrbm.mapper.BasicplayerMapper;
import com.jrsportsgame.jrbm.mapper.TeaminfoMapper;
import com.jrsportsgame.jrbm.mapper.UserplayersMapper;
import com.jrsportsgame.jrbm.model.*;
import com.jrsportsgame.jrbm.service.intf.MyTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyTeamServiceImpl implements MyTeamService {
    @Autowired
    private UserplayersMapper userplayersMapper;
    @Autowired
    private BasicplayerMapper basicplayerMapper;
    @Autowired
    private TeaminfoMapper teaminfoMapper;

    @Override
    public List<MyTeamPlayerDTO> getMyTeamPlayerList(Integer tid) {
        //根据tid获得其所有bpid
        UserplayersExample userplayersExample=new UserplayersExample();
        userplayersExample.createCriteria().andTidEqualTo(tid);
        List<Userplayers> userplayersList=userplayersMapper.selectByExample(userplayersExample);

        //根据所有bpid获得球员对应信息
        BasicplayerExample basicplayerExample=new BasicplayerExample();
        List<MyTeamPlayerDTO> myTeamPlayerDTOList=new ArrayList<>();
        for(Userplayers userplayers:userplayersList){
            basicplayerExample.clear();
            basicplayerExample.createCriteria().andBpidEqualTo(userplayers.getBpid());
            List<Basicplayer> basicplayers = basicplayerMapper.selectByExample(basicplayerExample);
            if(!basicplayers.isEmpty()) {
                Basicplayer basicplayer=basicplayers.get(0);
                myTeamPlayerDTOList.add(new MyTeamPlayerDTO(userplayers.getUpid(),basicplayer.getChname(),basicplayer.getOffensive(),basicplayer.getDefensive()));
            }
        }
        return myTeamPlayerDTOList;



    }

    @Override
    public Teaminfo getMyTeamInfo(Integer tid) {
        TeaminfoExample teaminfoExample=new TeaminfoExample();
        teaminfoExample.createCriteria().andTidEqualTo(tid);
        List<Teaminfo> teaminfos = teaminfoMapper.selectByExample(teaminfoExample);
        if(!teaminfos.isEmpty())
            return teaminfos.get(0);
        return null;
    }


}
