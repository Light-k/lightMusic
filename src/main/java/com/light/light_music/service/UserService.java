package com.light.light_music.service;

import com.light.light_music.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user类的service层
 *
 * @Author : KangXu
 * @Date : 2020/7/3
 * @Package : com.light.light_music.service
 */

@Service
public interface UserService {
    //查询所有
    List<User> findAll();

    //根据uuid查询
    User findByUUID(String uuid);

    //根据email查询
    User findByEmail(String email);

    //根据nickName查询
    User findByNickname(String nickname);

    //添加用户
    void save(User user);

    //更新用户
    void update(User user);

    //删除用户
    void delete(String uuid);
}
