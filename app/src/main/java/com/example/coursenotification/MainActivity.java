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
        String cookieStr = "gr_user_id=811a64be-94b2-432e-ab46-f4526054da1c; 87b5a3c3f1a55520_gr_last_sent_cs1=deshapriyakhatua; __stripe_mid=afa791fc-c67f-41e4-aeed-7182f4a98a0de0b05c; _gcl_au=1.1.21572496.1670728269; intercom-id-pq9rak4o=d98cc369-26af-4b9e-a1ec-1cf5253a58fe; intercom-device-id-pq9rak4o=91d813ab-f72c-4e4f-b207-415d786b45d3; _ga_DKXQ03QCVK=GS1.1.1670728268.1.1.1670728274.54.0.0; _gid=GA1.2.2135118631.1671165589; __atuvc=3%7C48%2C2%7C49%2C0%7C50%2C3%7C51; csrftoken=IoJUo87p30YlLyanFoiL980XhiX1WE45oRjzPCTjM03DZUYhE4Z6YH8K7cahwboX; LEETCODE_SESSION=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfYXV0aF91c2VyX2lkIjoiNTQzNzM2MCIsIl9hdXRoX3VzZXJfYmFja2VuZCI6ImFsbGF1dGguYWNjb3VudC5hdXRoX2JhY2tlbmRzLkF1dGhlbnRpY2F0aW9uQmFja2VuZCIsIl9hdXRoX3VzZXJfaGFzaCI6IjdjOTBmNzVlMTc1YjVlY2M5Y2E4ZjViNjVmMGQ5Y2RjYzM0ZGVjMWQiLCJpZCI6NTQzNzM2MCwiZW1haWwiOiJkZXNoYXByaXlha2hhdHVhQGdtYWlsLmNvbSIsInVzZXJuYW1lIjoiZGVzaGFwcml5YWtoYXR1YSIsInVzZXJfc2x1ZyI6ImRlc2hhcHJpeWFraGF0dWEiLCJhdmF0YXIiOiJodHRwczovL2Fzc2V0cy5sZWV0Y29kZS5jb20vdXNlcnMvYXZhdGFycy9hdmF0YXJfMTY1NjY2MDE4MS5wbmciLCJyZWZyZXNoZWRfYXQiOjE2NzE1NDgyMTksImlwIjoiMTE1LjE4Ny40OS4xODMiLCJpZGVudGl0eSI6ImE4MThhYjM1OTgwNDUxN2YyNTQ5ZTk0Yzg4ZDAzYzBiIiwic2Vzc2lvbl9pZCI6MzI0ODcxNDd9.eijpU4Z18ZebT2Mf7BXIqNOmIdTZv2Tt9cGGdUmuCBI; _ga=GA1.1.740290690.1669701377; 87b5a3c3f1a55520_gr_cs1=deshapriyakhatua; _ga_CDRWKZTDEX=GS1.1.1671596579.42.0.1671596579.0.0.0; NEW_PROBLEMLIST_PAGE=1; _dd_s=rum=1&id=013e3baf-e1cf-4f2b-94d9-41315dc02c60&created=1671601333952&expire=1671602233952";

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://leetcode.com/graphql/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                header.put("content-type","application/json");
                header.put("cookie",cookieStr);
                return header;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                return super.getBody();
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