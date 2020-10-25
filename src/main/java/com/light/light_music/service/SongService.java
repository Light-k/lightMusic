package com.light.light_music.service;

import com.light.light_music.pojo.Song;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * song类的service层
 *
 * @Author : KangXu
 * @Date : 2020/7/2
 * @Package : com.light.light_music.service
 */

@Service
public interface SongService {
    //查询所有
    List<Song> findAll();

    //根据uuid查询
    Song findBySongId(String songId);

    //根据歌曲名称查询
    List<Song> findBySongName(String songName);

    //根据歌手的名称查询
    List<Song> findBySinger(String singer);

    //根据歌手的名称查询
    Integer findSongsBySinger(String singer);

    //根据歌曲所在榜单分组查询
    List<Song> findBySongGroup(String songGroup);

    //根据歌曲的歌手名称查询对应专辑的数量
    Integer findAlbumSumBySinger(String singer);

    //根据歌曲的uuid查询对应的专辑
    Song findAlbumBySongId(String uuid);

    //添加歌曲
    void save(Song song);

    //向中间表中插入
    void addSongGroup(String songId, Integer[] groupIds);

    //更新歌曲的排名
    void update(Song song);

    //删除歌曲
    void delete(String uuid);

    //删除中间表
    void deleteSongGroup(String songId);
}
