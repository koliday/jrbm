package com.jrsportsgame.jrbm.dto;

import com.jrsportsgame.jrbm.entity.BasicPlayerEntity;
import com.jrsportsgame.jrbm.entity.UserPlayerEntity;
import com.jrsportsgame.jrbm.service.intf.BasicPlayerService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**

 *@desc
 * 某球队某球员的所有信息的传输模型
 *@author  koliday

 *@date  2019/6/24
 */

@Getter
@Setter
public class TeamPlayerDTO {
    @Autowired
    private BasicPlayerService basicPlayerService;
    //用户球员ID
    private Long upid;
    //对应的基础球员信息
    private BasicPlayerDTO basicPlayerDTO;
    //所在的球队ID
    private Long tid;
    //球员工资
    private Long salary;
    //球员在球队中的位置
    private Integer position;

    public TeamPlayerDTO(UserPlayerEntity userPlayer) {
        this.upid=userPlayer.getUpId();
        this.tid=userPlayer.getTeamId();
        this.salary=userPlayer.getUpSalary();
        this.position=userPlayer.getUpPosition();
        BasicPlayerEntity basicPlayer = basicPlayerService.getBasicPlayerByBpid(userPlayer.getBpId());
        this.basicPlayerDTO=new BasicPlayerDTO(basicPlayer);
    }

    public TeamPlayerDTO() {
    }
}
