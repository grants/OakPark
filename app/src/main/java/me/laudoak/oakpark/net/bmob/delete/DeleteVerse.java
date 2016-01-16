package me.laudoak.oakpark.net.bmob.delete;

import android.content.Context;
import android.os.AsyncTask;

import cn.bmob.v3.listener.DeleteListener;
import me.laudoak.oakpark.entity.core.Verse;

/**
 * Created by LaudOak on 2016-1-16 at 10:58.
 */
public class DeleteVerse extends AbDelete
{
    public DeleteVerse(Context context)
    {
        super(context);
    }


    public void deleteVerse(final String objectID, final DeleteCallback callback)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {

                Verse verse = new Verse();
                verse.setObjectId(objectID);

                verse.delete(context, objectID, new DeleteListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        callback.onDeleteSuccess();
                    }

                    @Override
                    public void onFailure(int i, String s)
                    {
                        callback.onDeleteFailure(s);
                    }
                });

                return null;
            }


        }.execute();
    }


}
