package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.OuterActivity;
import me.laudoak.oakpark.ctrl.xv.AbXVOberver;
import me.laudoak.oakpark.entity.core.XVerse;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SupWhisperFragment extends XBaseFragment implements
        AbXVOberver, View.OnClickListener
{
    private static final String TAG = SupWhisperFragment.class.getName();

    public static final String EXTRA_POET = "extra poet";

    private View rootView;
    private TextView whisper;
    private SimpleDraweeView avatar;
    private TextView nick;
    private XVerse curXV;
    private boolean isViewCreated = false;


    private static class HolderClass
    {
        private final static SupWhisperFragment fragment = new SupWhisperFragment();
    }

    public static SupWhisperFragment getSingletonInstance()
    {
        return HolderClass.fragment;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.view_sup_whisper,container,false);
        }else if (null != (rootView.getParent())){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }

        buildViews(rootView);
        isViewCreated = true;
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        onXVUpdate();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        isViewCreated = false;
    }

    private void buildViews(View view)
    {
        whisper = (TextView) view.findViewById(R.id.sup_whisper_whisper);
        avatar = (SimpleDraweeView) view.findViewById(R.id.sup_whisper_avatar);
        nick = (TextView) view.findViewById(R.id.sup_whisper_nick);
    }


    private void onXVUpdate()
    {
        if (null != curXV)
        {
            whisper.setText(curXV.getWhisper());
            nick.setText(curXV.getBulbul().getUsername());

            if (null != curXV.getBulbul().getAvatarURL())
            {
                Uri uri = Uri.parse(curXV.getBulbul().getAvatarURL());
                avatar.setImageURI(uri);
            }
        }
    }

    @Override
    public void notifyXVUpdate(XVerse xv)
    {
        if (curXV != xv)
        {
            curXV = xv;
            if (isViewCreated)
            {
                onXVUpdate();
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.sup_whisper_avatar && curXV != null && curXV.getBulbul() != null)
        {
            Intent intent = new Intent(context, OuterActivity.class);
            intent.putExtra(EXTRA_POET, curXV.getBulbul());
            context.startActivity(intent);
        }
    }

}
