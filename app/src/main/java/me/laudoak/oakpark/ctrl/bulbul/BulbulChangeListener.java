package me.laudoak.oakpark.ctrl.bulbul;

/**
 * Created by LaudOak on 2015-12-6 at 15:09.
 */
public interface BulbulChangeListener
{
    void onNickChanged(boolean needUpdate , String newNick);
    void onAvatarChanged(boolean needUpdate, String newAvatarPath);
    void onCoverChanged(boolean needUpdate, String newCoverPath);
}
