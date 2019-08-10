package com.jrsportsgame.jrbm.mapper;

import com.jrsportsgame.jrbm.entity.BasicPlayerEntity;
import com.jrsportsgame.jrbm.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BasicPlayerMapper {
    BasicPlayerEntity getBasicPlayerByBpId(@Param("bpId") Integer bpId);
}
