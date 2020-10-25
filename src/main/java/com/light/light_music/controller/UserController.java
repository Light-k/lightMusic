package com.light.light_music.controller;

import com.light.light_music.MyUtils.*;
import com.light.light_music.pojo.User;
import com.light.light_music.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * user类的controller层
 *
 * @Author : KangXu
 * @Date : 2020/7/3
 * @Package : com.light.light_music.controller
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    MyRole myRole;
    @Autowired
    MyTimer myTimer;

    @ApiOperation("用户登录")
    @PostMapping(value = "/login.do")
    public String login(@ApiParam("用户对象") User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        User loginUser = userService.findByEmail(user.getEmail());
        if (null != loginUser) {
            if (user.getPassword().equals(loginUser.getPassword())) {
                //保存用户的昵称
                Cookie cookie = new Cookie("loginUser", loginUser.getNickname());
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(cookie);
                //设置登录时间
                Date loginTime = new Date();
                //查询上次登录时间
                Date lastLoginTime = (Date) request.getSession().getAttribute("lastLoginTime");
                //封装数据
                loginUser.setLoginTime(loginTime);
                loginUser.setLastLoginTime(lastLoginTime);
                userService.update(loginUser);
                //保存用户的uuid值
                request.getSession().setAttribute("uuid", loginUser.getUuid());
                return "redirect:/index";
            } else {
                model.addAttribute("msg", "密码错误");
                return "user/login";
            }
        } else {
            model.addAttribute("msg", "账号不存在");
            return "user/login";
        }
    }

    @ApiOperation("用户注销")
    @GetMapping(value = "/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            User user = userService.findByNickname(nickname);
            //设置离线时间
            Date logoutTime = new Date();
            //计算在线时间
            Date onlineTime = MyDate.getOnlineTime(user.getLoginTime(), logoutTime);
            //记录上次登录的时间
            Date lastLoginTime = user.getLoginTime();
            request.getSession().setAttribute("lastLoginTime", lastLoginTime);
            //判断是否升级成会员
            Integer role = myRole.toRoleOrToUser(user, logoutTime, lastLoginTime);
            //封装数据
            user.setRole(role);
            user.setLogoutTime(logoutTime);
            user.setOnlineTime(onlineTime);
            user.setLastLoginTime(lastLoginTime);
            userService.update(user);
            //清除cookie
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            //销毁session
            request.getSession().removeAttribute("uuid");
        }
        return "redirect:/index";
    }

    @ApiOperation("注册新用户")
    @PostMapping(value = "/saveUser.do")
    public String saveUser(@ApiParam("用户对象") User user, @ApiParam("确认密码") String verifyPwd, Model model) {
        User register = userService.findByEmail(user.getEmail());
        if (null != register) {
            model.addAttribute("msg", "您输入的邮箱已存在");
            return "user/register";
        } else {
            //邮箱验证
            boolean flag = MyValidate.emailValidate(user.getEmail());
            if (flag) {
                User nickname = userService.findByNickname(user.getNickname());
                if (null != nickname) {
                    model.addAttribute("msg", "您输入的昵称已存在");
                    return "user/register";
                } else {
                    if (!verifyPwd.equals(user.getPassword())) {
                        model.addAttribute("msg", "两次输入的密码不一致");
                        return "user/register";
                    } else {
                        if (2 == user.getSex()) {
                            model.addAttribute("msg", "性别尚未选择");
                            return "user/register";
                        } else {
                            userService.save(user);
                            model.addAttribute("msg", "注册成功,请登录");
                            return "user/login";
                        }
                    }
                }
            } else {
                model.addAttribute("msg", "请输入合法的邮箱");
                return "user/register";
            }
        }
    }

    @ApiOperation("用户申诉")
    @ResponseBody
    @PostMapping(value = "/appeal.do")
    public Integer appeal(@ApiParam("用户的邮箱") String email, @ApiParam("用户申诉的次数") Integer count) {
        User user = userService.findByEmail(email);
        if (null != user) {
            count++;
            //第一次申诉
            if (1 == count) {
                user.setAppeal(count);
                userService.update(user);
                String password = user.getPassword();
                //设置延迟时间(单位：ms)
                Long delay = 7200000L;
                //发送邮件
                myTimer.timer(email, password, delay);
                return 1;
            }
            //申诉3次后重置
            else {
                user.setAppeal(count);
                userService.update(user);
                while (4 == count) {
                    user.setAppeal(0);
                    userService.update(user);
                    count = 0;
                }
                return count;
            }
        } else {
            return null;
        }
    }

    @ApiOperation("获取用户申诉的次数")
    @ResponseBody
    @PostMapping(value = "/getCount.do")
    public Integer getCount(@ApiParam("用户的邮箱") String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            return user.getAppeal();
        } else {
            return 404;
        }
    }

    @ApiOperation("更新用户信息")
    @PostMapping(value = "/updateUser.do")
    public String updateUser(@ApiParam("用户对象") User user, Model model) {
        User tempUser = userService.findByUUID(user.getUuid());
        //已经修改昵称
        if (!tempUser.getNickname().equals(user.getNickname())) {
            User nickname = userService.findByNickname(user.getNickname());
            if (null != nickname) {
                model.addAttribute("msg", "该昵称已存在");
                return "user/update";
            } else {
                tempUser.setNickname(user.getNickname());
                tempUser.setPassword(user.getPassword());
                tempUser.setSex(user.getSex());
                userService.update(tempUser);
                return "redirect:/index";
            }
        }
        //未修改昵称
        else {
            tempUser.setPassword(user.getPassword());
            tempUser.setSex(user.getSex());
            userService.update(tempUser);
            return "redirect:/index";
        }
    }

    @ApiOperation("用户试听歌曲")
    @ResponseBody
    @GetMapping(value = "/toPlay.do/{counts}")
    public String toPlay(@ApiParam("用户的试听歌曲数") @PathVariable("counts") Integer counts, HttpSession session) {
        String uuid = (String) session.getAttribute("uuid");
        User user = userService.findByUUID(uuid);
        if (1 != user.getRole()) {
            if (5 <= user.getCounts()) {
                return "对不起,您的试听歌曲已达到五首,升级会员后才能继续享用！";
            } else {
                user.setCounts(counts);
                userService.update(user);
                return "正在为您播放：";
            }
        } else {
            return "正在为您播放：";
        }
    }

    @ApiOperation("留言板的密码认证")
    @ResponseBody
    @GetMapping(value = "/toCheck.do/{userPwd}/{auth}")
    public Integer toCheck(@ApiParam("用户输入的密码") @PathVariable("userPwd") String userPwd, @ApiParam("用户验证密码的次数") @PathVariable("auth") Integer auth, HttpServletRequest request) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            User user = userService.findByNickname(nickname);
            Integer msg = 0;
            if (0 < auth) {
                user.setAuth(--auth);
                userService.update(user);
                if (userPwd.equals(user.getPassword())) {
                    msg = 8;
                    user.setAuth(3);        //恢复认证的默认次数
                    userService.update(user);
                } else {
                    msg = auth;
                }
                if (0 == auth) {
                    myTimer.updateAuth(user, 3600000L);
                }
            }
            return msg;
        }
        return null;
    }

    @ApiOperation("获取用户的验证次数")
    @ResponseBody
    @GetMapping(value = "/getAuth.do")
    public Integer getAuth(HttpServletRequest request) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            User user = userService.findByNickname(nickname);
            return user.getAuth() < 1 == true ? 0 : user.getAuth();
        }
        return null;
    }

    @ApiOperation("举报用户的评论或回复")
    @ResponseBody
    @GetMapping(value = "/complain.do")
    public String complain(HttpServletRequest request) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            User user = userService.findByNickname(nickname);
            if (5 <= user.getCredit()) {
                user.setCredit(user.getCredit() - 5);
                userService.update(user);
            }
            return "已举报";
        }
        return "未举报";
    }

    @ApiOperation("查询该用户当前的信誉度")
    @ResponseBody
    @GetMapping(value = "/getCredit.do")
    public Integer getCredit(HttpServletRequest request) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            User user = userService.findByNickname(nickname);
            return user.getCredit();
        }
        return null;
    }

    @ApiOperation("更新用户的个性签名")
    @ResponseBody
    @GetMapping(value = "/updateSignature.do/{signature}")
    public String updateSignature(@ApiParam("用户的个性签名") @PathVariable("signature") String signature, HttpServletRequest request) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            User user = userService.findByNickname(nickname);
            user.setSignature(signature);
            userService.update(user);
            return "已更新";
        }
        return null;
    }

    @ApiOperation("获取用户的身份")
    @ResponseBody
    @GetMapping(value = "/getRole.do")
    public Integer getRole(HttpServletRequest request) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            User user = userService.findByNickname(nickname);
            return user.getRole();
        }
        return null;
    }
}
