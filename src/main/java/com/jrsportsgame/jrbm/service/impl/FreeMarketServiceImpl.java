package com.jrsportsgame.jrbm.service.impl;

import com.alibaba.fastjson.JSON;
import com.jrsportsgame.jrbm.dto.FreeMarketPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerDTO;
import com.jrsportsgame.jrbm.mapper.FreeMarketMapper;
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
//    @Autowired
//    private FreemarketMapper freemarketMapper;
//    @Autowired
//    private FreeMarketMapper freeMarketMapper;
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
        //freemarketplayercount为所有进入过自由市场的历史球员数量计数器，并作为主键
        if(!redisTemplate.hasKey("freemarketplayercount"))
            redisTemplate.opsForValue().set("freemarketplayercount", 0);
        //freemarketplayerstart为当前自由市场的球员的第一个id
        if(!redisTemplate.hasKey("freemarketplayerstart"))
            redisTemplate.opsForValue().set("freemarketplayerstart", 1);

        //获取历史球员数量，将其+1作为新的fpid
        int freeMarketPlayerCount= (int) redisTemplate.opsForValue().get("freemarketplayercount");
        //封装FreeMarketPlayerDTO
        FreeMarketPlayerDTO freeMarketPlayerDTO=new FreeMarketPlayerDTO();
        freeMarketPlayerDTO.setFpid(freeMarketPlayerCount+1);
        freeMarketPlayerDTO.setTeamPlayerDTO(teamPlayerDTO);
        //freeMarketPlayerDTO.setFreetime();
        freeMarketPlayerDTO.setSource(source);
        //设置key为fpid，value为freeMarketPlayerDTO的JSON字符串，并为该自由球员设置30分钟的过期时间，这里测试使用3分钟
        redisTemplate.opsForValue().set("fp"+freeMarketPlayerDTO.getFpid(), JSON.toJSONString(freeMarketPlayerDTO),1*60, TimeUnit.SECONDS);
        //以上操作完成后将freemarketplayercount+1
        redisTemplate.opsForValue().increment("freemarketplayercount");
    }

    @Override
    //upid过期一个，将freemarketplayerstart++,并将该球员的status设为0
    public void removeExpiredFreeMarketPlayer(String fpid){
        redisTemplate.opsForValue().increment("freemarketplayerstart");
        //freeMarketMapper.changeUserPlayerStatusByFpid(Integer.valueOf(fpid.substring(2)));
    }


    @Override
    public Integer getFreeMarketPlayerCount() {
        Integer start=(Integer)redisTemplate.opsForValue().get("freemarketplayerstart");
        Integer count=(Integer)redisTemplate.opsForValue().get("freemarketplayercount");
        if(start == null && count == null)
            return 0;
        return count-start+1;
    }

    @Override
    public List<FreeMarketPlayerDTO> getAllFreeMarketPlayerDTOList() {
        Integer start=(Integer)redisTemplate.opsForValue().get("freemarketplayerstart");
        Integer count=(Integer)redisTemplate.opsForValue().get("freemarketplayercount");
        return getFreeMarketPlayerDTOPageListByRange(start,count-start+1);
    }

    @Override
    public List<FreeMarketPlayerDTO> getFreeMarketPlayerDTOPageList(Integer start, Integer length) {
        return getFreeMarketPlayerDTOPageListByRange(start,length);
    }

    private List<FreeMarketPlayerDTO> getFreeMarketPlayerDTOPageListByRange(Integer start,Integer length){

        List<FreeMarketPlayerDTO> freeMarketPlayerDTOList=new ArrayList<>();

        Integer realStart=(Integer)redisTemplate.opsForValue().get("freemarketplayerstart");
        Integer freeMarketPlayerTotalCount=(Integer)redisTemplate.opsForValue().get("freemarketplayercount");;
        if(realStart == null || freeMarketPlayerTotalCount == null)
            return freeMarketPlayerDTOList;
        System.out.println(start);
        start+=realStart-1;
        int freeMarketPlayerListCount=0;
        //如果个数足够则必须取满length个非空自由球员
        while(freeMarketPlayerListCount<length && start<=freeMarketPlayerTotalCount){
            String freeMarketPlayerDTOJSON= (String) redisTemplate.opsForValue().get("fp"+start);
            //如果是过期的，则继续取
            if(freeMarketPlayerDTOJSON == null){
                System.out.println("expired"+start);
                start++;
                continue;
            }
            System.out.println(start);
            //如果不是空的，封装成FreeMarketPlayerDTO
            FreeMarketPlayerDTO freeMarketPlayerDTO=JSON.parseObject(freeMarketPlayerDTOJSON,FreeMarketPlayerDTO.class);
            Long expireTime=redisTemplate.getExpire("fp"+start, TimeUnit.SECONDS);
            //将过期时间（秒）转化为分：秒
            freeMarketPlayerDTO.setTimeleft(DateTimeUtil.secondsToTime(expireTime));
            freeMarketPlayerDTOList.add(freeMarketPlayerDTO);
            start++;
            freeMarketPlayerListCount++;
        }
        return freeMarketPlayerDTOList;
    }
}
