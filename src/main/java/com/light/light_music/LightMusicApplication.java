package com.light.light_music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableScheduling                           //开启定时功能
@EnableSwagger2
@SpringBootApplication
public class LightMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightMusicApplication.class, args);
    }

}
