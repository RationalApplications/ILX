package xyz.ratapp.ilx.controllers.data.callbacks;

import xyz.ratapp.ilx.controllers.data.DataBinder;
import xyz.ratapp.ilx.controllers.main.MainController;

/**
 * Created by timtim on 14/09/2017.
 */

public class StatusChangeBinderCallback extends BinderCallback {

    private MainController controller;

    public StatusChangeBinderCallback(DataBinder binder,
                                      MainController controller) {
        super(binder);
        this.controller = controller;
    }

    @Override
    public void success(Object... data) {
        //Что делать?
    }

    @Override
    public void fail(Object... data) {
        //Достать строку
    }
}
