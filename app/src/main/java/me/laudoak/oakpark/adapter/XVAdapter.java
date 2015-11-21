package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.XVerse;
import me.laudoak.oakpark.ui.fittext.AutofitTextView;

/**
 * Created by LaudOak on 2015-10-20 at 18:02.
 */
public class XVAdapter extends RecyclerView.Adapter<XVAdapter.SimpleViewHolder>{

    private static final String TAG = "XVAdapter";

    private Context context;
    private LayoutInflater inflater;
    private RecyclerView recyclerView;

    protected List<XVerse> xVerseList;


    public List<XVerse> getxVerseList()
    {
        return this.xVerseList;
    }

    public XVAdapter(Context c, List<XVerse> list,RecyclerView recy) {

        this.context = c;
        this.inflater = LayoutInflater.from(context);
        this.xVerseList = list;
        this.recyclerView = recy;

    }

    public void addItem(int position) {

        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        xVerseList.remove(position);
        notifyItemRemoved(position);
    }


    public XVerse getXVByPosition(int position)
    {
        return this.xVerseList.get(position);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = inflater.inflate(R.layout.view_item_xverse,parent,false);

        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

        XVerse vs = xVerseList.get(position);

        holder.title.setText(vs.getTitle());
        holder.author.setText(vs.getAuthor());
        holder.verse.setText(vs.getVerse());

        if (null != vs.getImageURL())
        {
            Uri uri = Uri.parse(vs.getImageURL());
            holder.image.setAspectRatio(1.67f);
            holder.image.setImageURI(uri);
        }

    }

    @Override
    public int getItemCount() {
        return xVerseList.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder
    {

        SimpleDraweeView image;
        AutofitTextView title;
        AutofitTextView author;
        TextView verse;

        public SimpleViewHolder(View view) {
            super(view);
            image = (SimpleDraweeView) view.findViewById(R.id.item_xverse_image);
            title = (AutofitTextView) view.findViewById(R.id.item_xverse_title);
            author = (AutofitTextView) view.findViewById(R.id.item_xverse_author);
            verse = (TextView) view.findViewById(R.id.item_xverse_verse);
        }
    }

}
