package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.CommentActivity;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.adapter.CommentAdapter;
import me.laudoak.oakpark.entity.Comment;
import me.laudoak.oakpark.entity.XVerse;
import me.laudoak.oakpark.net.query.QueryComment;
import me.laudoak.oakpark.widget.message.AppMsg;
import me.laudoak.oakpark.widget.paging.PagingListView;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPCommentFragment extends XBaseFragment implements
        OakParkActivity.NXVUCallback ,
        QueryComment.QueryCallback ,
        PagingListView.LoadCallback {

    private static final String TAG = "SUPCommentFragment";

    public static final String EXTRA_XVERSE = "EXTRA_XVERSE";
    private static final int REQUEST_COMMENT = 121;

    private Button writeComment;

    private PagingListView listView;
    private CommentAdapter commentAdapter;

    private int currPage;
    private XVerse curXV;

    @Override
    public void initData() {
        currPage = 0;
        commentAdapter = new CommentAdapter(context);
    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.view_sup_comment,null);
    }

    @Override
    public void buildViews(View view) {
        writeComment = (Button) view.findViewById(R.id.sup_comment_comment);
        writeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != curXV)
                {
                    Intent intent = new Intent();
                    intent.setClass(context, CommentActivity.class);
                    intent.putExtra(EXTRA_XVERSE, curXV);
                    startActivityForResult(intent,REQUEST_COMMENT);
                }else
                {
                    AppMsg.makeText(context,"评论异常",AppMsg.STYLE_CONFIRM).show();
                }
            }
        });

        listView = (PagingListView) view.findViewById(R.id.sup_comment_lv);

        listView.setLoadCallback(this);
        listView.setAdapter(commentAdapter);
    }

    @Override
    public void onUpdateXV(XVerse xv) {
        curXV = xv;
        upDateComment();
    }

    private void upDateComment() {
        currPage = 0;
        if (null != curXV)
        {
            new QueryComment(context, curXV, currPage,SUPCommentFragment.this);
        }else {
            AppMsg.makeText(context,"查询评论异常",AppMsg.STYLE_CONFIRM).show();
        }
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
            upDateComment();
        }
    }

    /*query comment callback*/
    @Override
    public void onFailure(String why) {

        AppMsg.makeText(context,why,AppMsg.STYLE_ALERT).show();
    }

    @Override
    public void onSuccess(boolean hasMore, List<Comment> results) {
        listView.onLoadCompleted(hasMore, results);
    }

    /*query comment callback*/

    /*loading callback*/
    @Override
    public void onLoadMore() {
        if (null != curXV)
        {
            new QueryComment(context,curXV,currPage++,this);
        }
    }
}
