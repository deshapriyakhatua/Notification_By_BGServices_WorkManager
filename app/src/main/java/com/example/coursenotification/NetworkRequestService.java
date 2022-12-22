package com.example.coursenotification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class NetworkRequestService extends Service {

    private NotificationManager notificationManager;
    private Notification notification;
    private Notification notification2;
    private Notification notification3;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private static final int REQUEST_CODE = 1;
    private static int NOTIFICATION_ID = 1;
    private static String NOTIFICATION_CHANNEL_ID = "1";

    private static Runnable runnable;
    private String requestedResult;
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
                queue.add(stringRequest);

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

