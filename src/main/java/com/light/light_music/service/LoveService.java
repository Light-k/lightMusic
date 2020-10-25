package com.light.light_music.service;

import com.light.light_music.pojo.Love;
import com.light.light_music.pojo.Song;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * love表的service层
 *
 * @Author : KangXu
 * @Date : 2020/8/10
 * @Package : com.light.light_music.service
 */

@Service
public interface LoveService {
    //根据收藏的uuid查询
    Song findByLoveId(String loveId);

    //根据用户的昵称查询
    List<Love> findByNickname(String nickname);

    //根据已收藏的歌曲的名称查询
    List<Love> findBySongName(String songName);

    //根据歌曲的uuid值和用户的昵称查询
    Love findBySongIdAndNickname(String songId, String nickname);

    //添加收藏
    void save(Love love);

    //向中间表中添加
    void toLove(String songId, String loveId);

    //移除中间表
    void toRemoveLove(String loveId);

    //取消收藏
    void delete(String uuid);
}
