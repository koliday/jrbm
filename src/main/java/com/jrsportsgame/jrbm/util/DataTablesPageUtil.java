package com.jrsportsgame.jrbm.util;

import com.jrsportsgame.jrbm.dto.FreeMarketPlayerDTO;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Getter
@Setter
public class DataTablesPageUtil<E> {
    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private List<E> data;

}
