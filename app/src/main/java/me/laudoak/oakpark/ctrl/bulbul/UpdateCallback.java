package me.laudoak.oakpark.ctrl.bulbul;

/**
 * Created by LaudOak on 2015-12-6 at 15:40.
 */
public interface UpdateCallback
{
    void onForbidden(String why);
    void onFailure(String why);
    void onSuccess();
}
