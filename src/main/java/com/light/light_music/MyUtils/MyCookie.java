package com.light.light_music.MyUtils;

import javax.servlet.http.Cookie;

/**
 * cookie
 *
 * @Author : KangXu
 * @Date : 2020/9/2
 * @Package : com.light.light_music.MyUtils
 */


public class MyCookie {
    /**
     * 查询cookie
     * @param name：要查询的cookie名
     * @param cookies：所有的cookie
     * @return：cookie对象
     */
    public static Cookie findCookie(String name, Cookie[] cookies){
        if (name == null || cookies == null || cookies.length == 0){
            return null;
        }
        for (Cookie cookie : cookies) {
            if(name.equals(cookie.getName())){
                return cookie;
            }
        }
        return null;
    }
}
