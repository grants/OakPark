package me.laudoak.oakpark.utils;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LaudOak on 2015-10-9 at 17:03.
 */
public class TimeUtil {

    public static CharSequence getTimeTextViewTimeDifference(Context context,long createdTime)
    {
        long curTime = System.currentTimeMillis();
        long timeDifference = curTime - createdTime;

        return (timeDifference>0&&timeDifference< DateUtils.MINUTE_IN_MILLIS)?
                "刚刚":DateUtils.getRelativeTimeSpanString(createdTime,curTime,DateUtils.MINUTE_IN_MILLIS,DateUtils.FORMAT_ABBREV_ALL);
    }

    private static final String dateTextPattern = "yyyy-MM-dd";
    private static final String welcomeDatePattern = "MMM.d    EEEE";
    private static final String todayDateCodePattern = "yyyy-MM-dd";

    public static Date getNowDate()
    {
        return new Date(System.currentTimeMillis());
    }

    public static String getWelcomeDateText()
    {

        SimpleDateFormat formatter = new SimpleDateFormat(welcomeDatePattern, Locale.ENGLISH);

        return formatter.format(getNowDate());
    }

    public static String parseDateToDateText()
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateTextPattern);

        String dateStr = formatter.format(getNowDate());

        return dateStr;
    }

    public static String getTodayDateCode()
    {
        SimpleDateFormat formatter = new SimpleDateFormat(todayDateCodePattern);

        return formatter.format(getNowDate());
    }

    public static String genFileName()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        return timeStamp;
    }


}
