package me.laudoak.oakpark.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.EnterActivity;
import me.laudoak.oakpark.activity.PoetActivity;
import me.laudoak.oakpark.activity.SettingActivity;
import me.laudoak.oakpark.net.UserProxy;
import me.laudoak.oakpark.widget.cciv.CircleImageView;

/**
 * Created by LaudOak on 2015-10-16 at 22:03.
 */
public class DrawerFragment extends XBaseFragment {

    private static final String TAG = "DrawerFragment";

    private CircleImageView avatar;
    private LinearLayout setting;

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.view_drawer,null);
    }

    @Override
    public void buildViews(View view) {
        avatar = (CircleImageView) view.findViewById(R.id.drawer_cciv);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        setting = (LinearLayout) view.findViewById(R.id.drawer_ll_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SettingActivity.class));
            }
        });
    }
}
