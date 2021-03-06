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
import xyz.ratapp.ilx.data.dao.Names;
import xyz.ratapp.ilx.data.dao.Order;
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
    private List<Rerequest> history;
    private List<Order> recent;
    private String domainName;
    private Names names;
    private Uuser user;
    private int frequency;
    private String mdKey = null;
    private String param;


    private DataController() {
    }

    public static DataController getInstance() {
        if(data == null) {
            data = new DataController();
        }

        return data;
    }

    public void orderList(final MainController controller) {
        String sessionId = user.getSessionId();

        apiUser.orderList(sessionId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                int status = response.body().getAsJsonObject("response").
                        get("status").getAsInt();

                if(status == 1) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<Map<String, Order>>(){}.getType();
                    JsonObject orderList = response.body().getAsJsonObject("response").
                            getAsJsonObject("orders");
                    Map<String, Order> orderMap = gson.fromJson(orderList, type);
                    recent = new ArrayList<>();
                    recent.addAll(orderMap.values());

                    user.setOrders(recent);
                    controller.bindData(Screens.RECENT);

                    if(mdKey != null &&
                            user.getHistory() != null &&
                            model.getNewRequests() != null) {
                        sendNextByMdKey(controller);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("MyTag", t.toString());
            }
        });
    }

    public void orderListHistory(final MainController controller) {
        String sessionId = user.getSessionId();

        apiUser.orderListHistory(sessionId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.e("MyTag", response.message());
                int status = response.body().getAsJsonObject("response").
                        get("status").getAsInt();

                if(status == 1) {

                    Gson gson = new Gson();
                    Type type = new TypeToken<Map<String, Rerequest>>(){}.getType();
                    JsonObject orderList = response.body().getAsJsonObject("response").
                            getAsJsonObject("order_list_history");
                    Map<String, Rerequest> orderMap = gson.fromJson(orderList, type);
                    history = new ArrayList<>();
                    history.addAll(orderMap.values());

                    List<Request> newRequests = new ArrayList<>();
                    for (Rerequest r : history) {
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
                        req.setMdKey(r.getMdKey());

                        newRequests.add(req);
                    }

                    user.setHistory(newRequests);
                    controller.bindData(Screens.HISTORY);

                    if(mdKey != null &&
                            model.getNewRequests() != null &&
                            user.getOrders() != null) {
                        sendNextByMdKey(controller);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("MyTag", t.toString());
            }
        });
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
                        req.setMdKey(r.getMdKey());

                        newRequests.add(req);
                    }

                    model.setNewRequests(newRequests);
                    controller.bindData(Screens.STOCK);

                    if(mdKey != null &&
                            user.getHistory() != null &&
                            user.getOrders() != null) {
                        sendNextByMdKey(controller);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("MyTag", t.toString());
            }
        });

        //TODO: ВОЗМОЖНО ЭТО ДОЛЖНО БЫТЬ НЕ ТУТ (тут отправляется на сервер reg_id для FCM) Почему? Ответит Олег=D
        SharedPreferences prefs = controller.getContext().getSharedPreferences(
                PREFS, Context.MODE_PRIVATE);

        boolean isFirstStart = prefs.getBoolean("first_start", Boolean.parseBoolean("true"));

        if (isFirstStart) {
            sendFCMRegIdToServer(controller.getContext());

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first_start", Boolean.parseBoolean("false"));
            editor.apply();
        }
    }

    public void courierLocation(UserLocation location, Context context) {
        if(user != null) {
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
                    Log.e("MyTag", response.body().toString());
                    JsonObject obj = response.body().getAsJsonObject("response");
                    int status = obj.get("status").getAsInt();

                    if(status == 1) {

                        String domain = "https://" + domainName + "/";

                        String courierName = obj.get("courier_name").getAsString();
                        String ava = domain + obj.get("ava_url").getAsString();
                        String preview = domain + obj.get("ava_url_preview").getAsString();
                        String courierPhone = obj.get("courier_phone").getAsString();
                        String clientName = obj.get("client_name").getAsString();
                        String clientPhone = obj.get("client_phone").getAsString();
                        String authProperty = obj.get("auth_property").getAsString();
                        String authMd = obj.get("auth_md").getAsString();
                        String sessionId = obj.get("session_id").getAsString();
                        String cid = obj.get("cid").getAsString();
                        int wareHouseProperty = obj.get("warehouse_property").getAsInt();
                        String gpsTimeout = obj.get("gps_timeout").getAsString();
                        String workStatus = obj.get("work_status").getAsString();
                        String courierType = obj.get("courier_type").getAsString();
                        int gps = obj.get("gps").getAsInt();

                        frequency = Integer.parseInt(gpsTimeout);
                        //get names
                        Gson gson = new Gson();
                        Type type = new TypeToken<Names>(){}.getType();
                        JsonObject orderList = response.body().getAsJsonObject("response").
                                getAsJsonObject("elements_name");
                        names = gson.fromJson(orderList, type);

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

    public void sendMessage(String text, String lat,
                            String lng, String speed,
                            String acc, String time,
                            String mdKey) {
        String sessionId = user.getSessionId();

        apiUser.sendMessage(sessionId, text, lat, lng,
                speed, acc, time, mdKey).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("MyTag", response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void sendNextByMdKey(String mdKey, String param) {
        this.mdKey = mdKey;
        this.param = param;
    }

    private void sendNextByMdKey(MainController controller) {
        Object data = getDataByMdKey(mdKey);
        String id = idOf(data);
        Screens screen = id.startsWith("r") ?
                Screens.RECENT : Screens.HISTORY;
        screen = id.startsWith("h") ?
                screen : Screens.STOCK;

        controller.next(screen, data, param);
    }

    private Object getDataByMdKey(String mdKey) {
        List<Request> stock = model.getNewRequests();
        List<Request> history = user.getHistory();
        List<Order> recent = user.getOrders();

        for (Request r : stock) {
            if(r.getMdKey().equals(mdKey)) {
                return r;
            }
        }

        for (Request r : history) {
            if(r.getMdKey().equals(mdKey)) {
                return r;
            }
        }

        for (Order o : recent) {
            if(o.getMdKey().equals(mdKey)) {
                return o;
            }
        }

        return null;
    }

    public String idOf(Object data, Screens screen) {
        String id = "-1";

        if(screen.equals(Screens.STOCK)) {
            //s mean stock
            id = "s" + model.getNewRequests().indexOf(data);
        }
        else if(screen.equals(Screens.RECENT)) {
            //r mean recent
            id = "r" + user.getOrders().indexOf(data);
        }
        else if(screen.equals(Screens.HISTORY)) {
            //h mean history
            id = "h" + user.getHistory().indexOf(data);
        }

        return id;
    }

    private String idOf(Object data) {
        String id = "-1";

        List<Request> stock = model.getNewRequests();
        List<Request> history = user.getHistory();
        List<Order> recent = user.getOrders();

        for (Request r : stock) {
            if(r.getMdKey().equals(mdKey)) {
                id = "s" + stock.indexOf(r);

                return id;
            }
        }

        for (Request r : history) {
            if(r.getMdKey().equals(mdKey)) {
                id = "h" + history.indexOf(r);

                return id;
            }
        }

        for (Order o : recent) {
            if(o.getMdKey().equals(mdKey)) {
                id = "r" + recent.indexOf(o);

                return id;
            }
        }

        return id;
    }

    private Object getRequest(String id) {
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
            return user.getOrders().get(n);
        }
        if(id.startsWith("h")) {
            //history
            return user.getHistory().get(n);
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

    //OLEG
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

    public Names getNames() {
        return names;
    }

    public void bindRequests(Screens screen,
                             //TODO: АААААА, КОСТЫЛЬ, ЕБАТЬ МЕНЯ В РОТ
                             ListSettable settable) {
        if(screen.equals(Screens.STOCK)) {
            settable.setData(model.getNewRequests());
        }
        else if(screen.equals(Screens.RECENT)) {
            settable.setData(user.getOrders());
        }
        else {
            settable.setData(user.getHistory());
        }
    }

    public void bindUser(MainController controller) {
        state = user.isOnline();
        controller.setData(user);
    }

    public void bindInfoData(InfoController infoController,
                             String id) {
        Object obj = getRequest(id);

        if(obj != null) {
            infoController.setData(obj);
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

    public void exit(MainController controller) {
        setState(controller, false);

        Context context = controller.getContext();
        SharedPreferences prefs = context.getSharedPreferences(
                PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("session_id", "");
        editor.putString("domain_name", "");
        editor.apply();
    }

    private void savePrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("session_id", user.getSessionId());
        editor.putString("domain_name", domainName);
        editor.apply();
    }

    public void registerFCM(String regId, Context context) {

        SharedPreferences prefs = context.getSharedPreferences(
                PREFS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("reg_fcm_id", regId);
        editor.apply();

        if(apiUser != null) {
            sendFCMRegIdToServer(context);
        }

    }

    public void sendFCMRegIdToServer(Context context){
        SharedPreferences prefs = context.getSharedPreferences(
                PREFS, Context.MODE_PRIVATE);

        String regId = prefs.getString("reg_fcm_id", "");

        Log.e("MyTag", "sended: " + regId);
        apiUser.registerFCM(user.getSessionId(), regId).
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
    }

    public int getFrequency() {
        return frequency;
    }
}

//for commit