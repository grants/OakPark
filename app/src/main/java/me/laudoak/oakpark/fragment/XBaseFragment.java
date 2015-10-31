package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

/**
 * Created by LaudOak on 2015-9-27.
 */
public abstract class XBaseFragment extends Fragment {

    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }


    public void delayExit()
    {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getActivity().setResult(Activity.RESULT_OK,null);
                getActivity().finish();
            }
        };

        handler.postDelayed(runnable, 1200);
    }

}
