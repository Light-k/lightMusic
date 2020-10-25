package com.light.light_music.MyConfig;

import com.light.light_music.MyUtils.MySongGroup;
import com.light.light_music.pojo.Song;
import com.light.light_music.pojo.User;
import com.light.light_music.service.SongService;
import com.light.light_music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时器
 *
 * @Author : KangXu
 * @Date : 2020/7/19
 * @Package : com.light.light_music.MyConfig
 */

@Component
public class MyScheduled {
    @Autowired
    SongService songService;
    @Autowired
    UserService userService;

    /**
     * 每天0点更新歌曲所在的榜单及排名
     * cron : 秒 分 时 日 月 周(0和7都是星期日)
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void updateRanking() {
        List<Song> songList = songService.findAll();
        for (Song song : songList) {
            Integer oldRanking = song.getRanking();
            Integer newRanking = (int) (Math.random() * 100 + 1);
            //设置更新的排名
            song.setRanking(newRanking);
            Integer realFloat = newRanking - oldRanking;
            //设置排名的真实浮动值
            song.setRealFloat(realFloat);
            //设置排名的浮动状态
            if (0 < realFloat) {
                song.setStatus("上升");
            } else if (0 == realFloat) {
                song.setStatus("未发生变化");
            } else {
                song.setStatus("下降");
            }
            songService.update(song);
            //得出歌曲所在的榜单
            Integer[] groupIds = MySongGroup.dealWithSongGroup(oldRanking, newRanking);
            //移除中间表原有的数据
            songService.deleteSongGroup(song.getUuid());
            //向中间表插入
            songService.addSongGroup(song.getUuid(), groupIds);
        }
    }

    /**
     * 每天0点更新用户的信誉度
     * cron : 秒 分 时 日 月 周(0和7都是星期日)
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void updateCredit() {
        List<User> users = userService.findAll();
        for (User user : users) {
            if (100 > user.getCredit()) {
                user.setCredit(user.getCredit() + 1);
                userService.update(user);
            }
        }
    }

}
