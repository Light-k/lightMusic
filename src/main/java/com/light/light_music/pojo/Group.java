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
 * group表
 *
 * @Author : KangXu
 * @Date : 2020/7/23
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("g")
@ApiModel("榜单表")
public class Group implements Serializable {
    @ApiModelProperty("榜单的id")
    private Integer id;
    @ApiModelProperty("歌曲的所在的榜单")
    private String songGroup;
    @ApiModelProperty("对应的歌曲")
    private List<Song> songs;
}
