package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.view.LoginView;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class LoginFragment extends XBaseFragment {

    private static final String TAG = "LoginFragment";

    public static LoginFragment newInstance()
    {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return new LoginView(getActivity());
    }

    @Override
    public void buildViews(View view) {

    }
}
