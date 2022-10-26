package com.example.coursenotification;

import static android.content.Context.NOTIFICATION_SERVICE;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

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
