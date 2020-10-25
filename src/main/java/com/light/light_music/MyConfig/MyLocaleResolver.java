package com.light.light_music.MyConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 国际化资源文件配置
 *
 * @Author : KangXu
 * @Date : 2020/7/2
 * @Package : com.light.light_music.MyConfig
 */

@Configuration
public class MyLocaleResolver implements LocaleResolver {
    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //获取语言参数
        String language = request.getParameter("l");
        //获取默认的地区
        Locale locale = Locale.getDefault();
        if(!StringUtils.isEmpty(language)){
            //分隔语言参数
            String[] s = language.split("_");
            //存放国家和地区
            locale = new Locale(s[0],s[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
