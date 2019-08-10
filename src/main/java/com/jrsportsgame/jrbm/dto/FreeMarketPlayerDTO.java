package com.jrsportsgame.jrbm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**

 *@desc 自由市场球员的数据传输模型

 *@author  koliday

 *@date  2019/6/26
 */
@Getter
@Setter
public class FreeMarketPlayerDTO {
    //自由球员ID
    private Integer fpid;
    //基础球员类
    private TeamPlayerDTO teamPlayerDTO;
    //球员进入自由市场的时间
    private Date freetime;
    //球员剩余时间
    private String timeleft;
    //球员的来源，1-玩家解雇，2-合同到期，3-选秀大会落选
    private Integer source;



    public FreeMarketPlayerDTO() {
    }
}
