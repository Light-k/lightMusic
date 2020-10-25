package com.light.light_music.MyUtils;

import com.light.light_music.pojo.User;
import com.light.light_music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时任务
 *
 * @Author : KangXu
 * @Date : 2020/7/21
 * @Package : com.light.light_music.MyUtils
 */

@Component
public class MyTimer {

    @Autowired
    MyEmail myEmail;
    @Autowired
    UserService userService;

    /**
     * 仅执行一次的定时任务
     * @param receiver：收件人
     * @param password：用户的登录密码
     * @param delay：延迟执行本次任务的时间
     */
    public void timer(String receiver,String password,Long delay){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                myEmail.sendSimpleMail("1343450971@qq.com",receiver,"申诉结果","你的登录密码是：" + password);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,delay);
    }

    /**
     * 执行一次更新验证次数任务
     * @param user：更新的用户
     * @param delay：延迟执行本次任务的时间
     */
    public void updateAuth(User user,Long delay){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                user.setAuth(3);
                userService.update(user);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,delay);
    }

}
