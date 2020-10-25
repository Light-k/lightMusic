package com.light.light_music.mapper;

import com.light.light_music.pojo.Album;
import com.light.light_music.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * album类的mapper层
 *
 * @Author : KangXu
 * @Date : 2020/7/30
 * @Package : com.light.light_music.mapper
 */

@Mapper
@Repository
public interface AlbumMapper {
    //查询所有的专辑
    List<Album> findAll();

    //根据专辑的id值查询对应的专辑
    Album findById(@Param("id") Integer id);

    //根据专辑的名称查询
    List<Album> findByAlbumName(@Param("albumName") String albumName);

    //查询未被收录的歌曲
    List<Song> findOtherSongs();

    //根据专辑的albumId查询该专辑已收录的歌曲
    List<Song> querySongFromAlbum(@Param("albumId") Integer albumId);

    //根据专辑的albumId分组查询该专辑的歌曲总数
    Integer querySongCounts(@Param("albumId") Integer albumId);

    //添加新专辑
    void save(Album album);

    //收录新单曲
    void saveNewSongs(Map<String, Object> map);

    //更新专辑已收录的歌曲总数
    void update(Album album);

    //移除单曲
    void removeSong(@Param("songId") String songId);
}
