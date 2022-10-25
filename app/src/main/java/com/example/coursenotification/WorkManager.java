package com.example.coursenotification;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkManager extends Worker {

    private static Runnable runnable;
    private String requestedResult;
    MediaPlayer mediaPlayer;


    public WorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        //Log.d("constructor", "WorkManager: constructer called");
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Log.d("doWork", "doWork: started");

            new Thread(new Runnable() {
                public void run()
                {
                    // code goes here.
                    mediaPlayer =MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_RINGTONE_URI);
                    mediaPlayer.start();

                }}).start();



        }catch (Exception e){
            Result.retry();
        }
        return Result.success();
    }




}
