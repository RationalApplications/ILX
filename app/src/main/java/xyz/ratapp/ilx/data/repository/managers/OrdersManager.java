package xyz.ratapp.ilx.data.repository.managers;

import java.util.List;

import xyz.ratapp.ilx.data.dao.orders.BaseOrder;
import xyz.ratapp.ilx.data.repository.callbacks.HistoryListCallback;
import xyz.ratapp.ilx.data.repository.callbacks.OrderListCallback;
import xyz.ratapp.ilx.data.repository.callbacks.TradeListCallback;
import xyz.ratapp.ilx.data.retrofit.API;

/**
 * Created by timtim on 05/09/2017.
 */

class OrdersManager {

    private static volatile OrdersManager instance;
    private ManageableRepository repo;
    private List<BaseOrder> recent;
    private List<BaseOrder> trade, history;
    private String sessionId;
    private API apiUser;

    private OrdersManager() {

    }

    static OrdersManager getInstance(ManageableRepository repo,
                                     String sessionId) {
        OrdersManager localInstance = instance;

        if (localInstance == null) {
            synchronized (OrdersManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance =
                            new OrdersManager();
                }
                instance.repo = repo;
                instance.apiUser = repo.getApiUser();
                instance.sessionId = sessionId;
            }
        }

        return localInstance;
    }

    /**
     * Load list of orders for current courier
     */
    void orderList(OrderListCallback callback) {
        apiUser.orderList(sessionId).enqueue(callback);
    }

    /**
     * Load list of orders that current courier have in history
     */
    void orderListHistory(HistoryListCallback callback) {
        apiUser.orderListHistory(sessionId).enqueue(callback);
    }

    /**
     * Method that load list that contain all orders
     * from trade
     */
    public void orderListTrading(TradeListCallback callback) {
        apiUser.orderListTrading(sessionId).enqueue(callback);
    }
}
