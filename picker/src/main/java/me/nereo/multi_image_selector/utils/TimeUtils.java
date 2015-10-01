package me.nereo.multi_image_selector.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理工具
 * Created by Nereo on 2015/4/8.
 */
public class TimeUtils {

    public static String timeFormat(long timeMillis, String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    public static String formatPhotoDate(long time){
        return timeFormat(time, "yyyy-MM-dd");
    }

    public static String formatPhotoDate(String path){
        File file = new File(path);
        if(file.exists()){
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }

    /***/
    private static final String timePattern = "yyyyMMddHHmmss";

    public static String getNowDateToString()
    {
        SimpleDateFormat formatter = new SimpleDateFormat(timePattern);

        String dateStr = formatter.format(getNowDate());

        return dateStr;
    }

    public static Date getNowDate()
    {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

}
