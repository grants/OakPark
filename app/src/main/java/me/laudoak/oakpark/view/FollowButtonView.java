package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.loani.ProgressWheel;

/**
 * Created by LaudOak on 2015-12-29 at 22:28.
 */
public class FollowButtonView extends RelativeLayout
{
    private static final String TAG = FollowButtonView.class.getName();

    private Context context;

    private TextView hintText;
    private ProgressWheel loani;

    public FollowButtonView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public FollowButtonView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FollowButtonView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    private void init()
    {
        View view = inflate(context, R.layout.view_followbutton,this);
        hintText = (TextView) view.findViewById(R.id.view_follow_text);
        loani = (ProgressWheel) view.findViewById(R.id.view_follow_loani);
    }

}
