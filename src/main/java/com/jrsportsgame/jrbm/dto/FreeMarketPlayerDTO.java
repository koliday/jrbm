package com.jrsportsgame.jrbm.dto;

import com.jrsportsgame.jrbm.model.Basicplayer;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FreeMarketPlayerDTO {
    private Integer fpid;
    private Basicplayer basicplayer;
    private Date freetime;
    private Integer source;

    public FreeMarketPlayerDTO(Integer fpid, Basicplayer basicplayer, Date freetime, Integer source) {
        this.fpid = fpid;
        this.basicplayer = basicplayer;
        this.freetime = freetime;
        this.source = source;
    }
}
