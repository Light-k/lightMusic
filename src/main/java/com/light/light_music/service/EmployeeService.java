package com.light.light_music.service;

import com.light.light_music.pojo.Employee;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * admin类的service层
 *
 * @Author : KangXu
 * @Date : 2020/7/5
 * @Package : com.light.light_music.service
 */

@Service
public interface EmployeeService {
    //查询所有
    List<Employee> findAllEmployees();
    //根据uuid查询
    Employee findEmployeeByUUID(String uuid);
    //根据username查询
    Employee findEmployeeByUsername(String username);
    //添加管理员
    void save(Employee employee);
    //更新管理员
    void update(Employee employee);
    //删除管理员
    void delete(String uuid);
}
