package com.light.light_music.mapper;

import com.light.light_music.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * comment表的mapper层
 *
 * @Author : KangXu
 * @Date : 2020/8/11
 * @Package : com.light.light_music.mapper
 */

@Mapper
@Repository
public interface CommentMapper {
    //查询所有
    List<Comment> findAll();

    //根据歌曲的uuid值查询
    List<Comment> findBySongId(@Param("songId") String songId);

    //根据用户的昵称查询
    List<Comment> findByNickname(@Param("nickname") String nickname);

    //添加评论
    void save(Comment comment);

    //向中间表中添加
    void toComment(@Param("songId") String songId, @Param("commentId") String commentId);

    //移除中间表
    void toRemoveComment(@Param("commentId") String commentId);

    //删除评论
    void delete(@Param("uuid") String uuid);
}
