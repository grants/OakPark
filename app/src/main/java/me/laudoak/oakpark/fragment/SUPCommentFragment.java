package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.entity.XVerse;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPCommentFragment extends XBaseFragment implements OakParkActivity.NXVUCallback{

    private static final String TAG = "SUPCommentFragment";

    private EditText writeComment;
    

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.view_sup_comment,null);
    }

    @Override
    public void buildViews(View view) {

    }

    @Override
    public void onUpdateXV(XVerse xv) {

    }
}
