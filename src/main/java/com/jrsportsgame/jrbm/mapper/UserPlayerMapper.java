package com.jrsportsgame.jrbm.mapper;

import com.jrsportsgame.jrbm.entity.UserPlayerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserPlayerMapper {
    void substituteForFiredPlayer(@Param("tid") int tid, @Param("position") int position);

    void adjustFiredPlayerPostion(@Param("tid") int tid, @Param("position") int position);

    void adjustUnregFiredPlayerPosition(@Param("tid") int tid, @Param("position") int position);

    List<UserPlayerEntity> getTeamPlayerListByTeamId(@Param("teamId")Long teamId);

    UserPlayerEntity getUserPlayerByUpid(@Param("upId") Long upId);
}
