package me.laudoak.oakpark.entity.core;

import cn.bmob.v3.BmobObject;

/**
 * Created by LaudOak on 2015-10-23 at 8:56.
 */
public class Comment extends BmobObject {

    private String content;
    private Poet poet;
    private XVerse xVerse;
    private long commentTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Poet getPoet() {
        return poet;
    }

    public void setPoet(Poet poet) {
        this.poet = poet;
    }

    public XVerse getxVerse() {
        return xVerse;
    }

    public void setxVerse(XVerse xVerse) {
        this.xVerse = xVerse;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }
}
