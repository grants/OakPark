package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.activity.EditorActivity;
import me.laudoak.oakpark.view.NormalEditorView;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class NormalEditorFragment extends XBaseFragment implements EditorActivity.PushCallback{

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return new NormalEditorView(getActivity());
    }

    @Override
    public void buildViews(View view) {

    }

    @Override
    public void onPush() {

    }
}
