package com.baru.shawnmendes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VideoLyricPage extends AppCompatActivity {

    private RecyclerView recycleView;
    private RecyclerView.LayoutManager layoutMan;
    private RViewAdapter Adapter;
    private AdView adviewAds;
    private ImageView btn_back, btn_search, btn_close;
    private LinearLayout search, tulbar;
    private EditText t_search;

    private ConsentForm consentForm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_video_lyrics);
        PrepareData();

//        ConsentInformation.getInstance(this).setDebugGeography(DebugGeography.DEBUG_GEOGRAPHY_EEA);
//        ConsentInformation.getInstance(this)
//                .addTestDevice("ED5E4F6216188E095F905572D3AC38CD");

        ConsentInformation consentInformation = ConsentInformation.getInstance(this);

        consentInformation.requestConsentInfoUpdate(
                new String[]{getString(R.string.kodeiklanadmob)}, new ConsentInfoUpdateListener() {

                    @Override
                    public void onConsentInfoUpdated(ConsentStatus consentStatus) {

                        if (consentStatus == ConsentStatus.UNKNOWN) {
                            displayConsentForm();
                        }
                    }

                    @Override
                    public void onFailedToUpdateConsentInfo(String errorDescription) {
                        Log.e("ERROR", errorDescription);

                    }
                });

        MobileAds.initialize(this, getString(R.string.kodeappidadmob));
        adviewAds = findViewById(R.id.ad_view);

        adviewAds.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                adviewAds.setVisibility(View.INVISIBLE);
            }
        });


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adviewAds.loadAd(adRequest);

        recycleView = findViewById(R.id.recyclerView);
        layoutMan = new GridLayoutManager(this, 1);
        recycleView.setLayoutManager(layoutMan);
        Adapter = new RViewAdapter(Data.videos, this);
        recycleView.setAdapter(Adapter);
        btn_back = findViewById(R.id.btn_back);
        btn_search = findViewById(R.id.btn_search);
        btn_close = findViewById(R.id.btn_close);
        search = findViewById(R.id.search);
        tulbar = findViewById(R.id.tulbar);
        t_search = findViewById(R.id.t_search);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoLyricPage.this, DashboardPage.class));
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
    }

    private void displayConsentForm() {

        consentForm = new ConsentForm.Builder(this, getAppsPrivacyPolicy())
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {

                        consentForm.show();
                    }

                    @Override
                    public void onConsentFormOpened() {

                    }

                    @Override
                    public void onConsentFormClosed(
                            ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        Log.e("Error Consent",errorDescription);

                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();
        consentForm.load();
    }

    private URL getAppsPrivacyPolicy() {
        URL mUrl = null;
        try
        {
            mUrl = new URL("https://suciwulandarimawar.blogspot.com/2019/05/soy-luna-privacy-policy.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mUrl;
    }

    void PrepareData() {
        Data.infos = getResources().getStringArray(R.array.infos);
        Data.ytlinks = getResources().getStringArray(R.array.ytlinks);
        Data.titles = getResources().getStringArray(R.array.titles);
        for (int i = 0; i < Data.titles.length; i++){
            Data.videos.add(i+1);
        }
    }

    public void searchData(String text)
    {
        Data.videos = new ArrayList<>();
        for (int i = 0; i < Data.titles.length; i++){
            Data.videos.add(i+1);
        }
        Adapter.notifyDataSetChanged();
        List<Integer> videosFilter = new ArrayList<>();
        int index = 0;
        for(String title : Data.titles){
            if(title.toLowerCase().contains(text.toLowerCase()))
            {
                videosFilter.add(Data.videos.get(index));
            }
            index++;
        }
        Data.videos = new ArrayList<>();
        Data.videos = videosFilter;
        Adapter.filterList(Data.videos);
        Adapter.notifyDataSetChanged();
    }
}
