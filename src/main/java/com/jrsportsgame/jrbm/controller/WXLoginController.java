package com.jrsportsgame.jrbm.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.jrsportsgame.jrbm.entity.WXTokenEntity;
import com.jrsportsgame.jrbm.entity.WXUserEntity;
import com.jrsportsgame.jrbm.service.intf.LoginService;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@Slf4j
public class WXLoginController {
    @Autowired
    private LoginService loginService;
    @GetMapping("/wxlogin")
    public String wxLogin(HttpServletRequest request, Model model, HttpSession session) {
        String code = request.getParameter("code");
        if(code==null){
            System.out.println("用户终止授权登录");
            return null;
        }
        String token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7287a60bb700fd21" +
                "&secret=1ef8755f92bebae8ad7bab432ba29cbf&code="+code+"&grant_type=authorization_code";
        String token_content = HttpUtil.get(token_url);
        WXTokenEntity token = JSON.parseObject(token_content, WXTokenEntity.class);
        String user_url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token.getAccess_token()+"&openid="+token.getOpenid();
        String user_content = HttpUtil.get(user_url);
        WXUserEntity wxUser=JSON.parseObject(user_content, WXUserEntity.class);
        Long loginResult = loginService.loginByWeixin(wxUser);
        log.info("登录uid"+loginResult);
        if(loginResult>0){
            session.setAttribute("uid", loginResult);
            //这里tid仅做实验使用
            session.setAttribute("tid",1L);
            session.setAttribute("username",wxUser.getNickname());
            session.setAttribute("avatar",wxUser.getHeadimgurl());
            return "redirect:/myteam";
        }
        model.addAttribute("loginResult","微信账号不存在");
        return "login";
    }
}
