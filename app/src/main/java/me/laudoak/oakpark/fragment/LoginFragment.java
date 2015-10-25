package me.laudoak.oakpark.fragment;

import android.app.ProgressDialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.net.UserProxy;
import me.laudoak.oakpark.view.LoginView;
import me.laudoak.oakpark.widget.message.AppMsg;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class LoginFragment extends XBaseFragment implements View.OnClickListener{

    private static final String TAG = "LoginFragment";

    private LoginView loginView;

    public static LoginFragment newInstance()
    {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void initData() {
        loginView = new LoginView(context);
    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {

        return loginView;
    }

    @Override
    public void buildViews(View view) {
        loginView.getLoginButton().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在登录...");
        dialog.setTitle(null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        UserProxy proxy = new UserProxy.Builder(getContext())
                .email(loginView.getEmail().getText().toString().trim())
                .password(loginView.getPassword().getText().toString().trim())
                .build();

        proxy.doLogin(new UserProxy.CallBack() {
            @Override
            public void onSuccess() {
                dialog.dismiss();
                AppMsg.makeText(getContext(), "欢迎", AppMsg.STYLE_INFO).show();

                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        getActivity().finish();
                    }
                };

                handler.postDelayed(runnable,1200);

            }

            @Override
            public void onFailure(String reason) {
                dialog.dismiss();
                AppMsg.makeText(getContext(),reason,AppMsg.STYLE_ALERT).show();
            }
        });
    }
}
