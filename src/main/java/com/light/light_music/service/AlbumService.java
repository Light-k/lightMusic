package com.light.light_music.service;

import com.light.light_music.pojo.Album;
import com.light.light_music.pojo.Song;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * album类的service层
 *
 * @Author : KangXu
 * @Date : 2020/7/30
 * @Package : com.light.light_music.service
 */

@Service
public interface AlbumService {
    //查询所有的专辑
    List<Album> findAll();

    //根据专辑的id值查询对应的专辑
    Album findById(Integer id);

    //根据专辑的名称查询
    List<Album> findByAlbumName(String albumName);

    //查询未被收录的歌曲
    List<Song> findOtherSongs();

    //根据专辑的albumId查询该专辑已收录的歌曲
    List<Song> querySongFromAlbum(Integer albumId);

    //根据专辑的albumId分组查询该专辑的歌曲总数
    Integer querySongCounts(Integer albumId);

    //添加新专辑
    void save(Album album);

    //收录新单曲
    void saveNewSongs(String[] songIds, Integer albumId);

    //更新专辑已收录的歌曲总数
    void update(Album album);

    //移除单曲
    void removeSong(String songId, Integer albumId);
}
