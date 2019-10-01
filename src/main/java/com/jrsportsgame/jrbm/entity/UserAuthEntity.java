package com.jrsportsgame.jrbm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthEntity {
    private Long userAuthId;
    private Long userId;
    private Integer authType;
    private String authId;
    private String authName;
    private String authAvatar;
}
