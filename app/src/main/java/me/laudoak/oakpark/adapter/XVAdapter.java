package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by LaudOak on 2015-10-20 at 18:02.
 */
public class XVAdapter extends RecyclerView.Adapter<XVAdapter.SimpleViewHolder>{

    private static final String TAG = "XVAdapter";

    private Context context;
    private RecyclerView recyclerView;


    public XVAdapter(Context c, RecyclerView recy) {
        this.context = c;
//        mItems = new ArrayList<>(COUNT);
//        for (int i = 0; i < COUNT; i++) {
//            addItem(i);
//        }

        this.recyclerView = recy;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View view) {
            super(view);
        }
    }

}
