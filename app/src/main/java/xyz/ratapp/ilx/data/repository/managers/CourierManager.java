package xyz.ratapp.ilx.data.repository.managers;

import android.os.AsyncTask;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.data.dao.app.Button;
import xyz.ratapp.ilx.data.dao.users.Courier;
import xyz.ratapp.ilx.data.dao.users.CourierLocation;
import xyz.ratapp.ilx.data.repository.callbacks.StateChangeCallback;
import xyz.ratapp.ilx.data.retrofit.API;

import static xyz.ratapp.ilx.data.retrofit.API.USER_URL_MASK;

/**
 * Created by timtim on 06/09/2017.
 */

class CourierManager {

    private static volatile CourierManager instance;
    private ManageableRepository repo;
    private String sessionId;
    private API apiUser;

    private CourierManager() {

    }

    static CourierManager getInstance(ManageableRepository repo,
                                      String sessionId) {
        CourierManager localInstance = instance;

        if (localInstance == null) {
            synchronized (CourierManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance =
                            new CourierManager();
                }
                instance.repo = repo;
                instance.apiUser = repo.getApiUser();
                instance.sessionId = sessionId;
            }
        }

        return localInstance;
    }

    void courierLocation(CourierLocation location) {

        String lat = location.getLatitude();
        String lng = location.getLongitude();
        String speed = location.getSpeed();
        String acc = location.getAcc();
        String time = location.getTime();

        apiUser.courierLocation(sessionId, lat, lng, speed, acc, time).
                enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        int status = response.body().getAsJsonObject("response").
                                get("status").getAsInt();

                        if(status == 0) {
                            //TODO: должны ли мы это обрабатывать???
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        //log here
                    }
                });
    }

    void sendMessage(final BinderCallback callback,
                     String text, String lat,
                     String lng, String speed,
                     String acc, String time,
                     String mdKey) {
        apiUser.sendMessage(sessionId, text, lat, lng,
                speed, acc, time, mdKey).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                int status = response.body().getAsJsonObject("response").
                        get("status").getAsInt();

                if(status == 0) {
                    callback.fail(R.string.connection_error);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.fail(R.string.connection_error);
            }
        });
    }

    public void setState(StateChangeCallback callback, boolean state) {
        apiUser.workStatusUpdate(sessionId, state ? "1" : "0").
                enqueue(callback);
    }

    public void onPushButton(Button btn,
                             String domainName) {
        if(btn != null && btn.getUrl() != null) {
            new PushButtonTask().doInBackground(domainName, btn.getUrl());
        }
    }

    private final static class PushButtonTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                String domainName = strings[0];
                String urlString = strings[1];

                String path = String.format(USER_URL_MASK, domainName) +
                        urlString;
                URL url = new URL(path);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } catch (Exception e) {
                //TODO: log here
            }

            return null;
        }
    }
}
