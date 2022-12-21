package com.example.coursenotification;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private Button button;
    private static int NOTIFICATION_ID = 1;
    private static String NOTIFICATION_CHANNEL_ID = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PENDING INTENT
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.big_image);

        // big picture style
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                .bigPicture(largeIcon)
                .bigLargeIcon(largeIcon)
                .setBigContentTitle("Content Title")
                .setSummaryText("Summary Text");

        // creating notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(MainActivity.this,NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setContentTitle("new Title")
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentText("new content text")
                .setSubText("new subtext")
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setStyle(bigPictureStyle)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID,"General",NotificationManager.IMPORTANCE_HIGH));

        button = findViewById(R.id.button);


        // Network request

        // Instantiate the RequestQueue.
        String cookieStr = "_gcl_au=1.1.921406518.1669701353; gfg_nluid=07557b7692f198ff343480c3e65402cf; gfguserName=deshapriyakhatua%2FeyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvd3d3LmdlZWtzZm9yZ2Vla3Mub3JnXC8iLCJpYXQiOjE2Njk3MDEzNTgsImV4cCI6MTY3MjI5MzM1NywiaGFuZGxlIjoiZGVzaGFwcml5YWtoYXR1YSIsInV1aWQiOiIwODk2OGY4ZmM5ZGYyYmY0M2YzOTU2YzRiYTZlZjg1ZiIsInByb2ZpbGVVcmwiOiJodHRwczpcL1wvbWVkaWEuZ2Vla3Nmb3JnZWVrcy5vcmdcL2F1dGhcL3Byb2ZpbGVcLzE0YWZiZHFlYnJnc21sNjI5czJtIiwiaW5zdGl0dXRlSWQiOjMyMjYsImluc3RpdHV0ZU5hbWUiOiJVbml2ZXJzaXR5IG9mIENhbGN1dHRhIEtvbGthdGEiLCJuYW1lIjoiRGVzaGFwcml5YSBLaGF0dWEiLCJpc0ludGVyZXN0U2VsZWN0ZWQiOnRydWUsInB1aWQiOiJzbWlOUnRBMDB3PT0iLCJwYSI6MX0.aGQnLv3w4Q3axia2WHR6g8SKnh8XPf7jjQnbHh6L7_XxH_DnAWVKzsmHzVy-dR18Fi7KYptYruRFPKvoxJXO-xZaIBaxLUEa3QMnjpLpzs33SxVAnmlzVJ0kdsEN6esQ4xt63izJ4-9LInV9s8Adqy8gP05fBhIhUbxsVb1zw4KFHlrrGbLBhRGzBANI6W6c744tB5FGVKpyuw7LXUAuOl-Jho7FwEEAfkGtZVL6Hs4O77QbS0UD896K4FPK2h8Rxp_lh7sQDfDM0PSAC2dgJjqVquSY8VAqV77TdGkCxrmOFbgRCZPTuaQ3ih6x1uNuGIiW08HC7Y2g1I7dAAarfQ; gfg_id5_identity=5feceb66ffc86f38d952786c6d696c79c2dbc239dd4e91b46729d73a27fb57e9; gfg_theme=gfgThemeDark; __gads=ID=5b015fadcaad6946:T=1669702234:S=ALNI_MbHbSw7T9GiFuK_Zf0Le2i6OjCZLw; _fbp=fb.1.1669953814971.895157593; _gid=GA1.2.473384834.1671506705; _gaexp=GAX1.2.jhuNsO7LQHy-phLreIrQFA.19432.2!Nyj9kzaYThyH_VqFdyCUrQ.19378.1!Dl3qzJlrQ1OS_ygo0Lrqdw.19380.2!qxX5h9pCShOJ6wpFLYyrmA.19380.1; __gpi=UID=00000b85b47a599f:T=1669702234:RT=1671557008:S=ALNI_MZt-VXYo6yk-JlETnH7p5LMamW_Pg; FCNEC=%5B%5B%22AKsRol9Ock9egG_sbqJz-fBI5W5moXUQb5s7FBTEguGxlMkAIWom8mtTLQNqOSLH4EgxOnf4awZYSdESIlp4Nc00BBBLSpIhBuvj9-GmfAv09B-2tlRkykjBY7iqJMDIBKv6Q90W4HUPwPOOAiaUm1mZUaDWjYd2dg%3D%3D%22%5D%2Cnull%2C%5B%5D%5D; _ga_DWCCJLKX3X=GS1.1.1671557004.10.1.1671560580.33.0.0; _clck=1puwscc|1|f7l|0; _ga=GA1.1.1676381588.1669701353; _gat_gtag_UA_71763465_1=1; _clsk=1y129kv|1671633532250|1|1|e.clarity.ms/collect; _ga_SZ454CLTZM=GS1.1.1671633531.36.0.1671633540.51.0.0; http_referrer=\"https://practice.geeksforgeeks.org/problem-of-the-day\"";
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://practiceapi.geeksforgeeks.org/api/v1/users/getProfile";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(MainActivity.this, "Response is: " + response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap();
                header.put("cookie",cookieStr);
                return header;
            }

        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationManager.notify(NOTIFICATION_ID,notification);
            }
        });
    }
}