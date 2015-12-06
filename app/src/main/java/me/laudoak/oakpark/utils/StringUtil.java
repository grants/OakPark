package me.laudoak.oakpark.utils;

import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import java.util.Random;

/**
 * Created by LaudOak on 2015-11-26 at 22:14.
 */
public class StringUtil {

    public static String genNickTail(String source)
    {

        int opidlen = source.length();
        String dest = "";
        for (int i = 0 ; i < 4 ; i++)
        {
            dest = dest + source.charAt(genRandom(opidlen-1));
        }

        return dest;
    }

    public static boolean needReUpdate(String who ,String nick)
    {
        boolean res = true;
        switch (who)
        {
            case "qq":
            {
                res = nick.length() > 28;
                break;
            }
            case "weibo":
            {
                res = nick.length() > 25;
                break;
            }
        }
        return res;
    }

    private static int genRandom(int len)
    {
        int max=len;
        int min=0;
        Random random = new Random();

        return random.nextInt ( max ) % ( max - min + 1) + min;
    }

    public enum SpannyType
    {

        UNDERLINE,
        URL,
        FOREIGN,
        QUOTE,
        MULTIPLE;

    }

    public static Spanny genSpannyText(String source , SpannyType type)
    {
        Spanny spanny = null;

        if (null != source)
        {
            switch (type)
            {
                case UNDERLINE:
                {
                    spanny = new Spanny(source,new UnderlineSpan());
                    break;
                }
            }
        }
        return spanny;
    }

}
