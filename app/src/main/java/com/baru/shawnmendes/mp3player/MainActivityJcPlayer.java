package com.baru.shawnmendes.mp3player;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.baru.shawnmendes.optionmenu.MenuAbout;
import com.baru.shawnmendes.optionmenu.MenuPrivacyPolicy;
import com.baru.shawnmendes.optionmenu.MenuSetting;
import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.general.errors.OnInvalidPathListener;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.baru.shawnmendes.R;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivityJcPlayer extends AppCompatActivity
        implements OnInvalidPathListener, JcPlayerManagerListener, AudioAdapter.AdapterCallback {

    private static final String TAG = MainActivityJcPlayer.class.getSimpleName();
    private JcPlayerView player;
    private RecyclerView recyclerView;
    private AudioAdapter audioAdapter;
    private InterstitialAd interstitialAd;
    private AdView adView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    String title, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainjcplay);

        prepareAd();

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Log.i("hello", "world");
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            Log.d("TAG"," Interstitial not loaded");
                        }

                        prepareAd();
                    }
                });
            }
        }, 1000, 1000, TimeUnit.SECONDS);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        player = findViewById(R.id.jcplayer);

        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        getJcAudios();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.createNotification();
    }

    protected void adapterSetup() {
        audioAdapter = new AudioAdapter(MainActivityJcPlayer.this,player.getMyPlaylist());
        audioAdapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                player.playAudio(player.getMyPlaylist().get(position));
            }

            @Override
            public void onSongItemDeleteClicked(int position) {
                Toast.makeText(MainActivityJcPlayer.this, "Delete song at position " + position,
                        Toast.LENGTH_SHORT).show();
//                if(player.getCurrentPlayedAudio() != null) {
//                    Toast.makeText(MainActivity.this, "Current audio = " + player.getCurrentPlayedAudio().getPath(),
//                            Toast.LENGTH_SHORT).show();
//                }
                removeItem(position);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(audioAdapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    @Override
    public void onPause() {
        super.onPause();
        player.createNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.kill();
    }

    @Override
    public void onPathError(JcAudio jcAudio) {
        Toast.makeText(this, jcAudio.getPath() + " with problems", Toast.LENGTH_LONG).show();
//        player.removeAudio(jcAudio);
//        player.next();
    }


    @Override
    public void onPreparedAudio(JcStatus status) {

    }

    @Override
    public void onCompletedAudio() {

    }

    @Override
    public void onPaused(JcStatus status) {

    }

    @Override
    public void onContinueAudio(JcStatus status) {

    }

    @Override
    public void onPlaying(JcStatus status) {

    }

    @Override
    public void onTimeChanged(@NonNull JcStatus status) {
        updateProgress(status);
    }

    @Override
    public void onJcpError(@NonNull Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void updateProgress(final JcStatus jcStatus) {
        Log.d(TAG, "Song duration = " + jcStatus.getDuration()
                + "\n song position = " + jcStatus.getCurrentPosition());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // calculate progress
                float progress = (float) (jcStatus.getDuration() - jcStatus.getCurrentPosition())
                        / (float) jcStatus.getDuration();
                progress = 1.0f - progress;
                audioAdapter.updateProgress(jcStatus.getJcAudio(), progress);
            }
        });
    }

    private void removeItem(int position) {
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);

        //        jcAudios.remove(position);
        player.removeAudio(player.getMyPlaylist().get(position));
        audioAdapter.notifyItemRemoved(position);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    public void onStopped(JcStatus status) {

    }

    @Override
    public void downloadSong(int position) {
        url = jcAudios.get(position).getPath();
        title = jcAudios.get(position).getTitle();
        new DownloadFile().execute(url);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private boolean isDownloaded;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(MainActivityJcPlayer.this);
            this.progressDialog.setMessage("Downloading - "+title);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);


                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());
                String extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
                fileName = title+"."+extension;

                //External directory path to save file
                folder = Environment.getExternalStorageDirectory() + File.separator + "Soyluna/";

                //Create androiddeft folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return "Download Success";

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Check Internet Connection";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();

            // Display File path after downloading
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
            if(message.equals("Download Success")){
                getJcAudios();
                audioAdapter.notifyDataSetChanged();
            }
        }
    }
    protected void getJcAudios(){
        if(jcAudios.size() != 0){
            jcAudios.clear();
        }
        jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromURL("1. Señorita (feat. Camila Cabello)", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%2C%20Camila%20Cabello%20-%20Se%C3%B1orita.mp3"));
        jcAudios.add(JcAudio.createFromURL("2. Never Be Alone", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Never%20Be%20Alone.mp3"));
        jcAudios.add(JcAudio.createFromURL("3. Mercy", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Mercy.mp3"));
        jcAudios.add(JcAudio.createFromURL("4. Treat You Better", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Treat%20You%20Better.mp3"));
        jcAudios.add(JcAudio.createFromURL("5. Stitches", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Stitches%20%28Official%20Video%29.mp3"));
        jcAudios.add(JcAudio.createFromURL("6. In My Blood", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20In%20My%20Blood.mp3"));
        jcAudios.add(JcAudio.createFromURL("7. There's Nothing Holdin' Me Back", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20There%60s%20Nothing%20Holdin%60%20Me%20Back.mp3"));
        jcAudios.add(JcAudio.createFromURL("8. If I Can't Have You", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20If%20I%20Can%60t%20Have%20You.mp3"));
        jcAudios.add(JcAudio.createFromURL("9. Imagination", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Imagination%20%28Audio%29.mp3"));
        jcAudios.add(JcAudio.createFromURL("10. Fallin' All In You", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Fallin%60%20All%20In%20You%20%28lyrics%29.mp3"));
        jcAudios.add(JcAudio.createFromURL("11. Why", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Why%20%7B%20lyrics%20%7D.mp3"));
        jcAudios.add(JcAudio.createFromURL("12. Lost In Japan", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20%26quot%3BLost%20In%20Japan%26quot%3B%20%28Audio%29.mp3"));
        jcAudios.add(JcAudio.createFromURL("13. Youth (feat. Khalid)", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Youth%20%28Lyric%20Video%29%20ft.%20Khalid.mp3"));
        jcAudios.add(JcAudio.createFromURL("14. Nervous", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Nervous.mp3"));
        jcAudios.add(JcAudio.createFromURL("15. Bad Reputation", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Bad%20Reputation%20%28lyrics%29.mp3"));
        jcAudios.add(JcAudio.createFromURL("16. I Know What You Did Last Summer (feat. Camila Cabello)", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%2C%20Camila%20Cabello%20-%20I%20Know%20What%20You%20Did%20Last%20Summer%20%28Official%20Video%29.mp3"));
        jcAudios.add(JcAudio.createFromURL("17. Roses", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Roses%20%28lyrics%29.mp3"));
        jcAudios.add(JcAudio.createFromURL("18. Perfectly Wrong", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Perfectly%20Wrong%20%28lyrics%29.mp3"));
        jcAudios.add(JcAudio.createFromURL("19. Life Of The Party", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20-%20Life%20Of%20The%20Party%20%28Lyric%20Video%29.mp3"));
        jcAudios.add(JcAudio.createFromURL("20. Use Somebody", "https://ia601408.us.archive.org/34/items/abangmendesssss/Shawn%20Mendes%20~%20Use%20Somebody%20%28Lyrics%29.mp3"));

        for (int i = 0; i < jcAudios.size(); i++){
            String folder = Environment.getExternalStorageDirectory() + File.separator + "Soyluna/"+jcAudios.get(i).getTitle()+".mp3";
            File directory = new File(folder);

            if (directory.exists()) {
                jcAudios.set(i, JcAudio.createFromFilePath(jcAudios.get(i).getTitle(), folder));
            }
        }

        player.initPlaylist(jcAudios, this);
        adapterSetup();
        audioAdapter.notifyDataSetChanged();
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
    public void onBackPressed() {
//        super.onBackPressed();

        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            super.onBackPressed();
        }
    }

    public void  prepareAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.kodeiklanintersial));
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
}