package com.light.light_music.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * singer表
 *
 * @Author : KangXu
 * @Date : 2020/8/20
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("sr")
@ApiModel("歌曲表")
public class Singer {
    @ApiModelProperty("歌手的uuid")
    private String uuid;
    @ApiModelProperty("歌手的名称")
    private String singer;
    @ApiModelProperty("歌手的性别")
    private String sex;
    @ApiModelProperty("歌手所在的地区")
    private String region;
    @ApiModelProperty("歌手所在的团队")
    private String team;
    @ApiModelProperty("歌手演唱的类型")
    private String singerType;
    @ApiModelProperty("歌手的头像存储的位置")
    private String path;
    @ApiModelProperty("歌手的名称的首字母大写")
    private String firstLatter;
    @ApiModelProperty("展示歌手的头像")
    private String pathStr;

    //处理展示歌手的头像
    public String getPathStr() {
        if (null != path) {
            return path.substring(path.lastIndexOf("music"));
        } else {
            return null;
        }
    }
}
