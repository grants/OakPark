package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.text.FangTextView;
import me.laudoak.oakpark.utils.TimeUtil;

/**
 * Created by LaudOak on 2015-12-16 at 15:51.
 */
public class LanternTextView extends LinearLayout
{
    private static final String TAG = LanternTextView.class.getName();

    private Context context;

    private FangTextView day;
    private TextView month;
    private TextView week;

    public LanternTextView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public LanternTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LanternTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init()
    {
        View view = inflate(context, R.layout.view_lanterntext,this);
        day = (FangTextView) view.findViewById(R.id.lantern_day);
        month = (TextView) view.findViewById(R.id.lantern_month);
        week = (TextView) view.findViewById(R.id.lantern_week);
    }

    public void setLanternText(int dateCode)
    {
        day.setText(TimeUtil.lanternDay(dateCode));
        month.setText(TimeUtil.lanternMonth(dateCode));
        week.setText(TimeUtil.lanternWeek(dateCode));
    }

}
