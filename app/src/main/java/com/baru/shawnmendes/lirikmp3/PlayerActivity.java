package com.baru.shawnmendes.lirikmp3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.baru.shawnmendes.R;
import com.baru.shawnmendes.optionmenu.MenuAbout;
import com.baru.shawnmendes.optionmenu.MenuPrivacyPolicy;
import com.baru.shawnmendes.optionmenu.MenuSetting;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    private boolean playerPause = false;
    Button button;
    MediaPlayer mediaplayer;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    private LinearLayout llButton;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4;
    ImageView prev, play, stop, next;
    boolean first_load = true;
    String id, name, speaker, url;
    int r_id;
    private List<Audio> audioList;
    private String[] arrId, arrName, arrUrl;
    private CollapsingToolbarLayout collapsing;
    private Toolbar toolbar;

    int duration, progress;
    SeekBar seekbar;
    TextView endtime, progressTime;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        collapsing = findViewById(R.id.collapsing);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        r_id = this.getIntent().getExtras().getInt("id",0);
        id = "a"+String.valueOf(r_id)+".txt";
        name = this.getIntent().getExtras().getString("name");
        speaker = this.getIntent().getExtras().getString("speaker");
        url = this.getIntent().getExtras().getString("url");

        seekbar = findViewById(R.id.seekbar);
        endtime = findViewById(R.id.endtime);
        progressTime = findViewById(R.id.progreesTime);

        progress = -1;

        initElements();

        arrName = getResources().getStringArray(R.array.audio_name);
        arrUrl = getResources().getStringArray(R.array.audio_url);
        Audio audio;
        audioList = new ArrayList<>();

        int c = arrName.length;

        for(int i=0; i<c; i++) {
            audio = new Audio((i+1), arrName[i], "", "", arrUrl[i]);
            audioList.add(audio);
        }

        llButton = (LinearLayout) findViewById(R.id.ll_button);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);

        stop.setEnabled(false);

        final String lyricsOnly = this.getIntent().getExtras().getString("lyricsonly");
        if (lyricsOnly.equals("on")) {
            llButton.setVisibility(View.GONE);
        }

