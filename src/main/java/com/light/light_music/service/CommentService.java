package com.light.light_music.service;

import com.light.light_music.pojo.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * comment表的service层
 *
 * @Author : KangXu
 * @Date : 2020/8/11
 * @Package : com.light.light_music.service
 */

@Service
public interface CommentService {
    //查询所有
    List<Comment> findAll();

    //根据歌曲的uuid值查询
    List<Comment> findBySongId(String songId);

    //根据用户的昵称查询
    List<Comment> findByNickname(String nickname);

    //添加评论
    void save(Comment comment);

    //向中间表中添加
    void toComment(String songId, String commentId);

    //移除中间表
    void toRemoveComment(String commentId);

    //删除评论
    void delete(String uuid);
}
