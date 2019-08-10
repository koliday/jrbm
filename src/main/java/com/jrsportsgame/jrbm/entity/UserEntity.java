package com.jrsportsgame.jrbm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
public class UserEntity {
    private Long userId;
    private String userName;
    private String password;
    private String userAvatar;
    private Integer userVip;
    private Long createTime;
    private Long lastLoginTime;
    private Integer userStatus;
}
