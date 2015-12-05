package me.laudoak.oakpark.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.PoetryWebAdapter;
import me.laudoak.oakpark.ctrl.browser.BrowserHelper;
import me.laudoak.oakpark.entity.PoetryWebSite;
import me.laudoak.oakpark.ui.dialog.XBaseDialog;
import me.laudoak.oakpark.ui.extlv.ListViewExt;

/**
 * Created by LaudOak on 2015-12-3 at 13:49.
 */
public class PoetryPickerFragment extends XBaseDialog {

    private static final String TAG = PoetryPickerFragment.class.getName();

    /**
     *      http://www.thepoemforyou.com/
     *      http://www.bedtimepoem.com/
     *      http://www.poemhunter.com/
     *      http://www.poetryfoundation.org/
     *      https://www.poets.org/
     *      http://www.poetry-archive.com/
     *      http://poems.com/
     *      http://hellopoetry.com/
     */

    @Bind(R.id.poetry_picker_listview)
    ListViewExt listViewExt;

    private View contentView;
    private ArrayList<PoetryWebSite> webLists;


    public static PoetryPickerFragment newInstance()
    {
        return new PoetryPickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initWebs();
        initViews();
    }

    private void initWebs()
    {
        webLists = new ArrayList<PoetryWebSite>();

        webLists.add(new PoetryWebSite("http://www.thepoemforyou.com/","The Poem For You"));
        webLists.add(new PoetryWebSite("http://www.bedtimepoem.com/","读首诗再睡觉"));
        webLists.add(new PoetryWebSite("http://www.poemhunter.com/","PoemHunter.com: Poems - Quotes - Poetry"));
        webLists.add(new PoetryWebSite("http://www.poetryfoundation.org/","Poetry Foundation"));
        webLists.add(new PoetryWebSite("https://www.poets.org/","Poem-A-Day | Academy of American Poets"));
        webLists.add(new PoetryWebSite("http://poems.com/","Poetry Daily, a new poem every day"));
        webLists.add(new PoetryWebSite("http://hellopoetry.com/","Hello Poetry"));
        webLists.add(new PoetryWebSite("http://www.poetry-archive.com/","Poetry Archive | Poems"));
    }

    @SuppressLint("InflateParams")
    private void initViews()
    {
        contentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_poetry_picker,null);
        ButterKnife.bind(this,contentView);

        listViewExt.setAdapter(new PoetryWebAdapter(webLists,context));

        listViewExt.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
//                BrowserHelper helper = new BrowserHelper(context);
//                helper.show(webLists.get(position).getUrl());
//                PoetryPickerFragment.this.dismiss();

                new FinestWebView.Builder(getActivity()).show(webLists.get(position).getUrl());
            }
        });
    }

    @Override
    public Dialog callDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomDialog)
                .setTitle("一些诗歌网站")
                .setNegativeButton("确定",null)
                .setView(contentView);

        return builder.create();
    }
}
