package com.jrsportsgame.jrbm.controller;

import com.jrsportsgame.jrbm.service.intf.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**

 *@desc 登录业务

 *@author  koliday

 *@date  2019/6/18
 */
@Controller
public class LoginController {
    private static final String LOGIN_USERNOTEXISTS="用户不存在，请注册！";
    private static final String LOGIN_WRONGPASSWORD="密码错误！";
    private static final String LOGIN_USERNAMTNOTNULL="用户名不能为空！";
    private static final String LOGIN_PASSWORDNOTNULL="密码不能为空！";
    private static final Map<Long,String> loginExceptionMap=new HashMap<>();
    static{
        loginExceptionMap.put(-1L,LOGIN_USERNOTEXISTS);
        loginExceptionMap.put(-2L,LOGIN_WRONGPASSWORD);
        loginExceptionMap.put(-3L,LOGIN_USERNAMTNOTNULL);
        loginExceptionMap.put(-4L,LOGIN_PASSWORDNOTNULL);
    }
    @Autowired
    private LoginService loginService;



    @GetMapping("/")
    public String getLoginPage(){
        return "login";
    }


    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        Model model,
                        HttpSession session){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        model.addAttribute("username",username);
        model.addAttribute("password",password);

        if(username==null || "".equals(username)){
            model.addAttribute("loginResult",loginExceptionMap.get(-3L));
            return "login";
        }
        if(password==null || "".equals(password)){
            model.addAttribute("loginResult",loginExceptionMap.get(-4L));
            return "login";
        }
        Long loginResult=loginService.loginByUsername(username,password);
        //登录成功，loginResult为uid
        if(loginResult>0){
            session.setAttribute("uid", loginResult);
            //这里tid仅做实验使用
            session.setAttribute("tid",loginResult);
            session.setAttribute("username",username);
            session.setAttribute("avatar","/img/profile.jpg" );
            return "redirect:/myteam";
        }
        model.addAttribute("loginResult",loginExceptionMap.get(loginResult));
        return "login";
    }

    @GetMapping("/logoff")
    public String logoff(HttpSession session){
        session.removeAttribute("uid");
        session.removeAttribute("username");
        session.removeAttribute("tid");
        return "redirect:/";
    }

}
