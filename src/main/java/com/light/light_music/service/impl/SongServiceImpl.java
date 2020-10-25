package com.light.light_music.service.impl;

import com.light.light_music.MyUtils.MyUUID;
import com.light.light_music.mapper.SongMapper;
import com.light.light_music.pojo.Song;
import com.light.light_music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * song类的service层实现类
 *
 * @Author : KangXu
 * @Date : 2020/7/2
 * @Package : com.light.light_music.service.impl
 */

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    SongMapper songMapper;

    //查询所有
    @Override
    public List<Song> findAll() {
        return songMapper.findAll();
    }

    //根据uuid查询
    @Override
    public Song findBySongId(String songId) {
        return songMapper.findBySongId(songId);
    }

    //根据歌曲名称查询
    @Override
    public List<Song> findBySongName(String songName) {
        return songMapper.findBySongName(songName);
    }

    //根据歌手的名称查询
    @Override
    public List<Song> findBySinger(String singer) {
        return songMapper.findBySinger(singer);
    }

    //根据歌手的名称查询
    @Override
    public Integer findSongsBySinger(String singer) {
        return songMapper.findSongsBySinger(singer);
    }

    //根据歌曲所在榜单分组查询
    @Override
    public List<Song> findBySongGroup(String songGroup) {
        return songMapper.findBySongGroup(songGroup);
    }

    //根据歌曲的歌手名称查询对应专辑的数量
    @Override
    public Integer findAlbumSumBySinger(String singer) {
        return songMapper.findAlbumSumBySinger(singer);
    }

    //根据歌曲的uuid查询对应的专辑
    @Override
    public Song findAlbumBySongId(String uuid) {
        return songMapper.findAlbumBySongId(uuid);
    }

    //添加歌曲
    @Override
    public void save(Song song) {
        song.setUuid(MyUUID.getUUID());
        song.setRanking((int) (Math.random() * 100 + 1));
        songMapper.save(song);
    }

    //向中间表中插入
    @Override
    public void addSongGroup(String songId, Integer[] groupIds) {
        for (Integer groupId : groupIds) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("songId", songId);
            map.put("groupId", groupId);
            songMapper.addSongGroup(map);
        }
    }

    //更新歌曲的排名
    @Override
    public void update(Song song) {
        songMapper.update(song);
    }


    //删除歌曲
    @Override
    public void delete(String uuid) {
        songMapper.delete(uuid);
    }

    //删除中间表
    @Override
    public void deleteSongGroup(String songId) {
        songMapper.deleteSongGroup(songId);
    }
}
