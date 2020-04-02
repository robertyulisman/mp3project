package com.baru.shawnmendes.daftaralbum;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.baru.shawnmendes.R;


public class RecyclerHolder extends RecyclerView.ViewHolder {

    CardView blas;
    public YouTubeThumbnailView thumbnailView;
    TextView judulvideo, waktuvideo;

    public RecyclerHolder(View itemView) {
        super(itemView);
        thumbnailView = itemView.findViewById(R.id.youtube_acty);
        judulvideo = itemView.findViewById(R.id.judul_lagu);
        waktuvideo = itemView.findViewById(R.id.lama);

        blas = (CardView) itemView.findViewById(R.id.listcard);
    }
}
