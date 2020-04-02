package com.baru.shawnmendes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.ImageViewHolder> {

    private List<Integer> videos;
    private Context ctx;
    int vid_id;

    public RViewAdapter(List<Integer> videos, Context ctx) {
        this.videos = videos;
        this.ctx = ctx;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.halaman_video_lyrics_item, parent,false);

        ImageViewHolder ivHolder = new ImageViewHolder(v, ctx, videos);

        return ivHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        vid_id = videos.get(position);
        final String ytlink = Data.ytlinks[vid_id-1];
        final String info = Data.infos[vid_id-1];
        holder.video.setImageResource(R.drawable.icondua);
        holder.title.setText(Data.titles[vid_id-1]);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, HalamanPemutarYoutubeLirik.class);
                i.putExtra("ytlink", ytlink);
                i.putExtra("info", info);
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.videos.size();
    }

    public static class ImageViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView video;
        TextView title;
        CardView card;

        Context ctx;
        List<Integer> videos;

        public ImageViewHolder(View itemView, Context ctx, List<Integer> videos) {
            super(itemView);
            video = itemView.findViewById(R.id.video);
            title = itemView.findViewById(R.id.title);
            card = itemView.findViewById(R.id.card);

            itemView.setOnClickListener(this);
            this.ctx = ctx;
            this.videos = videos;

        }

        @Override
        public void onClick(View view) {

        }

//        @Override
//        public void onClick(View v) {
//            int vid_id = this.videos.get(getAdapterPosition());
////            Intent i = new Intent(ctx, HalamanPemutarYoutubeLirik.class);
////            i.putExtra("ytlink", Data.ytlinks[vid_id-1]);
////            i.putExtra("info", Data.infos[vid_id-1]);
////            ctx.startActivity(i);
//        }
    }

    public void filterList(List<Integer> videos)
    {
        this.videos = videos;
        notifyDataSetChanged();
    }

}
