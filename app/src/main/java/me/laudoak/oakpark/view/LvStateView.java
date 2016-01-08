package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.loani.ProgressWheel;

/**
 * Created by LaudOak on 2016-1-8 at 20:24.
 */
public class LvStateView extends RelativeLayout
{

    private Context context;

    private TextView text;
    private ProgressWheel progressWheel;

    public LvStateView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public LvStateView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LvStateView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init()
    {
        View view = inflate(context, R.layout.view_lvstate,this);
        text = (TextView) view.findViewById(R.id.lvstate_text);
        progressWheel = (ProgressWheel) view.findViewById(R.id.lvstate_progressbar);
    }


    public void loading()
    {
        text.setVisibility(GONE);
        progressWheel.setVisibility(VISIBLE);
    }

    public void noData()
    {
        text.setVisibility(VISIBLE);
        text.setText("还没有内容");
        progressWheel.setVisibility(GONE);
    }

    public void loadFailed()
    {
        text.setVisibility(VISIBLE);
        text.setText("获取数据失败了");
        progressWheel.setVisibility(GONE);
    }

    public void hide()
    {
        text.setVisibility(GONE);
        progressWheel.setVisibility(GONE);
    }

}
