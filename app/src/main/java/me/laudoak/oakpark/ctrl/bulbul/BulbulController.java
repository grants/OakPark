package me.laudoak.oakpark.ctrl.bulbul;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import me.laudoak.oakpark.OP;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.net.bmob.UserProxy;

/**
 * Created by LaudOak on 2015-12-6 at 15:07.
 */
public class BulbulController implements BulbulChangeListener
{

    private static final String TAG = BulbulController.class.getName();

    private Context context;

    private boolean updateNick,updateAvatar,updateCover;
    private String newNick,newAvatar,newCover;

    public BulbulController(Context context)
    {
        this.context = context;
    }


    @Override
    public void onNickChanged(boolean needUpdate, String newNick)
    {
        this.updateNick = needUpdate;
        this.newNick = newNick;
    }

    @Override
    public void onAvatarChanged(boolean needUpdate, String newAvatarPath)
    {
        this.updateAvatar = needUpdate;
        this.newAvatar = newAvatarPath;
    }

    @Override
    public void onCoverChanged(boolean needUpdate, String newCoverPath)
    {
        this.updateCover = needUpdate;
        this.newCover = newCoverPath;
    }

    UpdateCallback callback;

    public void update(UpdateCallback cbk)
    {
        this.callback = cbk;

        if (!updateNick && !updateAvatar && !updateCover)
        {
            callback.onForbidden("你没有作改动");
        }else
        {
            Poet poet = UserProxy.currentPoet(context);
            if (null != callback)
            {
                Poet updatedPoet = new Poet();
                if (poet != null)
                {
                    updatedPoet.setObjectId(poet.getObjectId());

                    if (updateNick && newNick != null)
                    {
                        updateNick(updatedPoet);
                    }
                    if (updateAvatar && newAvatar != null)
                    {
                        updateAvatar(updatedPoet);
                    }
                    if (updateCover && newCover != null)
                    {
                        updateCover(updatedPoet);
                    }

                    updatedPoet.update(context, updatedPoet.getObjectId(), new UpdateListener()
                    {
                        @Override
                        public void onSuccess()
                        {
                            callback.onSuccess();
                        }

                        @Override
                        public void onFailure(int i, String s)
                        {
                            callback.onFailure(s);
                        }
                    });

                }else
                {
                    callback.onForbidden("产生意外错误");
                }
            }else
            {
                callback.onForbidden("用户不存在");
            }
        }

    }

    private void updateNick(Poet poet)
    {
       poet.setUsername(newNick);
    }

    private void updateAvatar(Poet poet)
    {
        uploadImage(poet, newAvatar,0);
    }
    private void updateCover(Poet poet)
    {
        uploadImage(poet, newCover,1);
    }

    private void uploadImage(final Poet poet,final String path,final int who)
    {

        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                BTPFileResponse response = BmobProFile.getInstance(context).upload(path, new UploadListener()
                {
                    @Override
                    public void onSuccess(String s, String s1, BmobFile bmobFile)
                    {
                        String url = BmobProFile.getInstance(context).signURL(s,s1, OP.BMOB_ACCESS_KEY,0,null);
                        switch (who)
                        {
                            case 0:
                            {
                                Log.d(TAG,"poet.setAvatarURL(url);");

                                poet.setAvatarURL(url);
                                break;
                            }
                            case 1:
                            {
                                Log.d(TAG,"poet.setCoverURL(url);");

                                poet.setCoverURL(url);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onProgress(int i)
                    {
                        //nothing to do
                    }

                    @Override
                    public void onError(int i, String s)
                    {
                        callback.onFailure(s);
                    }
                });

                return null;
            }
        }.execute();

    }

}
