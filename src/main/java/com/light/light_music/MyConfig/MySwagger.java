package com.light.light_music.MyConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.ArrayList;

/**
 * 自定义的swagger配置类
 *
 * @Author : KangXu
 * @Date : 2020/7/14
 * @Package : com.light.light_music.MyConfig
 */

@Configuration
public class MySwagger {
    //配置Swagger_2
    @Bean
    public Docket getDocket(Environment environment){
        //设置swagger环境
        Profiles profiles = Profiles.of("dev","test");
        //判断是否处在当前设置的环境中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                //配置apiInfo
                .apiInfo(getApiInfo())
                //配置分组名
                .groupName("light")
                //是否启动swagger
                .enable(flag)
                .select()
                //配置扫描包
                .apis(RequestHandlerSelectors.basePackage("com.light.light_music.controller"))
                //过滤路径
//                .paths(PathSelectors.ant("com.light.light_music.controller/**"))
                .build();
    }

    //设置apiInfo
    public ApiInfo getApiInfo(){
        //作者信息
        Contact contact = new Contact("康旭", "https://note.youdao.com/web/#/file/recent/note/WEB0143c70b595b15ccd7994ece35c7c8c6/", "1343450971@qq.com");
        //apiInfo
        return new ApiInfo(
                "light音乐的swagger API文档",
                "光阴似箭，日月如梭，听音乐就来light音乐吧",
                "svp1.0",
                "http://www.light.com/Light/500.0.html",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

    //可以配置多个Swagger_2
    @Bean
    public Docket getDocket1(Environment environment){
        //设置swagger环境
        Profiles profiles = Profiles.of("dev","test");
        //判断是否处在当前设置的环境中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                //配置apiInfo
                .apiInfo(getApiInfo1())
                //配置分组名
                .groupName("A")
                //是否启动swagger
                .enable(flag)
                .select()
                //配置扫描包
                .apis(RequestHandlerSelectors.basePackage("com.light.light_music.controller"))
                //过滤路径
//                .paths(PathSelectors.ant("com.light.light_music.controller/**"))
                .build();
    }

    //设置apiInfo
    public ApiInfo getApiInfo1(){
        //作者信息
        Contact contact = new Contact("A", "https://note.youdao.com/web/#/file/recent/note/WEB0143c70b595b15ccd7994ece35c7c8c6/", "1343450971@qq.com");
        //apiInfo
        return new ApiInfo(
                "A的swagger API文档",
                "光阴似箭，日月如梭，听音乐就来light音乐吧",
                "svp1.0",
                "http://www.light.com/Light/500.0.html",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

}
