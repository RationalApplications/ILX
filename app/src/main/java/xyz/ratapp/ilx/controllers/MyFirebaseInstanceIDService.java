package xyz.ratapp.ilx.controllers;

import android.app.Service;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import xyz.ratapp.ilx.controllers.data.DataController;

/**
 * Created by Олег on 20.08.2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("MyTag", "Refreshed token: " + refreshedToken);

        String regId = FirebaseInstanceId.getInstance().getToken();
        Log.e("MyTag", regId);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        DataController controller = DataController.getInstance();
        controller.registerFCM(regId, getApplicationContext());

        //TODO:sendRegistrationToServer(refreshedToken);
    }


}
