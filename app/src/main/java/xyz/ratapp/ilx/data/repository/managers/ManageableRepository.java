package xyz.ratapp.ilx.data.repository.managers;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.OrdersBinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.TradeBinderCallback;
import xyz.ratapp.ilx.data.dao.app.Button;
import xyz.ratapp.ilx.data.dao.users.CourierLocation;
import xyz.ratapp.ilx.data.repository.callbacks.AuthCallback;
import xyz.ratapp.ilx.data.repository.callbacks.HistoryListCallback;
import xyz.ratapp.ilx.data.repository.callbacks.OrderListCallback;
import xyz.ratapp.ilx.data.repository.callbacks.StateChangeCallback;
import xyz.ratapp.ilx.data.repository.callbacks.TradeListCallback;
import xyz.ratapp.ilx.data.retrofit.API;

import static xyz.ratapp.ilx.data.retrofit.API.USER_URL_MASK;

/**
 * Created by timtim on 08/09/2017.
 */

public class ManageableRepository {

    private API apiUser;
    protected String domainName;
    protected String sessionId;
    private final boolean firstRun;
    //Managers
    private AuthManager auth;
    private CourierManager courier;
    private FcmManager message;
    private OrdersManager orders;
    private PreferencesManager prefs; //TODO: singleton?


    public ManageableRepository(Context context) {
        prefs = new PreferencesManager();
        firstRun = prefs.isFirstStart(context);

        if(domainName == null ||
                sessionId == null) {
            //prefs have not null strings,
            //because firstStart in controller run before
            domainName = prefs.getDomainName();
            sessionId = prefs.getSessionId();
        }

        orders = OrdersManager.getInstance(this, sessionId);
        courier = CourierManager.getInstance(this, sessionId);
        auth = AuthManager.getInstance(this);
    }

    API getApiUser() {
        if(apiUser == null) {
            Retrofit retrofitUser = new Retrofit.Builder().
                    addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(String.format(USER_URL_MASK, domainName))
                    .build();
            apiUser = retrofitUser.create(API.class);
        }

        return apiUser;
    }

    public boolean isFirstStart() {
        return firstRun;
    }

    public void auth(AuthCallback callback) {
        auth.auth(callback, sessionId);
    }

    public void auth(AuthCallback callback, String code) {
        auth.authAccessCode(callback, code);
    }

    public void loadAllData(TradeListCallback trade,
                            OrderListCallback order,
                            HistoryListCallback history) {
        orders.orderListTrading(trade);
        orders.orderList(order);
        orders.orderListHistory(history);
    }

    public void loadOrderListTrading(TradeBinderCallback callback) {

    }

    public void loadOrderList(OrdersBinderCallback callback) {

    }

    public void loadOrderListHistory(HistoryListCallback callback) {

    }

    public void setWorkStatus(StateChangeCallback callback, boolean status) {
        courier.setState(callback, status);
    }


    public void sendCourierLocation(CourierLocation location) {
        courier.courierLocation(location);
    }

    public void sendMessage(BinderCallback callback,
                            String text, String lat,
                            String lng, String speed,
                            String acc, String time,
                            String mdKey) {
        courier.sendMessage(callback, text, lat,
                lng, speed, acc, time, mdKey);
    }

    public void pushButton(Button button) {
        courier.onPushButton(button, domainName);
    }

    public void savePrefs(Context context) {
        prefs.savePrefs(context, sessionId, domainName);
    }
}
