package com.light.light_music.service;

import com.light.light_music.pojo.Role;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * role的service层
 *
 * @Author : KangXu
 * @Date : 2020/7/5
 * @Package : com.light.light_music.service
 */

@Service
public interface RoleService {
    //查询所有
    List<Role> findAll();
    //根据员工id查询
    List<Role> findByEmployeeId(String uuid);
    //查询可以添加的身份
    List<Role> findEnableRole(String employeeId);
    //添加员工身份
    void save(String employeeId, Integer[] roleIds);
    //删除员工身份
    void delete(String employeeId, Integer roleId);
}
