package me.laudoak.oakpark.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.tauth.Tencent;

import org.json.JSONObject;

import java.util.regex.Pattern;

import me.laudoak.oakpark.OP;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.net.UserProxy;
import me.laudoak.oakpark.sosh.tplogin.qq.QQAuthListener;
import me.laudoak.oakpark.sosh.tplogin.XBaseAuth;
import me.laudoak.oakpark.sosh.tplogin.weibo.LoginButton;
import me.laudoak.oakpark.widget.message.AppMsg;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class LoginFragment extends XBaseFragment implements
        TextWatcher {

    private static final String TAG = "LoginFragment";

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    );

    private EditText email,password;
    private Button login;

    private ImageView login_qq,login_weixin;
    private LoginButton login_weibo;

    private static Tencent tencent;
    private QQAuthListener qqListener;


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
        return inflater.inflate(R.layout.view_login, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildViews(view);
    }

    private void buildViews(View v)
    {
        email = (EditText) v.findViewById(R.id.login_email);
        password = (EditText) v.findViewById(R.id.login_password);
        email.addTextChangedListener(this);
        password.addTextChangedListener(this);

        login = (Button) v.findViewById(R.id.login_login);
        login.setEnabled(false);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithRaw();
            }
        });


        /**/
        login_qq = (ImageView) v.findViewById(R.id.login_qq);
        login_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithQQ();
            }
        });

        login_weibo = (LoginButton) v.findViewById(R.id.login_weibo);
        loginWithWeibo();

        login_weixin = (ImageView) v.findViewById(R.id.login_weixin);

    }


    /*login by Bmob count*/
    private void loginWithRaw()
    {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在登录...");
        dialog.setTitle(null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        UserProxy proxy = new UserProxy.Builder(context)
                .email(email.getText().toString().trim())
                .password(password.getText().toString().trim())
                .build();

        proxy.doLogin(new UserProxy.CallBack() {
            @Override
            public void onSuccess(String nick) {
                dialog.dismiss();
                AppMsg.makeText(context, "欢迎" + nick, AppMsg.STYLE_INFO).show();
                loginSuccess();
            }

            @Override
            public void onFailure(String reason) {
                dialog.dismiss();
                AppMsg.makeText(context, reason, AppMsg.STYLE_ALERT).show();
            }
        });
    }

    /*Login by QQ*/
    private void loginWithQQ()
    {

        if(tencent == null)
        {
            tencent = Tencent.createInstance(OP.QQ_APP_ID, context);
        }

        qqListener = new QQAuthListener(context, new XBaseAuth.AuthCallback() {
            @Override
            public void onSuccess(String desc,JSONObject authInfo) {
                AppMsg.makeText(context,"欢迎"+UserProxy.currentPoet(context).getUsername(),AppMsg.STYLE_INFO).show();
                loginSuccess();
            }

            @Override
            public void onFailure(String why) {
                AppMsg.makeText(context,why,AppMsg.STYLE_ALERT).show();
            }

            @Override
            public void onCancel(String desc) {
                AppMsg.makeText(context,desc,AppMsg.STYLE_ALERT).show();
            }
        });

        tencent.logout(context);
        tencent.login(LoginFragment.this, "all", qqListener);

    }

    /*Login by Weibo*/
    private void loginWithWeibo()
    {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        /*!!!how terrible with Tencent SDK document*/
        Tencent.onActivityResultData(requestCode, resultCode, data, qqListener);
//        if (requestCode == Constants.REQUEST_API)
//        {
//            Tencent.handleResultData(data, qqListener);
//        }
    }

    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getActivity().finish();
        }
    };

    private void loginSuccess()
    {

        handler.postDelayed(runnable, 1200);
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