package xyz.ratapp.ilx.data.repository.callbacks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.data.dao.orders.Request;
import xyz.ratapp.ilx.data.repository.DataRepository;

/**
 * Created by timtim on 11/09/2017.
 */

public class HistoryListCallback extends RepositoryCallback {

    public HistoryListCallback(DataRepository repo,
                               BinderCallback binder) {
        super(repo, binder);
    }

    @Override
    void onSuccess(JsonObject obj) {
        ArrayList<Request> history = new ArrayList<>();

        if(obj.has("order_list_history")) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Request>>() {
            }.getType();
            JsonObject orderList = obj.getAsJsonObject("order_list_history");
            Map<String, Request> orderMap = gson.fromJson(orderList, type);

            history.addAll(orderMap.values());
        }

        repo.setData(this, history, binder);
    }

    @Override
    void onFailed(JsonObject obj) {
        String error = obj.get("errormessage").getAsString();
        binder.fail(error);
    }
}
