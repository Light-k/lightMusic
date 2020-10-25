package com.light.light_music.service.impl;

import com.light.light_music.mapper.AlbumMapper;
import com.light.light_music.pojo.Album;
import com.light.light_music.pojo.Song;
import com.light.light_music.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * album类的service层实现类
 *
 * @Author : KangXu
 * @Date : 2020/7/30
 * @Package : com.light.light_music.service.impl
 */

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;

    //查询所有的专辑
    @Override
    public List<Album> findAll() {
        return albumMapper.findAll();
    }

    //根据专辑的id值查询对应的专辑
    @Override
    public Album findById(Integer id) {
        return albumMapper.findById(id);
    }

    //根据专辑的名称查询
    @Override
    public List<Album> findByAlbumName(String albumName) {
        return albumMapper.findByAlbumName(albumName);
    }

    //查询未被收录的歌曲
    @Override
    public List<Song> findOtherSongs() {
        return albumMapper.findOtherSongs();
    }

    //根据专辑的albumId查询该专辑已收录的歌曲
    @Override
    public List<Song> querySongFromAlbum(Integer albumId) {
        return albumMapper.querySongFromAlbum(albumId);
    }

    //根据专辑的albumId分组查询该专辑的歌曲总数
    @Override
    public Integer querySongCounts(Integer albumId) {
        return albumMapper.querySongCounts(albumId);
    }

    //添加新专辑
    @Override
    public void save(Album album) {
        albumMapper.save(album);
    }

    //收录新单曲
    @Override
    public void saveNewSongs(String[] songIds, Integer albumId) {
        for (String songId : songIds) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("songId", songId);
            map.put("albumId", albumId);
            albumMapper.saveNewSongs(map);
            //及时更新专辑已收录的歌曲总数
            Album album = albumMapper.findById(albumId);
            int counts = albumMapper.querySongCounts(albumId);
            album.setCounts(counts);
            albumMapper.update(album);
        }
    }

    //更新专辑已收录的歌曲总数
    @Override
    public void update(Album album) {
        albumMapper.update(album);
    }

    //移除单曲
    @Override
    public void removeSong(String songId, Integer albumId) {
        albumMapper.removeSong(songId);
        //及时更新专辑已收录的歌曲总数
        Album album = albumMapper.findById(albumId);
        Integer counts = albumMapper.querySongCounts(albumId);
        if (null == counts) {
            album.setCounts(0);
        } else {
            album.setCounts(counts);
        }
        albumMapper.update(album);
    }


}
