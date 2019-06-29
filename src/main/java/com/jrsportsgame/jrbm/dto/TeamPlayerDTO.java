package com.jrsportsgame.jrbm.dto;

import com.jrsportsgame.jrbm.mapper.BasicplayerMapper;
import com.jrsportsgame.jrbm.model.Basicplayer;
import com.jrsportsgame.jrbm.model.BasicplayerExample;
import com.jrsportsgame.jrbm.model.Userplayers;
import com.jrsportsgame.jrbm.service.intf.BasicPlayerService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    private Integer upid;
    //对应的基础球员信息
    private BasicPlayerDTO basicPlayerDTO;
    //所在的球队ID
    private Integer tid;
    //球员工资
    private Integer salary;
    //球员在球队中的位置
    private Integer position;

    public TeamPlayerDTO(Userplayers userplayers) {
        this.upid=userplayers.getUpid();
        this.tid=userplayers.getTid();
        this.salary=userplayers.getSalary();
        this.position=userplayers.getPosition();
        Basicplayer basicPlayer = basicPlayerService.getBasicPlayerByBpid(userplayers.getBpid());
        this.basicPlayerDTO=new BasicPlayerDTO(basicPlayer);
    }

    public TeamPlayerDTO() {
    }
}
