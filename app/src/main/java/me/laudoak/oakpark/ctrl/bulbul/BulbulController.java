package me.laudoak.oakpark.ctrl.bulbul;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadBatchListener;
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
            callback.onForbidden("你没有作改动.");
        }else
        {
            Poet poet = UserProxy.currentPoet(context);
            if (null != callback)
            {
                Poet updatedPoet = new Poet();
                if (poet != null)
                {
                    updatedPoet.setObjectId(poet.getObjectId());

                    /**only nick*/
                    if (updateNick && newNick != null && !updateCover && !updateAvatar)
                    {
                        updateNickOnly(updatedPoet);
                    }

                    /**nick & avatar*/
                    if (updateNick && updateAvatar && !updateCover)
                    {
                        updateNick(updatedPoet);
                        updateAvatar(updatedPoet);
                    }

                    /**nick & cover*/
                    if (updateNick && !updateAvatar && updateCover)
                    {
                        updateNick(updatedPoet);
                        updateCover(updatedPoet);
                    }

                    /**nick & avatar & cover*/
                    if (updateNick && updateAvatar && updateCover)
                    {
                        updateNick(updatedPoet);
                        updateBath(updatedPoet);
                    }

                    /**avatar*/
                    if (!updateNick && updateAvatar && !updateCover)
                    {
                        updateAvatar(updatedPoet);
                    }

                    /**cover*/
                    if (!updateNick && !updateAvatar && updateCover)
                    {
                        updateCover(updatedPoet);
                    }

                    /**avatar & cover*/
                    if (!updateNick && updateAvatar && updateCover)
                    {
                        updateBath(updatedPoet);
                    }

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

    private void updateBath(Poet poet)
    {
        String files[] = new String[2];
        files[0] = newAvatar;
        files[1] = newCover;
        updateBathFile(poet,files);
    }

    private void updateAvatar(Poet poet)
    {
        updateSingleFile(poet, newAvatar, 0);
    }
    private void updateCover(Poet poet)
    {
        updateSingleFile(poet, newCover, 1);
    }

    private void updateNick(Poet poet)
    {
       poet.setUsername(newNick);
    }

    private void updateNickOnly(Poet poet)
    {
        poet.setUsername(newNick);
        gatherUpdate(poet);
    }

    private void updateSingleFile( final Poet poet,final String path, final int who)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                BTPFileResponse response = BmobProFile.getInstance(context).upload(path, new UploadListener()
                {
                    @Override
                    public void onSuccess(String fileName,String url,BmobFile file)
                    {
                        String urlsigned = BmobProFile.getInstance(context).signURL(fileName,url, OP.BMOB_ACCESS_KEY,0,null);
                        switch (who)
                        {
                            case 0:
                            {
                                Log.d(TAG,"poet.setAvatarURL(url);");
                                poet.setAvatarURL(urlsigned);
                                gatherUpdate(poet);
                                break;
                            }
                            case 1:
                            {
                                Log.d(TAG,"poet.setCoverURL(url);");
                                poet.setCoverURL(urlsigned);
                                gatherUpdate(poet);
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

    private void updateBathFile(final Poet poet,final String paths[])
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {

                BmobProFile.getInstance(context).uploadBatch(paths, new UploadBatchListener()
                {
                    @Override
                    public void onSuccess(boolean isFinish,String[] fileNames,String[] urls,BmobFile[] files)
                    {
                        // isFinish :批量上传是否完成
                        // fileNames:文件名数组
                        // urls     : url：文件地址数组
                        // files    : BmobFile文件数组，`V3.4.1版本`开始提供，用于兼容新旧文件服务。
                        // 注:若上传的是图片，url(s)并不能直接在浏览器查看（会出现404错误），需要经过`URL签名`得到真正的可访问的URL地址,当然，`V3.4.1`版本可直接从BmobFile中获得可访问的文件地址。

                        if (isFinish)
                        {
                            String signedAvatarUrl = BmobProFile.getInstance(context).signURL(fileNames[0],urls[0], OP.BMOB_ACCESS_KEY,0,null);
                            String signedCoverUrl = BmobProFile.getInstance(context).signURL(fileNames[1],urls[1], OP.BMOB_ACCESS_KEY,0,null);
                            poet.setAvatarURL(signedAvatarUrl);
                            poet.setCoverURL(signedCoverUrl);

                            gatherUpdate(poet);

                        }else
                        {
                            callback.onFailure("图片文件上传失败");
                        }

                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3)
                    {

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

    private void gatherUpdate(Poet poet)
    {
        poet.update(context, poet.getObjectId(), new UpdateListener()
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
    }

}
