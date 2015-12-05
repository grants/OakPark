package me.laudoak.oakpark.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.XVAdapter;
import me.laudoak.oakpark.ctrl.xv.AbXVOberver;
import me.laudoak.oakpark.ctrl.xv.AbXVSubject;
import me.laudoak.oakpark.entity.core.XVerse;
import me.laudoak.oakpark.net.bmob.query.QueryXVerse;
import me.laudoak.oakpark.ui.loani.ProgressWheel;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.recy.RecyclerViewPager;

/**
 * Created by LaudOak on 2015-11-24 at 16:32.
 */
public class XVHFragment extends AbXVSubject {

    private static final String TAG = "XVHFragment";

    private int currXVPage = 0;
    private View rootView;
    protected RecyclerViewPager mRecyclerView;
    private ProgressWheel loani;
    private TextView loadFailed;


    private static class ClassHolder
    {
        private final static XVHFragment fragment = new XVHFragment();
    }

    public static XVHFragment getSingletonInstance()
    {
        return ClassHolder.fragment;
    }

    /**/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.attach((AbXVOberver) context);
        this.attach(SUPWhisperFragment.getSingletonInstance());
        this.attach(SUPCommentFragment.getSingletonInstance());
        this.attach(SUPShareFragment.getSingletonInstance());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.view_xv_recy,container,false);
        }else if (null != (rootView.getParent())){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }

        buildViews(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    /**/

    private void buildViews(View view)
    {
        mRecyclerView = (RecyclerViewPager) view.findViewById(R.id.view_recy);
        loani = (ProgressWheel) view.findViewById(R.id.view_recy_loading);
        loadFailed = (TextView) view.findViewById(R.id.view_recy_load_failed);

        buildRecy();
    }

    private void buildRecy()
    {

        mRecyclerView.setVisibility(View.GONE);

        LinearLayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);

        new QueryXVerse(context, new QueryXVerse.QueryCallback()
        {
            @Override
            public void onFailure(String why)
            {
                AppMsg.makeText(context, "获取数据出错了", AppMsg.STYLE_CONFIRM).show();
                loani.setVisibility(View.GONE);
                if (loadFailed.getVisibility() != View.VISIBLE)
                {
                    loadFailed.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSuccess(List<XVerse> results) {

                mRecyclerView.setAdapter(new XVAdapter(context, results, mRecyclerView));
                mRecyclerView.setVisibility(View.VISIBLE);
                loani.setVisibility(View.GONE);
                notifyAllXVUpdated(results.get(0));

            }
        });

        setRecyListener();

    }

    private void setRecyListener()
    {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

                int tmp = mRecyclerView.getCurrentPosition();

                XVAdapter adapter = (XVAdapter) mRecyclerView.getAdapter();
                if (null != adapter && tmp != currXVPage)
                {
                    currXVPage = tmp;
                    XVerse xv = adapter.getxVerseList().get(currXVPage);
                    notifyAllXVUpdated(xv);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int childCount = mRecyclerView.getChildCount();
                int width = mRecyclerView.getChildAt(0).getWidth();
                int padding = (mRecyclerView.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                        v.setScaleX(1 - rate * 0.1f);

                    } else {
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                        v.setScaleX(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerView.getChildCount() < 3) {
                    if (mRecyclerView.getChildAt(1) != null) {
                        if (mRecyclerView.getCurrentPosition() == 0) {
                            View v1 = mRecyclerView.getChildAt(1);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        } else {
                            View v1 = mRecyclerView.getChildAt(0);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        }
                    }
                } else {
                    if (mRecyclerView.getChildAt(0) != null) {
                        View v0 = mRecyclerView.getChildAt(0);
                        v0.setScaleY(0.9f);
                        v0.setScaleX(0.9f);
                    }
                    if (mRecyclerView.getChildAt(2) != null) {
                        View v2 = mRecyclerView.getChildAt(2);
                        v2.setScaleY(0.9f);
                        v2.setScaleX(0.9f);
                    }
                }

            }
        });
    }

    @Override
    public void notifyAllXVUpdated(XVerse xVerse)
    {
        for (AbXVOberver oberver : obervers)
        {
            oberver.notifyXVUpdate(xVerse);
        }
    }

}
