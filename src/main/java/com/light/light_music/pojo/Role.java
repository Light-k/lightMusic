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
 * role表
 *
 * @Author : KangXu
 * @Date : 2020/7/5
 * @Package : com.light.light_music.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("r")
@ApiModel("员工身份表")
public class Role implements Serializable {
    @ApiModelProperty("身份的id")
    private Integer id;
    @ApiModelProperty("身份的描述")
    private String roleDesc;
    @ApiModelProperty("身份的名称")
    private String roleName;
    @ApiModelProperty("对应的员工")
    private List<Employee> employees;
}
