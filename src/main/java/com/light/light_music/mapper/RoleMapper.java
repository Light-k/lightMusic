package com.light.light_music.mapper;

import com.light.light_music.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * role的mapper层
 *
 * @Author : KangXu
 * @Date : 2020/7/5
 * @Package : com.light.light_music.mapper
 */

@Mapper
@Repository
public interface RoleMapper {
    //查询所有
    List<Role> findAll();

    //根据员工id查询
    List<Role> findByEmployeeId(@Param("uuid") String uuid);

    //查询可以添加的身份
    List<Role> findEnableRole(@Param("employeeId") String employeeId);

    //向中间表中插入
    void save(Map<String, Object> map);

    //向中间表中删除
    void delete(Map<String, Object> map);
}
