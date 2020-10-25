package com.light.light_music.service.impl;

import com.light.light_music.MyUtils.MyUUID;
import com.light.light_music.mapper.EmployeeMapper;
import com.light.light_music.pojo.Employee;
import com.light.light_music.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * admin类的service层实现类
 *
 * @Author : KangXu
 * @Date : 2020/7/5
 * @Package : com.light.light_music.service.impl
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    //查询所有
    @Override
    public List<Employee> findAllEmployees() {
        return employeeMapper.findAll();
    }

    //根据uuid查询
    @Override
    public Employee findEmployeeByUUID(String uuid) {
        return employeeMapper.findByUUID(uuid);
    }

    //根据username查询
    @Override
    public Employee findEmployeeByUsername(String username) {
        return employeeMapper.findByUsername(username);
    }

    //添加员工
    @Override
    public void save(Employee employee) {
        employee.setUuid(MyUUID.getUUID());
        //密码加密
        employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
        //设置入职时间
        Date inTime = new Date();
        employee.setInTime(inTime);
        //设置员工的状态
        employee.setStatus(1);
        employeeMapper.save(employee);
    }

    //更新员工密码
    @Override
    public void update(Employee employee) {
        //密码加密
        if(null != employee.getPassword()){
            employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
        }
        employeeMapper.update(employee);
    }

    //删除员工
    @Override
    public void delete(String uuid) {
        employeeMapper.delete(uuid);
    }
}
