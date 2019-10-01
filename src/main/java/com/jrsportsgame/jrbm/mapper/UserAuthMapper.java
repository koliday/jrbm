package com.jrsportsgame.jrbm.mapper;

import com.jrsportsgame.jrbm.entity.UserAuthEntity;
import com.jrsportsgame.jrbm.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAuthMapper {
    UserAuthEntity getWXUserByOpenId(@Param("wxOpenId") String wxOpenId);
}
