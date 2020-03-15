package com.linglouyi.tools;

import java.util.Date;

/**
 * 时间工具
 */
public class TimeTools {


    /**
     * 判断当前时间是否大于预设时间，大于true
     * @param date 时间
     * @param second 预加毫秒
     * @return boolean
     */
    public static boolean timeOut(Date date,long second){
        long oldTime = date.getTime() + second;
        long time = System.currentTimeMillis();
        return oldTime > time;
    }
}
