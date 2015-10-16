package me.laudoak.oakpark.utils;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by LaudOak on 2015-10-16 at 13:18.
 */
public class CalUtil {

    private static final String TAG = "CalUtil";

    private static final String DELIM = "-";
    private static final String ZERO = "0";

    public static int dateStringToDateCode(String ds)
    {
        int dateCode = 0;

        String[] strs = ds.split(DELIM);

        if (strs.length>0)
        {
            String y = strs[0];
            String m = strs[1];
            String d = strs[2];

            String yCode = y;

            String mCode = null;

            if (m.length()==2)
            {
                mCode = m;
            }else if (m.length()==1)
            {
                mCode = ZERO+m;
            }

            String dCode = null;
            if (d.length()==2)
            {
                dCode = d;
            }else if (d.length()==1)
            {
                dCode = ZERO+d;
            }

            String codeStr = yCode+mCode+dCode;

            dateCode = Integer.parseInt(codeStr);

        }

        Log.d(TAG,"dateStringToDateCode(String ds)"+dateCode);

        return dateCode;
    }

    public static String dateCodeToDateString(int dc)
    {
        String tmp = String.valueOf(dc);
        String dateString = tmp.substring(0,4)+DELIM+tmp.substring(4,6)+DELIM+tmp.substring(6);
        return dateString;
    }

    public static int[] getYM()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;

        int[] YM = new int[2];
        YM[0] = year;
        YM[1] = month;

        return YM;

    }

}
