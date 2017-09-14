package xyz.ratapp.ilx.data.repository.callbacks;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.data.repository.DataRepository;

/**
 * Created by timtim on 11/09/2017.
 */

public abstract class RepositoryCallback implements Callback<JsonObject> {

    DataRepository repo;
    BinderCallback binder;

    RepositoryCallback(DataRepository repo,
                              BinderCallback binder) {
        this.repo = repo;
        this.binder = binder;
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        try {
            JsonObject obj = response.body().getAsJsonObject("response");
            int status = obj.get("status").getAsInt();

            if(status == 1) {
                onSuccess(obj);
            }
            else {
                onFailed(obj);
            }
        }
        catch (Exception e) {
            if(binder != null) {
                binder.fail();
            }
        }
    }

    abstract void onSuccess(JsonObject response);

    abstract void onFailed(JsonObject response);

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        if(binder != null) {
            binder.fail();
        }
    }

}
