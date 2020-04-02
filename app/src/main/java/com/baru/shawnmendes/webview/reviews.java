package com.baru.shawnmendes.webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.baru.shawnmendes.R;
import com.baru.shawnmendes.optionmenu.MenuAbout;
import com.baru.shawnmendes.optionmenu.MenuPrivacyPolicy;
import com.baru.shawnmendes.optionmenu.MenuSetting;

public class reviews extends AppCompatActivity {


    private WebView webSite;
    private AdView adviewAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        adviewAds = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adviewAds.loadAd(adRequest);



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