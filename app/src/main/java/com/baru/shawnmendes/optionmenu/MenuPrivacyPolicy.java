package com.baru.shawnmendes.optionmenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.baru.shawnmendes.DashboardPage;
import com.baru.shawnmendes.R;

public class MenuPrivacyPolicy extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private AdView adviewAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_privacy_policy);


        adviewAds = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adviewAds.loadAd(adRequest);



        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btm_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_home :
                        Intent intentkusatu= new Intent(MenuPrivacyPolicy.this, DashboardPage.class);
                        startActivity(intentkusatu);
                        break;
                    case R.id.action_Aboutme :
                        Intent intentkudua= new Intent(MenuPrivacyPolicy.this, MenuAbout.class);
                        startActivity(intentkudua);
                        break;
                    case R.id.action_privacy :
                        Toast.makeText(MenuPrivacyPolicy.this, "This Privacy Policy", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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



}