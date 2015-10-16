package me.laudoak.oakpark.utils;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static Date getNowDate()
    {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public static String parseDateToDateText()
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateTextPattern);

        String dateStr = formatter.format(getNowDate());

        return dateStr;
    }
}
