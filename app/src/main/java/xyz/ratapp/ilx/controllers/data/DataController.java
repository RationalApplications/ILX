package xyz.ratapp.ilx.controllers.data;

import com.google.gson.JsonObject;


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
import xyz.ratapp.ilx.data.dao.Details;
import xyz.ratapp.ilx.data.dao.Request;
import xyz.ratapp.ilx.data.dao.User;
import xyz.ratapp.ilx.data.dao.UserLocation;
import xyz.ratapp.ilx.data.dao.Uuser;
import xyz.ratapp.ilx.data.retrofit.API;

import static xyz.ratapp.ilx.data.retrofit.API.BASE_URL;
import static xyz.ratapp.ilx.data.retrofit.API.USER_URL_MASK;

/**
 * Created by timtim on 14/08/2017.
 */

//TODO: в синглтон
public class DataController {

    private boolean state = false;
    private static DataController data;
    private final static Model model = new Model();
    private final static Map<Screens, List<Request>> screenRequestsMap;

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

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

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
                        String courierName = obj.get("courier_name").getAsString();
                        String ava = obj.get("ava_url").getAsString();
                        String preview = obj.get("ava_url_preview").getAsString();
                        String courierPhone = obj.get("courier_phone").getAsString();
                        String clientName = obj.get("client_name").getAsString();
                        String clientPhone = obj.get("client_phone").getAsString();
                        String authProperty = obj.get("auth_property").getAsString();
                        String authMd = obj.get("auth_md").getAsString();
                        String sessionId = obj.get("session_id").getAsString();
                        String cid = obj.get("cid").getAsString();
                        int wareHouseProperty = obj.get("warehouse_property").getAsInt();
                        int gps = obj.get("gps").getAsInt();
                        String workStatus = response.body().get("work_status").getAsString();
                        String courierType = response.body().get("courier_type").getAsString();

                        user = new Uuser(courierName, ava, preview, courierPhone, clientName,
                                clientPhone, authProperty, authMd, sessionId, cid,
                                wareHouseProperty, gps, workStatus, courierType);

                        controller.authSucceseed();
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

    public Uuser setState(boolean state) {
        user.setOnline(state);
        this.state = state;

        return user;
    }

    public void bindRequests(Screens screen,
                             ListSettable<Request> settable) {
        settable.setData(screenRequestsMap.get(screen));
    }

    public void bindUser(MainController controller) {
        state = user.isOnline();
        controller.setData(user);
    }

    public void bindDetails(InfoController infoController,
                            String id) {
        Request r = getRequest(id);

        if(r != null) {
            List<Details> details = r.getDetails();
            infoController.setData(details);
        }
    }


    public boolean getLastState() {
        return state;
    }

}
