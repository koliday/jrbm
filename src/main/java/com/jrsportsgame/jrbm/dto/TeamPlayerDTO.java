package com.jrsportsgame.jrbm.dto;

import com.jrsportsgame.jrbm.model.Userplayers;
import lombok.Getter;
import lombok.Setter;

/**

 *@desc
 * 某球队某球员的所有信息的传输模型
 *@author  koliday

 *@date  2019/6/24
 */

@Getter
@Setter
public class TeamPlayerDTO {
    //用户球员ID
    private Integer upid;
    //对应的基础球员信息
    private BasicPlayerDTO basicPlayerDTO;
    //所在的球队ID
    private Integer tid;
    //球员工资
    private Integer salary;
    //球员在球队中的位置
    private Integer position;


}
