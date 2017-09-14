package xyz.ratapp.ilx.controllers.data.callbacks;

import java.util.List;

import xyz.ratapp.ilx.controllers.data.DataBinder;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.controllers.routing.Screens;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;

/**
 * Created by timtim on 14/09/2017.
 */

public class HistoryBinderCallback extends BinderCallback {

    private MainController controller;

    public HistoryBinderCallback(DataBinder binder,
                                MainController controller) {
        super(binder);
        this.controller = controller;
    }

    @Override
    public void success(Object... data) {
        if(data.length == 1 &&
                data[0] instanceof List) {

            List<BaseOrder> history = (List<BaseOrder>) data[1];

            binder.setHistory(history);
            controller.bindScreen(Screens.HISTORY);
        }
    }

    @Override
    public void fail(Object... data) {

    }
}
