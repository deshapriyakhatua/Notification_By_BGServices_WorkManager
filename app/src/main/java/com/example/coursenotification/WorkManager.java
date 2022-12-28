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


    public WorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            networkRequest();
            queue.add(stringRequest);
        }catch (Exception e){
            Result.retry();
        }
        return Result.success();
    }


    private void networkRequest(){

        queue = Volley.newRequestQueue(getApplicationContext());
        requestedResult = null;


        String cookieStr = "_ga=GA1.1.324437731.1671815920; _ga_QPY5B063JV=GS1.1.1672238911.2.0.1672238916.0.0.0; XSRF-TOKEN=eyJpdiI6Ing1ZzVJeUFhWHBVaGlPcytFM1ZWMHc9PSIsInZhbHVlIjoiZkYwSEVVQzI4MGRnKy9sZE1UY3lscWRYYTF6NWYxSG1rN09IQ0VYRXdhaU9MK0VtcXRMRU1abGNxZ3dmaGtqYlNUbWs1K2hsTkQ1ODhXN1UvQmNCb1dhY2lxWk9tWG5IZmVJSXpGaENVdENPMFdoLzVLWXdTVzNkckk0WGppR2EiLCJtYWMiOiI2OWRmMGI5YTFjMTFmYjQyNmVjMzRiYjQ0OWQ5NTEwZWZhNTc4NGE4MmRjMTE2ZjdkNzRjNDkxZmQ3MWFkN2VmIiwidGFnIjoiIn0%3D; masai_school_course_session=eyJpdiI6IlFaei9Hd2s3R3pvaEpqT0xHU3VTT0E9PSIsInZhbHVlIjoiMFNtUjJLZ3NTaXZYQUthb3RGZGR2YzRNLzZFR0dSTU1kSWtqRm9KT1ZGZkYzRU5kTXNMeWhQN094Ykhya0ZveHp5SFUySjFWUzZHTmZvS0pVc2lCWkdWMmdSQUNLeisxVlBtK05xNkxvcGZiMEMvS2ZiNnF5NTh2Q2laUTFnTloiLCJtYWMiOiJjYjAzNDQzZDk2YTUxYjc5NGVmYTc4M2I4ZWI2YzQ0OWI4YTA2N2RlMjkzNDI5MGZiNTBjZTcyYmRkZDY3ODk3IiwidGFnIjoiIn0%3D";



        String url = "https://course.masaischool.com/dashboard";

        // Request a string response from the provided URL.
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        requestedResult = response.toString();
                        //Toast.makeText(NetworkRequestService.this, "Response is: " + response, Toast.LENGTH_SHORT).show();
                        Log.d("response",response);

                        if(requestedResult != null){
                            if(requestedResult.contains("adarsha")){

                                if(requestedResult.contains("You have unread notifications")){
                                    Log.d("response1",response);
                                    notificationManager.notify(NOTIFICATION_ID,notification3);
                                }

                            }else{
                                notificationManager.notify(NOTIFICATION_ID,notification);
                            }
                        }else{
                            notificationManager.notify(NOTIFICATION_ID,notification);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(NetworkRequestService.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
                notificationManager.notify(NOTIFICATION_ID,notification2);
            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> header = new HashMap<>();
                return header;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header = new HashMap<>();
                header.put("cookie",cookieStr);
                return header;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }


        };
    }

    public void sendNotification(){

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID,"General",NotificationManager.IMPORTANCE_HIGH));

        //PENDING INTENT
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);



        // big picture style
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.big_image);
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                .bigPicture(largeIcon)
                .bigLargeIcon(largeIcon)
                .setBigContentTitle("Content Title")
                .setSummaryText("Summary Text");



        // creating notification
        notification = new Notification.Builder(NetworkRequestService.this,NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setContentTitle("Update your cookie")
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentText("log in to browser and copy the cookie")
                .setSubText("cookie expired !")
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                //.setStyle(bigPictureStyle)
                .setContentIntent(pendingIntent)
                .build();

        // creating notification2
        notification2 = new Notification.Builder(NetworkRequestService.this,NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setContentTitle("Turn on internet")
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentText("turn on your internet for update")
                .setSubText("internet turned off !")
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                //.setStyle(bigPictureStyle)
                .setContentIntent(pendingIntent)
                .build();

        // creating notification3
        notification3 = new Notification.Builder(NetworkRequestService.this,NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setContentTitle("New Notification")
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentText("new notifications available")
                .setSubText("notification available")
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                //.setStyle(bigPictureStyle)
                .setContentIntent(pendingIntent)
                .build();
    }
}
