package com.light.light_music.mapper;

import com.light.light_music.pojo.Singer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * singer表的mapper层
 *
 * @Author : KangXu
 * @Date : 2020/8/20
 * @Package : com.light.light_music.mapper
 */

@Mapper
@Repository
public interface SingerMapper {
    //根据歌手的uuid值查询
    Singer findByUuid(@Param("uuid") String uuid);

    //查询所有
    List<Singer> findAll();

    //根据歌手的名称查询
    List<Singer> findBySingers(@Param("singer") String singer);

    //根据歌手的名称的首字母/性别/所在的地区/演唱的曲风查询
    List<Singer> querySinger(Singer singer);

    //根据歌手的名称查询
    Singer findBySinger(@Param("singer") String singer);

    //添加歌手
    void save(Singer singer);

    //更新歌手信息
    void update(Singer singer);

    //删除歌手
    void delete(@Param("uuid") String uuid);
}
