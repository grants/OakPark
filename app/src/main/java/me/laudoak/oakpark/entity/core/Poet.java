package me.laudoak.oakpark.entity.core;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class Poet extends BmobUser implements Serializable{

    private String coverURL;
    private String avatarURL;
    private boolean snib;

    public String getCoverURL()
    {
        return coverURL;
    }

    public void setCoverURL(String coverURL)
    {
        this.coverURL = coverURL;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public boolean isSnib() {
        return snib;
    }

    public void setSnib(boolean snib) {
        this.snib = snib;
    }

}