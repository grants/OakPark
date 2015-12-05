package me.laudoak.oakpark.net.bmob.push;

import android.content.Context;
import android.util.Log;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import me.laudoak.oakpark.OP;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.net.bmob.UserProxy;

/**
 * Created by LaudOak on 2015-10-2.
 */
public class PushVerse extends XBasePush {

    private static final String TAG = PushVerse.class.getName();

    private PushVerse(Builder builder)
    {
        this.context = builder.context;
        this.title = builder.title;
        this.author = builder.author;
        this.verse = builder.verse;
        this.imagePath = builder.imagePath;
        this.pushTime = builder.pushTime;
    }

    @Override
    public void push(final PushCallBack callBack) {

        Poet poet = UserProxy.currentPoet(context);

        if (null== poet)
        {
            callBack.onFailure("poet empty");
            return ;
        }


        final Verse v = new Verse();

        v.setPoet(poet);
        v.setTitle(this.title);
        v.setAuthor(this.author);
        v.setVerse(this.verse);
        v.setPushTime(this.pushTime);

        if (null!=this.imagePath)
        {
            Log.d(TAG,"null!=this.imagePath");

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
        long pushTime;

        public PushVerse build()
        {
            return new PushVerse(this);
        }

        public Builder(Context c)
        {
            this.pushTime = System.currentTimeMillis();
            this.context = c;
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

    }

}
