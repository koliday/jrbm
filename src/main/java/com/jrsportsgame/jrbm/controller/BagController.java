package com.jrsportsgame.jrbm.controller;

import com.jrsportsgame.jrbm.dto.TeamInfoDTO;
import com.jrsportsgame.jrbm.entity.BagEntity;
import com.jrsportsgame.jrbm.service.intf.BagService;
import com.jrsportsgame.jrbm.service.intf.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BagController {
    @Autowired
    private BagService bagService;
    @Autowired
    private TeamService teamService;
    @GetMapping("/bag")
    public String getBagPage(Model model, HttpSession session){
        Long teamId=(Long)session.getAttribute("tid");
        List<BagEntity> bagList=bagService.getBagListByTeamId(teamId);
        TeamInfoDTO teamInfo = teamService.getTeamInfo(teamId);
        model.addAttribute("teamInfo",teamInfo);
        model.addAttribute("bagList", bagList);
        return "bag";
    }

}
