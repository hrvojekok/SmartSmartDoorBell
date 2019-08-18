package com.example.smartdoorbell;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.support.constraint.Constraints.TAG;

public class FirebaseInstanceService extends FirebaseInstanceIdService {

    private static final String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {

        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN, recentToken);
        Toast.makeText(getApplicationContext(),recentToken, Toast.LENGTH_LONG);


    }
}
