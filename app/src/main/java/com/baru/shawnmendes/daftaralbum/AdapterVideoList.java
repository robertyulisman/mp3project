package com.baru.shawnmendes.daftaralbum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.baru.shawnmendes.DataAPIYoutube;
import com.baru.shawnmendes.R;

import java.util.ArrayList;


public class AdapterVideoList extends RecyclerView.Adapter<RecyclerHolder> {
    private static final String TAG = AdapterVideoList.class.getSimpleName();
    private Context context;
    private ArrayList<ItemList> videoArraylap;

    public AdapterVideoList(Context context, ArrayList<ItemList> youtubeVideoModelArrayList) {
        this.context = context;
        this.videoArraylap = youtubeVideoModelArrayList;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_video, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {


        final ItemList youtubeVideoModel = videoArraylap.get(position);

        holder.judulvideo.setText(youtubeVideoModel.getTitle());
        holder.waktuvideo.setText(youtubeVideoModel.getDuration());


        /*  initialize the thumbnail image view , we need to pass Developer Key */
        holder.thumbnailView.initialize(DataAPIYoutube.getApiKey(), new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(youtubeVideoModel.getVideoId());

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        //print or show error when thumbnail load failed
                        Log.e(TAG, "Youtube Thumbnail Error");
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //print or show error when initialization failed
                Log.e(TAG, "Youtube Initialization Failure");

            }
        });

    }

    @Override
    public int getItemCount() {
        return videoArraylap != null ? videoArraylap.size() : 0;
    }

}