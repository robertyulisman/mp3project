package com.baru.shawnmendes.lirikmp3;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.baru.shawnmendes.R;
import com.baru.shawnmendes.optionmenu.MenuAbout;
import com.baru.shawnmendes.optionmenu.MenuPrivacyPolicy;
import com.baru.shawnmendes.optionmenu.MenuSetting;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Audio> audioList;
    List<com.baru.shawnmendes.lirikmp3.Audio> audioFilter;
    private RecyclerView recyclerView;
    private AudioAdapter mAdapter;
    private AdView mAdView;
    private String[] arrId, arrName, arrUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if(getSupportActionBar() != null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);


        //arrId = getResources().getStringArray(R.array.audio_id);
        ImageView btn_back = findViewById(R.id.btn_back);
        ImageView btn_search = findViewById(R.id.btn_search);
        ImageView btn_close = findViewById(R.id.btn_close);
        final LinearLayout tulbar = findViewById(R.id.tulbar);
        final LinearLayout search = findViewById(R.id.search);

        arrName = getResources().getStringArray(R.array.audio_name);
        arrUrl = getResources().getStringArray(R.array.audio_url);
        final EditText t_search = findViewById(R.id.t_search);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setVisibility(View.VISIBLE);
                tulbar.setVisibility(View.GONE);
                t_search.requestFocus();
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setVisibility(View.GONE);
                tulbar.setVisibility(View.VISIBLE);
            }
        });

        mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final Context context = this;

//        setTitle(R.string.app_name);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        prepareAudioData();
        mAdapter = new AudioAdapter(audioList);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        final String lyricsOnly = this.getIntent().getExtras().getString("lyricsonly");

        t_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchData(editable.toString());
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Audio audio = audioList.get(position);

                Intent playerIntent = new Intent(context, PlayerActivity.class);
                playerIntent.putExtra("lyricsonly", lyricsOnly);
                playerIntent.putExtra("id", audio.getId());
                playerIntent.putExtra("name", audio.getName());
                playerIntent.putExtra("speaker", audio.getSpeaker());
                playerIntent.putExtra("url", audio.getUrl());
                startActivity(playerIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void prepareAudioData() {
        Audio audio;
        audioList = new ArrayList<>();

        int c = arrName.length;

        for(int i=0; i<c; i++) {
            audio = new Audio((i+1), arrName[i], "", "", arrUrl[i]);
            audioList.add(audio);
        }

//        mAdapter.notifyDataSetChanged();
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

    public void searchData(String text)
    {
        prepareAudioData();
        mAdapter.notifyDataSetChanged();
        audioFilter = new ArrayList<>();
        for(Audio audio : audioList){
            if(audio.getName().toLowerCase().contains(text.toLowerCase()))
            {
                audioFilter.add(audio);
            }
        }
        audioList = audioFilter;
        mAdapter.filterList(audioFilter);
        mAdapter.notifyDataSetChanged();
    }

}
