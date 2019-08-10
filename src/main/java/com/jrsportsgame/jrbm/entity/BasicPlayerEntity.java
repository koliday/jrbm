package com.jrsportsgame.jrbm.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BasicPlayerEntity {
    private Integer bpId;
    private String bpEnName;
    private String bpChName;
    private String bpPosition;
    private Integer bpOffensive;
    private Integer bpDefensive;
}
