package xyz.ratapp.ilx.controllers.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.ratapp.ilx.controllers.Screens;
import xyz.ratapp.ilx.controllers.info.InfoController;
import xyz.ratapp.ilx.controllers.interfaces.ListSettable;
import xyz.ratapp.ilx.controllers.launch.LaunchController;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.data.Model;
import xyz.ratapp.ilx.data.dao.Button;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.data.dao.Rerequest;
import xyz.ratapp.ilx.data.dao.UserLocation;
import xyz.ratapp.ilx.data.dao.Uuser;
import xyz.ratapp.ilx.data.retrofit.API;

import static xyz.ratapp.ilx.data.retrofit.API.BASE_URL;
import static xyz.ratapp.ilx.data.retrofit.API.USER_URL_MASK;

/**
 * Created by timtim on 14/08/2017.
 */

public class DataController {

    private boolean state = false;
    private static DataController data;
    private final static Model model = new Model();
    private final static Map<Screens, List<Request>> screenRequestsMap;
    private final static String PREFS = "ilx.preferences";

    static {
        Map<Screens, List<Request>> tmp = new HashMap<>();
        tmp.put(Screens.RECENT, model.getUser().getCurrentRequests());
        tmp.put(Screens.STOCK, model.getNewRequests());
        tmp.put(Screens.HISTORY, model.getUser().getHistoryOfRequests());

        screenRequestsMap = Collections.unmodifiableMap(tmp);
    }

    private Retrofit retrofitBase = new Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build(), retrofitUser;
    private API apiBase = retrofitBase.create(API.class),
            apiUser;
    private List<Rerequest> stock;
    private String domainName;
    private Uuser user;


    private DataController() {
    }

    public static DataController getInstance() {
        if(data == null) {
            data = new DataController();
        }

        return data;
    }

