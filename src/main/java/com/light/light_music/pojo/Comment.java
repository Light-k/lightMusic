package com.light.light_music.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * comment表
 *
 * @Author : KangXu
 * @Date : 2020/8/11
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("c")
@ApiModel("评论表")
public class Comment implements Serializable {
    @ApiModelProperty("评论表的uuid值")
    private String uuid;
    @ApiModelProperty("评论的人")
    private String nickname;
    @ApiModelProperty("评论的歌曲名称")
    private String songName;
    @ApiModelProperty("评论的时间")
    private Date commentTime;
    @ApiModelProperty("评论的内容")
    private String content;
    @ApiModelProperty("评论的歌曲")
    private Song song;
    @ApiModelProperty("评论对应的回复")
    private List<Reply> replies;
}
