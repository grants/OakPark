package me.laudoak.oakpark.net.bmob;

import android.content.Context;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.laudoak.oakpark.OP;
import me.laudoak.oakpark.entity.core.Poet;

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
        BmobUser.loginByAccount(this.context, this.email, this.password, new LogInListener<Poet>()
        {
            @Override
            public void done(Poet poet, BmobException e) {
                if (null != poet && null == e) {
                    if (poet.getEmailVerified()) {
                        callBack.onSuccess(poet.getUsername());
                    } else {
                        callBack.onFailure("邮箱没有验证");
                    }
                } else {
                    if (null != e) {
                        callBack.onFailure(e.toString());
                    } else {
                        callBack.onFailure("登陆失败");
                    }
                }
            }
        });
    }

    public void doRegister(final CallBack callBack)
    {
        final Poet poet = new Poet();
        poet.setUsername(this.nick);
        poet.setEmail(this.email);
        poet.setPassword(this.password);
        poet.setSnib(false);

        poet.signUp(this.context, new SaveListener()
        {
            @Override
            public void onSuccess()
            {
                callBack.onSuccess(poet.getUsername());
            }

            @Override
            public void onFailure(int i, String s)
            {
                callBack.onFailure(s);
            }
        });
    }

    public static void doUpdate(final Context context,String newNick,String newAvatarPath, final CallBack callBack)
    {
        final Poet poet = currentPoet(context);
        final Poet curPoet = new Poet();
        curPoet.setObjectId(poet.getObjectId());
        curPoet.setUsername(newNick);

        if (null!=newAvatarPath)
        {
            BTPFileResponse response = BmobProFile.getInstance(context).upload(newAvatarPath, new UploadListener() {

                @Override
                public void onSuccess(String s, String s1, BmobFile bmobFile) {
                    String url = BmobProFile.getInstance(context).signURL(s,s1, OP.BMOB_ACCESS_KEY,0,null);
                    curPoet.setAvatarURL(url);
                    curPoet.update(context, curPoet.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess()
                        {
                            callBack.onSuccess(poet.getUsername());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            callBack.onFailure(s);
                        }
                    });
                }

                @Override
                public void onProgress(int i) {

                }

                @Override
                public void onError(int i, String s) {
                    callBack.onFailure(s);
                }
            });

        }else {

            curPoet.update(context, curPoet.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess()
                {
                    callBack.onSuccess(poet.getUsername());
                }

                @Override
                public void onFailure(int i, String s)
                {
                    callBack.onFailure(s);
                }
            });

        }

    }

    public static void qqUpdate(final Context context,String newNick,String newAvatarPath, final CallBack callBack)
    {
        final Poet poet = currentPoet(context);
        final Poet curPoet = new Poet();
        curPoet.setObjectId(poet.getObjectId());
        curPoet.setUsername(newNick);
        curPoet.setAvatarURL(newAvatarPath);
        curPoet.update(context, curPoet.getObjectId(), new UpdateListener()
        {
            @Override
            public void onSuccess()
            {
                callBack.onSuccess(poet.getUsername());
            }

            @Override
            public void onFailure(int i, String s)
            {
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


    /*Boolean class*/
    public static Poet currentPoet(Context context)
    {
        Poet poet = BmobUser.getCurrentUser(context,Poet.class);
        if (null != poet)
        {

            if (null == poet.getEmail() || poet.getEmail().equals(""))
            {
                return poet;
            }else
            {
                if (null != poet.getEmailVerified() && poet.getEmailVerified())
                {
                    return poet;
                }
            }
        }
        return null;

    }

    public static boolean ifLogin(Context contex)
    {
        Poet poet = BmobUser.getCurrentUser(contex, Poet.class);
        if(null!=BmobUser.getCurrentUser(contex,Poet.class))
        {

            if (null == poet.getEmail() || poet.getEmail().equals(""))
            {
                return true;
            }else {
                if (null != poet.getEmailVerified() && poet.getEmailVerified())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public interface CallBack
    {
        void onSuccess(String nick);
        void onFailure(String reason);
    }

}
