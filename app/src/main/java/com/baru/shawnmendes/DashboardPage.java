package com.baru.shawnmendes;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.view.animation.AnimationUtils;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import com.baru.shawnmendes.optionmenu.MenuAbout;
import com.baru.shawnmendes.optionmenu.MenuPrivacyPolicy;
import com.baru.shawnmendes.optionmenu.MenuSetting;
import com.baru.shawnmendes.mp3player.MainActivityJcPlayer;
import com.baru.shawnmendes.lirikmp3.HomeActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardPage extends AppCompatActivity {


    private AdView adView;
    private ConsentForm consentForm;
    private ImageView videolyric, biograpy,  otherapp, buttonmusikplayeronline, albumvideo, lyricmusic;
    private ViewFlipper viewFlipper;
    private Animation FadeIn, FadeOut;
    private CardView satu, dua, tiga, empat, albumone, albumtwo, albumtree, albumfour, albumfive;
    private Button lifestylebutton, fashionbutton, reviewsbutton, seevideoalbum;
    private TextView judulbiosatufont, fontsatufont, lifestyle, fashion, reviews, lirikmusik, lirikvideo, playermusik;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_dashboard);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);




        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btm_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_home :
                        Toast.makeText(DashboardPage.this, "This Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_Aboutme :
                        Intent intentkudua= new Intent(DashboardPage.this, MenuAbout.class);
                        startActivity(intentkudua);
                        break;
                    case R.id.action_privacy :
                        Intent intenttiga= new Intent(DashboardPage.this, MenuPrivacyPolicy.class);
                        startActivity(intenttiga);
                        break;
                }

                return true;
            }
        });

//        super.onCreate(savedInstanceState);

        // custom font
//        judulbiosatufont=(TextView)findViewById(R.id.judulbio);
//        fontsatufont=(TextView)findViewById(R.id.fontsatu);
//        lifestyle=(TextView)findViewById(R.id.judullifestyle);
//        fashion=(TextView)findViewById(R.id.judulfashion);
//        reviews=(TextView)findViewById(R.id.judulReviews);
        lirikmusik=(TextView)findViewById(R.id.musiklirik);
        lirikvideo=(TextView)findViewById(R.id.videolirik);
        playermusik=(TextView)findViewById(R.id.musikplayer);



//        Typeface customfont=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
//        judulbiosatufont.setTypeface(customfont);
//
//        Typeface customfont2=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
//        fontsatufont.setTypeface(customfont2);
//
//        Typeface customfont3=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
//        lifestyle.setTypeface(customfont3);
//
//        Typeface customfont4=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
//        fashion.setTypeface(customfont4);
//
//        Typeface customfont5=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
//        reviews.setTypeface(customfont5);

        Typeface customfont6=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
        lirikmusik.setTypeface(customfont6);

        Typeface customfont7=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
        lirikvideo.setTypeface(customfont7);

        Typeface customfont8=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
        playermusik.setTypeface(customfont8);



        // daftar ALBUM
//        albumone = (CardView) findViewById(R.id.albumsatu);
//        albumone.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, AlbumSatu.class);
//                startActivity(songs);
//            }
//        });
//
//        albumtwo = (CardView) findViewById(R.id.albumdua);
//        albumtwo.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, AlbumDua.class);
//                startActivity(songs);
//            }
//        });
//
//        albumtree = (CardView) findViewById(R.id.albumtiga);
//        albumtree.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, AlbumTiga.class);
//                startActivity(songs);
//            }
//        });
//
//        albumfour = (CardView) findViewById(R.id.albumempat);
//        albumfour.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, AlbumEmpat.class);
//                startActivity(songs);
//            }
//        });
//
//        albumfive = (CardView) findViewById(R.id.albumlima);
//        albumfive.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, AlbumLima.class);
//                startActivity(songs);
//            }
//        });


        // music video with lyric thumbnail
//        satu = (CardView) findViewById(R.id.mvsatu);
//        satu.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, VideoLyricPage.class);
//                startActivity(songs);
//            }
//        });
//        dua = (CardView) findViewById(R.id.mvdua);
//        dua.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, VideoLyricPage.class);
//                startActivity(songs);
//            }
//        });
//        tiga = (CardView) findViewById(R.id.mvtiga);
//        tiga.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, VideoLyricPage.class);
//                startActivity(songs);
//            }
//        });
//        empat = (CardView) findViewById(R.id.mvempat);
//        empat.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, VideoLyricPage.class);
//                startActivity(songs);
//            }
//        });

        // tombol tombol Taylor swift NEWS

//        seevideoalbum = (Button) findViewById(R.id.tombolseevideoalbum);
//        seevideoalbum.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, AlbumHome.class);
//                startActivity(songs);
//            }
//        });
//
//
//        lifestylebutton = (Button) findViewById(R.id.tombollifestyle);
//        lifestylebutton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, lifestyle.class);
//                startActivity(songs);
//            }
//        });
//
//        fashionbutton = (Button) findViewById(R.id.tombolfashion);
//        fashionbutton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, fashion.class);
//                startActivity(songs);
//            }
//        });
//
//        reviewsbutton = (Button) findViewById(R.id.tombolreviews);
//        reviewsbutton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent songs = new Intent(DashboardPage.this, reviews.class);
//                startActivity(songs);
//            }
//        });



        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        FadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        FadeOut = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        viewFlipper.setInAnimation(FadeIn);
        viewFlipper.setOutAnimation(FadeOut);
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();



        videolyric = (ImageView) findViewById(R.id.videolyric);
        videolyric.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent songs = new Intent(DashboardPage.this, VideoLyricPage.class);
                startActivity(songs);
            }
        });



        albumvideo = (ImageView) findViewById(R.id.albumvideo);
        albumvideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent songs = new Intent(DashboardPage.this, MainActivityJcPlayer.class);
                startActivity(songs);

            }
        });

        lyricmusic = (ImageView) findViewById(R.id.lirikmusik);
        lyricmusic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent songs = new Intent(DashboardPage.this, HomeActivity.class);
                startActivity(songs);

            }
        });


//        PrepareData();
//
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
        adView = findViewById(R.id.adView);

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                adView.setVisibility(View.INVISIBLE);
            }
        });


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
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
//
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
//
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

        } else if (item.getItemId()==R.id.rateme) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="
                    + getPackageName())));
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("Soyluna", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count",0);
        boolean later = sharedPreferences.getBoolean("later",false);
        boolean rated = sharedPreferences.getBoolean("rated",false);
        count+=1;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count",count);
        editor.apply();

        if(count >= 5 && !later && !rated){
            final Dialog dialog = new Dialog(DashboardPage.this);
            dialog.setContentView(R.layout.apprate);
            dialog.setTitle("Rate This App");
            dialog.setCancelable(false);
            TextView nothanks = dialog.findViewById(R.id.nothanks);
            TextView maybelater = dialog.findViewById(R.id.maybelater);
            TextView ratenow = dialog.findViewById(R.id.ratenow);

            nothanks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Soyluna", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("count",0);
                    editor.apply();
                    dialog.dismiss();
                }
            });
            maybelater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Soyluna", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("count",0);
                    editor.putBoolean("later",true);
                    editor.apply();
                    dialog.dismiss();
                }
            });
            ratenow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Soyluna", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("count",0);
                    editor.putBoolean("rated",true);
                    editor.apply();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}




