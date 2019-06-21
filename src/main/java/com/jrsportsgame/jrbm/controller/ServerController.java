package com.jrsportsgame.jrbm.controller;

import com.jrsportsgame.jrbm.model.Teaminfo;
import com.jrsportsgame.jrbm.service.intf.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**

 *@desc 选择服务器及对应球队

 *@author  koliday

 *@date  2019/6/18
 */
@Controller
public class ServerController {
    @Autowired
    private ServerService serverService;
    @PostMapping("/getTeamInfo")
    public String getTeamInfo(HttpServletRequest request,
                              Model model){
        Integer uid= (Integer) request.getSession().getAttribute("uid");
        List<Teaminfo> teamInfoList = serverService.getTeamInfoList(uid);
        model.addAttribute("teamInfoList",teamInfoList);
        return "server";
    }
    @PostMapping("/createNewTeam")
    public String createNewTeam(HttpServletRequest request,
                             Model model){
        Integer uid= (Integer) request.getSession().getAttribute("uid");
        String teamName=request.getParameter("teamName");
        Integer sid= Integer.valueOf(request.getParameter("serverId"));
        Integer createResult=serverService.createNewTeam(uid,teamName,sid);
        if(createResult>0){

        }
        return "index";
    }
}
