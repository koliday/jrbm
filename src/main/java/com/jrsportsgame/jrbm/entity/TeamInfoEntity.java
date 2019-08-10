package com.jrsportsgame.jrbm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamInfoEntity {
    private Long teamId;
    private Long userId;
    private Integer serverId;
    private String teamName;
    private Long teamCoin;
    private Long teamDiamond;
    private Long teamExp;
}
