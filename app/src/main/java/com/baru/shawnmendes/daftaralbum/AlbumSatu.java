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

public class AlbumSatu extends AppCompatActivity {
    private RecyclerView rview;
    private AdView bannerIklan;
    public InterstitialAd iklanInters;
    RecyclerView.LayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_satu);


//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setTitle("      ");
//        toolbar.setSubtitle("          ");
//        toolbar.setNavigationIcon(R.drawable.gress);

        manager = new GridLayoutManager(this, getSpanCount());

        bannerIklan = findViewById(R.id.iklanBn);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        bannerIklan.loadAd(adRequest);


//        iklanInters = new InterstitialAd(this);
//        iklanInters.setAdUnitId(getString(R.string.kodeiklanintersial));
//
//        iklanInters.loadAd(adRequest);
//        iklanInters.setAdListener(new AdListener() {
////            public void onAdLoaded() {
//                if (iklanInters.isLoaded()) {
////                    iklanInters.show();
//               }
//            }
//        });
//
        setUpRecyclerView();
        populateRecyclerView();

    }


    private void setUpRecyclerView() {
        rview = findViewById(R.id.listebd);
        rview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rview.setLayoutManager(manager);
    }

    private int getSpanCount() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int xmetrics = metrics.widthPixels;

        float xType = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                200,
                metrics
        );
        return (int) (xmetrics / xType);
    }

    private void populateRecyclerView() {
        final ArrayList<ItemList> youtubeVideoModelArrayList = generateDummyVideoList();
        AdapterVideoList adapter = new AdapterVideoList(this, youtubeVideoModelArrayList);
        rview.setAdapter(adapter);


        rview.addOnItemTouchListener(new RecyclerOnClick(this, new RecyclerOnClick.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                startActivity(new Intent(AlbumSatu.this, AlbumPemutarYoutube.class)
                        .putExtra("video_id", youtubeVideoModelArrayList.get(position).getVideoId()));

            }
        }));
    }


    private ArrayList<ItemList> generateDummyVideoList() {
        ArrayList<ItemList> vidModelArraylist = new ArrayList<>();

        String[] videoIDArray = getResources().getStringArray(R.array.link_video_satu);
        String[] videoTitleArray = getResources().getStringArray(R.array.judul_lagu_satu);
        String[] videoDurationArray = getResources().getStringArray(R.array.waktu_video_satu);

        for (int i = 0; i < videoIDArray.length; i++) {

            ItemList youtubeVideoModel = new ItemList();
            youtubeVideoModel.setVideoId(videoIDArray[i]);
            youtubeVideoModel.setTitle(videoTitleArray[i]);
            youtubeVideoModel.setDuration(videoDurationArray[i]);

            vidModelArraylist.add(youtubeVideoModel);

        }

        return vidModelArraylist;
    }


}


