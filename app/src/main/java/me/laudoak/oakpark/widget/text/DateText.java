package me.laudoak.oakpark.widget.text;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.utils.CalUtil;
import me.laudoak.oakpark.utils.TimeUtil;

/**
 * Created by LaudOak on 2015-10-15 at 22:53.
 */
public class DateText extends TextView {

    private static final String DELIM = "-";
    private Context context;

    private static final String dateHead = "将在";
    private static final String dateTail = "日展示";

    public DateText(Context c) {
        super(c);
        this.context = c;
        init();
    }

    public DateText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DateText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init()
    {
        setTextColor(getResources().getColor(R.color.darkergreen));
        setTypeface(Typeface.SERIF);
        setTextSize(15);
        setText(dateHead+todayDateText()+dateTail);
    }

    public void setDateCode(int dc)
    {
        setText(dateHead+DateCodeToText(dc)+dateTail);
    }

    public int getDateCode()
    {
        String text = (String) getText();
        String dateString = text.substring(2,12);
        return CalUtil.dateStringToDateCode(dateString);
    }

    private String todayDateText()
    {
        return TimeUtil.parseDateToDateText();
    }

    private String DateCodeToText(int dc)
    {
        return CalUtil.dateCodeToDateString(dc);
    }
}
