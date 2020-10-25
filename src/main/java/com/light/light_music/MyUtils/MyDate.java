package com.light.light_music.MyUtils;

import java.util.Date;
import java.util.TimeZone;

/**
 * 处理时间工具类
 *
 * @Author : KangXu
 * @Date : 2020/7/4
 * @Package : com.light.light_music.MyUtils
 */


public class MyDate {
    //计算时间差
    public static Date getOnlineTime(Date loginTime, Date logoutTime) {
        //统计在线时长并消除时区差异
        Long result = (logoutTime.getTime() - loginTime.getTime()) - TimeZone.getDefault().getRawOffset();
        //讲long类型转换为date类型
        Date onlineTime = new Date(result);
        return onlineTime;
    }

}
