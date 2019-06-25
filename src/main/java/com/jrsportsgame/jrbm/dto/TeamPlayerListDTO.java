package com.jrsportsgame.jrbm.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**

 *@desc
 * 用于存放该球队球员的所有信息的传输模型
 * 所有球员均按他在球队的position顺序排放

 *@author  koliday

 *@date  2019/6/24
 */
@Getter
@Setter
public class TeamPlayerListDTO {
    //首发5人的信息
    private List<TeamPlayerDTO> starterList;
    //替补7人的信息
    private List<TeamPlayerDTO> subList;
    //非注册球员3人的信息
    private List<TeamPlayerDTO> unregList;

}
