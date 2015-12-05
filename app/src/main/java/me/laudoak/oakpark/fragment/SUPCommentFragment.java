package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.CommentActivity;
import me.laudoak.oakpark.adapter.PagingComAdapter;
import me.laudoak.oakpark.ctrl.xv.AbXVOberver;
import me.laudoak.oakpark.entity.core.Comment;
import me.laudoak.oakpark.entity.core.XVerse;
import me.laudoak.oakpark.net.bmob.query.QueryPagingComment;
import me.laudoak.oakpark.ui.loani.ProgressWheel;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.paging.PagingListView;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPCommentFragment extends XBaseFragment implements
        AbXVOberver,
        PagingListView.LoadCallback,
        QueryPagingComment.QueryCallback
{

    private static final String TAG = SUPCommentFragment.class.getName();

    public static final String EXTRA_XVERSE = "EXTRA_XVERSE";
    private static final int REQUEST_COMMENT = 121;


    @Bind(R.id.sup_comment_lv) PagingListView listView;
    @Bind(R.id.sup_comment_loani) ProgressWheel loani;
    @Bind(R.id.sup_comment_load_failed) TextView loadFailed;
    @Bind(R.id.sup_comment_comment) Button writeComment;

    private int currentPage;
    private PagingComAdapter adapter;
    private XVerse currentVerse;


    private static class ClassHolder
    {
        private final static SUPCommentFragment fragment = new SUPCommentFragment();
    }

    public static SUPCommentFragment getSingletonInstance()
    {
        return ClassHolder.fragment;
    }


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    /**Lifecycle */


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        currentPage = 0;
        adapter = new PagingComAdapter(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.view_sup_comment,container,false);
        ButterKnife.bind(this, rootView);
        buildListener();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        listView.setAdapter(adapter);
        listView.setLoadCallback(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(TAG); //统计页面

        if (null != currentVerse)
        {
            beginLoad();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }


    /**AbObserver callback*/
    @Override
    public void notifyXVUpdate(XVerse xv)
    {
        if (currentVerse != xv)
        {
            currentVerse = xv;
        }
    }

    private void buildListener()
    {

        writeComment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (null != currentVerse)
                {
                    Intent intent = new Intent();
                    intent.setClass(context, CommentActivity.class);
                    intent.putExtra(EXTRA_XVERSE, currentVerse);
                    startActivityForResult(intent, REQUEST_COMMENT);

                } else
                {
                    AppMsg.makeText(context, "无法评论", AppMsg.STYLE_CONFIRM).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_COMMENT && resultCode == Activity.RESULT_OK)
        {
        }
    }

    /**PagingListView callback*/
    @Override
    public void onLoadMore()
    {
        beginLoad();
    }

    private void beginLoad()
    {
        if (null != currentVerse)
        {
            currentPage = 0;

            new QueryPagingComment(
                    context,
                    currentPage,
                    currentVerse,
                    new QueryPagingComment.QueryCallback()
                    {
                        @Override
                        public void onFailure(String why)
                        {
                            loani.setVisibility(View.GONE);
                            if (loadFailed.getVisibility() != View.VISIBLE)
                            {
                                loadFailed.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onSuccess(boolean hasMore, List<Comment> results)
                        {
                            loani.setVisibility(View.GONE);
                        }
                    });

        }
    }

    /**Query PagingComment callback*/
    @Override
    public void onFailure(String why)
    {
        loani.setVisibility(View.GONE);
        if (loadFailed.getVisibility() != View.VISIBLE)
        {
            loadFailed.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(boolean hasMore, List<Comment> results)
    {
        listView.onLoadCompleted(hasMore, results);
    }

}