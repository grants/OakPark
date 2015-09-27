package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.view.RegisterView;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class RegisterFragment extends XBaseFragment {

    private static final String TAG = "RegisterFragment";


    public static RegisterFragment newInstance()
    {
        RegisterFragment fragment = new RegisterFragment();

        return fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return new RegisterView(getActivity());
    }

    @Override
    public void buildViews(View view) {

    }
}
