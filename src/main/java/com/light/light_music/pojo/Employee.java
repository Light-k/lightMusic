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
 * admin表
 *
 * @Author : KangXu
 * @Date : 2020/7/5
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("e")
@ApiModel("员工表")
public class Employee implements Serializable {
    @ApiModelProperty("员工的id")
    private String uuid;
    @ApiModelProperty("员工的账号")
    private String username;
    @ApiModelProperty("员工的密码")
    private String password;
    @ApiModelProperty("员工的真实名字")
    private String trueName;
    @ApiModelProperty("员工的性别")
    private Integer sex;
    @ApiModelProperty("员工的手机号码")
    private String phone;
    @ApiModelProperty("员工的上班签到时间")
    private Date startTime;
    @ApiModelProperty("员工的下班签到时间")
    private Date endTime;
    @ApiModelProperty("员工的入职时间")
    private Date inTime;
    @ApiModelProperty("员工的状态")
    private Integer status;
    @ApiModelProperty("员工的离职时间")
    private Date outTime;
    @ApiModelProperty("员工的身份")
    private List<Role> roles;

}
