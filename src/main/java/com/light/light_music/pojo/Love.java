package com.light.light_music.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * love表
 *
 * @Author : KangXu
 * @Date : 2020/8/10
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("l")
@ApiModel("歌曲收藏表")
public class Love implements Serializable {
    @ApiModelProperty("歌曲收藏表的uuid值")
    private String uuid;
    @ApiModelProperty("用户的昵称")
    private String nickname;
    @ApiModelProperty("歌曲收藏表的歌曲名称")
    private String songName;
    @ApiModelProperty("歌曲收藏表的歌曲的演唱歌手")
    private String singer;
    @ApiModelProperty("歌曲收藏表的歌曲的播放时长")
    private String playTime;
    @ApiModelProperty("歌曲收藏表的歌曲的封面图片")
    private String songImage;
    @ApiModelProperty("歌曲收藏表的歌曲所在的专辑")
    private String songAlbum;
    @ApiModelProperty("收藏的歌曲")
    private Song song;
}
