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
 * user表
 *
 * @Author : KangXu
 * @Date : 2020/7/2
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("u")
@ApiModel("用户表")
public class User implements Serializable {
    @ApiModelProperty("用户id")
    private String uuid;
    @ApiModelProperty("用户的昵称")
    private String nickname;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("用户的性别")
    private Integer sex;
    @ApiModelProperty("用户的角色")
    private Integer role;
    @ApiModelProperty("登录的时间")
    private Date loginTime;
    @ApiModelProperty("累计在线的时间")
    private Date onlineTime;
    @ApiModelProperty("退出的时间")
    private Date logoutTime;
    @ApiModelProperty("上次登录的时间")
    private Date lastLoginTime;
    @ApiModelProperty("验证密码的次数")
    private Integer auth;
    @ApiModelProperty("累计听歌的数量")
    private Integer counts;
    @ApiModelProperty("用户的邮箱")
    private String email;
    @ApiModelProperty("用户的申诉次数")
    private Integer appeal;
    @ApiModelProperty("用户的信誉度")
    private Integer credit;
    @ApiModelProperty("用户的个性签名")
    private String signature;
}
