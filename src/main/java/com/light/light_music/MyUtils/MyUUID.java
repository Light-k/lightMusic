package com.light.light_music.MyUtils;

import java.util.UUID;

/**
 * uuid工具类
 *
 * @Author : KangXu
 * @Date : 2020/7/3
 * @Package : com.light.light_music.MyUtils
 */


public class MyUUID {
    //获取uuid
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","");
    }
}
