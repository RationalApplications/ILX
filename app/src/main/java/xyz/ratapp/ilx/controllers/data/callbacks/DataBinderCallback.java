package xyz.ratapp.ilx.controllers.data.callbacks;

import java.util.List;

import xyz.ratapp.ilx.controllers.data.DataBinder;
import xyz.ratapp.ilx.controllers.main.MainController;
import xyz.ratapp.ilx.data.dao.orders.BaseOrder;
import xyz.ratapp.ilx.data.dao.users.Courier;

/**
 * Created by timtim on 13/09/2017.
 */

public class DataBinderCallback extends BinderCallback {

    private MainController controller;

    public DataBinderCallback(DataBinder binder,
                              MainController controller) {
        super(binder);
        this.controller = controller;
    }

    @Override
    public void success(Object... data) {
        if(data.length == 2 &&
                data[0] instanceof Courier &&
                data[1] instanceof List) {

            Courier courier = (Courier) data[0];
            List<BaseOrder> trade = (List<BaseOrder>) data[1];

            binder.setData(courier, trade);
            controller.setData(courier);
            controller.bindScreens();
        }
    }

    @Override
    public void fail(Object... data) {

    }
}
