package com.light.light_music.mapper;

import com.light.light_music.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * user类的mapper层
 *
 * @Author : KangXu
 * @Date : 2020/7/3
 * @Package : com.light.light_music.mapper
 */

@Mapper
@Repository
public interface UserMapper {
    //查询所有
    List<User> findAll();

    //根据uuid查询
    User findByUUID(@Param("uuid") String uuid);

    //根据email查询
    User findByEmail(@Param("email") String email);

    //根据nickName查询
    User findByNickname(@Param("nickname") String nickname);

    //添加用户
    void save(User user);

    //更新用户
    void update(User user);

    //删除用户
    void delete(@Param("uuid") String uuid);
}
