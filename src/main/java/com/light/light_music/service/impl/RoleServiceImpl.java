package com.light.light_music.service.impl;

import com.light.light_music.mapper.RoleMapper;
import com.light.light_music.pojo.Role;
import com.light.light_music.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

/**
 * role的service层实现类
 *
 * @Author : KangXu
 * @Date : 2020/7/5
 * @Package : com.light.light_music.service.impl
 */

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    //查询所有
    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    //根据员工id查询
    @Override
    public List<Role> findByEmployeeId(String uuid) {
        return roleMapper.findByEmployeeId(uuid);
    }

    //查询可以添加的身份
    @Override
    public List<Role> findEnableRole(String employeeId) {
        return roleMapper.findEnableRole(employeeId);
    }

    //添加员工身份
    @Override
    public void save(String employeeId, Integer[] roleIds) {
        for (Integer roleId : roleIds) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("employeeId",employeeId);
            map.put("roleId",roleId);
            roleMapper.save(map);
        }
    }
    //删除员工身份
    @Override
    public void delete(String employeeId, Integer roleId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("employeeId",employeeId);
        map.put("roleId",roleId);
        roleMapper.delete(map);
    }

}
