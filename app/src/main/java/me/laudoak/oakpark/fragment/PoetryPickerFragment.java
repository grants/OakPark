package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import java.util.ArrayList;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.PoetryWebAdapter;
import me.laudoak.oakpark.ctrl.helper.BrowserHelper;
import me.laudoak.oakpark.entity.PoetryWebSite;
import me.laudoak.oakpark.ui.extlv.ListViewExt;

/**
 * Created by LaudOak on 2015-12-3 at 13:49.
 */
public class PoetryPickerFragment extends DialogFragment
{

    private static final String TAG = PoetryPickerFragment.class.getName();

    /**
     *    http://www.thepoemforyou.com/
     *    http://www.bedtimepoem.com/
     *    http://www.poemhunter.com/
     *    http://www.poetryfoundation.org/
     *    https://www.poets.org/
     *    http://www.poetry-archive.com/
     *    http://poems.com/
     *    http://hellopoetry.com/
     */

    private ListViewExt listViewExt;

    private View contentView;
    private ArrayList<PoetryWebSite> webLists;


    public static PoetryPickerFragment newInstance()
    {
        return new PoetryPickerFragment();
    }

    @Override
    public void onAttach(Activity activity)
    {
        Log.d(TAG," onAttach(Activity activity)");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle savedInstanceState)");

        initWebs();
        initViews();
    }

    private void initWebs()
    {
        webLists = new ArrayList<PoetryWebSite>();

        webLists.add(0,new PoetryWebSite("http://www.thepoemforyou.com/","The Poem For You"));
        webLists.add(1,new PoetryWebSite("http://www.bedtimepoem.com/","读首诗再睡觉"));
        webLists.add(2,new PoetryWebSite("http://www.poetryfoundation.org/","Poetry Foundation"));
        webLists.add(3, new PoetryWebSite("http://poems.com/", "Poetry Daily, a new poem every day"));
        webLists.add(4,new PoetryWebSite("http://hellopoetry.com/","Hello Poetry"));
        webLists.add(5, new PoetryWebSite("http://www.poetry-archive.com/", "Poetry Archive | Poems"));
        webLists.add(6, new PoetryWebSite("https://www.poets.org/", "Poem-A-Day | Academy of American Poets"));
        webLists.add(7, new PoetryWebSite("http://www.poemhunter.com/", "PoemHunter.com: Poems - Quotes - Poetry"));
    }

    private void initViews()
    {
        Log.d(TAG, "private void initViews()");

        contentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_poetry_picker,null);
        Log.d(TAG, ".inflate(R.layout.fragment_poetry_picker,null)");

        listViewExt = (ListViewExt) contentView.findViewById(R.id.poetry_picker_listview);
        Log.d(TAG, ".findViewById(R.id.poetry_picker_listview)");

        listViewExt.setAdapter(new PoetryWebAdapter(webLists, getContext()));
        Log.d(TAG, ".setAdapter.setAdapter");

       listViewExt.setOnItemClickListener(new AdapterView.OnItemClickListener()
       {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
           {
               Log.d(TAG, "listViewExt Clicked & position = " + i);

               BrowserHelper helper = new BrowserHelper(getContext());
               helper.show(webLists.get(i).getUrl());
               PoetryPickerFragment.this.dismiss();
           }
       });
        Log.d(TAG, "t.setOnItemClickListener");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return callDialog();
    }

    private Dialog callDialog()
    {
        Log.d(TAG, "callDialog()");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomDialog)
                .setTitle("一些诗歌网站")
                .setNegativeButton("关闭",null)
                .setView(contentView);

        return builder.create();
    }
}
