package me.laudoak.oakpark.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Pattern;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.net.UserProxy;
import me.laudoak.oakpark.widget.message.AppMsg;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class LoginFragment extends XBaseFragment implements
        View.OnClickListener,
        TextWatcher {

    private static final String TAG = "LoginFragment";


    private EditText email,password;
    private Button login;
    private ImageView login_qq,login_weibo,login_weixin;

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    );


    public static LoginFragment newInstance()
    {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.view_login,container,false);
        buildViews(loginView);

        return loginView;
    }


    private void buildViews(View v)
    {
        email = (EditText) v.findViewById(R.id.login_email);
        password = (EditText) v.findViewById(R.id.login_password);
        login = (Button) v.findViewById(R.id.login_login);

        email.addTextChangedListener(this);
        password.addTextChangedListener(this);

        login.setEnabled(false);

        login_qq = (ImageView) v.findViewById(R.id.login_qq);
        login_weibo = (ImageView) v.findViewById(R.id.login_weibo);
        login_weixin = (ImageView) v.findViewById(R.id.login_weixin);
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
                .email(email.getText().toString().trim())
                .password(password.getText().toString().trim())
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

    /*TextWatcher*/
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString()).matches()
                &&password.getText().toString().length()>=6)
        {
            login.setEnabled(true);
        }else
        {
            login.setEnabled(false);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}