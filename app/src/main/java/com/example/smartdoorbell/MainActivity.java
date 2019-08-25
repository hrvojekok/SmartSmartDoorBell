package com.example.smartdoorbell;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    Button button;
    String noviUrl;
    TextView textView;
    String oldUri = "";
    Bitmap bitmapOld = null;

    private NotificationManagerCompat notificationManagerCompat;
    private Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //String path = "https://firebasestorage.googleapis.com/v0/b/smartdoorbell-9b89e.appspot.com/o/87849.jpg?alt=media&token=a0affe45-a2fe-49d4-940d-85622f86f8ad";
        //Uri path = Uri.parse("https://firebasestorage.googleapis.com/v0/b/iot-project-249706.appspot.com/o/image3.jpg?alt=media&token=0a0dd2f3-86c6-4615-9e81-a4dd0ed9c8a7");

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

//
//        Glide.with(getApplicationContext())
//                .load(path)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        createNotification();
//                        Toast.makeText(getApplicationContext(), "download", Toast.LENGTH_LONG).show();
//                        return false;
//                    }
//                })
//                .into(imageView);

//        Bitmap bitmap = null;
//        try {
//            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(path));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        //Log.d("bitmap", bitmap.toString());

//        if (!(bitmap == null)) {
//            Glide.with(getApplicationContext())
//                    .load(bitmap)
//                    .into(imageView);
//
//
//            Log.d("notification", "afterImage");
//            createNotification();
//
//        } else {
//            Log.d("notification", "else");
//
//        }
//        FutureTarget<File> futureTarget = Glide.with(getApplicationContext())
//                .load(path)
//                .downloadOnly(500,500);
//        File cacheFile= new File("");
//        try {
//             cacheFile = futureTarget.get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if(cacheFile == null){
//            Log.d("empty", "empty");
//        } else {
//            Glide.with(getApplicationContext())
//                    .load(cacheFile)
//                    .into(imageView);
//            Log.d("fulll", "fulll");
//        }

//        Glide.with(getApplicationContext())
//                .load(bitmap)
//                .into(imageView);
        //.onLoadFailed(getDrawable(R.drawable.ic_default));

        //Bitmap bitmap = BitmapFactory.decodeFile(path);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        FirebaseApp.initializeApp(this);
        Log.d("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());

        String registrationToken = "cRlyP5Bkyb8:APA91bHNbMaNTLliDl1-BUCmpFw5iPFVhswO0O1jBJ0Gtlb-PvGhsUIeFJ2XVQu9CAdin_QuxLmVskitCIe0q-yw1S9G6CdKX9JBaPydYxICDAClrt96hBPbVKTxIoo_bxFJJQ2odk3Z";

        // See documentation on defining a message payload.


        //textView.setText(FirebaseInstanceId.getInstance().getToken());


        getPicture();
        //startRepeating();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNotification();
            }
        });
    }


    public void getPicture() {

        final StorageReference pictureReference = FirebaseStorage.getInstance().getReference("image3.jpg");

        pictureReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

            Log.d("imageDownloadedUrl", uri.toString());
            noviUrl = uri.toString();
            Glide.with(getApplicationContext())
                    .load(noviUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            //createNotification();
                            Toast.makeText(getApplicationContext(), "download", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    })
                    .into(imageView);
            }
        });
    }

    public void createNotification() {

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
        android.app.Notification notification9 = new NotificationCompat.Builder(this, Notification.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_person)
                .setContentTitle("Smart Door Bell")
                .setContentText("Someone is at the doooor!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification9);
    }


}
