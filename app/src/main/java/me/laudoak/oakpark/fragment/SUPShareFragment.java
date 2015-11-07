package me.laudoak.oakpark.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.activity.PrinterActivity;
import me.laudoak.oakpark.entity.XVerse;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPShareFragment extends XBaseFragment
        implements OakParkActivity.NXVUCallback{

    private static final String TAG = "SUPShareFragment";

    private View rootView;
    private TextView title,author;
    private LinearLayout linearLayout;
    private XVerse currXV;

    public static SUPShareFragment newInstance()
    {
        return HolderClass.fragment;
    }

    private static class HolderClass
    {
        private final static SUPShareFragment fragment = new SUPShareFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.view_sup_share,container,false);

        }else if (null != (rootView.getParent())){

            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }

        buildViews(rootView);

        return rootView;
    }

    private void buildViews(View view)
    {
        linearLayout = (LinearLayout) view.findViewById(R.id.sup_share_share);

        title = (TextView) view.findViewById(R.id.sup_share_title);
        author = (TextView) view.findViewById(R.id.sup_share_author);
    }

    @Override
    public void onResume() {
        super.onResume();
        onShowNow();
    }

    private void onShowNow()
    {

        if (null != currXV)
        {
            title.setText(currXV.getTitle());
            author.setText(currXV.getAuthor());
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != currXV)
                {
                    doShare();
                }
            }
        });
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
        Uri uri = Uri.parse(currXV.getImageURL());
        intent.putExtra(PrinterActivity.EXTRA_VERSE_URI_STR,uri.toString());

        startActivity(intent);
    }

    @Override
    public void onUpdateXV(XVerse xv) {

        if (currXV != xv)
        {
            currXV = xv;
            //onShowNow();
        }
    }

}
