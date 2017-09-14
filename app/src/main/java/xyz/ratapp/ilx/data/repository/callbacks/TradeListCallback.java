package xyz.ratapp.ilx.data.repository.callbacks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.ratapp.ilx.controllers.data.callbacks.BinderCallback;
import xyz.ratapp.ilx.data.dao.orders.Request;
import xyz.ratapp.ilx.data.repository.DataRepository;

/**
 * Created by timtim on 11/09/2017.
 */

public class TradeListCallback extends RepositoryCallback {

    public TradeListCallback(DataRepository repo,
                             BinderCallback binder) {
        super(repo, binder);
    }

    @Override
    void onSuccess(JsonObject obj) {
        List<Request> stock = new ArrayList<>();

        if(obj.has("order_list")) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Request>>() {
            }.getType();
            JsonObject orderList = obj.getAsJsonObject("order_list");
            Map<String, Request> orderMap = gson.fromJson(orderList, type);

            stock.addAll(orderMap.values());
        }

        repo.setData(this, stock, binder);
    }

    @Override
    void onFailed(JsonObject obj) {
        String error = obj.get("errormessage").getAsString();
        binder.fail(error);
    }
}