    public void orderListTrading(final MainController controller) {
        String sessionId = user.getSessionId();

        apiUser.orderListTrading(sessionId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.e("MyTag", response.message());
                int status = response.body().getAsJsonObject("response").
                        get("status").getAsInt();

                if(status == 1) {

                    Gson gson = new Gson();
                    Type type = new TypeToken<Map<String, Rerequest>>(){}.getType();
                    JsonObject orderList = response.body().getAsJsonObject("response").
                            getAsJsonObject("order_list");
                    Map<String, Rerequest> orderMap = gson.fromJson(orderList, type);
                    stock = new ArrayList<>();
                    stock.addAll(orderMap.values());

                    List<Request> newRequests = new ArrayList<>();
                    for (Rerequest r : stock) {
                        String diff = r.getDifficult();
                        Request req;
                        if(diff.isEmpty()) {

                            req = new Request(r.getH2(), r.getH3(), r.getH1(),
                                    r.getImage(), new ArrayList<>(r.getComments().values()),
                                    new ArrayList<>(r.getAddress().values()),
                                    r.getBtn());
                        }
                        else {
                            req = new Request(r.getH2(), r.getH3(), r.getH1(),
                                    Color.parseColor(r.getDifficult()),
                                    r.getImage(), new ArrayList<>(r.getComments().values()),
                                    new ArrayList<>(r.getAddress().values()),
                                    r.getBtn());
                        }

                        newRequests.add(req);
                    }

                    model.setNewRequests(newRequests);
                    controller.bindData();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("MyTag", t.toString());
            }
        });
    }

    public void courierLocation(UserLocation location) {
        String sessionId = user.getSessionId();
        String lat = location.getLatitude();
        String lng = location.getLongitude();
        String speed = location.getSpeed();
        String acc = location.getAcc();
        String time = location.getTime();

        apiUser.courierLocation(sessionId, lat, lng, speed, acc, time).
                enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("MyTag", response.message());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("MyTag", t.toString());
            }
        });
    }

    public void authAccessCode(final LaunchController controller, String code) {
        apiBase.authAccessCode(code).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonObject obj = response.body().getAsJsonObject("response");
                    int status = obj.get("status").getAsInt();

                    if(status == 1) {
                        domainName = obj.get("domain_name").getAsString();
                        String sessionId = obj.get("session_id").getAsString();

                        DataController.this.auth(controller, sessionId);
                    }
                    else {
                        //TODO: hardcoded
                        controller.authFailed("Ошибка соединения!");
                    }
                }
                catch (Exception e) {
                    //TODO: hardcoded
                    controller.authFailed("Ошибка соединения!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: hardcoded
                controller.authFailed("Ошибка соединения!");
            }
        });
    }

    public void auth(final LaunchController controller,
                     String sessionId) {
        retrofitUser = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl(String.format(USER_URL_MASK, domainName))
                .build();
        apiUser = retrofitUser.create(API.class);

        apiUser.auth(sessionId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonObject obj = response.body().getAsJsonObject("response");
                    int status = obj.get("status").getAsInt();

                    if(status == 1) {

                        String ava = obj.get("ava_url").getAsString();
                        String preview = obj.get("ava_url_preview").getAsString();
                        String clientName = obj.get("client_name").getAsString();
                        String clientPhone = obj.get("client_phone").getAsString();
                        String authProperty = obj.get("auth_property").getAsString();
                        String sessionId = obj.get("session_id").getAsString();
                        int gps = obj.get("gps").getAsInt();
                        String workStatus = obj.get("work_status").getAsString();
                        String gpsTimeout = obj.get("gps_timeout").getAsString();
                        String cid = obj.get("cid").getAsString();
                        int wareHouseProperty = obj.get("warehouse_property").getAsInt();
                        String courierName = obj.get("courier_name").getAsString();
                        String courierType = obj.get("courier_type").getAsString();

                        user = new Uuser(ava, preview, clientName, clientPhone,
                                authProperty, sessionId, gps, workStatus, gpsTimeout,
                                cid, wareHouseProperty, courierName, courierType);

                        savePrefs(controller.getContext());
                        controller.next();
                    }
                    else {
                        //TODO: hardcoded
                        String error = obj.get("errormessage").getAsString();
                        controller.authFailed(error);
                    }
                }
                catch (Exception e) {
                    //TODO: hardcoded
                    controller.authFailed("Ошибка соединения!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //TODO: hardcoded
                controller.authFailed("Ошибка соединения!");
            }
        });
    }

    public String idOf(Request r, Screens screen) {
        String id = "-1";

        if(screen.equals(Screens.STOCK)) {
            //s mean stock
            id = "s" + model.getNewRequests().indexOf(r);
        }
        else if(screen.equals(Screens.RECENT)) {
            //r mean recent
            id = "r" + model.getUser().getCurrentRequests().indexOf(r);
        }
        else if(screen.equals(Screens.HISTORY)) {
            //h mean history
            id = "h" + model.getUser().getHistoryOfRequests().indexOf(r);
        }

        return id;
    }

    private Request getRequest(String id) {
        if(id.equals("-1")) {
            return null;
        }

        int n = Integer.parseInt(id.substring(1));
        if(id.startsWith("s")) {
            //stock
            return model.getNewRequests().get(n);
        }
        if(id.startsWith("r")) {
            //recent
            return model.getUser().getCurrentRequests().get(n);
        }
        if(id.startsWith("h")) {
            //history
            return model.getUser().getHistoryOfRequests().get(n);
        }

        return null;
    }

    public Uuser setState(final MainController controller, final boolean state) {
        apiUser.workStatusUpdate(user.getSessionId(), state ? "1" : "0").
                enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("MyTag", response.message());
                user.setOnline(state);
                DataController.this.state = state;
                controller.bindUser(user);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("MyTag", t.toString());
            }
        });

        return user;
    }

    public void onPushButton(final Button btn) {
        if(btn != null && btn.getUrl() != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String path = String.format(USER_URL_MASK, domainName) +
                                btn.getUrl();
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
                        Log.e("MyTag", e.toString());
                    }
                }
            }).start();
        }
    }

    public void bindRequests(Screens screen,
                             ListSettable<Request> settable) {
        if(screen.equals(Screens.STOCK)) {
            settable.setData(model.getNewRequests());
        }
        else {
            settable.setData(screenRequestsMap.get(screen));
        }
    }

    public void bindUser(MainController controller) {
        state = user.isOnline();
        controller.setData(user);
    }

    public void bindDetails(InfoController infoController,
                            String id) {
        Request r = getRequest(id);

        if(r != null && r.getDetails() != null) {
            List<String> details = r.getDetails();
            infoController.setData(details);
        }
    }

    public void bindReqInfo(InfoController infoController,
                            String id) {
        Request r = getRequest(id);

        if(r != null) {
            infoController.setData(r);
        }
    }


    public boolean getLastState() {
        return state;
    }

    public boolean getPrefs(LaunchController controller) {
        Context context = controller.getContext();
        SharedPreferences prefs = context.getSharedPreferences(
                PREFS, Context.MODE_PRIVATE);
        String sessionId = prefs.getString("session_id", "");
        domainName = prefs.getString("domain_name", "");

        if(sessionId.isEmpty() ||
                domainName.isEmpty()) {
            return true;
        }

        auth(controller, sessionId);
        return false;
    }

    private void savePrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("session_id", user.getSessionId());
        editor.putString("domain_name", domainName);
        editor.apply();
    }
}
