package me.laudoak.oakpark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import me.laudoak.oakpark.view.RegisterView;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class RegisterFragment extends XBaseFragment
{

    private static final String TAG = "RegisterFragment";


    public static RegisterFragment newInstance()
    {
        return new RegisterFragment();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart("RegisterFragment"); //统计页面
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd("RegisterFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return new RegisterView(getActivity());
    }
}
