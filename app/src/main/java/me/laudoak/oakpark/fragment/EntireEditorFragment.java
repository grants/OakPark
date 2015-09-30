package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.view.EntireEditorView;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class EntireEditorFragment extends XBaseFragment {

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return new EntireEditorView(getActivity());
    }

    @Override
    public void buildViews(View view) {

    }
}