//        TextView tvSpeaker = (TextView)findViewById(R.id.textSpeaker);
//        tvSpeaker.setText(speaker);
        //tvSpeaker.setText(lyricsOnly);

        mInterstitialAd= new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.kodeiklanintersial));
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest2);


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        button = (Button)findViewById(R.id.button);

        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (first_load) {
                    mediaplayer.prepareAsync();
                    button.setText("PLEASE WAIT ...");
                    button.setEnabled(false);
                    play.setEnabled(false);
                    play.setBackgroundColor(getResources().getColor(R.color.abuabu));
                    prev.setEnabled(false);
                    prev.setBackgroundColor(getResources().getColor(R.color.abuabu));
                    next.setEnabled(false);
                    next.setBackgroundColor(getResources().getColor(R.color.abuabu));
                    stop.setEnabled(false);
                    stop.setBackgroundColor(getResources().getColor(R.color.abuabu));
                    first_load = false;
                } else {
                    if (playerPause) {
                        mediaplayer.pause();
                        play.setImageDrawable(getResources().getDrawable(R.drawable.play));
                        playerPause = false;
                        prev.setEnabled(false);
                        prev.setBackgroundColor(getResources().getColor(R.color.abuabu));
                        next.setEnabled(false);
                        next.setBackgroundColor(getResources().getColor(R.color.abuabu));
                        stop.setEnabled(true);
                        stop.setBackgroundColor(getResources().getColor(R.color.hitam));
                    } else {
                        mediaplayer.start();
                        playerPause = true;
                        button.setText("PLAY");
                        play.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                        play.setEnabled(true);
                        play.setBackgroundColor(getResources().getColor(R.color.hitam));
                        stop.setEnabled(true);
                        stop.setBackgroundColor(getResources().getColor(R.color.hitam));
                        prev.setEnabled(false);
                        prev.setBackgroundColor(getResources().getColor(R.color.abuabu));
                        next.setEnabled(false);
                        next.setBackgroundColor(getResources().getColor(R.color.abuabu));
                    }
            }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaplayer.isPlaying() && playerPause){
                    mediaplayer.pause();
                    mediaplayer.seekTo(0);
                    progress = -1;
                    seekbar.setProgress(0);
                    progressTime.setText("00:00");
                    play.setImageDrawable(getResources().getDrawable(R.drawable.play));
                    prev.setEnabled(true);
                    prev.setBackgroundColor(getResources().getColor(R.color.hitam));
                    next.setEnabled(true);
                    next.setBackgroundColor(getResources().getColor(R.color.hitam));
                    stop.setEnabled(false);
                    stop.setBackgroundColor(getResources().getColor(R.color.abuabu));
                    playerPause = false;
                    play.setEnabled(true);
                    play.setBackgroundColor(getResources().getColor(R.color.hitam));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r_id != audioList.size()){
                    r_id += 1;
                    id = "a"+String.valueOf(r_id)+".txt";
                    name = audioList.get(r_id-1).getName();
                    speaker = audioList.get(r_id-1).getSpeaker();
                    url = audioList.get(r_id-1).getUrl();
                    initElements();
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r_id != 1){
                    r_id -= 1;
                    id = "a"+String.valueOf(r_id)+".txt";
                    name = audioList.get(r_id-1).getName();
                    speaker = audioList.get(r_id-1).getSpeaker();
                    url = audioList.get(r_id-1).getUrl();
                    initElements();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        mediaplayer.stop();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (item.getItemId()==R.id.about){
            startActivity(new Intent(this, MenuAbout.class));
        } else if (item.getItemId() == R.id.setting) {
            startActivity(new Intent(this, MenuSetting.class));
        } else if (item.getItemId() == R.id.help) {
            startActivity(new Intent(this, MenuPrivacyPolicy.class));
        } else if (id==android.R.id.home) {
        }
        return super.onOptionsItemSelected(item);
    }
    public void initElements(){
        first_load = true;
        collapsing.setTitle(name);
//        collapsing.setTitleEnabled(false);
//        toolbar.setTitle(name);
        try {
            InputStream is = getAssets().open(id);
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String text = new String(buffer);

            TextView tv = (TextView)findViewById(R.id.textView);
            tv.setText(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mediaplayer = new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaplayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer player) {
                player.start();
                playerPause = true;
                button.setText("STOP");
                button.setEnabled(true);
                play.setEnabled(true);
                play.setBackgroundColor(getResources().getColor(R.color.hitam));
                play.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                stop.setEnabled(true);
                stop.setBackgroundColor(getResources().getColor(R.color.hitam));
                prev.setEnabled(false);
                prev.setBackgroundColor(getResources().getColor(R.color.abuabu));
                next.setEnabled(false);
                next.setBackgroundColor(getResources().getColor(R.color.abuabu));
                duration = mediaplayer.getDuration();
                @SuppressLint("DefaultLocale") String music_time = String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(duration),
                        TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MILLISECONDS.toMinutes(duration) * 60);
                endtime.setText(music_time);
                seekbar.setMax((int) TimeUnit.MILLISECONDS.toSeconds(duration));

                handler = new Handler();
                PlayerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mediaplayer != null && playerPause) {
                            int currentPos = mediaplayer.getCurrentPosition() / 1000;
                            seekbar.setProgress(currentPos);
                            progress += 1;
                            @SuppressLint({"DefaultLocale", "NewApi", "LocalSuppress"}) String music_duration = String.format("%02d : %02d",
                                    TimeUnit.SECONDS.toMinutes(progress),
                                    progress - TimeUnit.SECONDS.toMinutes(progress) * 60);
                            progressTime.setText(music_duration);
                        }
                        handler.postDelayed(this, 1000);
                    }
                });
            }

        });

        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer player) {
                mediaplayer.stop();
                playerPause = false;
                button.setText("PLAY");
                play.setImageDrawable(getResources().getDrawable(R.drawable.play));
                prev.setEnabled(true);
                prev.setBackgroundColor(getResources().getColor(R.color.hitam));
                next.setEnabled(true);
                next.setBackgroundColor(getResources().getColor(R.color.hitam));
                stop.setEnabled(false);
                stop.setBackgroundColor(getResources().getColor(R.color.abuabu));
                duration = -1;
                progressTime.setText("00:00");
                seekbar.setProgress(0);
            }
        });
    }
}