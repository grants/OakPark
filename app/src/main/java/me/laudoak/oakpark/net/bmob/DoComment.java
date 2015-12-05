package me.laudoak.oakpark.net.bmob;

import android.content.Context;

import cn.bmob.v3.listener.SaveListener;
import me.laudoak.oakpark.entity.core.Comment;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.entity.core.XVerse;

/**
 * Created by LaudOak on 2015-10-24 at 15:16.
 */
public class DoComment
{

    private static final String TAG = "DoComment";

    private Context context;

    private Poet poet;
    private String content;
    private XVerse xVerse;
    private long commentTime;

    private DoComment(Builder b)
    {
        this.context = b.context;
        this.poet = b.poet;
        this.xVerse = b.xVerse;
        this.content = b.content;
        this.commentTime = b.commentTime;
    }

    public static class Builder
    {
        private Context context;
        private Poet poet;
        private XVerse xVerse;
        private String content;
        private long commentTime;

        public Builder(Context c)
        {
            this.context = c;
        }

        public Builder poet(Poet p)
        {
            this.poet = p;
            return this;
        }

        public Builder xVerse(XVerse xv)
        {
            this.xVerse = xv;
            return this;
        }

        public Builder content(String com)
        {
            this.content = com;
            return this;
        }

        public Builder commentTime(long ct)
        {
            this.commentTime = ct;
            return this;
        }

        public DoComment build()
        {
            return new DoComment(this);
        }
    }

    public void doComment(final CallBack callBack)
    {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPoet(poet);
        comment.setxVerse(xVerse);
        comment.setCommentTime(commentTime);

        comment.save(context, new SaveListener() {
            @Override
            public void onSuccess()
            {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(int i, String s)
            {
                callBack.onFailure(s);
            }
        });
    }

    public interface CallBack
    {
        void onSuccess();
        void onFailure(String why);
    }

}
