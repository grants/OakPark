package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.umeng.analytics.MobclickAgent;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.CommentActivity;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.adapter.CommentAdapter;
import me.laudoak.oakpark.entity.Comment;
import me.laudoak.oakpark.entity.XVerse;
import me.laudoak.oakpark.net.query.QueryComment;
import me.laudoak.oakpark.widget.loani.ProgressWheel;
import me.laudoak.oakpark.widget.message.AppMsg;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPCommentFragment extends XBaseFragment implements
        OakParkActivity.NXVUCallback{

    private static final String TAG = "SUPCommentFragment";

    public static final String EXTRA_XVERSE = "EXTRA_XVERSE";
    private static final int REQUEST_COMMENT = 121;

    private View rootView;
    private ListView listView;
    private ProgressWheel loani;
    private TextView loadFailed;

    private XVerse curXV;

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    public static SUPCommentFragment newInstance()
    {
        return ClassHolder.fragment;
    }

    private static class ClassHolder
    {
        private final static SUPCommentFragment fragment = new SUPCommentFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.view_sup_comment,container,false);
        }else if (null != (rootView.getParent())){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }

        buildViews(rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getComments();

    }

    private void buildViews(View view) {

        loani = (ProgressWheel) view.findViewById(R.id.sup_comment_loani);
        loadFailed = (TextView) view.findViewById(R.id.sup_comment_load_failed);

        listView = (ListView) view.findViewById(R.id.sup_comment_lv);

        Button writeComment = (Button) view.findViewById(R.id.sup_comment_comment);
        writeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != curXV) {

                    Intent intent = new Intent();
                    intent.setClass(context, CommentActivity.class);
                    intent.putExtra(EXTRA_XVERSE, curXV);
                    startActivityForResult(intent, REQUEST_COMMENT);

                } else {
                    AppMsg.makeText(context, "评论异常", AppMsg.STYLE_CONFIRM).show();
                }
            }
        });
    }

    private void getComments()
    {
        new QueryComment(context, curXV, new QueryComment.QueryCallback() {
            @Override
            public void onFailure(String why) {

                loani.setVisibility(View.GONE);
                if (loadFailed.getVisibility() != View.VISIBLE)
                {
                    loadFailed.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSuccess(List<Comment> results) {
                listView.setAdapter(new CommentAdapter(results,context));
                loani.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onUpdateXV(XVerse xv) {
        if (xv != curXV)
        {
            curXV = xv;
            getComments();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_COMMENT && resultCode == Activity.RESULT_OK)
        {
        }
    }

}