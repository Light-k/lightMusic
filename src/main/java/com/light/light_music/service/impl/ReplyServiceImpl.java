package com.light.light_music.service.impl;

import com.light.light_music.MyUtils.MyUUID;
import com.light.light_music.mapper.ReplyMapper;
import com.light.light_music.pojo.Reply;
import com.light.light_music.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * reply表的service层实现类
 *
 * @Author : KangXu
 * @Date : 2020/8/14
 * @Package : com.light.light_music.service.impl
 */

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyMapper replyMapper;

    //根据回复的uuid查询
    @Override
    public Reply findByReplyId(String uuid) {
        return replyMapper.findByReplyId(uuid);
    }

    //根据用户的昵称查询
    @Override
    public List<Reply> findByNickname(String nickname) {
        return replyMapper.findByNickname(nickname);
    }

    //根据评论的uuid查询出所有的回复
    @Override
    public List<Reply> findByCommentId(String commentId) {
        return replyMapper.findByCommentId(commentId);
    }

    //添加回复
    @Override
    public void save(Reply reply) {
        reply.setUuid(MyUUID.getUUID());
        reply.setReplyTime(new Date());
        replyMapper.save(reply);
    }

    //回复评论(中间表)
    @Override
    public void replyToComment(String commentId, String replyId) {
        replyMapper.replyToComment(commentId, replyId);
    }

    //删除中间表
    @Override
    public void toRemoveReply(String replyId) {
        replyMapper.toRemoveReply(replyId);
    }

    //删除回复
    @Override
    public void delete(String uuid) {
        replyMapper.delete(uuid);
    }
}
