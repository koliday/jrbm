package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.dto.BasicPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamInfoDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerListDTO;
import com.jrsportsgame.jrbm.mapper.TeaminfoMapper;
import com.jrsportsgame.jrbm.mapper.UserplayersMapper;
import com.jrsportsgame.jrbm.model.*;
import com.jrsportsgame.jrbm.service.intf.BasicPlayerService;
import com.jrsportsgame.jrbm.service.intf.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private UserplayersMapper userplayersMapper;
    @Autowired
    private BasicPlayerService basicPlayerService;
    @Autowired
    private TeaminfoMapper teaminfoMapper;



    @Override
    public TeamPlayerListDTO getTeamPlayerList(Integer tid) {

        List<Userplayers> userplayersList=getUserPlayersListByTid(tid);
        //封装TeamPlayerListDTO需要的三个list
        List<TeamPlayerDTO> starterList=new ArrayList<>();
        List<TeamPlayerDTO> subList=new ArrayList<>();
        List<TeamPlayerDTO> unregList=new ArrayList<>();
        //遍历userplayersList并进行封装
        for(Userplayers userplayers:userplayersList){
            //根据bpid得到对应的基础球员信息
            Basicplayer basicplayer=basicPlayerService.getBasicPlayerByBpid(userplayers.getBpid());
            //封装BasicPlayerDTO
            BasicPlayerDTO basicPlayerDTO=new BasicPlayerDTO();
            if(basicplayer!=null) {
                basicPlayerDTO.setBpid(basicplayer.getBpid());
                basicPlayerDTO.setEnname(basicplayer.getEnname());
                basicPlayerDTO.setChname(basicplayer.getChname());
                basicPlayerDTO.setPosition(basicplayer.getPosition());
                basicPlayerDTO.setOffensive(basicplayer.getOffensive());
                basicPlayerDTO.setDefensive(basicplayer.getDefensive());
            }
            //封装TeamPlayerDTO
            TeamPlayerDTO teamPlayerDTO=new TeamPlayerDTO();
            teamPlayerDTO.setUpid(userplayers.getUpid());
            teamPlayerDTO.setBasicPlayerDTO(basicPlayerDTO);
            teamPlayerDTO.setTid(userplayers.getTid());
            teamPlayerDTO.setSalary(userplayers.getSalary());
            teamPlayerDTO.setPosition(userplayers.getPosition());

            //添加到封装TeamPlayerListDTO需要的三个list
            //首发：1-5，替补：6-12，非激活：13-15
            if(teamPlayerDTO.getPosition()<=5)
                starterList.add(teamPlayerDTO);
            else if(teamPlayerDTO.getPosition()<=12)
                subList.add(teamPlayerDTO);
            else
                unregList.add(teamPlayerDTO);
        }
        //由于球队最少不低于8名球员，即5名主力和3名替补，因此这里需要替补不足7个，非激活不足3个的情况进行验证，并补入null以便前端进行判断
        for(int subsize=subList.size();subsize<7;subsize++){
            subList.add(null);
        }
        for(int unregsize=unregList.size();unregsize<3;unregsize++){
            unregList.add(null);
        }

        //封装TeamPlayerListDTO
        TeamPlayerListDTO teamPlayerListDTO=new TeamPlayerListDTO();
        teamPlayerListDTO.setStarterList(starterList);
        teamPlayerListDTO.setSubList(subList);
        teamPlayerListDTO.setUnregList(unregList);
        return teamPlayerListDTO;
    }


    @Override
    public TeamInfoDTO getTeamInfo(Integer tid) {


        TeaminfoExample teaminfoExample=new TeaminfoExample();
        teaminfoExample.createCriteria().andTidEqualTo(tid);
        List<Teaminfo> teaminfos = teaminfoMapper.selectByExample(teaminfoExample);
        if(teaminfos.isEmpty())
            return null;
        Teaminfo teamInfo=teaminfos.get(0);
        //计算球队进攻和防守值
        int offensive=0;
        int defensive=0;
        List<Userplayers> userplayersList=getUserPlayersListByTid(tid);
        for(Userplayers userplayers:userplayersList){
            //根据bpid得到对应的基础球员信息
            Basicplayer basicplayer=basicPlayerService.getBasicPlayerByBpid(userplayers.getBpid());
            //累加进攻和防守值
            if(basicplayer!=null){
                offensive+=basicplayer.getOffensive();
                defensive+=basicplayer.getDefensive();
            }
        }
        TeamInfoDTO teamInfoDTO=new TeamInfoDTO();
        teamInfoDTO.setTeamName(teamInfo.getTname());
        teamInfoDTO.setOffensive(offensive);
        teamInfoDTO.setDefensive(defensive);
        teamInfoDTO.setJrCoin(teamInfo.getJrcoin());
        teamInfoDTO.setJrDiamond(teamInfo.getJrdiamond());
        teamInfoDTO.setExp(teamInfo.getExp());
        return teamInfoDTO;
    }

    private List<Userplayers> getUserPlayersListByTid(Integer tid){
        //得到该tid下所有状态激活的球员并按所在球队中的位置进行升序
        UserplayersExample userplayersExample=new UserplayersExample();
        userplayersExample.createCriteria().andTidEqualTo(tid).andStatusEqualTo(1);
        userplayersExample.setOrderByClause("position asc");
        List<Userplayers> userplayersList=userplayersMapper.selectByExample(userplayersExample);
        return userplayersList;
    }

    @Override
    public List<TeamPlayerDTO> getTeamPlayerDTOList(Integer tid) {
        List<Userplayers> userplayersList=getUserPlayersListByTid(tid);
        List<TeamPlayerDTO> teamPlayerDTOList=new ArrayList<>();
        for(Userplayers userplayers:userplayersList){
            Basicplayer basicplayer=basicPlayerService.getBasicPlayerByBpid(userplayers.getBpid());
            //封装BasicPlayerDTO
            BasicPlayerDTO basicPlayerDTO=new BasicPlayerDTO();
            if(basicplayer!=null) {
                basicPlayerDTO.setBpid(basicplayer.getBpid());
                basicPlayerDTO.setEnname(basicplayer.getEnname());
                basicPlayerDTO.setChname(basicplayer.getChname());
                basicPlayerDTO.setPosition(basicplayer.getPosition());
                basicPlayerDTO.setOffensive(basicplayer.getOffensive());
                basicPlayerDTO.setDefensive(basicplayer.getDefensive());
            }
            //封装TeamPlayerDTO
            TeamPlayerDTO teamPlayerDTO=new TeamPlayerDTO();
            teamPlayerDTO.setUpid(userplayers.getUpid());
            teamPlayerDTO.setBasicPlayerDTO(basicPlayerDTO);
            teamPlayerDTO.setTid(userplayers.getTid());
            teamPlayerDTO.setSalary(userplayers.getSalary());
            teamPlayerDTO.setPosition(userplayers.getPosition());
            teamPlayerDTOList.add(teamPlayerDTO);
        }
        return teamPlayerDTOList;
    }


    @Transactional
    @Override
    public Integer exchangeTeamPlayer(Integer tid, Integer exchangeFromUpid, Integer exchangeToUpid) {
        //获取from球员的position
        UserplayersExample userplayersExample=new UserplayersExample();
        userplayersExample.createCriteria().andTidEqualTo(tid).andUpidEqualTo(exchangeFromUpid);
        List<Userplayers> userplayersList = userplayersMapper.selectByExample(userplayersExample);
        if(userplayersList.isEmpty())
            return 0;
        int fromposition=userplayersList.get(0).getPosition();

        userplayersExample.clear();
        userplayersExample.createCriteria().andTidEqualTo(tid).andUpidEqualTo(exchangeToUpid);
        userplayersList= userplayersMapper.selectByExample(userplayersExample);
        if(userplayersList.isEmpty())
            return 0;
        int toposition=userplayersList.get(0).getPosition();

        //交换
        Userplayers fromuserplayers=new Userplayers();
        fromuserplayers.setUpid(exchangeFromUpid);
        fromuserplayers.setPosition(toposition);
        int result=userplayersMapper.updateByPrimaryKeySelective(fromuserplayers);
        Userplayers touserplayers=new Userplayers();
        touserplayers.setUpid(exchangeToUpid);
        touserplayers.setPosition(fromposition);
        result+=userplayersMapper.updateByPrimaryKeySelective(touserplayers);
        if(result!=2)
            return 0;
        return 1;
    }
}
