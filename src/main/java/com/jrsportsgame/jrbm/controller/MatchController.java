package com.jrsportsgame.jrbm.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class MatchController {

    @GetMapping("/matching")
    public String getMatchingPage(){
        return "rankingmatch";
    }

    @PostMapping("/startmatching")
    @ResponseBody
    public String startMatching(HttpSession session){
        Long tid= (Long) session.getAttribute("tid");
        return JSON.toJSONString(tid);
    }
}
