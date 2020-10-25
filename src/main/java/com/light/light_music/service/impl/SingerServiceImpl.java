package com.light.light_music.service.impl;

import com.light.light_music.MyUtils.MyPinYin;
import com.light.light_music.MyUtils.MyUUID;
import com.light.light_music.mapper.SingerMapper;
import com.light.light_music.pojo.Singer;
import com.light.light_music.service.SingerService;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * singer表的service层实现类
 *
 * @Author : KangXu
 * @Date : 2020/8/20
 * @Package : com.light.light_music.service.impl
 */

@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    SingerMapper singerMapper;

    //根据歌手的uuid值查询
    @Override
    public Singer findByUuid(String uuid) {
        return singerMapper.findByUuid(uuid);
    }

    //查询所有
    @Override
    public List<Singer> findAll() {
        return singerMapper.findAll();
    }

    //根据歌手的名称查询
    @Override
    public List<Singer> findBySingers(String singer) {
        return singerMapper.findBySingers(singer);
    }

    //根据歌手的名称的首字母/性别/所在的地区/演唱的曲风查询
    @Override
    public List<Singer> querySinger(Singer singer) {
        return singerMapper.querySinger(singer);
    }

    //根据歌手的名称查询
    @Override
    public Singer findBySinger(String singer) {
        return singerMapper.findBySinger(singer);
    }

    //添加歌手
    @Override
    public void save(Singer singer) throws BadHanyuPinyinOutputFormatCombination {
        singer.setUuid(MyUUID.getUUID());
        singer.setFirstLatter(MyPinYin.convertToPinYin(singer.getSinger()));
        singerMapper.save(singer);
    }

    //更新歌手信息
    @Override
    public void update(Singer singer) throws BadHanyuPinyinOutputFormatCombination {
        singer.setFirstLatter(MyPinYin.convertToPinYin(singer.getSinger()));
        singerMapper.update(singer);
    }

    //删除歌手
    @Override
    public void delete(String uuid) {
        singerMapper.delete(uuid);
    }
}
