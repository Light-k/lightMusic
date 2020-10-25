package com.light.light_music.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * album表
 *
 * @Author : KangXu
 * @Date : 2020/7/30
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("a")
@ApiModel("专辑表")
public class Album {
    @ApiModelProperty("专辑的id")
    private Integer id;
    @ApiModelProperty("专辑的封面路径")
    private String image;
    @ApiModelProperty("专辑的名称")
    private String albumName;
    @ApiModelProperty("专辑已收录的歌曲的数量")
    private Integer counts;
    @ApiModelProperty("专辑已收录的歌曲的歌手名称")
    private String singer;
    @ApiModelProperty("专辑收录的歌曲")
    private List<Song> songs;
    @ApiModelProperty("前台展示专辑的封面路径")
    private String imageStr;

    //前台展示专辑的封面路径
    public String getImageStr() {
        return image.substring(image.lastIndexOf("music"));
    }
}
