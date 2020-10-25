package com.light.light_music.MyConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc配置类
 *
 * @Author : KangXu
 * @Date : 2020/7/2
 * @Package : com.light.light_music.MyConfig
 */

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //配置视图
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//    }

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/fonts/**", "/js/**", "/ico/**", "/img/**", "/music/**", "/plugins/**");
    }

    //配置地区解析器
    @Bean
    public MyLocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
