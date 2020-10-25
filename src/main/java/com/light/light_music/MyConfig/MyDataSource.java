package com.light.light_music.MyConfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * druid数据源
 *
 * @Author : KangXu
 * @Date : 2020/7/2
 * @Package : com.light.light_music.MyConfig
 */

@Configuration
public class MyDataSource {
    //注入druidDataSource
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
    //配置 Druid 监控管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        //创建servlet
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,Object> initParams = new HashMap<>();
        //设置后台登录的账号密码
        initParams.put("loginUsername","light");
        initParams.put("loginPassword","123");
        //后台允许谁可以访问: localhost 本机; null或空 所有人
        initParams.put("allow","");
        //后台禁止谁可以访问
        //initParams.put("deny","192.168.0.106");
        //设置初始化参数
        bean.setInitParameters(initParams);
        return bean;
    }
    //配置 Druid 监控 之  web 监控的 filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        //创建filter
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,Object> initParams = new HashMap<>();
        //exclusions：设置哪些请求进行过滤排除掉，从而不进行统计
        initParams.put("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        //设置初始化参数
        bean.setInitParameters(initParams);
        //设置过滤请求：  "/*" 表示过滤所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
