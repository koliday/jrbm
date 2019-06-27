package com.jrsportsgame.jrbm.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsportsgame.jrbm.dto.FreeMarketPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerDTO;
import com.jrsportsgame.jrbm.mapper.FreemarketMapper;
import com.jrsportsgame.jrbm.model.*;
import com.jrsportsgame.jrbm.service.intf.BasicPlayerService;
import com.jrsportsgame.jrbm.service.intf.FreeMarketService;
import com.jrsportsgame.jrbm.service.intf.UserPlayerService;
import com.jrsportsgame.jrbm.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FreeMarketServiceImpl implements FreeMarketService {
    @Autowired
    private FreemarketMapper freemarketMapper;
    @Autowired
    private BasicPlayerService basicPlayerService;
    @Autowired
    private UserPlayerService userPlayerService;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

//    @Override
//    public PageInfo<FreeMarketPlayerDTO> getFreeMarketPlayerList(Integer pagenum, Integer pagesize) {
//        PageHelper.startPage(pagenum,pagesize);
//        List<Freemarket> freemarketList= getAllFreeMarketPlayerList();
//        List<FreeMarketPlayerDTO> freeMarketPlayerDTOList=new ArrayList<>();
//        for(Freemarket freemarket:freemarketList){
//            //根据upid得到bpid
//            Integer upid=freemarket.getUpid();
//            Userplayers userPlayer = userPlayerService.getUserPlayerByUpid(upid);
//            //根据bpid得到basicplayer
//            Basicplayer basicplayer=basicPlayerService.getBasicPlayerByBpid(userPlayer.getBpid());
//            //将
//            //注入DTO
//            FreeMarketPlayerDTO freeMarketPlayerDTO =
//                    new FreeMarketPlayerDTO(freemarket.getFpid(),basicplayer,freemarket.getFreetime(), freemarket.getSource());
//            freeMarketPlayerDTO.setFpid(freemarket.getFpid());
//            freeMarketPlayerDTO.setFreetime(freemarket.getFreetime());
//            freeMarketPlayerDTO.setSource(freemarket.getSource());
//            freeMarketPlayerDTO.setTeamPlayerDTO();
//            freeMarketPlayerDTOList.add(freeMarketPlayerDTO);
//        }
//        PageInfo<FreeMarketPlayerDTO> pageInfo=new PageInfo(freeMarketPlayerDTOList);
//        return pageInfo;
//    }






    @Override
    public void createFreeMarketPlayer(TeamPlayerDTO teamPlayerDTO,Integer source) {
        RedisSerializer redisKeySerializer=new StringRedisSerializer();
        RedisSerializer redisValueSerializer=new Jackson2JsonRedisSerializer(Object.class);
        redisTemplate.setKeySerializer(redisKeySerializer);
        redisTemplate.setValueSerializer(redisValueSerializer);
        //创建两个自由市场球员计数器，freemarketplayercount为所有进入过自由市场的历史球员数量，并作为主键
        if(!redisTemplate.hasKey("freemarketplayercount"))
            redisTemplate.opsForValue().set("freemarketplayercount", 0);
        //获取历史球员数量，将其+1作为新的fpid
        int freeMarketPlayerCount= (int) redisTemplate.opsForValue().get("freemarketplayercount");
        //封装FreeMarketPlayerDTO
        FreeMarketPlayerDTO freeMarketPlayerDTO=new FreeMarketPlayerDTO();
        freeMarketPlayerDTO.setFpid(freeMarketPlayerCount+1);
        freeMarketPlayerDTO.setTeamPlayerDTO(teamPlayerDTO);
        //freeMarketPlayerDTO.setFreetime();
        freeMarketPlayerDTO.setSource(source);
        //设置key为fpid，value为freeMarketPlayerDTO的JSON字符串，并为该自由球员设置30分钟的过期时间，这里测试使用3分钟
        redisTemplate.opsForValue().set("fp"+freeMarketPlayerDTO.getFpid(), JSON.toJSONString(freeMarketPlayerDTO),10*60, TimeUnit.SECONDS);
        //将该球员的fpid存入freemarketplayerlist
        redisTemplate.opsForList().rightPush("freemarketplayerlist",freeMarketPlayerDTO.getFpid());
        //以上操作完成后将freemarketplayercount+1
        redisTemplate.opsForValue().increment("freemarketplayercount");
    }

    @Override
    //从列表中freemarketplayerlist移除已过期的upid
    public void removeExpiredFreeMarketPlayer(String fpid){
        redisTemplate.opsForList().remove("freemarketplayerlist", 1, fpid);
    }


    @Override
    public Long getFreeMarketPlayerCount() {
        return redisTemplate.opsForList().size("freemarketplayerlist");
    }

    @Override
    public List<FreeMarketPlayerDTO> getAllFreeMarketPlayerDTOList() {
        return getFreeMarketPlayerDTOPageListByListRange(0,-1);
    }

    @Override
    public List<FreeMarketPlayerDTO> getFreeMarketPlayerDTOPageList(Integer pagenum, Integer pagesize) {
        //Integer start=pagesize*(pagenum-1)+1;
        Integer start=pagenum;
        Integer end=start+pagesize-1;
        return getFreeMarketPlayerDTOPageListByListRange(start,end);
    }

    private List<FreeMarketPlayerDTO> getFreeMarketPlayerDTOPageListByListRange(Integer start,Integer end){
        List<FreeMarketPlayerDTO> freeMarketPlayerDTOList=new ArrayList<>();
        //得到当前自由市场的在给定区间的球员fpid
        List<Object> freeMarketPlayerFpidList=redisTemplate.opsForList().range("freemarketplayerlist", start, end);

        //遍历fpid，取出对应的freeMarketPlayerDTO的JSON字符串并转换为freeMarketPlayerDTO，将其加入list结果集
        for(Object fpid:freeMarketPlayerFpidList){
            String freeMarketPlayerDTOJSON= (String) redisTemplate.opsForValue().get("fp"+fpid);
            //有可能找不到fpid，可能过期却还来不及从list中删除，因此这里再次进行确认和删除，由于到期的一定在左边，因此从左边删除
            if(freeMarketPlayerDTOJSON==null){
                redisTemplate.opsForList().remove("freemarketplayerlist", 1, fpid);
                continue;
            }
            FreeMarketPlayerDTO freeMarketPlayerDTO=JSON.parseObject(freeMarketPlayerDTOJSON,FreeMarketPlayerDTO.class);
            Long expireTime=redisTemplate.getExpire("fp"+fpid, TimeUnit.SECONDS);
            //将过期时间（秒）转化为分：秒
            freeMarketPlayerDTO.setTimeleft(DateTimeUtil.secondsToTime(expireTime));
            freeMarketPlayerDTOList.add(freeMarketPlayerDTO);
        }
        return freeMarketPlayerDTOList;
    }
}
