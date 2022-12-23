package com.example.coursenotification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
        String cookieStr = "_ga=GA1.1.324437731.1671815920; _ga_QPY5B063JV=GS1.1.1671815919.1.1.1671816271.0.0.0; XSRF-TOKEN=eyJpdiI6InNVRWhCYm1FQjhpRlFnZmxqNzhxbnc9PSIsInZhbHVlIjoiQkduaENTai9leWFUcllBM21NZnpDckJGM1pHZGVpN0I3T0FCYVkzczMvVzRTK1hLQ2VNMU9TWkk0UG01d25CTHV6RmxOUmF5aDdibEU3b3ZmUUdlSi9sNHVtMDN1d2tTY3B2VFErdFcrM2xWNFVEMFU1WFhuaHRGNmo1SDdtTlEiLCJtYWMiOiIxY2VmZWY4MGRjOGZiYzUwNDViZmE5MTllZjE4YjRjYTk3MTQ5MmJjNmNjMDczMzkyMDlkMjBlZTRjMGY3NmVlIiwidGFnIjoiIn0%3D; masai_school_course_session=eyJpdiI6IjIyQ0xWNjY3ZXZXUS9vcjhTY0lVbXc9PSIsInZhbHVlIjoiU0pmVzcwaUp6VXNCelJrTWJGTmlRN1JuL0lCS0FjOWhBb0tQcVpxVEFic3BOajlXWUV6UUxtOHZFUVVuRGF5d1k1TjFrVmdnMlJrbjh6Z1RZNXZyUm94TldyMUJUenlzSjUvdlkrRS9nOUdiTnh0NVZyZldYbGlpVTFoNzZrc2QiLCJtYWMiOiIyNmI5MDAyMTg0Mjc5MTQ1OTVlZmZlMzAzYTA0ZGQ1ZmYwNjBkOTQxZGJkZWUzMzI3MzFlM2QwNGEwN2I0OWE1IiwidGFnIjoiIn0%3D";
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://course.masaischool.com/dashboard";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(MainActivity.this, "Response is: " + response, Toast.LENGTH_SHORT).show();
                        Log.d("response",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header = new HashMap<>();
                header.put("Host","<calculated when request is sent>");
                header.put("cookie",cookieStr);
                header.put("Postman-Token","<calculated when request is sent>");
                return header;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        // Add the request to the RequestQueue.



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue.add(stringRequest);
                //notificationManager.notify(NOTIFICATION_ID,notification);
            }
        });
    }
}