package com.example.mahe.eecevents3106.Classes;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;

/*
 * Created by Mahe on 01-01-2018.
 */

@SuppressLint("Registered")
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String tok = FirebaseInstanceId.getInstance().getToken();
        Log.v(StringDeclarations.REG_TOKEN, tok);
    }
}
