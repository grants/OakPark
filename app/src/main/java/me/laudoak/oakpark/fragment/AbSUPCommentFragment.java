package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.CommentActivity;
import me.laudoak.oakpark.adapter.PagingComAdapter;
import me.laudoak.oakpark.ctrl.xv.AbXVOberver;
import me.laudoak.oakpark.entity.core.Comment;
import me.laudoak.oakpark.entity.core.XVerse;
import me.laudoak.oakpark.net.bmob.UserProxy;
import me.laudoak.oakpark.net.bmob.query.QueryPagingComment;
import me.laudoak.oakpark.ui.loani.ProgressWheel;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.paging.PagingListView;
import me.laudoak.oakpark.utils.StringUtil;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public abstract class AbSupCommentFragment extends XBaseFragment implements
        AbXVOberver,
        PagingListView.LoadCallback,
        QueryPagingComment.QueryCallback,
        View.OnClickListener
{

    private static final String TAG = AbSupCommentFragment.class.getName();

    public static final String EXTRA_XVERSE = "EXTRA_XVERSE";
    private static final int REQUEST_COMMENT = 121;


    @Bind(R.id.sup_comment_lv) PagingListView listView;
    @Bind(R.id.sup_comment_loani) ProgressWheel loani;
    @Bind(R.id.sup_comment_load_failed) TextView loadFailed;
    @Bind(R.id.sup_comment_comment) ImageView writeComment;

    private int currentPage;
    protected PagingComAdapter adapter;
    private XVerse currentVerse;

    protected boolean isFirstLoad;



    /**Lifecycle */
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

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
        loadFailed.setText(StringUtil.genSpannyText("获取评论失败\n点击重新加载", StringUtil.SpannyType.UNDERLINE));
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
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }


    /**AbObserver callback*/
    @Override
    public void notifyXVUpdate(XVerse xv)
    {
        if (currentVerse != xv)
        {
            currentVerse = xv;
            isFirstLoad = true;
            loadComment();
        }
    }

    private void buildListener()
    {
        loadFailed.setOnClickListener(this);
        writeComment.setOnClickListener(this);

        listView.setAdapter(adapter);
        listView.setLoadCallback(this);
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
        Log.d(TAG,"onLoadMore()");
        Log.d(TAG, "currentXV == null:" + (currentVerse == null));

        if (null != currentVerse)
        {
            loadComment();
        }
    }

    private void loadComment()
    {
        if ( isFirstLoad && (null != adapter) )
        {
            currentPage = 0;
            onLoadingNew();
            new QueryPagingComment(context, currentPage++, currentVerse, this);

        }else
        {
            onLoadingPaging();
            new QueryPagingComment(context, currentPage++, currentVerse, this);
        }

    }

    /**Query PagingComment callback*/
    @Override
    public void onFailure(String why)
    {
        onLoadFailed();
        Log.d(TAG, why);
    }

    /**Query PagingComment callback*/
    @Override
    public void onSuccess(boolean hasMore, List<Comment> results)
    {
        listView.onLoadCompleted(hasMore, results);
        onLoadSuccess();
        Log.d(TAG, "onSuccess(boolean hasMore, List<Comment> results)");
    }

    /**handle reload*/
    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.sup_comment_load_failed:
            {
                if (loadFailed.getVisibility() == View.VISIBLE)
                {
                    loadComment();
                }
                break;
            }
            case R.id.sup_comment_comment:
            {
                if (null != currentVerse && UserProxy.ifLogin(context))
                {
                    Intent intent = new Intent();
                    intent.setClass(context, CommentActivity.class);
                    intent.putExtra(EXTRA_XVERSE, currentVerse);
                    startActivityForResult(intent, REQUEST_COMMENT);

                } else if (! UserProxy.ifLogin(context))
                {
                    AppMsg.makeText(context, "请先登录", AppMsg.STYLE_CONFIRM).show();
                }else
                {
                    AppMsg.makeText(context, "暂时无法评论", AppMsg.STYLE_CONFIRM).show();
                }

                break;
            }
        }
    }

    abstract void onLoadingNew();
    abstract void onLoadingPaging();
    abstract void onLoadFailed();
    abstract void onLoadSuccess();

}