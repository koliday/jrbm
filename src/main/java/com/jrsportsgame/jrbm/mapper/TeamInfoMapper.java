package com.jrsportsgame.jrbm.mapper;

import com.jrsportsgame.jrbm.entity.TeamInfoEntity;
import com.jrsportsgame.jrbm.entity.UserEntity;
import com.jrsportsgame.jrbm.entity.UserPlayerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeamInfoMapper {
    TeamInfoEntity getTeamInfoByTeamId(@Param("teamId") Long teamId);

}
