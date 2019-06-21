package com.jrsportsgame.jrbm.controller;

import com.jrsportsgame.jrbm.dto.MyTeamPlayerDTO;
import com.jrsportsgame.jrbm.model.Teaminfo;
import com.jrsportsgame.jrbm.service.intf.MyTeamService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**

 *@desc  我的球队
 *@author  koliday
 *@date  2019/6/19
 */
@Controller
public class TeamController {
    @Autowired
    private MyTeamService myTeamService;
    /*
     * 加载我的球队
     */
    @GetMapping("/myteam")
    public String myTeam(HttpSession session,
                         Model model){
        Integer uid= (Integer) session.getAttribute("uid");
        Integer tid= (Integer) session.getAttribute("tid");
        //获取我的球队信息
        Teaminfo teaminfo=myTeamService.getMyTeamInfo(tid);
        //获取我的球员信息列表
        List<MyTeamPlayerDTO> myTeamPlayerDTOList=myTeamService.getMyTeamPlayerList(tid);

        model.addAttribute("myteamplayerlist", myTeamPlayerDTOList);
        model.addAttribute("teaminfo",teaminfo);
        return "myteam";
    }

}
