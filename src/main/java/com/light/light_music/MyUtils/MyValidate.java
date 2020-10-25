package com.light.light_music.MyUtils;

/**
 * 校验器
 *
 * @Author : KangXu
 * @Date : 2020/7/20
 * @Package : com.light.light_music.MyUtils
 */


public class MyValidate {
    /**
     * 邮箱验证
     * @param email:用户输入的邮箱
     * @regex 正则表达式：
     *      []:表示字符所在的范围
     *      \:转义字符
     *      \w:代表字符(包括英文,数字)
     *      +:代表可以出现一次或多次
     *      '@':代表@符号
     *      '.':代表.符号
     * @return
     *      true:符合验证
     *      false:不符合验证
     */
    public static boolean emailValidate(String email){
        String result = "[\\w]+@[\\w]+.[\\w]+";
        if(email.matches(result)){
            return true;
        }
        else {
            return false;
        }
    }
}
