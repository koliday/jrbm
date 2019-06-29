package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.dto.BasicPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamInfoDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerListDTO;
import com.jrsportsgame.jrbm.mapper.MyUserPlayersMapper;
import com.jrsportsgame.jrbm.mapper.TeaminfoMapper;
import com.jrsportsgame.jrbm.mapper.UserplayersMapper;
import com.jrsportsgame.jrbm.model.*;
import com.jrsportsgame.jrbm.service.intf.BasicPlayerService;
import com.jrsportsgame.jrbm.service.intf.FreeMarketService;
import com.jrsportsgame.jrbm.service.intf.TeamService;
import com.jrsportsgame.jrbm.service.intf.UserPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private UserplayersMapper userplayersMapper;
    @Autowired
    private MyUserPlayersMapper myUserPlayersMapper;
    @Autowired
    private BasicPlayerService basicPlayerService;
    @Autowired
    private TeaminfoMapper teaminfoMapper;
    @Autowired
    private FreeMarketService freeMarketService;
    @Autowired
    private UserPlayerService userPlayerService;

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
        /*
         * 由于球队最少不低于8名球员，即5名主力和3名替补，因此这里需要替补不足7个，非激活不足3个的情况进行验证，并补入null以便前端进行判断
         */
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

    @Transactional
    @Override
    public Integer fireTeamPlayer(Integer tid, Integer upid) {
        //第一步：将被解雇球员的status设为2，表示进入自由市场
        UserplayersExample userplayersExample=new UserplayersExample();
        userplayersExample.createCriteria().andUpidEqualTo(upid).andTidEqualTo(tid);
        Userplayers firedUserPlayer=new Userplayers();
        firedUserPlayer.setStatus(2);
        int result = userplayersMapper.updateByExampleSelective(firedUserPlayer, userplayersExample);
        if(result < 1)
            return 0;
        //第二步：调整球员位置
        firedUserPlayer=userPlayerService.getUserPlayerByUpid(upid);
        int firedPlayerPosition=firedUserPlayer.getPosition();
        adjustFiredPlayer(firedUserPlayer.getTid(),firedPlayerPosition);
        //第三步：封装firedTeamPlayerDTO，创建自由球员，球员正式流入自由市场
        TeamPlayerDTO firedTeamPlayerDTO=new TeamPlayerDTO();
        userplayersExample.clear();
        userplayersExample.createCriteria().andUpidEqualTo(upid);
        List<Userplayers> userplayersList = userplayersMapper.selectByExample(userplayersExample);
        if(userplayersList.isEmpty())
            return 0;
        Integer firedPlayerBpid=userplayersList.get(0).getBpid();
        firedTeamPlayerDTO.setUpid(upid);
        firedTeamPlayerDTO.setPosition(firedUserPlayer.getPosition());
        firedTeamPlayerDTO.setSalary(firedUserPlayer.getSalary());
        firedTeamPlayerDTO.setTid(firedUserPlayer.getTid());
        firedTeamPlayerDTO.setBasicPlayerDTO(new BasicPlayerDTO(basicPlayerService.getBasicPlayerByBpid(firedPlayerBpid)));

        freeMarketService.createFreeMarketPlayer(firedTeamPlayerDTO, 1);
        return result;
    }

    /*
     * 如果解雇的是首发，则使用第一替补（position=6）进行替换，并将后面的球员向前移动
     * 如果解雇的是替补，则将该替补后的所有替补向前移动一个
     * 如果是未激活球员，则将该球员后的所有未激活球员向前移动一个
     */
    private void adjustFiredPlayer(Integer tid,Integer position){
        if(position<=5){
            myUserPlayersMapper.substituteForFiredPlayer(tid,position);
            myUserPlayersMapper.adjustFiredPlayerPostion(tid,6);
        }else if(position<=12){
            myUserPlayersMapper.adjustFiredPlayerPostion(tid,position);
        }else{
            myUserPlayersMapper.adjustUnregFiredPlayerPosition(tid,position);
        }
    }

    @Override
    public TeamPlayerDTO getTeamPlayerDTOByUpid(Integer upid) {
        UserplayersExample userplayersExample=new UserplayersExample();
        userplayersExample.createCriteria().andUpidEqualTo(upid);
        List<Userplayers> userplayersList = userplayersMapper.selectByExample(userplayersExample);
        if(userplayersList.isEmpty())
            return null;
        TeamPlayerDTO teamPlayerDTO=new TeamPlayerDTO(userplayersList.get(0));
        return teamPlayerDTO;
    }


    //自动调整最佳阵容
}
