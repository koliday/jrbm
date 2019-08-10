package com.jrsportsgame.jrbm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPlayerEntity {
    private Long upId;
    private Integer bpId;
    private Long teamId;
    private Long upSalary;
    private Integer upPosition;
    private Integer upStatus;
}
