package me.laudoak.oakpark.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import me.laudoak.oakpark.activity.OuterActivity;
import me.laudoak.oakpark.entity.core.Poet;

/**
 * Created by LaudOak on 2016-1-1 at 21:08.
 */
public class AvatarView extends SimpleDraweeView implements View.OnClickListener
{
    public static final String EXTRA_POET = "extra poet";

    private Context context;

    public AvatarView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public AvatarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }


    private void init()
    {
        this.setOnClickListener(this);
    }

    private Poet poet;

    public void setPoet(Poet poet)
    {
        this.poet = poet;
    }

    @Override
    public void onClick(View view)
    {
        if (null != poet)
        {
            Intent intent = new Intent(context, OuterActivity.class);
            intent.putExtra(OuterActivity.EXTRA_POET, poet);
            context.startActivity(intent);
        }
    }
}
