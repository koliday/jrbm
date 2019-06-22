package com.jrsportsgame.jrbm.interceptor;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    /*
     * 拦截规则如下：
     * 由于我们对根目录/是不拦截的，因此这里需要对是根目录的情况做一个判断
     * 如果session中没有uid也没有tid并且请求是根目录/，放行让其返回登录页面即可
     * 如果session中有uid和tid，即既登录又选择了球队，则重定向为/myteam进入球队页面
     * 如果session中有uid但没有tid，即只登录但没选择球队，则重定向为/server进入球队服务器选择页面
     * 如果session中没有uid和tid，即没有登录且使其他请求，则重定向为/，并保存登录后重定向的地址
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
        Object uid=session.getAttribute("uid");
        Object tid=session.getAttribute("tid");
        //访问根目录且未登录，进入登录页面，为了防止循环重定向
        if((uid==null || "".equals(uid)) && (tid==null || "".equals(tid)) && "/".equals(request.getRequestURI())){
            return true;
        }
        //未登录，重定向到根目录
        if(uid==null || "".equals(uid)){
            response.sendRedirect("/");
            return false;
        }
        //登录但未选择球队，重定向到/server
        if(tid==null || "".equals(tid)){
            response.sendRedirect("/server");
            return false;
        }
        //登录且选择球队且访问根目录，重定向到球队主页
        if("/".equals(request.getRequestURI())){
            response.sendRedirect("/myteam");
            return false;
        }
        //其他放行
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
