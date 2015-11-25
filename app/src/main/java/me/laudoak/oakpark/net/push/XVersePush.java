package me.laudoak.oakpark.net.push;

import android.content.Context;
import android.util.Log;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import me.laudoak.oakpark.OP;
import me.laudoak.oakpark.entity.Poet;
import me.laudoak.oakpark.entity.XVerse;
import me.laudoak.oakpark.net.UserProxy;

/**
 * Created by LaudOak on 2015-10-2.
 */
public class XVersePush extends XBasePush {

    private static final String TAG = "XVersePush";

    private String whisper;

    private XVersePush(Builder builder)
    {
        this.context = builder.context;
        this.title = builder.title;
        this.author = builder.author;
        this.verse = builder.verse;
        this.imagePath = builder.imagePath;
        this.whisper = builder.whisper;
    }

    @Override
    public void push(final PushCallBack callBack) {

        Poet poet = UserProxy.currentPoet(context);

        if (null== poet)
        {
            callBack.onFailure("poet empty");
            return ;
        }

        final XVerse v = new XVerse();

        v.setBulbul(poet);
        v.setTitle(this.title);
        v.setDateCode(0);
        v.setPass(false);
        v.setWhisper(this.whisper);
        v.setAuthor(this.author);
        v.setVerse(this.verse);

        if (null!=this.imagePath)
        {
            Log.d(TAG, "null!=this.imagePath");

            BTPFileResponse response = BmobProFile.getInstance(context).upload(
                    imagePath,
                    new UploadListener() {
                        @Override
                        public void onSuccess(String name, String url, BmobFile bmobFile) {

                            Log.d(TAG,"upload success:"+"name:"+name+";url:"+url);

                            String signedURL = BmobProFile.getInstance(context).signURL(name,url, OP.BMOB_ACCESS_KEY,0,null);

                            Log.d(TAG, "signedURL:" + signedURL);

                            v.setImageName(name);
                            v.setImageURL(signedURL);

                            Log.d(TAG, "test verse imgname in callback:" + v.getImageName());
                            Log.d(TAG, "test verse imgnurl in callback:" + v.getImageURL());

                            v.save(context, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    callBack.onSuccess();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    callBack.onFailure(s);
                                }
                            });

                        }

                        @Override
                        public void onProgress(int i) {
                            //nothing to do
                        }

                        @Override
                        public void onError(int i, String s) {
                            Log.d(TAG,"onError"+s);
                            callBack.onFailure(s);
                        }
                    });

        }else {

            Log.d(TAG,"test verse imgname:"+v.getImageName());
            Log.d(TAG, "test verse imgnurl:" + v.getImageURL());

            v.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    callBack.onSuccess();
                }

                @Override
                public void onFailure(int i, String s) {
                    callBack.onFailure(s);
                }
            });

        }
    }

    public static class Builder
    {
        Context context;
        String title;
        String author;
        String verse;
        String imagePath;
        String whisper;

        public Builder(Context c)
        {
            this.context = c;
        }

        public XVersePush build()
        {
            return new XVersePush(this);
        }


        public Builder title(String t)
        {
            this.title = t;
            return this;
        }
        public Builder author(String a)
        {
            this.author = a;
            return this;
        }
        public Builder verse(String v)
        {
            this.verse = v;
            return this;
        }
        public Builder imagePath(String ip)
        {
            this.imagePath = ip;
            return this;
        }
        public Builder whisper(String w)
        {
            this.whisper = w;
            return this;
        }
    }

}
