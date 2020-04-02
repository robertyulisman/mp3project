package com.baru.shawnmendes.lirikmp3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.baru.shawnmendes.R;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Story> storyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView cover, overflow;

        public MyViewHolder(View view){
            super(view);
            title=(TextView) view.findViewById(R.id.title);
            count=(TextView) view.findViewById(R.id.count);
            cover=(ImageView) view.findViewById(R.id.cover);
            //overflow=(ImageView) view.findViewById(R.id.overflow);
        }
    }

    public StoriesAdapter(Context mContext, List<Story>storyList) {
        this.mContext = mContext;
        this.storyList = storyList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_card,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public  void onBindViewHolder(final MyViewHolder holder,int position){
        Story story=storyList.get(position);
        holder.title.setText(story.getTitle());
        holder.count.setText(story.getContent());

        Glide.with(mContext).load(story.getCover()).into(holder.cover);

    }

    @Override
    public int getItemCount() {return storyList.size();}
}
