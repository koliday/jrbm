package com.jrsportsgame.jrbm.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface MyFreeMarketMapper {
    Integer changeUserPlayerStatusByFpid(int fpid);
}
