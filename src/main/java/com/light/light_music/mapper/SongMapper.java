package com.light.light_music.mapper;

import com.light.light_music.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * song类的mapper层
 *
 * @Author : KangXu
 * @Date : 2020/7/2
 * @Package : com.light.light_music.mapper
 */

@Mapper         //表示本类是一个 MyBatis 的 Mapper，等价于以前 Spring 整合 MyBatis 时的 Mapper 接口
@Repository
public interface SongMapper {
    //查询所有
    List<Song> findAll();

    //根据uuid查询
    Song findBySongId(@Param("uuid") String songId);

    //根据歌曲名称查询
    List<Song> findBySongName(@Param("songName") String songName);

    //根据歌手的名称查询
    List<Song> findBySinger(@Param("singer") String singer);

    //根据歌手的名称查询
    Integer findSongsBySinger(@Param("singer") String singer);

    //根据歌曲所在榜单分组查询
    List<Song> findBySongGroup(@Param("songGroup") String songGroup);

    //根据歌曲的歌手名称查询对应专辑的数量
    Integer findAlbumSumBySinger(@Param("singer") String singer);

    //根据歌曲的uuid查询对应的专辑
    Song findAlbumBySongId(@Param("uuid") String uuid);

    //添加歌曲
    void save(Song song);

    //向中间表中插入
    void addSongGroup(Map<String, Object> map);

    //更新歌曲的排名
    void update(Song song);

    //删除歌曲
    void delete(@Param("uuid") String uuid);

    //删除中间表
    void deleteSongGroup(@Param("songId") String songId);
}
