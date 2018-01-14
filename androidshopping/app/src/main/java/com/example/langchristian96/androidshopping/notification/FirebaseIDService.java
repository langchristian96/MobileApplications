package com.example.langchristian96.androidshopping.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by langchristian96 on 1/13/2018.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG="MyFirebaseInsIDService";

    @Override
    public void onTokenRefresh() {
        //Get the updated token
        String refreshedTocken= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"New Token: "+refreshedTocken);
    }
}