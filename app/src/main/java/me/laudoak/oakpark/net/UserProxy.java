package me.laudoak.oakpark.net;

import android.content.Context;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import me.laudoak.oakpark.entity.Poet;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class UserProxy {

    private final String TAG = "UserProxy";

    private Context context;
    private String email;
    private String nick;
    private String password;

    private UserProxy(Builder builder)
    {
        this.context = builder.context;
        this.email = builder.email;
        this.nick = builder.nick;
        this.password = builder.password;
    }

    public void doLogin(final CallBack callBack)
    {
        BmobUser.loginByAccount(this.context, this.email, this.password, new LogInListener<Poet>() {
            @Override
            public void done(Poet poet, BmobException e) {
                if(null!=poet&&null==e)
                {
                    if(poet.getEmailVerified())
                    {
                        callBack.onSuccess();
                    }else {
                        callBack.onFailure("邮箱没有验证");
                    }
                }else {
                    if (null!=e)
                    {
                        callBack.onFailure(e.toString());
                    }else {
                        callBack.onFailure("登陆失败");
                    }
                }
            }
        });
    }

    public void doRegister(final CallBack callBack)
    {
        Poet poet = new Poet();
        poet.setUsername(this.nick);
        poet.setEmail(this.email);
        poet.setPassword(this.password);
        poet.setSnib(false);

        poet.signUp(this.context, new SaveListener() {
            @Override
            public void onSuccess()
            {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                callBack.onFailure(s);
            }
        });
    }

    public static class Builder
    {
        private Context context;
        private String email;
        private String nick;
        private String password;

        public Builder(Context context)
        {
            this.context = context;
        }

        public Builder email(String email)
        {
            this.email = email;
            return this;
        }

        public Builder nick(String nick)
        {
            this.nick = nick;
            return this;
        }

        public Builder password(String password)
        {
            this.password = password;
            return this;
        }

        public UserProxy build()
        {
            return new UserProxy(this);
        }

    }

    public interface CallBack
    {
        void onSuccess();
        void onFailure(String reason);
    }

    public static Poet currentPoet(Context context)
    {
        Poet poet = BmobUser.getCurrentUser(context,Poet.class);
        if (null!=poet&&poet.getEmailVerified())
        {
            return poet;
        }
        return null;
    }

}
