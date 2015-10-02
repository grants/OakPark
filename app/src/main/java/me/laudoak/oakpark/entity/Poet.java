package me.laudoak.oakpark.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class Poet extends BmobUser {

    private String avatarURL;
    private boolean snib;

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
