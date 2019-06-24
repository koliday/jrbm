package com.jrsportsgame.jrbm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jrsportsgame.jrbm.dto.FreeMarketPlayerDTO;
import com.jrsportsgame.jrbm.mapper.FreemarketMapper;
import com.jrsportsgame.jrbm.model.Basicplayer;
import com.jrsportsgame.jrbm.model.Freemarket;
import com.jrsportsgame.jrbm.model.FreemarketExample;
import com.jrsportsgame.jrbm.model.Userplayers;
import com.jrsportsgame.jrbm.service.intf.BasicPlayerService;
import com.jrsportsgame.jrbm.service.intf.FreeMarketService;
import com.jrsportsgame.jrbm.service.intf.UserPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FreeMarketServiceImpl implements FreeMarketService {
    @Autowired
    private FreemarketMapper freemarketMapper;
    @Autowired
    private BasicPlayerService basicPlayerService;
    @Autowired
    private UserPlayerService userPlayerService;

    @Override
    public PageInfo<FreeMarketPlayerDTO> getFreeMarketPlayerList(Integer pagenum, Integer pagesize) {
        PageHelper.startPage(pagenum,pagesize);
        List<Freemarket> freemarketList= getAllFreeMarketPlayerList();

        List<FreeMarketPlayerDTO> freeMarketPlayerDTOList=new ArrayList<>();
        for(Freemarket freemarket:freemarketList){
            //根据upid得到bpid
            Integer upid=freemarket.getUpid();
            Userplayers userPlayer = userPlayerService.getUserPlayerByUpid(upid);
            //根据bpid得到basicplayer
            Basicplayer basicplayer=basicPlayerService.getBasicPlayerByBpid(userPlayer.getBpid());
            //注入DTO
            FreeMarketPlayerDTO freeMarketPlayerDTO =
                    new FreeMarketPlayerDTO(freemarket.getFpid(),basicplayer,freemarket.getFreetime(), freemarket.getSource());
            freeMarketPlayerDTOList.add(freeMarketPlayerDTO);
        }
        PageInfo<FreeMarketPlayerDTO> pageInfo=new PageInfo(freeMarketPlayerDTOList);
        return pageInfo;
    }

    @Override
    public List<Freemarket> getAllFreeMarketPlayerList() {
        FreemarketExample freemarketExample=new FreemarketExample();
        freemarketExample.createCriteria();
        List<Freemarket> freemarketList=freemarketMapper.selectByExample(freemarketExample);
        return freemarketList;
    }

    @Override
    public Integer getFreeMarketCount() {
        FreemarketExample freemarketExample=new FreemarketExample();
        freemarketExample.createCriteria();
        Long count=freemarketMapper.countByExample(freemarketExample);
        return count.intValue();
    }
}
