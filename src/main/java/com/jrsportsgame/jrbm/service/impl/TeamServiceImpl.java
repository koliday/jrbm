package com.jrsportsgame.jrbm.service.impl;

import com.jrsportsgame.jrbm.dto.BasicPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamInfoDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerListDTO;
import com.jrsportsgame.jrbm.entity.BasicPlayerEntity;
import com.jrsportsgame.jrbm.entity.TeamInfoEntity;
import com.jrsportsgame.jrbm.entity.UserPlayerEntity;
import com.jrsportsgame.jrbm.mapper.TeamInfoMapper;
import com.jrsportsgame.jrbm.mapper.UserPlayerMapper;
import com.jrsportsgame.jrbm.service.intf.BasicPlayerService;
import com.jrsportsgame.jrbm.service.intf.FreeMarketService;
import com.jrsportsgame.jrbm.service.intf.TeamService;
import com.jrsportsgame.jrbm.service.intf.UserPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private UserPlayerMapper userPlayerMapper;
    @Autowired
    private BasicPlayerService basicPlayerService;
    @Autowired
    private TeamInfoMapper teaminfoMapper;
    @Autowired
    private FreeMarketService freeMarketService;
    @Autowired
    private UserPlayerService userPlayerService;

    @Override
    public TeamPlayerListDTO getTeamPlayerList(Long tid) {

        List<UserPlayerEntity> userPlayerList=getUserPlayersListByTid(tid);
        //封装TeamPlayerListDTO需要的三个list
        List<TeamPlayerDTO> starterList=new ArrayList<>();
        List<TeamPlayerDTO> subList=new ArrayList<>();
        List<TeamPlayerDTO> unregList=new ArrayList<>();
        //遍历userPlayerList并进行封装
        for(UserPlayerEntity userPlayer:userPlayerList){
            //根据bpid得到对应的基础球员信息
            BasicPlayerEntity basicplayer=basicPlayerService.getBasicPlayerByBpid(userPlayer.getBpId());
            //封装BasicPlayerDTO
            BasicPlayerDTO basicPlayerDTO=new BasicPlayerDTO();
            if(basicplayer!=null) {
                basicPlayerDTO.setBpid(basicplayer.getBpId());
                basicPlayerDTO.setEnname(basicplayer.getBpEnName());
                basicPlayerDTO.setChname(basicplayer.getBpChName());
                basicPlayerDTO.setPosition(basicplayer.getBpPosition());
                basicPlayerDTO.setOffensive(basicplayer.getBpOffensive());
                basicPlayerDTO.setDefensive(basicplayer.getBpDefensive());
            }
            //封装TeamPlayerDTO
            TeamPlayerDTO teamPlayerDTO=new TeamPlayerDTO();
            teamPlayerDTO.setUpid(userPlayer.getUpId());
            teamPlayerDTO.setBasicPlayerDTO(basicPlayerDTO);
            teamPlayerDTO.setTid(userPlayer.getTeamId());
            teamPlayerDTO.setSalary(userPlayer.getUpSalary());
            teamPlayerDTO.setPosition(userPlayer.getUpPosition());

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
    public TeamInfoDTO getTeamInfo(Long teamId) {
        TeamInfoEntity teamInfo=teaminfoMapper.getTeamInfoByTeamId(teamId);
        if(teamInfo==null)
            return null;
        //计算球队进攻和防守值
        int offensive=0;
        int defensive=0;
        List<UserPlayerEntity> userPlayerList=getUserPlayersListByTid(teamId);
        for(UserPlayerEntity userPlayer:userPlayerList){
            //根据bpid得到对应的基础球员信息
            BasicPlayerEntity basicplayer=basicPlayerService.getBasicPlayerByBpid(userPlayer.getBpId());
            //累加进攻和防守值
            if(basicplayer!=null){
                offensive+=basicplayer.getBpOffensive();
                defensive+=basicplayer.getBpDefensive();
            }
        }
        TeamInfoDTO teamInfoDTO=new TeamInfoDTO();
        teamInfoDTO.setTeamName(teamInfo.getTeamName());
        teamInfoDTO.setOffensive(offensive);
        teamInfoDTO.setDefensive(defensive);
        teamInfoDTO.setJrCoin(teamInfo.getTeamCoin());
        teamInfoDTO.setJrDiamond(teamInfo.getTeamDiamond());
        teamInfoDTO.setExp(teamInfo.getTeamExp());
        return teamInfoDTO;
    }

    private List<UserPlayerEntity> getUserPlayersListByTid(Long teamId){
        //得到该tid下所有状态激活的球员并按所在球队中的位置进行升序
        return userPlayerMapper.getTeamPlayerListByTeamId(teamId);
    }

    @Override
    public List<TeamPlayerDTO> getTeamPlayerDTOList(Long tid) {
        List<UserPlayerEntity> userPlayerList=getUserPlayersListByTid(tid);
        List<TeamPlayerDTO> teamPlayerDTOList=new ArrayList<>();
        for(UserPlayerEntity userPlayer:userPlayerList){
            BasicPlayerEntity basicplayer=basicPlayerService.getBasicPlayerByBpid(userPlayer.getBpId());
            //封装BasicPlayerDTO
            BasicPlayerDTO basicPlayerDTO=new BasicPlayerDTO();
            if(basicplayer!=null) {
                basicPlayerDTO.setBpid(basicplayer.getBpId());
                basicPlayerDTO.setEnname(basicplayer.getBpEnName());
                basicPlayerDTO.setChname(basicplayer.getBpChName());
                basicPlayerDTO.setPosition(basicplayer.getBpPosition());
                basicPlayerDTO.setOffensive(basicplayer.getBpOffensive());
                basicPlayerDTO.setDefensive(basicplayer.getBpDefensive());
            }
            //封装TeamPlayerDTO
            TeamPlayerDTO teamPlayerDTO=new TeamPlayerDTO();
            teamPlayerDTO.setUpid(userPlayer.getUpId());
            teamPlayerDTO.setBasicPlayerDTO(basicPlayerDTO);
            teamPlayerDTO.setTid(userPlayer.getTeamId());
            teamPlayerDTO.setSalary(userPlayer.getUpSalary());
            teamPlayerDTO.setPosition(userPlayer.getUpPosition());
            teamPlayerDTOList.add(teamPlayerDTO);
        }
        return teamPlayerDTOList;
    }


//    @Transactional
//    @Override
//    public Integer exchangeTeamPlayer(Long tid, Long exchangeFromUpid, Long exchangeToUpid) {
//        //获取from球员的position
//        UserPlayerEntityExample userPlayerExample=new UserPlayerEntityExample();
//        userPlayerExample.createCriteria().andTidEqualTo(tid).andUpidEqualTo(exchangeFromUpid);
//        List<UserPlayerEntity> userPlayerList = userPlayerMapper.selectByExample(userPlayerExample);
//        if(userPlayerList.isEmpty())
//            return 0;
//        int fromposition=userPlayerList.get(0).getPosition();
//
//        userPlayerExample.clear();
//        userPlayerExample.createCriteria().andTidEqualTo(tid).andUpidEqualTo(exchangeToUpid);
//        userPlayerList= userPlayerMapper.selectByExample(userPlayerExample);
//        if(userPlayerList.isEmpty())
//            return 0;
//        int toposition=userPlayerList.get(0).getPosition();
//
//        //交换
//        UserPlayerEntity fromuserPlayer=new UserPlayerEntity();
//        fromuserPlayer.setUpid(exchangeFromUpid);
//        fromuserPlayer.setPosition(toposition);
//        int result=userPlayerMapper.updateByPrimaryKeySelective(fromuserPlayer);
//        UserPlayerEntity touserPlayer=new UserPlayerEntity();
//        touserPlayer.setUpid(exchangeToUpid);
//        touserPlayer.setPosition(fromposition);
//        result+=userPlayerMapper.updateByPrimaryKeySelective(touserPlayer);
//        if(result!=2)
//            return 0;
//        return 1;
//    }
//
//    @Transactional
//    @Override
//    public Integer fireTeamPlayer(Integer tid, Integer upid) {
//        //第一步：将被解雇球员的status设为2，表示进入自由市场
//        UserPlayerEntityExample userPlayerExample=new UserPlayerEntityExample();
//        userPlayerExample.createCriteria().andUpidEqualTo(upid).andTidEqualTo(tid);
//        UserPlayerEntity firedUserPlayer=new UserPlayerEntity();
//        firedUserPlayer.setStatus(2);
//        int result = userPlayerMapper.updateByExampleSelective(firedUserPlayer, userPlayerExample);
//        if(result < 1)
//            return 0;
//        //第二步：调整球员位置
//        firedUserPlayer=userPlayerService.getUserPlayerByUpid(upid);
//        int firedPlayerPosition=firedUserPlayer.getPosition();
//        adjustFiredPlayer(firedUserPlayer.getTid(),firedPlayerPosition);
//        //第三步：封装firedTeamPlayerDTO，创建自由球员，球员正式流入自由市场
//        TeamPlayerDTO firedTeamPlayerDTO=new TeamPlayerDTO();
//        userPlayerExample.clear();
//        userPlayerExample.createCriteria().andUpidEqualTo(upid);
//        List<UserPlayerEntity> userPlayerList = userPlayerMapper.selectByExample(userPlayerExample);
//        if(userPlayerList.isEmpty())
//            return 0;
//        Integer firedPlayerBpid=userPlayerList.get(0).getBpid();
//        firedTeamPlayerDTO.setUpid(upid);
//        firedTeamPlayerDTO.setPosition(firedUserPlayer.getPosition());
//        firedTeamPlayerDTO.setSalary(firedUserPlayer.getSalary());
//        firedTeamPlayerDTO.setTid(firedUserPlayer.getTid());
//        firedTeamPlayerDTO.setBasicPlayerDTO(new BasicPlayerDTO(basicPlayerService.getBasicPlayerByBpid(firedPlayerBpid)));
//
//        freeMarketService.createFreeMarketPlayer(firedTeamPlayerDTO, 1);
//        return result;
//    }

    /*
     * 如果解雇的是首发，则使用第一替补（position=6）进行替换，并将后面的球员向前移动
     * 如果解雇的是替补，则将该替补后的所有替补向前移动一个
     * 如果是未激活球员，则将该球员后的所有未激活球员向前移动一个
     */
    private void adjustFiredPlayer(Integer tid,Integer position){
        if(position<=5){
            userPlayerMapper.substituteForFiredPlayer(tid,position);
            userPlayerMapper.adjustFiredPlayerPostion(tid,6);
        }else if(position<=12){
            userPlayerMapper.adjustFiredPlayerPostion(tid,position);
        }else{
            userPlayerMapper.adjustUnregFiredPlayerPosition(tid,position);
        }
    }

//    @Override
//    public TeamPlayerDTO getTeamPlayerDTOByUpid(Integer upid) {
//        UserPlayerEntityExample userPlayerExample=new UserPlayerEntityExample();
//        userPlayerExample.createCriteria().andUpidEqualTo(upid);
//        List<UserPlayerEntity> userPlayerList = userPlayerMapper.selectByExample(userPlayerExample);
//        if(userPlayerList.isEmpty())
//            return null;
//        TeamPlayerDTO teamPlayerDTO=new TeamPlayerDTO(userPlayerList.get(0));
//        return teamPlayerDTO;
//    }


    //自动调整最佳阵容
}
