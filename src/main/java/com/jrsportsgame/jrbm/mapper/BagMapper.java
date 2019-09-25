package com.jrsportsgame.jrbm.mapper;

import com.jrsportsgame.jrbm.entity.BagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BagMapper {
    List<BagEntity> getBagListByTeamId(@Param("teamId")Long teamId);
}
