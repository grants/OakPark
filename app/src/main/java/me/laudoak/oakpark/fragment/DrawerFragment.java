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

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.EnterActivity;
import me.laudoak.oakpark.activity.PoetActivity;
import me.laudoak.oakpark.activity.SettingActivity;
import me.laudoak.oakpark.entity.Poet;
import me.laudoak.oakpark.net.UserProxy;

/**
 * Created by LaudOak on 2015-10-16 at 22:03.
 */
public class DrawerFragment extends XBaseFragment {

    private static final String TAG = "DrawerFragment";

    private View rootView;

    private Poet poet;
    private LinearLayout setting;
    private SimpleDraweeView avatar;
    private TextView nick,num;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        poet = UserProxy.currentPoet(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.view_drawer,container,false);
        }else if (null != (rootView.getParent())){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }

        buildViews(rootView);

        return rootView;
    }


    private void buildViews(View view) {
        avatar = (SimpleDraweeView) view.findViewById(R.id.drawer_avatar);
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

        nick = (TextView) view.findViewById(R.id.drawer_nick);
        num = (TextView) view.findViewById(R.id.drawer_num);

        setting = (LinearLayout) view.findViewById(R.id.drawer_ll_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SettingActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        poet = UserProxy.currentPoet(context);

        if (null!=poet)
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
}
