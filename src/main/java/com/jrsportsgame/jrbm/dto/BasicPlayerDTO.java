package com.jrsportsgame.jrbm.dto;

import com.jrsportsgame.jrbm.entity.BasicPlayerEntity;
import lombok.Getter;
import lombok.Setter;
/**

 *@desc
 * 基础球员的所有信息的传输模型

 *@author  koliday

 *@date  2019/6/24
 */
@Getter
@Setter
public class BasicPlayerDTO {
    //基础球员ID
    private Integer bpid;
    //基础球员英文名
    private String enname;
    //基础球员中文名
    private String chname;
    //基础球员位置
    private String position;
    //基础球员进攻能力
    private Integer offensive;
    //基础球员防守能力
    private Integer defensive;

    public BasicPlayerDTO(BasicPlayerEntity basicplayer) {
        this.bpid=basicplayer.getBpId();
        this.enname=basicplayer.getBpEnName();
        this.chname=basicplayer.getBpChName();
        this.position=basicplayer.getBpPosition();
        this.offensive=basicplayer.getBpOffensive();
        this.defensive=basicplayer.getBpDefensive();
    }

    public BasicPlayerDTO() {
    }
}
