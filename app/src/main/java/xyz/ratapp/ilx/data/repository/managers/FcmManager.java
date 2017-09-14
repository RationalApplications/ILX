package xyz.ratapp.ilx.data.repository.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by timtim on 07/09/2017.
 */

class FcmManager {

    /*void registerFCM(String regId, Context context) {

        SharedPreferences prefs = context.getSharedPreferences(
                PREFS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("reg_fcm_id", regId);
        editor.apply();

        if(apiUser != null) {
            sendFCMRegIdToServer(context);
        }

    }

    void sendFCMRegIdToServer(Context context){
        SharedPreferences prefs = context.getSharedPreferences(
                PREFS, Context.MODE_PRIVATE);

        String regId = prefs.getString("reg_fcm_id", "");

        Log.e("MyTag", "sended: " + regId);
        apiUser.registerFCM(courier.getSessionId(), regId).
                enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.e("MyTag", response.body().toString()  + " FCM ID sended to " + call.request().url().toString());
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("MyTag", t.toString() + " (FCM ID didn't send)");
                    }
                });
    }*/
}
