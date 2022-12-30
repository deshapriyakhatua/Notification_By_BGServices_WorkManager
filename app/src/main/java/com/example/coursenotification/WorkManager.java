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

    private NotificationManager notificationManager;
    private Notification notification;
    private Notification notification2;
    private Notification notification3;
    private Notification notification4;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private static final int REQUEST_CODE = 1;
    private static int NOTIFICATION_ID = 1;
    private static String NOTIFICATION_CHANNEL_ID = "1";

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

                    sendNotification();
                    networkRequest();
                    queue.add(stringRequest);

                }}).start();



        }catch (Exception e){
            Result.retry();
        }
        return Result.success();
    }


    private void networkRequest(){

        queue = Volley.newRequestQueue(getApplicationContext());
        requestedResult = null;


        String cookieStr;

         if(MainActivity.getCookie().length()>0){ cookieStr = MainActivity.getCookie(); }
         else{
             cookieStr = "_ga=GA1.1.324437731.1671815920; _ga_QPY5B063JV=GS1.1.1672421517.3.1.1672421546.0.0.0; XSRF-TOKEN=eyJpdiI6InRUV29ab0pBWHZheHNVUzZKUW5WaUE9PSIsInZhbHVlIjoiam4yOE4vLythU1RqUnQ2OFhHZDB1d2ZCWTVZK3VpR20rbHVseFRqeXNNTHBSWWhFbDlVenBEN3RrdmlISGttR1FyZ2xVK0lFN1RUdjdQbFZMdWxacmc4bDFiUWp0eGgxdVdXMHk5YVBxZDN4LzJWMG5acFQ4d3ZramtHVWNOU3YiLCJtYWMiOiIwNjg0MTRkOTZhNTYwNDNhZWRlODg1NDgwODQ4YzFlNThiZTNhNjFhZDYyYzhiMDE5ZmZmNmMwYjE2NTcxYjBhIiwidGFnIjoiIn0%3D; masai_school_course_session=eyJpdiI6InBOdko4RmhtR09DSjJIU3ZTK2wwUmc9PSIsInZhbHVlIjoiU1ZjaTZEb1hMelkwYjNGN0YvMThvU0lGcXVZaDlDa3BqaUVTTzNNb3JVTTQwNHIrZnJ1MUk4cWFGMzhyTlVRTHBhay91S1ZIendNYXFzaGQ1cmRrcjhrYVN4U2tFbCtac21WSGdmRVY4VGZXUlJaNVYrREg2YzVFMTN6VGUxOEgiLCJtYWMiOiIwMjM1ZGJhMDA4NDg0ODlkNTMzODBlZTNkYjkxZDE5YWY2MTg5Nzg0ZDk0NWFkNDBkNjY0NzUxNDZlZWE5MDViIiwidGFnIjoiIn0%3D";
         }

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
                                    mediaPlayer.start();
                                }else {
                                    notificationManager.notify(NOTIFICATION_ID,notification4);
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

        notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID,"General",NotificationManager.IMPORTANCE_HIGH));

        //PENDING INTENT
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://course.masaischool.com/dashboard"));

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);



        // big picture style
        Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.big_image);
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                .bigPicture(largeIcon)
                .bigLargeIcon(largeIcon)
                .setBigContentTitle("Content Title")
                .setSummaryText("Summary Text");



        // creating notification
        notification = new Notification.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID)
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
        notification2 = new Notification.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID)
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
        notification3 = new Notification.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setContentTitle("New Notification")
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentText("new notifications available")
                .setSubText("notification available")
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                //.setStyle(bigPictureStyle)
                .setContentIntent(pendingIntent)
                .build();

        // creating notification4
        notification4 = new Notification.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setContentTitle("No Notification")
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentText("No notifications available")
                .setSubText("No notification available")
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                //.setStyle(bigPictureStyle)
                .setContentIntent(pendingIntent)
                .build();
    }
}
