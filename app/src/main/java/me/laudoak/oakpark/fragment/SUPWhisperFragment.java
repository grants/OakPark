package me.laudoak.oakpark.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.entity.XVerse;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPWhisperFragment extends XBaseFragment implements OakParkActivity.NXVUCallback{

    private static final String TAG = "SUPWhisperFragment";

    private TextView whisper;
    private SimpleDraweeView avatar;
    private TextView nick;
    private XVerse curXV;


    @Override
    public void onResume() {
        super.onResume();
        setCurr();
        MobclickAgent.onPageStart(TAG); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    public static SUPWhisperFragment newInstance()
    {
        return HolderClass.fragment;
    }

    private static class HolderClass
    {
        private final static SUPWhisperFragment fragment = new SUPWhisperFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_sup_whisper,null);

        buildViews(view);

        return view;

    }


    private void buildViews(View view) {

        whisper = (TextView) view.findViewById(R.id.sup_whisper_whisper);
        avatar = (SimpleDraweeView) view.findViewById(R.id.sup_whisper_avatar);
        nick = (TextView) view.findViewById(R.id.sup_whisper_nick);
    }


    private void setCurr()
    {
        if (null != curXV)
        {
            whisper.setText(curXV.getWhisper());
            nick.setText(curXV.getPoet().getUsername());

            if (null != curXV.getPoet().getAvatarURL())
            {
                Uri uri = Uri.parse(curXV.getPoet().getAvatarURL());
                avatar.setImageURI(uri);
            }
        }
    }

    @Override
    public void onUpdateXV(XVerse xv) {

        if (xv != curXV)
        {
            curXV = xv;

            Log.d(TAG,"curXV getWhisper()" + curXV.getWhisper());

            setCurr();
        }
    }


}
