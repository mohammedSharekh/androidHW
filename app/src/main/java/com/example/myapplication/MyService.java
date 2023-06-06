package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    MediaPlayer mediaplayer;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaplayer = MediaPlayer.create(this,R.raw.alkother);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaplayer.isPlaying()){
            mediaplayer.stop();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mediaplayer.isPlaying()){
            mediaplayer.start();
        }
        return START_STICKY;
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}