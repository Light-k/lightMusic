package com.light.light_music.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 与前台json数据保持一致的
 *
 * @Author : KangXu
 * @Date : 2020/8/14
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("与json数据交互专用")
public class JsonParams implements Serializable {
    @ApiModelProperty("评论对象")
    private Comment comment;
    @ApiModelProperty("回复对象")
    private Reply reply;
}
