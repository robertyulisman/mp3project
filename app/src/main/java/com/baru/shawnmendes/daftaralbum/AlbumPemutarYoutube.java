package com.baru.shawnmendes.daftaralbum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.baru.shawnmendes.R;
import com.baru.shawnmendes.DataAPIYoutube;


public class AlbumPemutarYoutube extends YouTubeBaseActivity {
    private static final String TAG = AlbumPemutarYoutube.class.getSimpleName();
    String linkVideo;
    YouTubePlayerView viewerVideos;
       InterstitialAd iklanintersial;
    private AdView iklanAds;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);

        iklanAds = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        iklanAds.loadAd(adRequest);


        linkVideo = getIntent().getStringExtra("video_id");
        viewerVideos = findViewById(R.id.view_activt_video);
        initializeYoutubePlayer();
    }

    private void initializeYoutubePlayer() {
        viewerVideos.initialize(DataAPIYoutube.getApiKey(), new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer,
                                                boolean wasRestored) {

                if (!wasRestored) {

                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    youTubePlayer.loadVideo(linkVideo);

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {

                Log.e(TAG, "Youtube Player View initialization failed");
            }
        });
    }


//    public void onBackPressed(){
//
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//        iklanintersial = new InterstitialAd(this);
//        iklanintersial.setAdUnitId(getString(R.string.kodeiklanintersial));
//
//        iklanintersial.loadAd(adRequest);
//        iklanintersial.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                if (iklanintersial.isLoaded()) {
//                    iklanintersial.show();
//
//                }
//            }
//        });
//        super.onBackPressed();
//        this.finish();
//
//    }

}
