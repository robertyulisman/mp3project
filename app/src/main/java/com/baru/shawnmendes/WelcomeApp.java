package com.baru.shawnmendes;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baru.shawnmendes.mp3player.MainActivityJcPlayer;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import android.widget.TextView;


public class WelcomeApp extends AppCompatActivity {

    private AdView iklanAds;
    private Button tombol;
    private TextView text, popprivacy;
    Dialog myDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        SharedPreferences sharedPreferences = getSharedPreferences("Shawn Mendes", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count",0);
        editor.putBoolean("later", false);
        editor.apply();

        myDialog = new Dialog(this);


//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if(getSupportActionBar() != null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        text=(TextView)findViewById(R.id.textsss);
//        popprivacy=(TextView)findViewById(R.id.popup);


        Typeface customfont=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
        text.setTypeface(customfont);
//        Typeface customfont2=Typeface.createFromAsset(getAssets(),"font/contm.ttf");
//        popprivacy.setTypeface(customfont2);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3939903388953034~9374436762");


        iklanAds = (AdView) findViewById(R.id.kodeiklan);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        iklanAds.loadAd(adRequest);

        tombol = (Button) findViewById(R.id.tombolwelcome);
        tombol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent songs = new Intent(WelcomeApp.this, MainActivityJcPlayer.class);
                startActivity(songs);
            }
        });

    }

//    public void ShowPopup(View v) {
//        TextView txtclose;
//        myDialog.setContentView(R.layout.popupprivacypolicy);
//        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
//        txtclose.setText("x");
//        txtclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//    }



    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(WelcomeApp.this);
        alert.setTitle("Thanks for Using Our App, Please Rate Us :");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + getPackageName())));
                }

                dialog.dismiss();
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        alert.create();
        alert.show();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.optionmenu, menu);
//        //getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId()==R.id.about){
//            startActivity(new Intent(this, MenuAbout.class));
//        } else if (item.getItemId() == R.id.setting) {
//            startActivity(new Intent(this, MenuSetting.class));
//        } else if (item.getItemId() == R.id.help) {
//            startActivity(new Intent(this, MenuPrivacyPolicy.class));
//        }
//
//        return true;
//    }
}
