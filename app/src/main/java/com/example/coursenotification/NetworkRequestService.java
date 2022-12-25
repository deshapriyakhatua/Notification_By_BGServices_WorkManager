package com.example.coursenotification;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import androidx.annotation.Nullable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class NetworkRequestService extends Service {


    private static Runnable runnable;
    MediaPlayer mediaPlayer;
    ScheduledExecutorService scheduledExecutorService;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaPlayer =MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable(){
            public void run() {
                // TODO Auto-generated method stub
                mediaPlayer.start();

            }
        },10,30, TimeUnit.SECONDS);


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        scheduledExecutorService.shutdown();
        super.onDestroy();
    }



}

