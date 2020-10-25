package com.light.light_music.service;

import com.light.light_music.pojo.Singer;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * singer表的service层
 *
 * @Author : KangXu
 * @Date : 2020/8/20
 * @Package : com.light.light_music.service
 */

@Service
public interface SingerService {
    //根据歌手的uuid值查询
    Singer findByUuid(String uuid);

    //查询所有
    List<Singer> findAll();

    //根据歌手的名称查询
    List<Singer> findBySingers(String singer);

    //根据歌手的名称的首字母/性别/所在的地区/演唱的曲风查询
    List<Singer> querySinger(Singer singer);

    //根据歌手的名称查询
    Singer findBySinger(String singer);

    //添加歌手
    void save(Singer singer) throws BadHanyuPinyinOutputFormatCombination;

    //更新歌手信息
    void update(Singer singer) throws BadHanyuPinyinOutputFormatCombination;

    //删除歌手
    void delete(String uuid);
}
