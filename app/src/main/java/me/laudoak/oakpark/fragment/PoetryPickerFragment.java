package me.laudoak.oakpark.fragment;

import android.app.Dialog;
import android.os.Bundle;

import me.laudoak.oakpark.ui.dialog.XBaseDialog;

/**
 * Created by LaudOak on 2015-12-3 at 13:49.
 */
public class PoetryPickerFragment extends XBaseDialog {

    private static final String TAG = "PoetryPickerFragment";

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

    public static PoetryPickerFragment newInstance()
    {
        return new PoetryPickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {

    }

    @Override
    public Dialog callDialog()
    {
        return null;
    }
}
