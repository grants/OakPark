package me.laudoak.oakpark.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.EnterActivity;
import me.laudoak.oakpark.activity.PoetActivity;
import me.laudoak.oakpark.activity.SettingActivity;
import me.laudoak.oakpark.ctrl.listener.DrawerItemClickListener;
import me.laudoak.oakpark.ctrl.listener.SignClickListener;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.net.bmob.UserProxy;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.text.FangTextView;
import me.laudoak.oakpark.ui.text.FluidTextView;

/**
 * Created by LaudOak on 2015-10-16 at 22:03.
 */
public class DrawerFragment extends XBaseFragment
        implements SignClickListener.SignUpdateCallback
{

    private static final String TAG = DrawerFragment.class.getName();

    @Bind(R.id.drawer_nick) FangTextView nick;
    @Bind(R.id.drawer_sign) FluidTextView sign;
    @Bind(R.id.drawer_avatar) SimpleDraweeView avatar;
    @Bind(R.id.drawer_num) TextView num;
    @Bind(R.id.drawer_ll_setting) LinearLayout setting;
    @Bind(R.id.container_nicksign) LinearLayout signContainer;

    @Bind(R.id.drawer_menu_container) LinearLayout drawerMenuContainer;
    @Bind(R.id.drawer_item_newpoem) LinearLayout newpoem;
    @Bind(R.id.drawer_item_personal) LinearLayout personal;
    @Bind(R.id.drawer_item_calendar) LinearLayout calendar;
    @Bind(R.id.drawer_item_friend) LinearLayout friend;

    private Poet poet;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        poet = UserProxy.currentPoet(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_drawer,container,false);
        ButterKnife.bind(this, view);
        buildViews();

        return view;
    }

    private void buildViews()
    {
        avatar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (UserProxy.ifLogin(context))
                {
                    Intent intent = new Intent();
                    intent.setClass(context, PoetActivity.class);

                    startActivity(intent);
                }else
                {
                    Intent intent = new Intent();
                    intent.setClass(context, EnterActivity.class);

                    startActivity(intent);
                }
            }
        });

        sign.setOnClickListener(new SignClickListener(context,this));

        setting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(context, SettingActivity.class));
            }
        });

        DrawerItemClickListener menuListener = new DrawerItemClickListener(context);
        newpoem.setOnClickListener(menuListener);
        newpoem.setOnLongClickListener(menuListener);
        personal.setOnClickListener(menuListener);
        calendar.setOnClickListener(menuListener);
        friend.setOnClickListener(menuListener);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        updateBasic();
        updateSign();

        if (!UserProxy.ifLogin(context))
        {
            unLogin();
        }else
        {
           login();
        }
    }

    private void updateBasic()
    {
        poet = UserProxy.currentPoet(context);

        if (null != poet)
        {
            nick.setText(poet.getUsername());
            if (null!=poet.getAvatarURL())
            {
                Uri uri = Uri.parse(poet.getAvatarURL());
                avatar.setImageURI(uri);
            }
        }else{
            nick.setText("");
            avatar.setImageURI(null);
        }
    }

    private void updateSign()
    {
        poet = UserProxy.currentPoet(context);

        if (null != poet  && null != poet.getSign())
        {
            sign.setText(poet.getSign());
        }
    }

    /**Sign update callback*/
    @Override
    public void onSuccess()
    {
        AppMsg.makeText(context,"签名已更新",AppMsg.STYLE_INFO).show();
        updateSign();
    }

    @Override
    public void onFailure(String why)
    {
        AppMsg.makeText(context,"签名更新失败",AppMsg.STYLE_ALERT).show();
    }

    private void unLogin()
    {
        nick.setText("点击头像登录");
        drawerMenuContainer.setVisibility(View.GONE);
        signContainer.setVisibility(View.GONE);
    }

    private void login()
    {
        drawerMenuContainer.setVisibility(View.VISIBLE);
        signContainer.setVisibility(View.VISIBLE);
    }
}
