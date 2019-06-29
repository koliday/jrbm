package com.jrsportsgame.jrbm.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserPlayersMapper {
    void substituteForFiredPlayer(@Param("tid") int tid, @Param("position") int position);
    void adjustFiredPlayerPostion(@Param("tid") int tid, @Param("position") int position);
    void adjustUnregFiredPlayerPosition(@Param("tid") int tid, @Param("position") int position);
}
