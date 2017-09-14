package xyz.ratapp.ilx.controllers.data.callbacks;

import xyz.ratapp.ilx.controllers.data.DataBinder;

/**
 * Created by timtim on 11/09/2017.
 */

public abstract class BinderCallback {

    protected DataBinder binder;

    public BinderCallback(DataBinder binder) {
        this.binder = binder;
    }

    public abstract void success(Object... data);

    public abstract void fail(Object... data);

}
