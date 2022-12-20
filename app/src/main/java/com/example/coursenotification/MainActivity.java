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
        String cookieStr = "remember_web_59ba36addc2b2f9401580f014c7f58ea4e30989d=eyJpdiI6IjBUbWNyYmdVMVBCOGFVb2VTaUx4YlE9PSIsInZhbHVlIjoiQUhoK3ZwM0xoT21KU3pST2twVTEwZkZhVU56UEhpd2QxMWVnY3dBTkd1SmtKYVJsaFBLS2pQVmJTaWYwZE5Nd1JDZXJIT2NBQ2ZNNTlxdEdUaVV6QnV6TzdyUVpXREYzbmd0a0tOUUxHVlBpZHMyMEpsemRIcnorTy81cXcwbUZMYml2ZzNXa2YwZG16KzdxUithZEYxVmFXQnVzZzFRaG5Kbm4yOWdacG1XV0dwaGx0SEswbDAvOWF3OWc3Z0NRS0lnTzBRNmFuR2NYWGVHVDFMQm9SY1pad2RNMVJPWk1RVU1pL2lrM2VWMD0iLCJtYWMiOiI4YTM3MjUwMjIyY2I1NTlhY2IzNDI5ZTE4YmNmZDQ0MzlmYTViYThlZDE4Yjg4ZWM3MmQzZGU5MGNkZGI3OWY2IiwidGFnIjoiIn0%3D; _ga=GA1.1.823814017.1669915138; oj-production-session_token=thntgassih2tqtcgy5lpy6bv05drt4by; _ga_EGC300HF7J=GS1.1.1671098152.2.1.1671098196.0.0.0; XSRF-TOKEN=eyJpdiI6Ik9yclcyNmd6QUE4UVF1czZYMWtpOWc9PSIsInZhbHVlIjoiK25SejE0akp3NzhqRzdlcCtaKzVpNlg1ckdYaXd6RmRxM2hxeGVDVTlKOGhjZzQ4TDlEMFR4OWpNTWtjSlRJR3UyVkNqb2NHcGVlRnBoSHNvM21NWHlQazQwNHdQZThkR2o2aVlFNDM4R1RlbTNNTEZIRXhQbVk3a1NCZDRrb0YiLCJtYWMiOiJiNTZmYjM1YzE1YWRiMDEzMjA4ZTQ5YjAwOTkxZDE1NjFkODgwMDU3MjNmZTExMTViMTY3NTBlNmEzZjE0ZDNjIiwidGFnIjoiIn0%3D; masai_school_course_session=eyJpdiI6IjROMEFBaTBteFhOU1hFeGNFVVNTR0E9PSIsInZhbHVlIjoiZk1mYkdrdG5VRG8xZVgyRlRHMjNMeWZKTXRRUDZsMmZtVEM5SndTNTZFSWc0dmk3eG1ZOW5yM1grRDZZOWRqZzhJekRrV0ZVQTM5QVI0OVp4TUhpaVBibiswV2tDUlE4bFpMUktXa09LUTQ1ZWFDZnI1Zm5RNFpDMHE0YklkcnQiLCJtYWMiOiI3NzgwZWE0ZTRjM2NhNmM4ODM0ZDc1NzllMWY0ZGQ3ZDUyZGY5YjMzYjU5NTg2NmQzZWY1NWZmYjY2YWYxMTBjIiwidGFnIjoiIn0%3D";

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://course.masaischool.com/dashboard/";

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