package com.nf.commons.uilts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  时间转换工具类
 */
public class TimeUtils {
    /** 时间转换 */
    public static Date updateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}