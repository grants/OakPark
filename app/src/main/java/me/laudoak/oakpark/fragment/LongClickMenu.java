package me.laudoak.oakpark.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.dialog.XBaseDialog;

/**
 * Created by LaudOak on 2016-1-16 at 11:11.
 */
public class LongClickMenu extends XBaseDialog
{

    private ListView listView;
    private List<String> menuList;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }


    private void initData()
    {
        menuList = new ArrayList<String>();
        menuList.add(0,"删除");
        menuList.add(1,"分享");
    }

    private void initView()
    {
        listView = new ListView(context);
        listView.setAdapter(new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,menuList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (null != callback)
                {
                    callback.onMenuClick(adapterView, view, i, l);
                }
            }
        });
    }

    @Override
    public Dialog callDialog()
    {
        return buildDialog();
    }

    private Dialog buildDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.CustomDialog)
                .setTitle(null)
                .setView(listView);

        return builder.create();
    }

    private LongMenuClickCallback callback;

    public void setCallback(LongMenuClickCallback callback)
    {
        this.callback = callback;
    }

    public interface LongMenuClickCallback
    {
        void onMenuClick(AdapterView<?> adapterView, View view, int i, long l);
    }
}
