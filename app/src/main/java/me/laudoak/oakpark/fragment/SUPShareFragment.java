package me.laudoak.oakpark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.entity.XVerse;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPShareFragment extends XBaseFragment implements OakParkActivity.NXVUCallback{

    private static final String TAG = "SUPShareFragment";

    private View rootView;

    private XVerse currXV;

    public static SUPShareFragment newInstance()
    {
        return HolderClass.fragment;
    }

    private static class HolderClass
    {
        private final static SUPShareFragment fragment = new SUPShareFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.view_sup_share,container,false);
        }else if (null != (rootView.getParent())){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }

        return rootView;
    }


    @Override
    public void onUpdateXV(XVerse xv) {
        if (currXV != xv)
        {
            currXV = xv;
        }
    }

}
