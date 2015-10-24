package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.CommentActivity;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.entity.XVerse;
import me.laudoak.oakpark.widget.paging.PagingListView;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPCommentFragment extends XBaseFragment implements OakParkActivity.NXVUCallback{

    private static final String TAG = "SUPCommentFragment";

    public static final String EXTRA_XVERSE = "EXTRA_XVERSE";
    private static final int REQUEST_COMMENT = 121;

    private EditText writeComment;
    private PagingListView listView;

    private XVerse curXV;

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.view_sup_comment,null);
    }

    @Override
    public void buildViews(View view) {
        writeComment = (EditText) view.findViewById(R.id.sup_comment_comment);
        writeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, CommentActivity.class);
                intent.putExtra(EXTRA_XVERSE, curXV);
                startActivityForResult(intent,REQUEST_COMMENT);
            }
        });

        listView = (PagingListView) view.findViewById(R.id.sup_comment_lv);
    }

    @Override
    public void onUpdateXV(XVerse xv) {
        curXV = xv;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode!= Activity.RESULT_OK)
        {
            return ;
        }

        if (requestCode == REQUEST_COMMENT)
        {

        }
    }
}
