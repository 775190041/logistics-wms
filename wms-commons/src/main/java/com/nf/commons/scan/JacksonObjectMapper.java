package com.nf.commons.scan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 *  解决Jackson 差8小时的问题
 */
public class JacksonObjectMapper extends ObjectMapper {

    //全球区域设置
    private static final Locale CHINA = Locale.CHINA;

    public JacksonObjectMapper() {
        //区域设置
        this.setLocale(CHINA);
        //时间格式设置
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", CHINA));
        //GMT时区设置
        this.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }
}
