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

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.ui.fittext.ExpandableLinear;

/**
 * Created by LaudOak on 2015-12-6 at 22:09.
 */
public class OuterPoetAdapter extends RecyclerView.Adapter<OuterPoetViewHolder>
{
    private static final String TAG = OuterPoetAdapter.class.getName();

    private List<Verse> verses;
    private LayoutInflater inflater;

    public OuterPoetAdapter(Context context , List<Verse> list)
    {
        inflater = LayoutInflater.from(context) ;
        verses = list ;
    }

    @Override
    public OuterPoetViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.view_item_outerpoet,parent,false);
        return new OuterPoetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OuterPoetViewHolder holder, int position)
    {
        holder.title.setText(verses.get(position).getTitle());
        holder.author.setText(verses.get(position).getAuthor());
        holder.expandVerse.setText(verses.get(position).getVerse());

        if (null != verses.get(position).getImageURL())
        {
            Uri uri = Uri.parse(verses.get(position).getImageURL());
            holder.image.setAspectRatio(1.67f);
            holder.image.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount()
    {
        return verses.size();
    }
}

class OuterPoetViewHolder extends RecyclerView.ViewHolder
{

    @Bind(R.id.item_overse_image) SimpleDraweeView image;
    @Bind(R.id.item_overse_title) TextView title;
    @Bind(R.id.item_overse_author) TextView author;
    @Bind(R.id.item_verse_linear_expand) ExpandableLinear expandVerse;

    public OuterPoetViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
