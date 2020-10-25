package com.light.light_music.service.impl;

import com.light.light_music.MyUtils.MyUUID;
import com.light.light_music.mapper.UserMapper;
import com.light.light_music.pojo.User;
import com.light.light_music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user类的service层实现类
 *
 * @Author : KangXu
 * @Date : 2020/7/3
 * @Package : com.light.light_music.service.impl
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    //查询所有
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    //根据uuid查询
    @Override
    public User findByUUID(String uuid) {
        return userMapper.findByUUID(uuid);
    }

    //根据email查询
    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    //根据nickName查询
    @Override
    public User findByNickname(String nickname) {
        return userMapper.findByNickname(nickname);
    }

    //添加用户
    @Override
    public void save(User user) {
        user.setUuid(MyUUID.getUUID());
        user.setRole(0);            //0 普通用户，1 会员
        user.setAuth(3);
        user.setCounts(0);
        user.setAppeal(0);
        user.setCredit(100);
        userMapper.save(user);
    }

    //更新用户
    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    //删除用户
    @Override
    public void delete(String uuid) {
        userMapper.delete(uuid);
    }
}
