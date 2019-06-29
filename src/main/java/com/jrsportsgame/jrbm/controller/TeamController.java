package com.jrsportsgame.jrbm.controller;

import com.alibaba.fastjson.JSON;
import com.jrsportsgame.jrbm.dto.TeamInfoDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerDTO;
import com.jrsportsgame.jrbm.dto.TeamPlayerListDTO;
import com.jrsportsgame.jrbm.model.Teaminfo;
import com.jrsportsgame.jrbm.service.intf.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**

 *@desc  我的球队
 *@author  koliday
 *@date  2019/6/19
 */
@Controller
public class TeamController {
    Logger logger= LoggerFactory.getLogger(Logger.class);
    @Autowired
    private TeamService teamService;
    /*
     * 加载我的球队
     */
    @GetMapping("/myteam")
    public String myTeam(HttpSession session,
                         Model model){
        Integer tid= (Integer) session.getAttribute("tid");
        //获取我的球队信息
        TeamInfoDTO teamInfoDTO= teamService.getTeamInfo(tid);
        //获取我的球员信息列表
        TeamPlayerListDTO teamPlayerList = teamService.getTeamPlayerList(tid);


        model.addAttribute("teamPlayerList",teamPlayerList);
        model.addAttribute("teamInfoDTO",teamInfoDTO);
        return "myteam";
    }
    /*
     * 查看球队所有球员，可用于查看可替换球员
     */
    @ResponseBody
    @PostMapping(value = "/getteamplayer",produces = "application/json;charset=UTF-8")
    public String getTeamPlayer(HttpSession session){
        Integer tid= (Integer) session.getAttribute("tid");
        List<TeamPlayerDTO> teamPlayerDTOList = teamService.getTeamPlayerDTOList(tid);
        return JSON.toJSONString(teamPlayerDTOList);
    }

    @ResponseBody
    @PostMapping(value = "/exchangeplayer",produces = "application/json;charset=UTF-8")
    public String exchangePlayer(HttpServletRequest request,
                                 HttpSession session){
        Integer exchangeFromUpid=Integer.valueOf(request.getParameter("exchangefrom"));
        Integer exchangeToUpid=Integer.valueOf(request.getParameter("exchangeto"));
        Integer tid=(Integer)session.getAttribute("tid");
        logger.info(exchangeFromUpid+" "+exchangeToUpid);
        Integer exchangeresult=teamService.exchangeTeamPlayer(tid,exchangeFromUpid,exchangeToUpid);
        logger.info(JSON.toJSONString(exchangeresult));
        return JSON.toJSONString(exchangeresult);
    }

    @ResponseBody
    @PostMapping(value = "/fireplayer",produces = "application/json;charset=UTF-8")
    public String firePlayer(HttpServletRequest request,
                                 HttpSession session){
        Integer upid=Integer.valueOf(request.getParameter("upid"));
        Integer tid=(Integer)session.getAttribute("tid");
        Integer fireResult=teamService.fireTeamPlayer(tid,upid);
        return JSON.toJSONString(fireResult);
    }
}
