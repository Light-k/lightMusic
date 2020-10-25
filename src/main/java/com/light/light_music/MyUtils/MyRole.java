package com.light.light_music.MyUtils;

import com.light.light_music.pojo.User;
import com.light.light_music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * 升级会员工具类
 *
 * @Author : KangXu
 * @Date : 2020/7/4
 * @Package : com.light.light_music.MyUtils
 */

@Component
public class MyRole {
    @Autowired
    MyEmail myEmail;
    @Autowired
    UserService userService;

    /**
     * 升级或注销会员
     * @param user：用户
     * @param logoutTime：用户注销的时间
     * @param lastLoginTime：用户上次登录的时间
     * @return 1 会员 / 0 普通用户
     */
    public Integer toRoleOrToUser(User user, Date logoutTime, Date lastLoginTime) {
        //升级会员
        if (user.getRole() == 0) {
            //统计在线时长(以h为单位)
            Long result = (logoutTime.getTime() - user.getLoginTime().getTime()) / 1000 / 60 / 60;
            if (result >= 2) {
                myEmail.sendSimpleMail("1343450971@qq.com", user.getEmail(), "升级会员", "恭喜您成为light音乐的尊贵会员");
                user.setCounts(0);          //将用户的试听次数归0
                userService.update(user);
                return 1;       //会员
            } else {
                return 0;       //普通用户
            }
        }
        //注销会员
        else {
            //统计在线时长(以day为单位)
            Long result = (user.getLoginTime().getTime() - lastLoginTime.getTime()) / 1000 / 60 / 60 / 24;
            if (result > 2) {
                myEmail.sendSimpleMail("1343450971@qq.com", user.getEmail(), "失效会员", "很遗憾您的会员已过期");
                return 0;       //普通用户
            } else {
                return 1;       //会员
            }
        }
    }

}
