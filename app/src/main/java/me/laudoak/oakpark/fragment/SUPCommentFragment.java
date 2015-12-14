package me.laudoak.oakpark.fragment;

import android.view.View;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by LaudOak on 2015-12-12 at 12:28.
 */
public class SupCommentFragment extends AbSupCommentFragment
{

    private static final String TAG = SupCommentFragment.class.getName();


    private static class ClassHolder
    {
        private final static SupCommentFragment fragment = new SupCommentFragment();
    }

    public static SupCommentFragment getSingletonInstance()
    {
        return ClassHolder.fragment;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(TAG); //统计页面
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    /**handle load mission*/
    @Override
    void onLoadingNew()
    {
        adapter.removeAllDatas();
        adapter.notifyDataSetChanged();
        listView.setIsLoading(true);
    }

    @Override
    void onLoadingPaging()
    {

    }
    @Override
    void onLoadFailed()
    {
        listView.setIsLoading(false);
        loani.setVisibility(View.GONE);
        if (loadFailed.getVisibility() != View.VISIBLE)
        {
            loadFailed.setVisibility(View.VISIBLE);
        }
    }

    @Override
    void onLoadSuccess()
    {
        isFirstLoad = false;
    }

}
