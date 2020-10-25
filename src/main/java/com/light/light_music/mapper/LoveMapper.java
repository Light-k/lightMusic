package com.light.light_music.mapper;

import com.light.light_music.pojo.Love;
import com.light.light_music.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * love表的mapper层
 *
 * @Author : KangXu
 * @Date : 2020/8/10
 * @Package : com.light.light_music.mapper
 */

@Mapper
@Repository
public interface LoveMapper {
    //根据收藏的uuid查询
    Song findByLoveId(@Param("loveId") String loveId);

    //根据用户的昵称查询
    List<Love> findByNickname(@Param("nickname") String nickname);

    //根据已收藏的歌曲的名称查询
    List<Love> findBySongName(@Param("songName") String songName);

    //根据歌曲的uuid值和用户的昵称查询
    Love findBySongIdAndNickname(@Param("songId") String songId, @Param("nickname") String nickname);

    //添加收藏
    void save(Love love);

    //向中间表中添加
    void toLove(@Param("songId") String songId, @Param("loveId") String loveId);

    //移除中间表
    void toRemoveLove(@Param("loveId") String loveId);

    //取消收藏
    void delete(@Param("uuid") String uuid);
}
