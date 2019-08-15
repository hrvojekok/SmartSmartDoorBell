package com.example.smartdoorbell;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    Button button;

    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //String path = "https://firebasestorage.googleapis.com/v0/b/smartdoorbell-9b89e.appspot.com/o/87849.jpg?alt=media&token=a0affe45-a2fe-49d4-940d-85622f86f8ad";
        String path = "https://firebasestorage.googleapis.com/v0/b/iot-project-249706.appspot.com/o/image3.jpg?alt=media&token=0a0dd2f3-86c6-4615-9e81-a4dd0ed9c8a7";

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        Glide.with(getApplicationContext())
                .load(path)
                .into(imageView);


        notificationManagerCompat = NotificationManagerCompat.from(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNotification();
            }
        });
    }


    public void createNotification(){

       //android bellow 9
//        Intent intent = new Intent();
//        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,0);
//
//        Notification notification = new Notification.Builder(getApplicationContext())
//                .setSmallIcon(R.drawable.ic_person)
//                .setContentTitle("Smart Door Bell")
//                .setContentText("Someone is at the door!")
//                .setContentIntent(pendingIntent).getNotification();
//
//        notificationManagerCompat.notify(1, notification);



        //for 9.0 ->
        Notification notification9 = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_person)
                .setContentTitle("Smart Door Bell")
                .setContentText("Someone is at the door!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification9);
    }

}
