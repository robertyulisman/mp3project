package com.baru.shawnmendes.daftaralbum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.baru.shawnmendes.R;

import java.util.ArrayList;

public class AlbumSepuluh extends AppCompatActivity {

    private RecyclerView lisner;
    private AdView bannerIklan;
    private InterstitialAd iklanInters;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_sepuluh);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setTitle("      ");
//        toolbar.setSubtitle("          ");
//        toolbar.setNavigationIcon(R.drawable.gress);

        manager = new GridLayoutManager(this, getSpanCount());

        // banner
        bannerIklan = (AdView) findViewById(R.id.iklanBn);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        bannerIklan.loadAd(adRequest);

        // intertitial
        /*iklanInters = new InterstitialAd(this);
        iklanInters.setAdUnitId(getString(R.string.iklan_inters));

        iklanInters.loadAd(adRequest);
        iklanInters.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (iklanInters.isLoaded()) {
                    iklanInters.show();
                }
            }
        });*/
        setUpRecyclerView();
        populateRecyclerView();

    }

    /**
     * setup the recyclerview here
     */
    private void setUpRecyclerView() {
        lisner = findViewById(R.id.listebd);
        lisner.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lisner.setLayoutManager(manager);
    }

    private int getSpanCount() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int ezraezrageorge = metrics.widthPixels;

        float ezrageorge = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                200,
                metrics
        );
        return (int) (ezraezrageorge / ezrageorge);
    }

    /**
     * populate the recyclerview and implement the click event here
     */
    private void populateRecyclerView() {
        final ArrayList<ItemList> youtubeVideoModelArrayList = generateDummyVideoList();
        AdapterVideoList adapter = new AdapterVideoList(this, youtubeVideoModelArrayList);
        lisner.setAdapter(adapter);

        //set click event
        lisner.addOnItemTouchListener(new RecyclerOnClick(this, new RecyclerOnClick.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //start youtube player activity by passing selected video id via intent
                startActivity(new Intent(AlbumSepuluh.this, AlbumPemutarYoutube.class)
                        .putExtra("video_id", youtubeVideoModelArrayList.get(position).getVideoId()));

            }
        }));
    }


    /**
     * method to generate dummy array list of videos
     *
     * @return
     */
    private ArrayList<ItemList> generateDummyVideoList() {
        ArrayList<ItemList> youtubeVideoModelArrayList = new ArrayList<>();

        //get the video id array, title array and duration array from strings.xml
        String[] videoIDArray = getResources().getStringArray(R.array.link_video_empat);
        String[] videoTitleArray = getResources().getStringArray(R.array.judul_lagu_empat);
        String[] videoDurationArray = getResources().getStringArray(R.array.waktu_video_empat);

        //loop through all items and add them to arraylist
        for (int i = 0; i < videoIDArray.length; i++) {

            ItemList youtubeVideoModel = new ItemList();
            youtubeVideoModel.setVideoId(videoIDArray[i]);
            youtubeVideoModel.setTitle(videoTitleArray[i]);
            youtubeVideoModel.setDuration(videoDurationArray[i]);

            youtubeVideoModelArrayList.add(youtubeVideoModel);

        }

        return youtubeVideoModelArrayList;
    }

}


