package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.PrinterActivity;
import me.laudoak.oakpark.ctrl.xv.AbXVOberver;
import me.laudoak.oakpark.entity.core.XVerse;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SupShareFragment extends XBaseFragment
        implements AbXVOberver
{

    private static final String TAG = SupShareFragment.class.getName();

    private View rootView;
    private TextView title,author;
    private XVerse currXV;

    private boolean isViewCreated = false;

    private static class HolderClass
    {
        private final static SupShareFragment fragment = new SupShareFragment();
    }

    public static SupShareFragment getSingletonInstance()
    {
        return HolderClass.fragment;
    }


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.view_sup_share,container,false);

        }else if (null != (rootView.getParent()))
        {
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        buildViews(rootView);
        isViewCreated = true;

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        onXVUpdate();
        MobclickAgent.onPageStart(TAG); //统计页面
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        isViewCreated = false;
    }

    private void buildViews(View view)
    {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.sup_share_share);

        title = (TextView) view.findViewById(R.id.sup_share_title);
        author = (TextView) view.findViewById(R.id.sup_share_author);

        linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (null != currXV)
                {
                    doShare();
                }
            }
        });
    }

    private void onXVUpdate()
    {
        if (null != currXV)
        {
            title.setText(currXV.getTitle());
            author.setText(currXV.getAuthor());
        }
    }

    private void doShare()
    {
        Intent intent = new Intent(getActivity(), PrinterActivity.class);
        /**/
        PrinterActivity.VerseTag tag = PrinterActivity.VerseTag.XVERSE;
        tag.attachTo(intent);
        /**/
        intent.putExtra(PrinterActivity.EXTRA_VERSE_CONT, currXV);
        /**/
        if (null != currXV.getImageURL())
        {
            Uri uri = Uri.parse(currXV.getImageURL());
            intent.putExtra(PrinterActivity.EXTRA_VERSE_URI_STR, uri.toString());
        }
        startActivity(intent);
    }


    @Override
    public void notifyXVUpdate(XVerse xv)
    {
        if (currXV != xv)
        {
            currXV = xv;

            if (isViewCreated)
            {
                onXVUpdate();
            }
        }

    }

}
