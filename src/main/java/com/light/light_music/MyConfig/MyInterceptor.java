package com.light.light_music.MyConfig;

import com.light.light_music.MyUtils.MyCookie;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的拦截器
 *
 * @Author : KangXu
 * @Date : 2020/8/25
 * @Package : com.light.light_music.MyConfig
 */


public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie loginUser = MyCookie.findCookie("loginUser",request.getCookies());
        List<String> requestList = new ArrayList<>();
        requestList.add("/");
        requestList.add("/index");
        requestList.add("/index.html");
        requestList.add("/toLogin.do");
        requestList.add("/user/login.do");
        requestList.add("/toRegister.do");
        requestList.add("/user/saveUser.do");
        requestList.add("/toAppeal.do");
        requestList.add("/user/appeal.do");
        requestList.add("/user/getCount.do");
        if (request.getServletPath().contains("admin")) {
            return true;
        } else {
            if (null == loginUser && !requestList.contains(request.getServletPath())) {
                request.setAttribute("msg", "对不起,您没有权限访问,请登录");
                request.getRequestDispatcher("/toLogin.do").forward(request, response);
                return false;
            } else {
                return true;
            }
        }
    }
}
