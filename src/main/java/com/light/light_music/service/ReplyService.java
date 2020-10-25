package com.light.light_music.service;

import com.light.light_music.pojo.Reply;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * reply表的service层
 *
 * @Author : KangXu
 * @Date : 2020/8/14
 * @Package : com.light.light_music.service
 */

@Service
public interface ReplyService {
    //根据回复的uuid查询
    Reply findByReplyId(String uuid);

    //根据用户的昵称查询
    List<Reply> findByNickname(String nickname);

    //根据评论的uuid查询出所有的回复
    List<Reply> findByCommentId(String commentId);

    //添加回复
    void save(Reply reply);

    //回复评论(中间表)
    void replyToComment(String commentId, String replyId);

    //删除中间表
    void toRemoveReply(String replyId);

    //删除回复
    void delete(String uuid);
}
