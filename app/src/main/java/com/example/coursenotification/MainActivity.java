package com.example.coursenotification;



import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkRequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    Button start;
    Button stop;
    EditText editText;
    Constraints constraints;
    private WorkRequest uploadWorkRequest;
    public static String cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.button);
        stop = findViewById(R.id.button2);
        editText = findViewById(R.id.editTextTextPersonName);

        cookie = editText.getText().toString();

        // constraints
        constraints  = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        // Work request
        uploadWorkRequest =
                new PeriodicWorkRequest.Builder(WorkManager.class,15, TimeUnit.MINUTES)
                        .addTag("tag for periodic Request")
//                        .setConstraints(constraints)
                        .build();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //startService(new Intent(MainActivity.this,NetworkRequestService.class));
                Log.d("buttonPressed", "onClick: ");
                androidx.work.WorkManager.getInstance(getApplicationContext())
                        .enqueue(uploadWorkRequest);

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopService(new Intent(MainActivity.this,NetworkRequestService.class));
                androidx.work.WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("tag for periodic Request");
            }
        });
    }
    public static String getCookie(){
        return cookie;
    }
}