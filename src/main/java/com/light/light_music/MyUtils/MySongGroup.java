package com.light.light_music.MyUtils;


/**
 * 歌曲的排名工具类
 *
 * @Author : KangXu
 * @Date : 2020/7/23
 * @Package : com.light.light_music.MyUtils
 */


public class MySongGroup {
    /**
     * 处理歌曲所在的榜单
     * @param oldRanking：上次排名
     * @param newRanking：本次排名
     * @return ：0：飙升榜 ; 1：热歌榜 ; 2：流行指数榜 ; 3：新歌榜
     */
    public static Integer[] dealWithSongGroup(Integer oldRanking, Integer newRanking) {
        if (50 >= oldRanking) {
            if (10 >= oldRanking) {
                Integer[] groupIds = {1,2};
                return groupIds;
            } else if (10 <= (newRanking - oldRanking)) {
                Integer[] groupIds = {0,2};
                return groupIds;
            }  else {
                Integer[] groupIds = {2};
                return groupIds;
            }
        } else if (10 <= (newRanking - oldRanking)) {
            Integer[] groupIds = {0};
            return groupIds;
        } else {
            Integer[] groupIds = {3};
            return groupIds;
        }
    }
}
