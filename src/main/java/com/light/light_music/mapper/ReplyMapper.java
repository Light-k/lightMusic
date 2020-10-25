package com.light.light_music.mapper;

import com.light.light_music.pojo.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * reply表的mapper层
 *
 * @Author : KangXu
 * @Date : 2020/8/14
 * @Package : com.light.light_music.mapper
 */

@Mapper
@Repository
public interface ReplyMapper {
    //根据回复的uuid查询
    Reply findByReplyId(String uuid);

    //根据用户的昵称查询
    List<Reply> findByNickname(@Param("nickname") String nickname);

    //根据评论的uuid查询出所有的回复
    List<Reply> findByCommentId(@Param("commentId") String commentId);

    //添加回复
    void save(Reply reply);

    //回复评论(中间表)
    void replyToComment(@Param("commentId") String commentId, @Param("replyId") String replyId);

    //删除中间表
    void toRemoveReply(@Param("replyId") String replyId);

    //删除回复
    void delete(@Param("uuid") String uuid);
}
