package com.light.light_music.service.impl;

import com.light.light_music.mapper.EmployeeMapper;
import com.light.light_music.pojo.Employee;
import com.light.light_music.pojo.AdminDetail;
import com.light.light_music.pojo.Role;
import com.light.light_music.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * security授权认证类
 *
 * @Author : KangXu
 * @Date : 2020/7/9
 * @Package : com.light.light_music.service.impl
 */

@Service
public class AdminDetailServiceImpl implements UserDetailsService {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee = employeeMapper.findByUsername(s);
        if(null != employee){
            List<Role> roles = roleService.findByEmployeeId(employee.getUuid());
            return new AdminDetail(employee,roles);
        }
        return null;
    }
}
