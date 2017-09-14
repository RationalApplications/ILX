package xyz.ratapp.ilx.data.repository.callbacks;

import com.google.gson.JsonObject;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.data.repository.DataRepository;

/**
 * Created by timtim on 12/09/2017.
 */

public class StateChangeCallback extends RepositoryCallback {

    private boolean workStatus;

    public StateChangeCallback(DataRepository repo,
                               BinderCallback binder,
                               boolean workStatus) {
        super(repo, binder);
        this.workStatus = workStatus;
    }

    @Override
    void onSuccess(JsonObject response) {
        boolean workStatus = response.get("work_status").
                getAsInt() == 1;

        if(this.workStatus == workStatus &&
                binder != null) {
            binder.fail(R.string.change_status_error);
        }
        else {
            repo.setData(this, workStatus);
            binder.success();
        }
    }

    @Override
    void onFailed(JsonObject response) {
        String error = response.get("errormessage").getAsString();
        binder.fail(error);
    }
}
