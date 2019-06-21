package com.jrsportsgame.jrbm.interceptor;

import com.koliday.springproject.springbootexercise.Model.User;
import com.koliday.springproject.springbootexercise.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    /*
     * 如果session中有uid和tid，则重定向为myteam
     * 如果session中有uid但没有tid，则重定向为server
     * 如果session中没有uid和tid，则重定向为/
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HttpSession session,Object handler) throws Exception {
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if("token".equals(cookie.getName())){
                    String token=cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
