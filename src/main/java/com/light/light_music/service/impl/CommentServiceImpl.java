package com.light.light_music.service.impl;

import com.light.light_music.MyUtils.MyUUID;
import com.light.light_music.mapper.CommentMapper;
import com.light.light_music.pojo.Comment;
import com.light.light_music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * comment表的service层实现类
 *
 * @Author : KangXu
 * @Date : 2020/8/11
 * @Package : com.light.light_music.service.impl
 */

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    //查询所有
    @Override
    public List<Comment> findAll() {
        return commentMapper.findAll();
    }

    //根据歌曲的uuid值查询
    @Override
    public List<Comment> findBySongId(String songId) {
        return commentMapper.findBySongId(songId);
    }

    //根据用户的昵称查询
    @Override
    public List<Comment> findByNickname(String nickname) {
        return commentMapper.findByNickname(nickname);
    }

    //添加评论
    @Override
    public void save(Comment comment) {
        comment.setUuid(MyUUID.getUUID());
        comment.setCommentTime(new Date());
        commentMapper.save(comment);
    }

    //向中间表中添加
    @Override
    public void toComment(String songId, String commentId) {
        commentMapper.toComment(songId, commentId);
    }

    //移除中间表
    @Override
    public void toRemoveComment(String commentId) {
        commentMapper.toRemoveComment(commentId);
    }

    //删除评论
    @Override
    public void delete(String uuid) {
        commentMapper.delete(uuid);
    }
}
