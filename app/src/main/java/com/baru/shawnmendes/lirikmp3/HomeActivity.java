package com.baru.shawnmendes.lirikmp3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.baru.shawnmendes.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StoriesAdapter adapter;
    private List<Story> storyList;
    ProgressDialog mProgressDialog;
    private AdView mAdView;
//    InterstitialAd admobInters;

    private ImageView imageView;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemusiclyric);



//        admobInters = new InterstitialAd(this);
//        admobInters.setAdUnitId(getString(R.string.kodeiklanintersial));

        AdRequest adRequest = new AdRequest.Builder().build();
//        bannerview.loadAd(adRequest);

//        admobInters.loadAd(adRequest);
//        admobInters.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                if (admobInters.isLoaded()) {
//                    admobInters.show();
//
//                }
//            }
//        });

        new RecentSong().execute();

        mAdView = (AdView) findViewById(R.id.adViewHome);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest2);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        storyList = new ArrayList<>();
        adapter = new StoriesAdapter(this, storyList);

        RecyclerView.LayoutManager mLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent mainIntent = new Intent(context, MainActivity.class);

                switch (position) {
                    case 0:
                        mainIntent.putExtra("lyricsonly", "on");
                        startActivity(mainIntent);
                        break;
                    case 1:
                        mainIntent.putExtra("lyricsonly", "off");
                        startActivity(mainIntent);
                        break;

                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

       // loadStories();
    }

    private class RecentSong extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(HomeActivity.this);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loadStories();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //listview = (ListView) findViewById(R.id.listview);
            //adapter = new ListViewAdapter(MainActivityJcPlayer.this, arraylist);
            //listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            mProgressDialog.dismiss();
        }
    }

    private void loadStories() {
        Story s;

        s=new Story("Lyric Only",0,R.drawable.lyricaja,"","");
        storyList.add(s);
        s=new Story("Music And Lyric",0,R.drawable.music,"","");
        storyList.add(s);
//        s=new Story("More Apps",0,R.drawable.story3,"","");
//        storyList.add(s);
//        s=new Story("compartir ...",0,R.drawable.story4,"","");
//        storyList.add(s);



    }





}
