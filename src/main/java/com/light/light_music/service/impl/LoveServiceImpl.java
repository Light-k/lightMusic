package com.light.light_music.service.impl;

import com.light.light_music.MyUtils.MyUUID;
import com.light.light_music.mapper.LoveMapper;
import com.light.light_music.pojo.Love;
import com.light.light_music.pojo.Song;
import com.light.light_music.service.LoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * love表的service层实现类
 *
 * @Author : KangXu
 * @Date : 2020/8/10
 * @Package : com.light.light_music.service.impl
 */

@Service
public class LoveServiceImpl implements LoveService {
    @Autowired
    LoveMapper loveMapper;

    //根据收藏的uuid查询
    @Override
    public Song findByLoveId(String loveId) {
        return loveMapper.findByLoveId(loveId);
    }

    //根据用户的昵称查询
    @Override
    public List<Love> findByNickname(String nickname) {
        return loveMapper.findByNickname(nickname);
    }

    //根据已收藏的歌曲的名称查询
    @Override
    public List<Love> findBySongName(String songName) {
        return loveMapper.findBySongName(songName);
    }

    //根据歌曲的uuid值和用户的昵称查询
    @Override
    public Love findBySongIdAndNickname(String songId, String nickname) {
        return loveMapper.findBySongIdAndNickname(songId, nickname);
    }

    //添加收藏
    @Override
    public void save(Love love) {
        love.setUuid(MyUUID.getUUID());
        loveMapper.save(love);
    }

    //向中间表中添加
    @Override
    public void toLove(String songId, String loveId) {
        loveMapper.toLove(songId, loveId);
    }

    //移除中间表
    @Override
    public void toRemoveLove(String loveId) {
        loveMapper.toRemoveLove(loveId);
    }

    //取消收藏
    @Override
    public void delete(String uuid) {
        loveMapper.delete(uuid);
    }
}
