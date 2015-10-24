package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.entity.XVerse;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPShareFragment extends XBaseFragment implements OakParkActivity.NXVUCallback{

    private static final String TAG = "SUPShareFragment";


    private ImageView wechat,weibo,qq;

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.view_sup_share,null);
    }

    @Override
    public void buildViews(View view) {
        wechat = (ImageView) view.findViewById(R.id.sup_share_wechat);
        weibo = (ImageView) view.findViewById(R.id.sup_share_weibo);
        qq = (ImageView) view.findViewById(R.id.sup_share_qq);
        buildListener();
    }

    private void buildListener() {
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onUpdateXV(XVerse xv) {

    }
}
