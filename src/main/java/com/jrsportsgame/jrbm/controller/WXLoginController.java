package com.jrsportsgame.jrbm.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.jrsportsgame.jrbm.entity.WXTokenEntity;
import com.jrsportsgame.jrbm.entity.WXUserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class WXLoginController {
    @GetMapping("/wxlogin")
    @ResponseBody
    public String wxLogin(HttpServletRequest request) throws IOException {
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
        WXUserEntity user=JSON.parseObject(user_content, WXUserEntity.class);
        System.out.println(user.toString());
        return JSON.toJSONString(user);
    }
}
