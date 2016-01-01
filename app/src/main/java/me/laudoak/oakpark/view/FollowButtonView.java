package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.net.bmob.UserProxy;
import me.laudoak.oakpark.net.bmob.relevance.AbRelevance;
import me.laudoak.oakpark.net.bmob.relevance.Follow;
import me.laudoak.oakpark.net.bmob.relevance.IsFollow;
import me.laudoak.oakpark.net.bmob.relevance.UnFollow;
import me.laudoak.oakpark.ui.loani.ProgressWheel;

/**
 * Created by LaudOak on 2015-12-29 at 22:28.
 */
public class FollowButtonView extends RelativeLayout implements View.OnClickListener
{
    private static final String TAG = FollowButtonView.class.getName();

    private Context context;

    private TextView hintText;
    private ProgressWheel loani;

    private STATE state = STATE.LOADING;

    Poet currentPoet;

    enum STATE
    {
        LOADING,
        FOLLOW,
        UNFOLLOW
    }

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
        currentPoet = UserProxy.currentPoet(context);

        View view = inflate(context, R.layout.view_followbutton, this);
        hintText = (TextView) view.findViewById(R.id.view_follow_text);
        loani = (ProgressWheel) view.findViewById(R.id.view_follow_loani);

        this.setOnClickListener(this);

    }

    private Poet secondaryPoet;

    public void setSecondaryPoet(Poet poet)
    {

        if (null != currentPoet && null != poet)
        {
            if (poet.getObjectId().equals(currentPoet.getObjectId()))
            {
                this.setVisibility(GONE);
            }else
            {
                secondaryPoet = poet;
                new IsFollow(context,secondaryPoet,this).execute();
            }

        }else if (null == currentPoet)
        {
            this.unFollowSatate();
        }

    }

    public void followState()
    {
        this.state = STATE.FOLLOW;
        this.loani.setVisibility(GONE);
        this.hintText.setVisibility(VISIBLE);
        this.hintText.setText("取消关注");
    }

    public void unFollowSatate()
    {
        this.state = STATE.UNFOLLOW;
        this.loani.setVisibility(GONE);
        this.hintText.setVisibility(VISIBLE);
        this.hintText.setText("关注");
    }

    public void loading()
    {
        this.state = STATE.LOADING;
        this.loani.setVisibility(VISIBLE);
        this.hintText.setVisibility(GONE);
    }

    @Override
    public void onClick(View view)
    {

        if (null != UserProxy.currentPoet(context))
        {
            action();
        }else
        {
            Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
        }
    }

    private void action()
    {

        if (null != secondaryPoet && null != currentPoet)
        {
            switch (state)
            {
                case UNFOLLOW:
                {
                    this.loading();

                    new Follow(context, new AbRelevance.RelevanceCallBack()
                    {
                        @Override
                        public void onAssociateSucess()
                        {
                            FollowButtonView.this.followState();
                            Toast.makeText(context, "已关注", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAssociateFailure(String why)
                        {
                            FollowButtonView.this.unFollowSatate();
                            Toast.makeText(context,why,Toast.LENGTH_SHORT).show();
                        }
                    }).follow(secondaryPoet);

                    break;
                }
                case FOLLOW:
                {

                    this.loading();
                    new UnFollow(context, new AbRelevance.RelevanceCallBack()
                    {
                        @Override
                        public void onAssociateSucess()
                        {
                            FollowButtonView.this.unFollowSatate();
                            Toast.makeText(context,"已取消关注",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAssociateFailure(String why)
                        {
                            FollowButtonView.this.unFollowSatate();
                            Toast.makeText(context,why,Toast.LENGTH_SHORT).show();
                        }
                    }).unFollow(secondaryPoet);

                    break;
                }
            }
        }else
        {
            Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
        }
    }
}
