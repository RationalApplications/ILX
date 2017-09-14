package xyz.ratapp.ilx.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.OrdersBinderCallback;
import xyz.ratapp.ilx.controllers.data.callbacks.TradeBinderCallback;
import xyz.ratapp.ilx.data.dao.app.Button;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;
import xyz.ratapp.ilx.data.dao.orders.Order;
import xyz.ratapp.ilx.data.dao.orders.Request;
import xyz.ratapp.ilx.data.dao.users.Courier;
import xyz.ratapp.ilx.data.dao.users.CourierLocation;
import xyz.ratapp.ilx.data.repository.callbacks.AuthCallback;
import xyz.ratapp.ilx.data.repository.callbacks.HistoryListCallback;
import xyz.ratapp.ilx.data.repository.callbacks.OrderListCallback;
import xyz.ratapp.ilx.data.repository.callbacks.RepositoryCallback;
import xyz.ratapp.ilx.data.repository.callbacks.StateChangeCallback;
import xyz.ratapp.ilx.data.repository.callbacks.TradeListCallback;
import xyz.ratapp.ilx.data.repository.managers.ManageableRepository;
import xyz.ratapp.ilx.data.retrofit.API;

/**
 * Created by timtim on 07/09/2017.
 */

public class DataRepository extends ManageableRepository {

    private Courier courier;
    private List<Request> trade;
    private List<Request> history;
    private List<Order> recent;
    private Context context;

    public DataRepository(Context context) {
        super(context);
        this.context = context;
    }


    public void auth(BinderCallback callback) {
        AuthCallback auth =
                new AuthCallback(this, callback, domainName);

        auth(auth);
    }

    public void auth(BinderCallback callback,
                     String code) {
        AuthCallback auth =
                new AuthCallback(this, callback);

        auth(auth, code);
    }

    public void loadAllData(BinderCallback callback) {
        trade = null;
        recent = null;
        history = null;

        loadAllData(new TradeListCallback(this, callback),
                new OrderListCallback(this, callback),
                new HistoryListCallback(this, callback));
    }

    public void setWorkStatus(BinderCallback callback, boolean status) {
        setWorkStatus(new StateChangeCallback(this, callback, courier.getWorkStatus()), status);
    }

    //TODO: WTF?
    public void registerGcm() {

    }

    public void exit() {
        setWorkStatus(new StateChangeCallback(this, null, false), false);
        savePrefs(context);
    }

    public void setData(RepositoryCallback callback,
                        Object... data) {

        if(callback instanceof AuthCallback &&
                data[0] instanceof String &&
                data[1] instanceof String) {
            //auth_access_code
            this.domainName = (String) data[0];
            this.sessionId = (String) data[1];
        }
        else if(callback instanceof AuthCallback &&
                data[0] instanceof Courier) {
            //auth
            this.courier = (Courier) data[0];
        }
        else if(callback instanceof StateChangeCallback &&
                data[0] instanceof Boolean) {
            courier.setWorkStatus((Boolean) data[0]);
        }
        else if(callback instanceof TradeListCallback) {
            this.trade = (List<Request>) data[0];
            checkAllDataLoaded((BinderCallback) data[1]);
        }
        else if(callback instanceof OrderListCallback) {
            this.recent = (List<Order>) data[0];
            checkAllDataLoaded((BinderCallback) data[1]);
        }
        else if(callback instanceof HistoryListCallback) {
            this.history = (List<Request>) data[0];
            checkAllDataLoaded((BinderCallback) data[1]);
        }
    }

    private void checkAllDataLoaded(BinderCallback binderCallback) {
        if(trade != null &&
                recent != null &&
                history != null &&
                courier != null) {
            List<BaseOrder> history =
                    new ArrayList<BaseOrder>(this.history);
            List<BaseOrder> recent =
                    new ArrayList<BaseOrder>(this.recent);
            List<BaseOrder> trade =
                    new ArrayList<BaseOrder>(this.trade);

            courier.setRecentOrders(recent);
            courier.setHistoryOfOrders(history);
            binderCallback.success(courier, trade);
        }
    }

    public void loadOrderListTrading(BinderCallback callback) {
        //TODO: HERE
    }

    public void loadOrderList(BinderCallback callback) {

    }

    public void loadOrderListHistory(BinderCallback callback) {

    }
}
