package me.laudoak.oakpark.ctrl.listener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import me.laudoak.oakpark.activity.PrinterActivity;
import me.laudoak.oakpark.adapter.PagingPoetAdapter;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.fragment.LongClickMenu;
import me.laudoak.oakpark.net.bmob.delete.AbDelete;
import me.laudoak.oakpark.net.bmob.delete.DeleteVerse;
import me.laudoak.oakpark.ui.message.AppMsg;

/**
 * Created by LaudOak on 2016-1-16 at 10:56.
 */
public class PoetLongClickListener implements
        AdapterView.OnItemLongClickListener,
        LongClickMenu.LongMenuClickCallback,
        AbDelete.DeleteCallback
{

    private static final String TAG = PoetLongClickListener.class.getName();

    private Context context;
    private android.support.v4.app.FragmentManager fragmentManager;
    private PagingPoetAdapter adapter;

    private ProgressDialog ld;
    private LongClickMenu menu;


    public PoetLongClickListener(Context context , android.support.v4.app.FragmentManager fm,PagingPoetAdapter adapter)
    {
        Log.d(TAG,"PoetLongClickListener");

        this.context = context;
        this.fragmentManager = fm;
        this.adapter = adapter;
    }

    private int position;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {

        Log.d(TAG, "onItemLongClick" + i);

        this.position = i;

        menu = new LongClickMenu();
        menu.setCallback(this);
        menu.show(fragmentManager, "menuDialog");

        return true;
    }

    @Override
    public void onMenuClick(AdapterView<?> adapterView, View view, int i, long l)
    {

        if (null != menu)
        {
            menu.dismiss();
        }

        Log.d(TAG,"onMenuClick"+i);
        switch (i)
        {
            case 0:
            {
                delete();
                break;
            }
            case 1:
            {
                share();
                break;
            }
        }
    }

    private void delete()
    {
        Log.d(TAG,"delete()");

        ld = new ProgressDialog(context);
        ld.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ld.setMessage("正在删除...");
        ld.setTitle(null);
        ld.setCanceledOnTouchOutside(false);
        ld.show();

        new DeleteVerse(context).deleteVerse(((Verse)adapter.getItem(position)).getObjectId(),this);

    }

    private void share()
    {
        Intent intent = new Intent(context, PrinterActivity.class);
        /**/
        PrinterActivity.VerseTag tag = PrinterActivity.VerseTag.VERSE;
        tag.attachTo(intent);
        /**/
        Verse temp = (Verse)adapter.getItem(position);
        intent.putExtra(PrinterActivity.EXTRA_VERSE_CONT, temp);
        /**/
        if (null != temp.getImageURL())
        {
            Uri uri = Uri.parse(temp.getImageURL());
            intent.putExtra(PrinterActivity.EXTRA_VERSE_URI_STR, uri.toString());
        }
        context.startActivity(intent);
    }

    @Override
    public void onDeleteSuccess()
    {
        Log.d(TAG,"onDeleteSuccess()");
        adapter.removeItem(position);
        AppMsg.makeText(context,"已删除",AppMsg.STYLE_INFO).show();
        ld.dismiss();
    }

    @Override
    public void onDeleteFailure(String why)
    {
        Log.d(TAG,"onDeleteFailure(String why)");
        ld.dismiss();
        AppMsg.makeText(context,why,AppMsg.STYLE_ALERT).show();
    }
}
