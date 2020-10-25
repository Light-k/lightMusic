package com.light.light_music.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * reply表
 *
 * @Author : KangXu
 * @Date : 2020/8/14
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("rp")
@ApiModel("回复表")
public class Reply implements Serializable {
    @ApiModelProperty("回复表的uuid值")
    private String uuid;
    @ApiModelProperty("回复的人")
    private String nickname;
    @ApiModelProperty("回复的时间")
    private Date replyTime;
    @ApiModelProperty("回复的内容")
    private String content;
}
