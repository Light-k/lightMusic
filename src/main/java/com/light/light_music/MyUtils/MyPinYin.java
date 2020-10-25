package com.light.light_music.MyUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Pattern;

/**
 * 自定义的汉语拼音转换工具类
 *
 * @Author : KangXu
 * @Date : 2020/8/24
 * @Package : com.light.light_music.MyUtils
 */


public class MyPinYin {
    /**
     * 将汉字转换为汉语拼音
     *
     * @param name：歌手的名称
     * @return 首字母大写
     */
    public static String convertToPinYin(String name) throws BadHanyuPinyinOutputFormatCombination {
        String result;                  //首字母大写
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");         //确定汉字的范围
        if (p.matcher(name).find()) {
            HanyuPinyinOutputFormat py = new HanyuPinyinOutputFormat();
            py.setCaseType(HanyuPinyinCaseType.LOWERCASE);                  //将汉字转换为英文小写
            py.setToneType(HanyuPinyinToneType.WITHOUT_TONE);               //不区分多音字
            String res = PinyinHelper.toHanYuPinyinString(name, py, "", true);
            result = res.substring(0, 1).toUpperCase();
        } else {
            result = name.substring(0, 1).toUpperCase();
        }
        return result;
    }
}
