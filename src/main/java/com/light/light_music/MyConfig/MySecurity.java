package com.light.light_music.MyConfig;

import com.light.light_music.service.impl.AdminDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 自定义的security
 *
 * @Author : KangXu
 * @Date : 2020/7/8
 * @Package : com.light.light_music.MyConfig
 */

@EnableWebSecurity
public class MySecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    AdminDetailServiceImpl adminDetailService;
    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有身份都可以访问,其他页面需要对应的权限访问
        http.authorizeRequests()
                .antMatchers("/adminMain").permitAll()
                .antMatchers("/admin/findAll.do").hasRole("EMPLOYEE")
                .antMatchers("/user/findAll.do").hasRole("CUSTOMER")
                .antMatchers("/admin/querySong.do").hasRole("BUSSINESS")
                .antMatchers("/admin/queryAlbum.do").hasRole("BUSSINESS")
                .antMatchers("/admin/querySinger.do").hasRole("BUSSINESS")
                .antMatchers("/admin/queryComment.do").hasRole("BUSSINESS");

        //定制登录首页
        http.formLogin()
                .loginPage("/admin")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/admin/login.do")
                .defaultSuccessUrl("/adminMain");

        //记住我功能
        http.rememberMe().rememberMeParameter("remember-me");

        //注销
        http.logout()
                .logoutUrl("/admin/logout.do")
                .logoutSuccessUrl("/admin");
        //防止入侵
        http.csrf().disable();

    }
    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
