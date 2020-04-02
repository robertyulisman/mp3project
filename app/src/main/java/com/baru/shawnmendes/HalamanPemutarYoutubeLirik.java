package com.baru.shawnmendes;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.io.InputStream;

public class HalamanPemutarYoutubeLirik extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener{

    private YouTubePlayerView playerView;
    private TextView info;
    private AdView iklanAds;

    private InterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_pemutar_youtubelirik);

        iklanAds = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        iklanAds.loadAd(adRequest);
//        MobileAds.initialize(this, getString(R.string.kodeappidadmob));
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId(getString(R.string.kodeiklanintersial));
//        AdRequest adRequest = new AdRequest.Builder().build();
//        interstitialAd.loadAd(adRequest);
//
//        interstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                finish();
//            }
//        });

        playerView = (YouTubePlayerView) findViewById(R.id.player);
        playerView.initialize(DataAPIYoutube.getApiKey(),this);

        info = (TextView) findViewById(R.id.info);

        String txt =  "";

        try {
            InputStream is = getAssets().open(getIntent().getStringExtra("info")
                    + ".txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            txt = new String(buffer);

        } catch (IOException ex) {
            ex.printStackTrace();

        }

        info.setText(txt);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean b) {

        if(!b) {
            youTubePlayer.loadVideo(getIntent().getStringExtra("ytlink"));
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {

        Toast.makeText(this, "Initialization Failed", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            super.onBackPressed();
        }
    }
}
