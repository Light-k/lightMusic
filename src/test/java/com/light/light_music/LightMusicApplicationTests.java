package com.light.light_music;

import com.light.light_music.MyUtils.MyPinYin;
import com.light.light_music.MyUtils.MyTimer;
import com.light.light_music.controller.UserController;
import com.light.light_music.pojo.Group;
import com.light.light_music.pojo.Song;
import com.light.light_music.service.SongService;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class LightMusicApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    SongService songService;
    @Autowired
    UserController userController;



    @Test
    void contextLoads() throws NoSuchFieldException, IllegalAccessException, BadHanyuPinyinOutputFormatCombination {
        Integer a1 = new Integer(1);
        Integer a2 = new Integer(1);
        System.out.println(a1==a2);
    }


}
