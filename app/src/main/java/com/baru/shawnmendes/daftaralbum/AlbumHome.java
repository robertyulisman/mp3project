package com.baru.shawnmendes.daftaralbum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.baru.shawnmendes.R;


public class AlbumHome extends AppCompatActivity implements View.OnClickListener {
    AdView bannerIklan;
    private ProgressDialog progressDialog;
//    private ViewFlipper viewFlipper;
//    private Animation FadeIn, FadeOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);




//        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
//        FadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
//        FadeOut = AnimationUtils.loadAnimation(this,R.anim.fade_out);
//        viewFlipper.setInAnimation(FadeIn);
//        viewFlipper.setOutAnimation(FadeOut);
//        viewFlipper.setAutoStart(true);
//        viewFlipper.setFlipInterval(5000);
//        viewFlipper.startFlipping();

        MobileAds.initialize(this, "");
        new activ().execute();

        findViewById(R.id.card_satu).setOnClickListener(this);
        findViewById(R.id.card_dua).setOnClickListener(this);
        findViewById(R.id.card_tiga).setOnClickListener(this);
        findViewById(R.id.card_empat).setOnClickListener(this);
        findViewById(R.id.card_lima).setOnClickListener(this);
        findViewById(R.id.card_enam).setOnClickListener(this);
//        findViewById(R.id.card_tujuh).setOnClickListener(this);
//        findViewById(R.id.card_delapan).setOnClickListener(this);
//        findViewById(R.id.card_sembilan).setOnClickListener(this);
//        findViewById(R.id.card_sepuluh).setOnClickListener(this);
//        findViewById(R.id.card_sebelas).setOnClickListener(this);
//        findViewById(R.id.card_duabelas).setOnClickListener(this);
//        findViewById(R.id.card_tigabelas).setOnClickListener(this);
//        findViewById(R.id.card_empatbelas).setOnClickListener(this);
//        findViewById(R.id.card_limabelas).setOnClickListener(this);




        // banner
        bannerIklan = findViewById(R.id.iklanBn);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        bannerIklan.loadAd(adRequest);
    }



    @Override
    public void onClick(View v) {
        Class clazz = null;

        switch (v.getId()) {
            case R.id.card_satu:
                clazz = AlbumSatu.class;
                break;
        }
        switch (v.getId()) {
            case R.id.card_dua:
                clazz = AlbumDua.class;
                break;
        }
        switch (v.getId()) {
            case R.id.card_tiga:
                clazz = AlbumTiga.class;
                break;
        }
        switch (v.getId()) {
            case R.id.card_empat:
                clazz = AlbumEmpat.class;
                break;
        }
        switch (v.getId()) {
            case R.id.card_lima:
                clazz = AlbumLima.class;
                break;
        }
        switch (v.getId()) {
            case R.id.card_enam:
                clazz = AlbumEnam.class;
                break;
        }
//        switch (v.getId()) {
//            case R.id.card_tujuh:
//                clazz = Albumtujuh.class;
//                break;
//        }
//        switch (v.getId()) {
//            case R.id.card_delapan:
//                clazz = AlbumDelapan.class;
//                break;
//        }
//        switch (v.getId()) {
//            case R.id.card_sembilan:
//                clazz = AlbumSembilan.class;
//                break;
//        }
//        switch (v.getId()) {
//            case R.id.card_sepuluh:
//                clazz = AlbumSepuluh.class;
//                break;
//        }
//        switch (v.getId()) {
//            case R.id.card_sebelas:
//                clazz = AlbumSebelas.class;
//                break;
//        }
//        switch (v.getId()) {
//            case R.id.card_duabelas:
//                clazz = AlbumDuaBelas.class;
//                break;
//        }
//        switch (v.getId()) {
//            case R.id.card_tigabelas:
//                clazz = AlbumTigaBelas.class;
//                break;
//        }
//        switch (v.getId()) {
//            case R.id.card_empatbelas:
//                clazz = AlbumEmpatBelas.class;
//                break;
//        }
//        switch (v.getId()) {
//            case R.id.card_limabelas:
//                clazz = AlbumLimaBelas.class;
//                break;
//        }





        startActivity(new Intent(this, clazz));
    }
    private class activ extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AlbumHome.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
        }
    }

}
