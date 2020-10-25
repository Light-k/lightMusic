package com.light.light_music.mapper;

import com.light.light_music.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * employee类的mapper层
 *
 * @Author : KangXu
 * @Date : 2020/7/5
 * @Package : com.light.light_music.mapper
 */

@Mapper
@Repository
public interface EmployeeMapper {
    //查询所有
    List<Employee> findAll();

    //根据uuid查询
    Employee findByUUID(@Param("uuid") String uuid);

    //根据username查询
    Employee findByUsername(@Param("username") String username);

    //添加员工
    void save(Employee employee);

    //更新员工
    void update(Employee employee);

    //删除员工
    void delete(@Param("uuid") String uuid);
}
