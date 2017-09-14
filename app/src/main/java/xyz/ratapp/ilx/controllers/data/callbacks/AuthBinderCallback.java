package xyz.ratapp.ilx.controllers.data.callbacks;

import xyz.ratapp.ilx.controllers.data.DataBinder;
import xyz.ratapp.ilx.controllers.launch.LaunchController;
import xyz.ratapp.ilx.data.dao.app.AppStrings;

/**
 * Created by timtim on 13/09/2017.
 */

public class AuthBinderCallback extends BinderCallback {

    private LaunchController controller;

    public AuthBinderCallback(DataBinder dataBinder,
                              LaunchController controller) {
        super(dataBinder);
        this.controller = controller;
    }

    @Override
    public void success(Object... data) {
        if(data.length == 1 &&
                data[0] instanceof AppStrings) {

            binder.setStrings((AppStrings) data[0]);
            controller.next();
        }
    }

    @Override
    public void fail(Object... data) {
        controller.onAuthFailed();
    }
}
