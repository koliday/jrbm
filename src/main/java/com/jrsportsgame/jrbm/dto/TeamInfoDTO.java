package com.jrsportsgame.jrbm.dto;

import lombok.Getter;
import lombok.Setter;

/**

 *@desc
 * 球队基本信息的传输模型

 *@author  koliday

 *@date  2019/6/24
 */
@Getter
@Setter
public class TeamInfoDTO {
    private String teamName;
    private Integer offensive;
    private Integer defensive;
    private Integer jrCoin;
    private Integer jrDiamond;
    private Integer exp;
}
