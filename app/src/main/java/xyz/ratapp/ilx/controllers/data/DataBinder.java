package xyz.ratapp.ilx.controllers.data;

import android.content.Context;

import java.util.List;

import xyz.ratapp.ilx.controllers.data.callbacks.AuthBinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.DataBinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.HistoryBinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.OrdersBinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.StatusChangeBinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.TradeBinderCallback;
import xyz.ratapp.ilx.controllers.launch.LaunchController;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.controllers.routing.Screens;
import xyz.ratapp.ilx.controllers.interfaces.DataSettable;
import xyz.ratapp.ilx.controllers.interfaces.ListSettable;
import xyz.ratapp.ilx.data.dao.app.AppStrings;
import xyz.ratapp.ilx.data.dao.app.Button;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;
import xyz.ratapp.ilx.data.dao.users.Courier;
import xyz.ratapp.ilx.data.repository.DataRepository;
import xyz.ratapp.ilx.data.repository.callbacks.HistoryListCallback;

/**
 * Created by timtim on 14/08/2017.
 *
 * Class that can
 */

public class DataBinder {

    //instanse
    private static volatile DataBinder instance;
    //data
    private Courier courier;
    private List<BaseOrder> trade;
    private BaseOrder nextOrder = null;
    private DataRepository repo;
    private AppStrings strings;


    private DataBinder() {

    }

    public static DataBinder getInstance(Context context) {
        DataBinder localInstance = instance;

        if (localInstance == null) {
            synchronized (DataBinder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance =
                            new DataBinder();
                    instance.repo = new DataRepository(context);
                }
            }
        }

        return localInstance;
    }

    public void bindOrders(Screens screen,
                           ListSettable<BaseOrder> settable) {
        if(screen.equals(Screens.STOCK)) {
            settable.setData(trade);
        }
        else if(screen.equals(Screens.RECENT)) {
            settable.setData(courier.getRecent());
        }
        else if(screen.equals(Screens.HISTORY)) {
            settable.setData(courier.getHistory());
        }
    }

    public void bindCourier(DataSettable<Courier> settable) {
        if(courier != null) {
            settable.setData(courier);
        }
    }

    public void bindInfoData(DataSettable<BaseOrder> settable) {
        if(nextOrder != null) {
            settable.setData(nextOrder);
        }
    }

    public void bindWorkStatus(DataSettable<Boolean> settable) {
        if(courier != null) {
            settable.setData(courier.getWorkStatus());
        }
    }

    public void bindFirstStart(DataSettable<Boolean> settable,
                               Context context) {
        //call to data repo
    }

    public void setupNextOrder(BaseOrder order) {
        this.nextOrder = order;
    }

    public void exit() {
        repo.exit();
    }

    public AppStrings getStrings() {
        return strings;
    }

    public boolean isFirstStart() {
        return repo.isFirstStart();
    }

    public void auth(LaunchController controller, String code) {
        BinderCallback authCallback =
                new AuthBinderCallback(this, controller);
        repo.auth(authCallback, code);
    }

    public void auth(LaunchController controller) {
        BinderCallback authCallback =
                new AuthBinderCallback(this, controller);
        repo.auth(authCallback);
    }

    public void loadAllData(MainController controller) {
        BinderCallback dataCallback =
                new DataBinderCallback(this, controller);
        repo.loadAllData(dataCallback);
    }

    public void pushButton(Button btn) {
        repo.pushButton(btn);
    }

    public void sendMessage(String text, String lat,
                            String lng, String speed,
                            String acc, String time,
                            String mdKey) {
        /*BinderCallback msgCallback = new BinderCallback();
        repo.sendMessage(msgCallback, text, lat,
                lng, speed, acc, time, mdKey);*/
    }

    public void setStrings(AppStrings appStrings) {
        this.strings = appStrings;
    }

    public void setData(Courier courier, List<BaseOrder> trade) {
        this.courier = courier;
        this.trade = trade;
    }

    public void setState(MainController controller, boolean state) {
        StatusChangeBinderCallback callback =
                new StatusChangeBinderCallback(this, controller);
        repo.setWorkStatus(callback, state);
    }

    public void loadOrderListHistory(MainController controller) {
        HistoryBinderCallback callback = new HistoryBinderCallback(this, controller);
        repo.loadOrderListHistory(callback);
    }

    public void loadOrderList(MainController controller) {
        OrdersBinderCallback callback = new OrdersBinderCallback(this, controller);
        repo.loadOrderList(callback);
    }

    public void loadOrderListTrading(MainController controller) {
        TradeBinderCallback callback = new TradeBinderCallback(this, controller);
        repo.loadOrderListTrading(callback);
    }

    public void setTrade(List<BaseOrder> trade) {
        this.trade = trade;
    }

    public void setRecent(List<BaseOrder> recent) {
        this.courier.setRecentOrders(recent);
    }

    public void setHistory(List<BaseOrder> history) {
        this.courier.setHistoryOfOrders(history);
    }
}