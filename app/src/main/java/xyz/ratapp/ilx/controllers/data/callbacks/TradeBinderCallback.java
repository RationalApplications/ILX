package xyz.ratapp.ilx.controllers.data.callbacks;

import java.util.List;

import xyz.ratapp.ilx.controllers.data.DataBinder;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.controllers.routing.Screens;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;
import xyz.ratapp.ilx.data.dao.users.Courier;

/**
 * Created by timtim on 14/09/2017.
 */

public class TradeBinderCallback extends BinderCallback {

    private MainController controller;

    public TradeBinderCallback(DataBinder binder,
                                MainController controller) {
        super(binder);
        this.controller = controller;
    }

    @Override
    public void success(Object... data) {
        if(data.length == 1 &&
                data[0] instanceof List) {

            List<BaseOrder> trade = (List<BaseOrder>) data[1];

            binder.setTrade(trade);
            controller.bindScreen(Screens.STOCK);
        }
    }

    @Override
    public void fail(Object... data) {

    }
}
