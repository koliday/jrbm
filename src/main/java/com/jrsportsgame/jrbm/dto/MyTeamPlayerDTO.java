package com.jrsportsgame.jrbm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyTeamPlayerDTO {
    private Integer upid;
    private String chname;
    private Integer offensive;
    private Integer deffensive;
    private String position;

    public MyTeamPlayerDTO(Integer upid, String chname, Integer offensive, Integer deffensive, String position) {
        this.upid = upid;
        this.chname = chname;
        this.offensive = offensive;
        this.deffensive = deffensive;
        this.position = position;
    }
}
