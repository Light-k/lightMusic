package com.light.light_music.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

/**
 * song表
 *
 * @Author : KangXu
 * @Date : 2020/7/2
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("s")
@ApiModel("歌曲表")
public class Song implements Serializable {
    @ApiModelProperty("歌曲的uuid")
    private String uuid;
    @ApiModelProperty("歌曲的名称")
    private String songName;
    @ApiModelProperty("演唱的歌手")
    private String singer;
    @ApiModelProperty("歌曲所在的位置")
    private String path;
    @ApiModelProperty("文件的大小")
    private String fileSize;
    @ApiModelProperty("歌曲的播放时长")
    private String playTime;
    @ApiModelProperty("歌曲的排名")
    private Integer ranking;
    @ApiModelProperty("歌曲的排名变化")
    private String status;
    @ApiModelProperty("歌曲的排名的浮动")
    private Integer realFloat;
    @ApiModelProperty("歌曲的歌词")
    private String lrc;
    @ApiModelProperty("歌曲对应的榜单")
    private List<Group> groups;
    @ApiModelProperty("歌曲对应的专辑")
    private Album album;
    @ApiModelProperty("后台展示歌曲所在的位置")
    private String pathStr;
    @ApiModelProperty("前台提供歌曲所在的位置")
    private String songPath;
    @ApiModelProperty("处理歌曲的排名的浮动")
    private Integer realFloatStr;
    @ApiModelProperty("歌曲的收藏")
    private Love love;
    @ApiModelProperty("歌曲的评论")
    private List<Comment> comments;

    //后台展示歌曲所在的位置
    public String getPathStr() {
        if (null != path) {
            return path.substring(0, path.lastIndexOf("/"));
        } else {
            return null;
        }
    }

    //前台提供歌曲所在的位置
    public String getSongPath() {
        if (null != path) {
            return path.substring(path.lastIndexOf("music"));
        } else {
            return null;
        }
    }

    //处理歌曲的排名的浮动
    public Integer getRealFloatStr() {
        if (null != status) {
            if ("上升".equals(status)) {
                return realFloat;
            } else if ("未发生变化".equals(status)) {
                return realFloat;
            } else {
                return Math.abs(realFloat);
            }
        } else {
            return null;
        }
    }

}
