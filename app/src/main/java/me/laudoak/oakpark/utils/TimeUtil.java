package me.laudoak.oakpark.utils;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private static final String rawDateCodePattern = "yyyyMMdd";

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

    public static String lanternDay(int dateCode)
    {
        if (String.valueOf(dateCode).length() == 8)
        {
            SimpleDateFormat formatter = new SimpleDateFormat(rawDateCodePattern);
            try
            {
                Date date = formatter.parse(String.valueOf(dateCode));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String lanternMonth(int dateCode)
    {
        if (String.valueOf(dateCode).length() == 8)
        {
            SimpleDateFormat formatter = new SimpleDateFormat(rawDateCodePattern);
            try
            {
                Date date = formatter.parse(String.valueOf(dateCode));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                return String.valueOf(calendar.get(Calendar.MONTH)) + "月";

            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String lanternWeek(int dateCode)
    {
        if (String.valueOf(dateCode).length() == 8)
        {
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

            SimpleDateFormat formatter = new SimpleDateFormat(rawDateCodePattern);
            try
            {
                Date date = formatter.parse(String.valueOf(dateCode));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);


                /**
                 * SUNDAY = 1;
                 * MONDAY = 2;
                 * TUESDAY = 3;
                 * WEDNESDAY = 4;
                 * THURSDAY = 5;
                 * FRIDAY = 6;
                 * SATURDAY = 7;*/
                return weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];

            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

}
